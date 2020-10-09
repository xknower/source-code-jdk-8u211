/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.BufferOverflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.AsynchronousCloseException;
/*     */ import java.nio.channels.AsynchronousFileChannel;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.CompletionHandler;
/*     */ import java.nio.channels.FileLock;
/*     */ import java.nio.channels.NonReadableChannelException;
/*     */ import java.nio.channels.NonWritableChannelException;
/*     */ import java.util.concurrent.Future;
/*     */ import sun.misc.JavaIOFileDescriptorAccess;
/*     */ import sun.misc.SharedSecrets;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsAsynchronousFileChannelImpl
/*     */   extends AsynchronousFileChannelImpl
/*     */   implements Iocp.OverlappedChannel, Groupable
/*     */ {
/*  46 */   private static final JavaIOFileDescriptorAccess fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */   
/*     */   private static final int ERROR_HANDLE_EOF = 38;
/*     */ 
/*     */   
/*     */   private static class DefaultIocpHolder
/*     */   {
/*  53 */     static final Iocp defaultIocp = defaultIocp();
/*     */     private static Iocp defaultIocp() {
/*     */       try {
/*  56 */         return (new Iocp(null, ThreadPool.createDefault())).start();
/*  57 */       } catch (IOException iOException) {
/*  58 */         throw new InternalError(iOException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  64 */   private static final FileDispatcher nd = new FileDispatcherImpl();
/*     */ 
/*     */   
/*     */   private final long handle;
/*     */ 
/*     */   
/*     */   private final int completionKey;
/*     */ 
/*     */   
/*     */   private final Iocp iocp;
/*     */ 
/*     */   
/*     */   private final boolean isDefaultIocp;
/*     */ 
/*     */   
/*     */   private final PendingIoCache ioCache;
/*     */ 
/*     */   
/*     */   static final int NO_LOCK = -1;
/*     */   
/*     */   static final int LOCKED = 0;
/*     */ 
/*     */   
/*     */   private WindowsAsynchronousFileChannelImpl(FileDescriptor paramFileDescriptor, boolean paramBoolean1, boolean paramBoolean2, Iocp paramIocp, boolean paramBoolean3) throws IOException {
/*  88 */     super(paramFileDescriptor, paramBoolean1, paramBoolean2, paramIocp.executor());
/*  89 */     this.handle = fdAccess.getHandle(paramFileDescriptor);
/*  90 */     this.iocp = paramIocp;
/*  91 */     this.isDefaultIocp = paramBoolean3;
/*  92 */     this.ioCache = new PendingIoCache();
/*  93 */     this.completionKey = paramIocp.associate(this, this.handle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AsynchronousFileChannel open(FileDescriptor paramFileDescriptor, boolean paramBoolean1, boolean paramBoolean2, ThreadPool paramThreadPool) throws IOException {
/*     */     Iocp iocp;
/*     */     boolean bool;
/* 104 */     if (paramThreadPool == null) {
/* 105 */       iocp = DefaultIocpHolder.defaultIocp;
/* 106 */       bool = true;
/*     */     } else {
/* 108 */       iocp = (new Iocp(null, paramThreadPool)).start();
/* 109 */       bool = false;
/*     */     } 
/*     */     try {
/* 112 */       return new WindowsAsynchronousFileChannelImpl(paramFileDescriptor, paramBoolean1, paramBoolean2, iocp, bool);
/*     */     }
/* 114 */     catch (IOException iOException) {
/*     */       
/* 116 */       if (!bool)
/* 117 */         iocp.implClose(); 
/* 118 */       throw iOException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <V, A> PendingFuture<V, A> getByOverlapped(long paramLong) {
/* 124 */     return this.ioCache.remove(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 129 */     this.closeLock.writeLock().lock();
/*     */     try {
/* 131 */       if (this.closed)
/*     */         return; 
/* 133 */       this.closed = true;
/*     */     } finally {
/* 135 */       this.closeLock.writeLock().unlock();
/*     */     } 
/*     */ 
/*     */     
/* 139 */     invalidateAllLocks();
/*     */ 
/*     */     
/* 142 */     close0(this.handle);
/*     */ 
/*     */     
/* 145 */     this.ioCache.close();
/*     */ 
/*     */     
/* 148 */     this.iocp.disassociate(this.completionKey);
/*     */ 
/*     */     
/* 151 */     if (!this.isDefaultIocp) {
/* 152 */       this.iocp.detachFromThreadPool();
/*     */     }
/*     */   }
/*     */   
/*     */   public AsynchronousChannelGroupImpl group() {
/* 157 */     return this.iocp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IOException toIOException(Throwable paramThrowable) {
/* 164 */     if (paramThrowable instanceof IOException) {
/* 165 */       if (paramThrowable instanceof ClosedChannelException)
/* 166 */         paramThrowable = new AsynchronousCloseException(); 
/* 167 */       return (IOException)paramThrowable;
/*     */     } 
/* 169 */     return new IOException(paramThrowable);
/*     */   }
/*     */ 
/*     */   
/*     */   public long size() throws IOException {
/*     */     try {
/* 175 */       begin();
/* 176 */       return nd.size(this.fdObj);
/*     */     } finally {
/* 178 */       end();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AsynchronousFileChannel truncate(long paramLong) throws IOException {
/* 184 */     if (paramLong < 0L)
/* 185 */       throw new IllegalArgumentException("Negative size"); 
/* 186 */     if (!this.writing)
/* 187 */       throw new NonWritableChannelException(); 
/*     */     try {
/* 189 */       begin();
/* 190 */       if (paramLong > nd.size(this.fdObj))
/* 191 */         return this; 
/* 192 */       nd.truncate(this.fdObj, paramLong);
/*     */     } finally {
/* 194 */       end();
/*     */     } 
/* 196 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void force(boolean paramBoolean) throws IOException {
/*     */     try {
/* 202 */       begin();
/* 203 */       nd.force(this.fdObj, paramBoolean);
/*     */     } finally {
/* 205 */       end();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class LockTask<A>
/*     */     implements Runnable, Iocp.ResultHandler
/*     */   {
/*     */     private final long position;
/*     */ 
/*     */     
/*     */     private final FileLockImpl fli;
/*     */     
/*     */     private final PendingFuture<FileLock, A> result;
/*     */ 
/*     */     
/*     */     LockTask(long param1Long, FileLockImpl param1FileLockImpl, PendingFuture<FileLock, A> param1PendingFuture) {
/* 223 */       this.position = param1Long;
/* 224 */       this.fli = param1FileLockImpl;
/* 225 */       this.result = param1PendingFuture;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 230 */       long l = 0L;
/* 231 */       boolean bool = false;
/*     */       try {
/* 233 */         WindowsAsynchronousFileChannelImpl.this.begin();
/*     */ 
/*     */         
/* 236 */         l = WindowsAsynchronousFileChannelImpl.this.ioCache.add(this.result);
/*     */ 
/*     */ 
/*     */         
/* 240 */         synchronized (this.result) {
/* 241 */           int i = WindowsAsynchronousFileChannelImpl.lockFile(WindowsAsynchronousFileChannelImpl.this.handle, this.position, this.fli.size(), this.fli.isShared(), l);
/*     */           
/* 243 */           if (i == -2) {
/*     */             
/* 245 */             bool = true;
/*     */             
/*     */             return;
/*     */           } 
/* 249 */           this.result.setResult(this.fli);
/*     */         }
/*     */       
/* 252 */       } catch (Throwable throwable) {
/*     */         
/* 254 */         WindowsAsynchronousFileChannelImpl.this.removeFromFileLockTable(this.fli);
/* 255 */         this.result.setFailure(WindowsAsynchronousFileChannelImpl.toIOException(throwable));
/*     */       } finally {
/* 257 */         if (!bool && l != 0L)
/* 258 */           WindowsAsynchronousFileChannelImpl.this.ioCache.remove(l); 
/* 259 */         WindowsAsynchronousFileChannelImpl.this.end();
/*     */       } 
/*     */ 
/*     */       
/* 263 */       Invoker.invoke(this.result);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void completed(int param1Int, boolean param1Boolean) {
/* 269 */       this.result.setResult(this.fli);
/* 270 */       if (param1Boolean) {
/* 271 */         Invoker.invokeUnchecked(this.result);
/*     */       } else {
/* 273 */         Invoker.invoke(this.result);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void failed(int param1Int, IOException param1IOException) {
/* 280 */       WindowsAsynchronousFileChannelImpl.this.removeFromFileLockTable(this.fli);
/*     */ 
/*     */       
/* 283 */       if (WindowsAsynchronousFileChannelImpl.this.isOpen()) {
/* 284 */         this.result.setFailure(param1IOException);
/*     */       } else {
/* 286 */         this.result.setFailure(new AsynchronousCloseException());
/*     */       } 
/* 288 */       Invoker.invoke(this.result);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <A> Future<FileLock> implLock(long paramLong1, long paramLong2, boolean paramBoolean, A paramA, CompletionHandler<FileLock, ? super A> paramCompletionHandler) {
/* 299 */     if (paramBoolean && !this.reading)
/* 300 */       throw new NonReadableChannelException(); 
/* 301 */     if (!paramBoolean && !this.writing) {
/* 302 */       throw new NonWritableChannelException();
/*     */     }
/*     */     
/* 305 */     FileLockImpl fileLockImpl = addToFileLockTable(paramLong1, paramLong2, paramBoolean);
/* 306 */     if (fileLockImpl == null) {
/* 307 */       ClosedChannelException closedChannelException = new ClosedChannelException();
/* 308 */       if (paramCompletionHandler == null)
/* 309 */         return CompletedFuture.withFailure(closedChannelException); 
/* 310 */       Invoker.invoke(this, paramCompletionHandler, paramA, null, closedChannelException);
/* 311 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 315 */     PendingFuture<FileLock, A> pendingFuture = new PendingFuture<>(this, paramCompletionHandler, paramA);
/*     */     
/* 317 */     LockTask<A> lockTask = new LockTask<>(paramLong1, fileLockImpl, pendingFuture);
/* 318 */     pendingFuture.setContext(lockTask);
/*     */ 
/*     */     
/* 321 */     if (Iocp.supportsThreadAgnosticIo()) {
/* 322 */       lockTask.run();
/*     */     } else {
/* 324 */       boolean bool = false;
/*     */       try {
/* 326 */         Invoker.invokeOnThreadInThreadPool(this, lockTask);
/* 327 */         bool = true;
/*     */       } finally {
/* 329 */         if (!bool)
/*     */         {
/* 331 */           removeFromFileLockTable(fileLockImpl);
/*     */         }
/*     */       } 
/*     */     } 
/* 335 */     return pendingFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileLock tryLock(long paramLong1, long paramLong2, boolean paramBoolean) throws IOException {
/* 345 */     if (paramBoolean && !this.reading)
/* 346 */       throw new NonReadableChannelException(); 
/* 347 */     if (!paramBoolean && !this.writing) {
/* 348 */       throw new NonWritableChannelException();
/*     */     }
/*     */     
/* 351 */     FileLockImpl fileLockImpl = addToFileLockTable(paramLong1, paramLong2, paramBoolean);
/* 352 */     if (fileLockImpl == null) {
/* 353 */       throw new ClosedChannelException();
/*     */     }
/* 355 */     boolean bool = false;
/*     */     try {
/* 357 */       begin();
/*     */       
/* 359 */       int i = nd.lock(this.fdObj, false, paramLong1, paramLong2, paramBoolean);
/* 360 */       if (i == -1)
/* 361 */         return null; 
/* 362 */       bool = true;
/* 363 */       return fileLockImpl;
/*     */     } finally {
/* 365 */       if (!bool)
/* 366 */         removeFromFileLockTable(fileLockImpl); 
/* 367 */       end();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void implRelease(FileLockImpl paramFileLockImpl) throws IOException {
/* 373 */     nd.release(this.fdObj, paramFileLockImpl.position(), paramFileLockImpl.size());
/*     */   }
/*     */ 
/*     */   
/*     */   private class ReadTask<A>
/*     */     implements Runnable, Iocp.ResultHandler
/*     */   {
/*     */     private final ByteBuffer dst;
/*     */     
/*     */     private final int pos;
/*     */     
/*     */     private final int rem;
/*     */     
/*     */     private final long position;
/*     */     
/*     */     private final PendingFuture<Integer, A> result;
/*     */     
/*     */     private volatile ByteBuffer buf;
/*     */ 
/*     */     
/*     */     ReadTask(ByteBuffer param1ByteBuffer, int param1Int1, int param1Int2, long param1Long, PendingFuture<Integer, A> param1PendingFuture) {
/* 394 */       this.dst = param1ByteBuffer;
/* 395 */       this.pos = param1Int1;
/* 396 */       this.rem = param1Int2;
/* 397 */       this.position = param1Long;
/* 398 */       this.result = param1PendingFuture;
/*     */     }
/*     */     
/*     */     void releaseBufferIfSubstituted() {
/* 402 */       if (this.buf != this.dst) {
/* 403 */         Util.releaseTemporaryDirectBuffer(this.buf);
/*     */       }
/*     */     }
/*     */     
/*     */     void updatePosition(int param1Int) {
/* 408 */       if (param1Int > 0) {
/* 409 */         if (this.buf == this.dst) {
/*     */           try {
/* 411 */             this.dst.position(this.pos + param1Int);
/* 412 */           } catch (IllegalArgumentException illegalArgumentException) {}
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 417 */           this.buf.position(param1Int).flip();
/*     */           try {
/* 419 */             this.dst.put(this.buf);
/* 420 */           } catch (BufferOverflowException bufferOverflowException) {}
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       long l2;
/* 429 */       int i = -1;
/* 430 */       long l1 = 0L;
/*     */ 
/*     */ 
/*     */       
/* 434 */       if (this.dst instanceof DirectBuffer) {
/* 435 */         this.buf = this.dst;
/* 436 */         l2 = ((DirectBuffer)this.dst).address() + this.pos;
/*     */       } else {
/* 438 */         this.buf = Util.getTemporaryDirectBuffer(this.rem);
/* 439 */         l2 = ((DirectBuffer)this.buf).address();
/*     */       } 
/*     */       
/* 442 */       boolean bool = false;
/*     */       try {
/* 444 */         WindowsAsynchronousFileChannelImpl.this.begin();
/*     */ 
/*     */         
/* 447 */         l1 = WindowsAsynchronousFileChannelImpl.this.ioCache.add(this.result);
/*     */ 
/*     */         
/* 450 */         i = WindowsAsynchronousFileChannelImpl.readFile(WindowsAsynchronousFileChannelImpl.this.handle, l2, this.rem, this.position, l1);
/* 451 */         if (i == -2) {
/*     */           
/* 453 */           bool = true; return;
/*     */         } 
/* 455 */         if (i == -1) {
/* 456 */           this.result.setResult(Integer.valueOf(i));
/*     */         } else {
/* 458 */           throw new InternalError("Unexpected result: " + i);
/*     */         }
/*     */       
/* 461 */       } catch (Throwable throwable) {
/*     */         
/* 463 */         this.result.setFailure(WindowsAsynchronousFileChannelImpl.toIOException(throwable));
/*     */       } finally {
/* 465 */         if (!bool) {
/*     */           
/* 467 */           if (l1 != 0L)
/* 468 */             WindowsAsynchronousFileChannelImpl.this.ioCache.remove(l1); 
/* 469 */           releaseBufferIfSubstituted();
/*     */         } 
/* 471 */         WindowsAsynchronousFileChannelImpl.this.end();
/*     */       } 
/*     */ 
/*     */       
/* 475 */       Invoker.invoke(this.result);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void completed(int param1Int, boolean param1Boolean) {
/* 483 */       updatePosition(param1Int);
/*     */ 
/*     */       
/* 486 */       releaseBufferIfSubstituted();
/*     */ 
/*     */       
/* 489 */       this.result.setResult(Integer.valueOf(param1Int));
/* 490 */       if (param1Boolean) {
/* 491 */         Invoker.invokeUnchecked(this.result);
/*     */       } else {
/* 493 */         Invoker.invoke(this.result);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void failed(int param1Int, IOException param1IOException) {
/* 500 */       if (param1Int == 38) {
/* 501 */         completed(-1, false);
/*     */       } else {
/*     */         
/* 504 */         releaseBufferIfSubstituted();
/*     */ 
/*     */         
/* 507 */         if (WindowsAsynchronousFileChannelImpl.this.isOpen()) {
/* 508 */           this.result.setFailure(param1IOException);
/*     */         } else {
/* 510 */           this.result.setFailure(new AsynchronousCloseException());
/*     */         } 
/* 512 */         Invoker.invoke(this.result);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <A> Future<Integer> implRead(ByteBuffer paramByteBuffer, long paramLong, A paramA, CompletionHandler<Integer, ? super A> paramCompletionHandler) {
/* 523 */     if (!this.reading)
/* 524 */       throw new NonReadableChannelException(); 
/* 525 */     if (paramLong < 0L)
/* 526 */       throw new IllegalArgumentException("Negative position"); 
/* 527 */     if (paramByteBuffer.isReadOnly()) {
/* 528 */       throw new IllegalArgumentException("Read-only buffer");
/*     */     }
/*     */     
/* 531 */     if (!isOpen()) {
/* 532 */       ClosedChannelException closedChannelException = new ClosedChannelException();
/* 533 */       if (paramCompletionHandler == null)
/* 534 */         return CompletedFuture.withFailure(closedChannelException); 
/* 535 */       Invoker.invoke(this, paramCompletionHandler, paramA, null, closedChannelException);
/* 536 */       return null;
/*     */     } 
/*     */     
/* 539 */     int i = paramByteBuffer.position();
/* 540 */     int j = paramByteBuffer.limit();
/* 541 */     assert i <= j;
/* 542 */     boolean bool = (i <= j) ? (j - i) : false;
/*     */ 
/*     */     
/* 545 */     if (!bool) {
/* 546 */       if (paramCompletionHandler == null)
/* 547 */         return CompletedFuture.withResult(Integer.valueOf(0)); 
/* 548 */       Invoker.invoke(this, paramCompletionHandler, paramA, Integer.valueOf(0), null);
/* 549 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 553 */     PendingFuture<Integer, A> pendingFuture = new PendingFuture<>(this, paramCompletionHandler, paramA);
/*     */     
/* 555 */     ReadTask<A> readTask = new ReadTask<>(paramByteBuffer, i, bool, paramLong, pendingFuture);
/* 556 */     pendingFuture.setContext(readTask);
/*     */ 
/*     */     
/* 559 */     if (Iocp.supportsThreadAgnosticIo()) {
/* 560 */       readTask.run();
/*     */     } else {
/* 562 */       Invoker.invokeOnThreadInThreadPool(this, readTask);
/*     */     } 
/* 564 */     return pendingFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   private class WriteTask<A>
/*     */     implements Runnable, Iocp.ResultHandler
/*     */   {
/*     */     private final ByteBuffer src;
/*     */     
/*     */     private final int pos;
/*     */     
/*     */     private final int rem;
/*     */     
/*     */     private final long position;
/*     */     
/*     */     private final PendingFuture<Integer, A> result;
/*     */     
/*     */     private volatile ByteBuffer buf;
/*     */ 
/*     */     
/*     */     WriteTask(ByteBuffer param1ByteBuffer, int param1Int1, int param1Int2, long param1Long, PendingFuture<Integer, A> param1PendingFuture) {
/* 585 */       this.src = param1ByteBuffer;
/* 586 */       this.pos = param1Int1;
/* 587 */       this.rem = param1Int2;
/* 588 */       this.position = param1Long;
/* 589 */       this.result = param1PendingFuture;
/*     */     }
/*     */     
/*     */     void releaseBufferIfSubstituted() {
/* 593 */       if (this.buf != this.src) {
/* 594 */         Util.releaseTemporaryDirectBuffer(this.buf);
/*     */       }
/*     */     }
/*     */     
/*     */     void updatePosition(int param1Int) {
/* 599 */       if (param1Int > 0) {
/*     */         try {
/* 601 */           this.src.position(this.pos + param1Int);
/* 602 */         } catch (IllegalArgumentException illegalArgumentException) {}
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       long l2;
/* 610 */       int i = -1;
/* 611 */       long l1 = 0L;
/*     */ 
/*     */ 
/*     */       
/* 615 */       if (this.src instanceof DirectBuffer) {
/* 616 */         this.buf = this.src;
/* 617 */         l2 = ((DirectBuffer)this.src).address() + this.pos;
/*     */       } else {
/* 619 */         this.buf = Util.getTemporaryDirectBuffer(this.rem);
/* 620 */         this.buf.put(this.src);
/* 621 */         this.buf.flip();
/*     */ 
/*     */         
/* 624 */         this.src.position(this.pos);
/* 625 */         l2 = ((DirectBuffer)this.buf).address();
/*     */       } 
/*     */       
/*     */       try {
/* 629 */         WindowsAsynchronousFileChannelImpl.this.begin();
/*     */ 
/*     */         
/* 632 */         l1 = WindowsAsynchronousFileChannelImpl.this.ioCache.add(this.result);
/*     */ 
/*     */         
/* 635 */         i = WindowsAsynchronousFileChannelImpl.writeFile(WindowsAsynchronousFileChannelImpl.this.handle, l2, this.rem, this.position, l1);
/* 636 */         if (i == -2) {
/*     */           return;
/*     */         }
/*     */         
/* 640 */         throw new InternalError("Unexpected result: " + i);
/*     */       
/*     */       }
/* 643 */       catch (Throwable throwable) {
/*     */         
/* 645 */         this.result.setFailure(WindowsAsynchronousFileChannelImpl.toIOException(throwable));
/*     */ 
/*     */         
/* 648 */         if (l1 != 0L)
/* 649 */           WindowsAsynchronousFileChannelImpl.this.ioCache.remove(l1); 
/* 650 */         releaseBufferIfSubstituted();
/*     */       } finally {
/*     */         
/* 653 */         WindowsAsynchronousFileChannelImpl.this.end();
/*     */       } 
/*     */ 
/*     */       
/* 657 */       Invoker.invoke(this.result);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void completed(int param1Int, boolean param1Boolean) {
/* 665 */       updatePosition(param1Int);
/*     */ 
/*     */       
/* 668 */       releaseBufferIfSubstituted();
/*     */ 
/*     */       
/* 671 */       this.result.setResult(Integer.valueOf(param1Int));
/* 672 */       if (param1Boolean) {
/* 673 */         Invoker.invokeUnchecked(this.result);
/*     */       } else {
/* 675 */         Invoker.invoke(this.result);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void failed(int param1Int, IOException param1IOException) {
/* 682 */       releaseBufferIfSubstituted();
/*     */ 
/*     */       
/* 685 */       if (WindowsAsynchronousFileChannelImpl.this.isOpen()) {
/* 686 */         this.result.setFailure(param1IOException);
/*     */       } else {
/* 688 */         this.result.setFailure(new AsynchronousCloseException());
/*     */       } 
/* 690 */       Invoker.invoke(this.result);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <A> Future<Integer> implWrite(ByteBuffer paramByteBuffer, long paramLong, A paramA, CompletionHandler<Integer, ? super A> paramCompletionHandler) {
/* 699 */     if (!this.writing)
/* 700 */       throw new NonWritableChannelException(); 
/* 701 */     if (paramLong < 0L) {
/* 702 */       throw new IllegalArgumentException("Negative position");
/*     */     }
/*     */     
/* 705 */     if (!isOpen()) {
/* 706 */       ClosedChannelException closedChannelException = new ClosedChannelException();
/* 707 */       if (paramCompletionHandler == null)
/* 708 */         return CompletedFuture.withFailure(closedChannelException); 
/* 709 */       Invoker.invoke(this, paramCompletionHandler, paramA, null, closedChannelException);
/* 710 */       return null;
/*     */     } 
/*     */     
/* 713 */     int i = paramByteBuffer.position();
/* 714 */     int j = paramByteBuffer.limit();
/* 715 */     assert i <= j;
/* 716 */     boolean bool = (i <= j) ? (j - i) : false;
/*     */ 
/*     */     
/* 719 */     if (!bool) {
/* 720 */       if (paramCompletionHandler == null)
/* 721 */         return CompletedFuture.withResult(Integer.valueOf(0)); 
/* 722 */       Invoker.invoke(this, paramCompletionHandler, paramA, Integer.valueOf(0), null);
/* 723 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 727 */     PendingFuture<Integer, A> pendingFuture = new PendingFuture<>(this, paramCompletionHandler, paramA);
/*     */     
/* 729 */     WriteTask<A> writeTask = new WriteTask<>(paramByteBuffer, i, bool, paramLong, pendingFuture);
/* 730 */     pendingFuture.setContext(writeTask);
/*     */ 
/*     */     
/* 733 */     if (Iocp.supportsThreadAgnosticIo()) {
/* 734 */       writeTask.run();
/*     */     } else {
/* 736 */       Invoker.invokeOnThreadInThreadPool(this, writeTask);
/*     */     } 
/* 738 */     return pendingFuture;
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
/*     */   static {
/* 755 */     IOUtil.load();
/*     */   }
/*     */   
/*     */   private static native int readFile(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4) throws IOException;
/*     */   
/*     */   private static native int writeFile(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4) throws IOException;
/*     */   
/*     */   private static native int lockFile(long paramLong1, long paramLong2, long paramLong3, boolean paramBoolean, long paramLong4) throws IOException;
/*     */   
/*     */   private static native void close0(long paramLong);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\WindowsAsynchronousFileChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */