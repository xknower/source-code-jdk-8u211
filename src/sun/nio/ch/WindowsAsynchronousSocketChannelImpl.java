/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.BufferOverflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.AlreadyConnectedException;
/*     */ import java.nio.channels.AsynchronousCloseException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.CompletionHandler;
/*     */ import java.nio.channels.ConnectionPendingException;
/*     */ import java.nio.channels.InterruptedByTimeoutException;
/*     */ import java.nio.channels.ShutdownChannelGroupException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsAsynchronousSocketChannelImpl
/*     */   extends AsynchronousSocketChannelImpl
/*     */   implements Iocp.OverlappedChannel
/*     */ {
/*  46 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*  47 */   private static int addressSize = unsafe.addressSize();
/*     */   
/*     */   private static int dependsArch(int paramInt1, int paramInt2) {
/*  50 */     return (addressSize == 4) ? paramInt1 : paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static final int SIZEOF_WSABUF = dependsArch(8, 16);
/*     */   private static final int OFFSETOF_LEN = 0;
/*  61 */   private static final int OFFSETOF_BUF = dependsArch(4, 8);
/*     */ 
/*     */   
/*     */   private static final int MAX_WSABUF = 16;
/*     */   
/*  66 */   private static final int SIZEOF_WSABUFARRAY = 16 * SIZEOF_WSABUF;
/*     */ 
/*     */ 
/*     */   
/*     */   final long handle;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Iocp iocp;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int completionKey;
/*     */ 
/*     */   
/*     */   private final PendingIoCache ioCache;
/*     */ 
/*     */   
/*     */   private final long readBufferArray;
/*     */ 
/*     */   
/*     */   private final long writeBufferArray;
/*     */ 
/*     */ 
/*     */   
/*     */   WindowsAsynchronousSocketChannelImpl(Iocp paramIocp, boolean paramBoolean) throws IOException {
/*  92 */     super(paramIocp);
/*     */ 
/*     */     
/*  95 */     long l = IOUtil.fdVal(this.fd);
/*  96 */     int i = 0;
/*     */     try {
/*  98 */       i = paramIocp.associate(this, l);
/*  99 */     } catch (ShutdownChannelGroupException shutdownChannelGroupException) {
/* 100 */       if (paramBoolean) {
/* 101 */         closesocket0(l);
/* 102 */         throw shutdownChannelGroupException;
/*     */       } 
/* 104 */     } catch (IOException iOException) {
/* 105 */       closesocket0(l);
/* 106 */       throw iOException;
/*     */     } 
/*     */     
/* 109 */     this.handle = l;
/* 110 */     this.iocp = paramIocp;
/* 111 */     this.completionKey = i;
/* 112 */     this.ioCache = new PendingIoCache();
/*     */ 
/*     */     
/* 115 */     this.readBufferArray = unsafe.allocateMemory(SIZEOF_WSABUFARRAY);
/* 116 */     this.writeBufferArray = unsafe.allocateMemory(SIZEOF_WSABUFARRAY);
/*     */   }
/*     */   
/*     */   WindowsAsynchronousSocketChannelImpl(Iocp paramIocp) throws IOException {
/* 120 */     this(paramIocp, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public AsynchronousChannelGroupImpl group() {
/* 125 */     return this.iocp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <V, A> PendingFuture<V, A> getByOverlapped(long paramLong) {
/* 133 */     return this.ioCache.remove(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   long handle() {
/* 138 */     return this.handle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setConnected(InetSocketAddress paramInetSocketAddress1, InetSocketAddress paramInetSocketAddress2) {
/* 146 */     synchronized (this.stateLock) {
/* 147 */       this.state = 2;
/* 148 */       this.localAddress = paramInetSocketAddress1;
/* 149 */       this.remoteAddress = paramInetSocketAddress2;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void implClose() throws IOException {
/* 156 */     closesocket0(this.handle);
/*     */ 
/*     */     
/* 159 */     this.ioCache.close();
/*     */ 
/*     */     
/* 162 */     unsafe.freeMemory(this.readBufferArray);
/* 163 */     unsafe.freeMemory(this.writeBufferArray);
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (this.completionKey != 0) {
/* 168 */       this.iocp.disassociate(this.completionKey);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onCancel(PendingFuture<?, ?> paramPendingFuture) {
/* 173 */     if (paramPendingFuture.getContext() instanceof ConnectTask)
/* 174 */       killConnect(); 
/* 175 */     if (paramPendingFuture.getContext() instanceof ReadTask)
/* 176 */       killReading(); 
/* 177 */     if (paramPendingFuture.getContext() instanceof WriteTask) {
/* 178 */       killWriting();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class ConnectTask<A>
/*     */     implements Runnable, Iocp.ResultHandler
/*     */   {
/*     */     private final InetSocketAddress remote;
/*     */     private final PendingFuture<Void, A> result;
/*     */     
/*     */     ConnectTask(InetSocketAddress param1InetSocketAddress, PendingFuture<Void, A> param1PendingFuture) {
/* 190 */       this.remote = param1InetSocketAddress;
/* 191 */       this.result = param1PendingFuture;
/*     */     }
/*     */     
/*     */     private void closeChannel() {
/*     */       try {
/* 196 */         WindowsAsynchronousSocketChannelImpl.this.close();
/* 197 */       } catch (IOException iOException) {}
/*     */     }
/*     */     
/*     */     private IOException toIOException(Throwable param1Throwable) {
/* 201 */       if (param1Throwable instanceof IOException) {
/* 202 */         if (param1Throwable instanceof ClosedChannelException)
/* 203 */           param1Throwable = new AsynchronousCloseException(); 
/* 204 */         return (IOException)param1Throwable;
/*     */       } 
/* 206 */       return new IOException(param1Throwable);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void afterConnect() throws IOException {
/* 213 */       WindowsAsynchronousSocketChannelImpl.updateConnectContext(WindowsAsynchronousSocketChannelImpl.this.handle);
/* 214 */       synchronized (WindowsAsynchronousSocketChannelImpl.this.stateLock) {
/* 215 */         WindowsAsynchronousSocketChannelImpl.this.state = 2;
/* 216 */         WindowsAsynchronousSocketChannelImpl.this.remoteAddress = this.remote;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 225 */       long l = 0L;
/* 226 */       Throwable throwable = null;
/*     */       try {
/* 228 */         WindowsAsynchronousSocketChannelImpl.this.begin();
/*     */ 
/*     */ 
/*     */         
/* 232 */         synchronized (this.result) {
/* 233 */           l = WindowsAsynchronousSocketChannelImpl.this.ioCache.add(this.result);
/*     */           
/* 235 */           int i = WindowsAsynchronousSocketChannelImpl.connect0(WindowsAsynchronousSocketChannelImpl.this.handle, Net.isIPv6Available(), this.remote.getAddress(), this.remote
/* 236 */               .getPort(), l);
/* 237 */           if (i == -2) {
/*     */             return;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 243 */           afterConnect();
/* 244 */           this.result.setResult(null);
/*     */         } 
/* 246 */       } catch (Throwable throwable1) {
/* 247 */         if (l != 0L)
/* 248 */           WindowsAsynchronousSocketChannelImpl.this.ioCache.remove(l); 
/* 249 */         throwable = throwable1;
/*     */       } finally {
/* 251 */         WindowsAsynchronousSocketChannelImpl.this.end();
/*     */       } 
/*     */       
/* 254 */       if (throwable != null) {
/* 255 */         closeChannel();
/* 256 */         this.result.setFailure(toIOException(throwable));
/*     */       } 
/* 258 */       Invoker.invoke(this.result);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void completed(int param1Int, boolean param1Boolean) {
/* 266 */       Throwable throwable = null;
/*     */       try {
/* 268 */         WindowsAsynchronousSocketChannelImpl.this.begin();
/* 269 */         afterConnect();
/* 270 */         this.result.setResult(null);
/* 271 */       } catch (Throwable throwable1) {
/*     */         
/* 273 */         throwable = throwable1;
/*     */       } finally {
/* 275 */         WindowsAsynchronousSocketChannelImpl.this.end();
/*     */       } 
/*     */ 
/*     */       
/* 279 */       if (throwable != null) {
/* 280 */         closeChannel();
/* 281 */         this.result.setFailure(toIOException(throwable));
/*     */       } 
/*     */       
/* 284 */       if (param1Boolean) {
/* 285 */         Invoker.invokeUnchecked(this.result);
/*     */       } else {
/* 287 */         Invoker.invoke(this.result);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void failed(int param1Int, IOException param1IOException) {
/* 296 */       if (WindowsAsynchronousSocketChannelImpl.this.isOpen()) {
/* 297 */         closeChannel();
/* 298 */         this.result.setFailure(param1IOException);
/*     */       } else {
/* 300 */         this.result.setFailure(new AsynchronousCloseException());
/*     */       } 
/* 302 */       Invoker.invoke(this.result);
/*     */     }
/*     */   }
/*     */   
/*     */   private void doPrivilegedBind(final SocketAddress sa) throws IOException {
/*     */     try {
/* 308 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
/*     */             public Void run() throws IOException {
/* 310 */               WindowsAsynchronousSocketChannelImpl.this.bind(sa);
/* 311 */               return null;
/*     */             }
/*     */           });
/* 314 */     } catch (PrivilegedActionException privilegedActionException) {
/* 315 */       throw (IOException)privilegedActionException.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <A> Future<Void> implConnect(SocketAddress paramSocketAddress, A paramA, CompletionHandler<Void, ? super A> paramCompletionHandler) {
/* 324 */     if (!isOpen()) {
/* 325 */       ClosedChannelException closedChannelException = new ClosedChannelException();
/* 326 */       if (paramCompletionHandler == null)
/* 327 */         return CompletedFuture.withFailure(closedChannelException); 
/* 328 */       Invoker.invoke(this, paramCompletionHandler, paramA, null, closedChannelException);
/* 329 */       return null;
/*     */     } 
/*     */     
/* 332 */     InetSocketAddress inetSocketAddress = Net.checkAddress(paramSocketAddress);
/*     */ 
/*     */     
/* 335 */     SecurityManager securityManager = System.getSecurityManager();
/* 336 */     if (securityManager != null) {
/* 337 */       securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());
/*     */     }
/*     */ 
/*     */     
/* 341 */     IOException iOException = null;
/* 342 */     synchronized (this.stateLock) {
/* 343 */       if (this.state == 2)
/* 344 */         throw new AlreadyConnectedException(); 
/* 345 */       if (this.state == 1)
/* 346 */         throw new ConnectionPendingException(); 
/* 347 */       if (this.localAddress == null) {
/*     */         try {
/* 349 */           InetSocketAddress inetSocketAddress1 = new InetSocketAddress(0);
/* 350 */           if (securityManager == null) {
/* 351 */             bind(inetSocketAddress1);
/*     */           } else {
/* 353 */             doPrivilegedBind(inetSocketAddress1);
/*     */           } 
/* 355 */         } catch (IOException iOException1) {
/* 356 */           iOException = iOException1;
/*     */         } 
/*     */       }
/* 359 */       if (iOException == null) {
/* 360 */         this.state = 1;
/*     */       }
/*     */     } 
/*     */     
/* 364 */     if (iOException != null) {
/*     */       try {
/* 366 */         close();
/* 367 */       } catch (IOException iOException1) {}
/* 368 */       if (paramCompletionHandler == null)
/* 369 */         return CompletedFuture.withFailure(iOException); 
/* 370 */       Invoker.invoke(this, paramCompletionHandler, paramA, null, iOException);
/* 371 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 375 */     PendingFuture<Void, A> pendingFuture = new PendingFuture<>(this, paramCompletionHandler, paramA);
/*     */     
/* 377 */     ConnectTask<A> connectTask = new ConnectTask<>(inetSocketAddress, pendingFuture);
/* 378 */     pendingFuture.setContext(connectTask);
/*     */ 
/*     */     
/* 381 */     if (Iocp.supportsThreadAgnosticIo()) {
/* 382 */       connectTask.run();
/*     */     } else {
/* 384 */       Invoker.invokeOnThreadInThreadPool(this, connectTask);
/*     */     } 
/* 386 */     return pendingFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class ReadTask<V, A>
/*     */     implements Runnable, Iocp.ResultHandler
/*     */   {
/*     */     private final ByteBuffer[] bufs;
/*     */     
/*     */     private final int numBufs;
/*     */     
/*     */     private final boolean scatteringRead;
/*     */     
/*     */     private final PendingFuture<V, A> result;
/*     */     
/*     */     private ByteBuffer[] shadow;
/*     */ 
/*     */     
/*     */     ReadTask(ByteBuffer[] param1ArrayOfByteBuffer, boolean param1Boolean, PendingFuture<V, A> param1PendingFuture) {
/* 406 */       this.bufs = param1ArrayOfByteBuffer;
/* 407 */       this.numBufs = (param1ArrayOfByteBuffer.length > 16) ? 16 : param1ArrayOfByteBuffer.length;
/* 408 */       this.scatteringRead = param1Boolean;
/* 409 */       this.result = param1PendingFuture;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void prepareBuffers() {
/* 417 */       this.shadow = new ByteBuffer[this.numBufs];
/* 418 */       long l = WindowsAsynchronousSocketChannelImpl.this.readBufferArray;
/* 419 */       for (byte b = 0; b < this.numBufs; b++) {
/* 420 */         long l1; ByteBuffer byteBuffer = this.bufs[b];
/* 421 */         int i = byteBuffer.position();
/* 422 */         int j = byteBuffer.limit();
/* 423 */         assert i <= j;
/* 424 */         boolean bool = (i <= j) ? (j - i) : false;
/*     */         
/* 426 */         if (!(byteBuffer instanceof DirectBuffer)) {
/*     */           
/* 428 */           ByteBuffer byteBuffer1 = Util.getTemporaryDirectBuffer(bool);
/* 429 */           this.shadow[b] = byteBuffer1;
/* 430 */           l1 = ((DirectBuffer)byteBuffer1).address();
/*     */         } else {
/* 432 */           this.shadow[b] = byteBuffer;
/* 433 */           l1 = ((DirectBuffer)byteBuffer).address() + i;
/*     */         } 
/* 435 */         WindowsAsynchronousSocketChannelImpl.unsafe.putAddress(l + WindowsAsynchronousSocketChannelImpl.OFFSETOF_BUF, l1);
/* 436 */         WindowsAsynchronousSocketChannelImpl.unsafe.putInt(l + 0L, bool);
/* 437 */         l += WindowsAsynchronousSocketChannelImpl.SIZEOF_WSABUF;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void updateBuffers(int param1Int) {
/*     */       byte b;
/* 446 */       for (b = 0; b < this.numBufs; b++) {
/* 447 */         ByteBuffer byteBuffer = this.shadow[b];
/* 448 */         int i = byteBuffer.position();
/* 449 */         int j = byteBuffer.remaining();
/* 450 */         if (param1Int >= j) {
/* 451 */           param1Int -= j;
/* 452 */           int k = i + j;
/*     */           try {
/* 454 */             byteBuffer.position(k);
/* 455 */           } catch (IllegalArgumentException illegalArgumentException) {}
/*     */         }
/*     */         else {
/*     */           
/* 459 */           if (param1Int > 0) {
/* 460 */             assert (i + param1Int) < 2147483647L;
/* 461 */             int k = i + param1Int;
/*     */             try {
/* 463 */               byteBuffer.position(k);
/* 464 */             } catch (IllegalArgumentException illegalArgumentException) {}
/*     */           } 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 473 */       for (b = 0; b < this.numBufs; b++) {
/* 474 */         if (!(this.bufs[b] instanceof DirectBuffer)) {
/* 475 */           this.shadow[b].flip();
/*     */           try {
/* 477 */             this.bufs[b].put(this.shadow[b]);
/* 478 */           } catch (BufferOverflowException bufferOverflowException) {}
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void releaseBuffers() {
/* 486 */       for (byte b = 0; b < this.numBufs; b++) {
/* 487 */         if (!(this.bufs[b] instanceof DirectBuffer)) {
/* 488 */           Util.releaseTemporaryDirectBuffer(this.shadow[b]);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 496 */       long l = 0L;
/* 497 */       boolean bool1 = false;
/* 498 */       boolean bool2 = false;
/*     */       
/*     */       try {
/* 501 */         WindowsAsynchronousSocketChannelImpl.this.begin();
/*     */ 
/*     */         
/* 504 */         prepareBuffers();
/* 505 */         bool1 = true;
/*     */ 
/*     */         
/* 508 */         l = WindowsAsynchronousSocketChannelImpl.this.ioCache.add(this.result);
/*     */ 
/*     */         
/* 511 */         int i = WindowsAsynchronousSocketChannelImpl.read0(WindowsAsynchronousSocketChannelImpl.this.handle, this.numBufs, WindowsAsynchronousSocketChannelImpl.this.readBufferArray, l);
/* 512 */         if (i == -2) {
/*     */           
/* 514 */           bool2 = true;
/*     */           return;
/*     */         } 
/* 517 */         if (i == -1) {
/*     */           
/* 519 */           WindowsAsynchronousSocketChannelImpl.this.enableReading();
/* 520 */           if (this.scatteringRead) {
/* 521 */             this.result.setResult((V)Long.valueOf(-1L));
/*     */           } else {
/* 523 */             this.result.setResult((V)Integer.valueOf(-1));
/*     */           } 
/*     */         } else {
/* 526 */           throw new InternalError("Read completed immediately");
/*     */         } 
/* 528 */       } catch (Throwable throwable) {
/*     */ 
/*     */         
/* 531 */         WindowsAsynchronousSocketChannelImpl.this.enableReading();
/* 532 */         if (throwable instanceof ClosedChannelException)
/* 533 */           throwable = new AsynchronousCloseException(); 
/* 534 */         if (!(throwable instanceof IOException))
/* 535 */           throwable = new IOException(throwable); 
/* 536 */         this.result.setFailure(throwable);
/*     */       } finally {
/*     */         
/* 539 */         if (!bool2) {
/* 540 */           if (l != 0L)
/* 541 */             WindowsAsynchronousSocketChannelImpl.this.ioCache.remove(l); 
/* 542 */           if (bool1)
/* 543 */             releaseBuffers(); 
/*     */         } 
/* 545 */         WindowsAsynchronousSocketChannelImpl.this.end();
/*     */       } 
/*     */ 
/*     */       
/* 549 */       Invoker.invoke(this.result);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void completed(int param1Int, boolean param1Boolean) {
/* 558 */       if (param1Int == 0) {
/* 559 */         param1Int = -1;
/*     */       } else {
/* 561 */         updateBuffers(param1Int);
/*     */       } 
/*     */ 
/*     */       
/* 565 */       releaseBuffers();
/*     */ 
/*     */       
/* 568 */       synchronized (this.result) {
/* 569 */         if (this.result.isDone())
/*     */           return; 
/* 571 */         WindowsAsynchronousSocketChannelImpl.this.enableReading();
/* 572 */         if (this.scatteringRead) {
/* 573 */           this.result.setResult((V)Long.valueOf(param1Int));
/*     */         } else {
/* 575 */           this.result.setResult((V)Integer.valueOf(param1Int));
/*     */         } 
/*     */       } 
/* 578 */       if (param1Boolean) {
/* 579 */         Invoker.invokeUnchecked(this.result);
/*     */       } else {
/* 581 */         Invoker.invoke(this.result);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void failed(int param1Int, IOException param1IOException) {
/* 588 */       releaseBuffers();
/*     */ 
/*     */       
/* 591 */       if (!WindowsAsynchronousSocketChannelImpl.this.isOpen()) {
/* 592 */         param1IOException = new AsynchronousCloseException();
/*     */       }
/* 594 */       synchronized (this.result) {
/* 595 */         if (this.result.isDone())
/*     */           return; 
/* 597 */         WindowsAsynchronousSocketChannelImpl.this.enableReading();
/* 598 */         this.result.setFailure(param1IOException);
/*     */       } 
/* 600 */       Invoker.invoke(this.result);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void timeout() {
/* 608 */       synchronized (this.result) {
/* 609 */         if (this.result.isDone()) {
/*     */           return;
/*     */         }
/*     */         
/* 613 */         WindowsAsynchronousSocketChannelImpl.this.enableReading(true);
/* 614 */         this.result.setFailure(new InterruptedByTimeoutException());
/*     */       } 
/*     */ 
/*     */       
/* 618 */       Invoker.invoke(this.result);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <V extends Number, A> Future<V> implRead(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler) {
/*     */     ByteBuffer[] arrayOfByteBuffer;
/* 632 */     PendingFuture<V, A> pendingFuture = new PendingFuture<>(this, paramCompletionHandler, paramA);
/*     */ 
/*     */     
/* 635 */     if (paramBoolean) {
/* 636 */       arrayOfByteBuffer = paramArrayOfByteBuffer;
/*     */     } else {
/* 638 */       arrayOfByteBuffer = new ByteBuffer[1];
/* 639 */       arrayOfByteBuffer[0] = paramByteBuffer;
/*     */     } 
/* 641 */     final ReadTask<V, A> readTask = new ReadTask<>(arrayOfByteBuffer, paramBoolean, pendingFuture);
/*     */     
/* 643 */     pendingFuture.setContext(readTask);
/*     */ 
/*     */     
/* 646 */     if (paramLong > 0L) {
/* 647 */       Future<?> future = this.iocp.schedule(new Runnable() {
/*     */             public void run() {
/* 649 */               readTask.timeout();
/*     */             }
/*     */           },  paramLong, paramTimeUnit);
/* 652 */       pendingFuture.setTimeoutTask(future);
/*     */     } 
/*     */ 
/*     */     
/* 656 */     if (Iocp.supportsThreadAgnosticIo()) {
/* 657 */       readTask.run();
/*     */     } else {
/* 659 */       Invoker.invokeOnThreadInThreadPool(this, readTask);
/*     */     } 
/* 661 */     return pendingFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class WriteTask<V, A>
/*     */     implements Runnable, Iocp.ResultHandler
/*     */   {
/*     */     private final ByteBuffer[] bufs;
/*     */     
/*     */     private final int numBufs;
/*     */     
/*     */     private final boolean gatheringWrite;
/*     */     
/*     */     private final PendingFuture<V, A> result;
/*     */     
/*     */     private ByteBuffer[] shadow;
/*     */ 
/*     */     
/*     */     WriteTask(ByteBuffer[] param1ArrayOfByteBuffer, boolean param1Boolean, PendingFuture<V, A> param1PendingFuture) {
/* 681 */       this.bufs = param1ArrayOfByteBuffer;
/* 682 */       this.numBufs = (param1ArrayOfByteBuffer.length > 16) ? 16 : param1ArrayOfByteBuffer.length;
/* 683 */       this.gatheringWrite = param1Boolean;
/* 684 */       this.result = param1PendingFuture;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void prepareBuffers() {
/* 692 */       this.shadow = new ByteBuffer[this.numBufs];
/* 693 */       long l = WindowsAsynchronousSocketChannelImpl.this.writeBufferArray;
/* 694 */       for (byte b = 0; b < this.numBufs; b++) {
/* 695 */         long l1; ByteBuffer byteBuffer = this.bufs[b];
/* 696 */         int i = byteBuffer.position();
/* 697 */         int j = byteBuffer.limit();
/* 698 */         assert i <= j;
/* 699 */         boolean bool = (i <= j) ? (j - i) : false;
/*     */         
/* 701 */         if (!(byteBuffer instanceof DirectBuffer)) {
/*     */           
/* 703 */           ByteBuffer byteBuffer1 = Util.getTemporaryDirectBuffer(bool);
/* 704 */           byteBuffer1.put(byteBuffer);
/* 705 */           byteBuffer1.flip();
/* 706 */           byteBuffer.position(i);
/* 707 */           this.shadow[b] = byteBuffer1;
/* 708 */           l1 = ((DirectBuffer)byteBuffer1).address();
/*     */         } else {
/* 710 */           this.shadow[b] = byteBuffer;
/* 711 */           l1 = ((DirectBuffer)byteBuffer).address() + i;
/*     */         } 
/* 713 */         WindowsAsynchronousSocketChannelImpl.unsafe.putAddress(l + WindowsAsynchronousSocketChannelImpl.OFFSETOF_BUF, l1);
/* 714 */         WindowsAsynchronousSocketChannelImpl.unsafe.putInt(l + 0L, bool);
/* 715 */         l += WindowsAsynchronousSocketChannelImpl.SIZEOF_WSABUF;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void updateBuffers(int param1Int) {
/* 725 */       for (byte b = 0; b < this.numBufs; b++) {
/* 726 */         ByteBuffer byteBuffer = this.bufs[b];
/* 727 */         int i = byteBuffer.position();
/* 728 */         int j = byteBuffer.limit();
/* 729 */         int k = (i <= j) ? (j - i) : j;
/* 730 */         if (param1Int >= k) {
/* 731 */           param1Int -= k;
/* 732 */           int m = i + k;
/*     */           try {
/* 734 */             byteBuffer.position(m);
/* 735 */           } catch (IllegalArgumentException illegalArgumentException) {}
/*     */         }
/*     */         else {
/*     */           
/* 739 */           if (param1Int > 0) {
/* 740 */             assert (i + param1Int) < 2147483647L;
/* 741 */             int m = i + param1Int;
/*     */             try {
/* 743 */               byteBuffer.position(m);
/* 744 */             } catch (IllegalArgumentException illegalArgumentException) {}
/*     */           } 
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void releaseBuffers() {
/* 754 */       for (byte b = 0; b < this.numBufs; b++) {
/* 755 */         if (!(this.bufs[b] instanceof DirectBuffer)) {
/* 756 */           Util.releaseTemporaryDirectBuffer(this.shadow[b]);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 764 */       long l = 0L;
/* 765 */       boolean bool1 = false;
/* 766 */       boolean bool2 = false;
/* 767 */       boolean bool3 = false;
/*     */       
/*     */       try {
/* 770 */         WindowsAsynchronousSocketChannelImpl.this.begin();
/*     */ 
/*     */         
/* 773 */         prepareBuffers();
/* 774 */         bool1 = true;
/*     */ 
/*     */         
/* 777 */         l = WindowsAsynchronousSocketChannelImpl.this.ioCache.add(this.result);
/* 778 */         int i = WindowsAsynchronousSocketChannelImpl.write0(WindowsAsynchronousSocketChannelImpl.this.handle, this.numBufs, WindowsAsynchronousSocketChannelImpl.this.writeBufferArray, l);
/* 779 */         if (i == -2) {
/*     */           
/* 781 */           bool2 = true;
/*     */           return;
/*     */         } 
/* 784 */         if (i == -1) {
/*     */           
/* 786 */           bool3 = true;
/* 787 */           throw new ClosedChannelException();
/*     */         } 
/*     */         
/* 790 */         throw new InternalError("Write completed immediately");
/* 791 */       } catch (Throwable throwable) {
/*     */         
/* 793 */         WindowsAsynchronousSocketChannelImpl.this.enableWriting();
/* 794 */         if (!bool3 && throwable instanceof ClosedChannelException)
/* 795 */           throwable = new AsynchronousCloseException(); 
/* 796 */         if (!(throwable instanceof IOException))
/* 797 */           throwable = new IOException(throwable); 
/* 798 */         this.result.setFailure(throwable);
/*     */       } finally {
/*     */         
/* 801 */         if (!bool2) {
/* 802 */           if (l != 0L)
/* 803 */             WindowsAsynchronousSocketChannelImpl.this.ioCache.remove(l); 
/* 804 */           if (bool1)
/* 805 */             releaseBuffers(); 
/*     */         } 
/* 807 */         WindowsAsynchronousSocketChannelImpl.this.end();
/*     */       } 
/*     */ 
/*     */       
/* 811 */       Invoker.invoke(this.result);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void completed(int param1Int, boolean param1Boolean) {
/* 820 */       updateBuffers(param1Int);
/*     */ 
/*     */       
/* 823 */       releaseBuffers();
/*     */ 
/*     */       
/* 826 */       synchronized (this.result) {
/* 827 */         if (this.result.isDone())
/*     */           return; 
/* 829 */         WindowsAsynchronousSocketChannelImpl.this.enableWriting();
/* 830 */         if (this.gatheringWrite) {
/* 831 */           this.result.setResult((V)Long.valueOf(param1Int));
/*     */         } else {
/* 833 */           this.result.setResult((V)Integer.valueOf(param1Int));
/*     */         } 
/*     */       } 
/* 836 */       if (param1Boolean) {
/* 837 */         Invoker.invokeUnchecked(this.result);
/*     */       } else {
/* 839 */         Invoker.invoke(this.result);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void failed(int param1Int, IOException param1IOException) {
/* 846 */       releaseBuffers();
/*     */ 
/*     */       
/* 849 */       if (!WindowsAsynchronousSocketChannelImpl.this.isOpen()) {
/* 850 */         param1IOException = new AsynchronousCloseException();
/*     */       }
/* 852 */       synchronized (this.result) {
/* 853 */         if (this.result.isDone())
/*     */           return; 
/* 855 */         WindowsAsynchronousSocketChannelImpl.this.enableWriting();
/* 856 */         this.result.setFailure(param1IOException);
/*     */       } 
/* 858 */       Invoker.invoke(this.result);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void timeout() {
/* 866 */       synchronized (this.result) {
/* 867 */         if (this.result.isDone()) {
/*     */           return;
/*     */         }
/*     */         
/* 871 */         WindowsAsynchronousSocketChannelImpl.this.enableWriting(true);
/* 872 */         this.result.setFailure(new InterruptedByTimeoutException());
/*     */       } 
/*     */ 
/*     */       
/* 876 */       Invoker.invoke(this.result);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <V extends Number, A> Future<V> implWrite(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler) {
/*     */     ByteBuffer[] arrayOfByteBuffer;
/* 890 */     PendingFuture<V, A> pendingFuture = new PendingFuture<>(this, paramCompletionHandler, paramA);
/*     */ 
/*     */     
/* 893 */     if (paramBoolean) {
/* 894 */       arrayOfByteBuffer = paramArrayOfByteBuffer;
/*     */     } else {
/* 896 */       arrayOfByteBuffer = new ByteBuffer[1];
/* 897 */       arrayOfByteBuffer[0] = paramByteBuffer;
/*     */     } 
/* 899 */     final WriteTask<V, A> writeTask = new WriteTask<>(arrayOfByteBuffer, paramBoolean, pendingFuture);
/*     */     
/* 901 */     pendingFuture.setContext(writeTask);
/*     */ 
/*     */     
/* 904 */     if (paramLong > 0L) {
/* 905 */       Future<?> future = this.iocp.schedule(new Runnable() {
/*     */             public void run() {
/* 907 */               writeTask.timeout();
/*     */             }
/*     */           },  paramLong, paramTimeUnit);
/* 910 */       pendingFuture.setTimeoutTask(future);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 915 */     if (Iocp.supportsThreadAgnosticIo()) {
/* 916 */       writeTask.run();
/*     */     } else {
/* 918 */       Invoker.invokeOnThreadInThreadPool(this, writeTask);
/*     */     } 
/* 920 */     return pendingFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 943 */     IOUtil.load();
/* 944 */     initIDs();
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private static native int connect0(long paramLong1, boolean paramBoolean, InetAddress paramInetAddress, int paramInt, long paramLong2) throws IOException;
/*     */   
/*     */   private static native void updateConnectContext(long paramLong) throws IOException;
/*     */   
/*     */   private static native int read0(long paramLong1, int paramInt, long paramLong2, long paramLong3) throws IOException;
/*     */   
/*     */   private static native int write0(long paramLong1, int paramInt, long paramLong2, long paramLong3) throws IOException;
/*     */   
/*     */   private static native void shutdown0(long paramLong, int paramInt) throws IOException;
/*     */   
/*     */   private static native void closesocket0(long paramLong) throws IOException;
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\WindowsAsynchronousSocketChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */