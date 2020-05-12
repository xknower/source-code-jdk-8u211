/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.channels.AcceptPendingException;
/*     */ import java.nio.channels.AsynchronousCloseException;
/*     */ import java.nio.channels.AsynchronousSocketChannel;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.CompletionHandler;
/*     */ import java.nio.channels.NotYetBoundException;
/*     */ import java.nio.channels.ShutdownChannelGroupException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsAsynchronousServerSocketChannelImpl
/*     */   extends AsynchronousServerSocketChannelImpl
/*     */   implements Iocp.OverlappedChannel
/*     */ {
/*  45 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */ 
/*     */   
/*     */   private static final int DATA_BUFFER_SIZE = 88;
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
/*     */   private final PendingIoCache ioCache;
/*     */ 
/*     */   
/*     */   private final long dataBuffer;
/*     */   
/*     */   private AtomicBoolean accepting;
/*     */ 
/*     */   
/*     */   WindowsAsynchronousServerSocketChannelImpl(Iocp paramIocp) throws IOException {
/*  69 */     super(paramIocp);
/*     */     int i;
/*     */     this.accepting = new AtomicBoolean();
/*  72 */     long l = IOUtil.fdVal(this.fd);
/*     */     
/*     */     try {
/*  75 */       i = paramIocp.associate(this, l);
/*  76 */     } catch (IOException iOException) {
/*  77 */       closesocket0(l);
/*  78 */       throw iOException;
/*     */     } 
/*     */     
/*  81 */     this.handle = l;
/*  82 */     this.completionKey = i;
/*  83 */     this.iocp = paramIocp;
/*  84 */     this.ioCache = new PendingIoCache();
/*  85 */     this.dataBuffer = unsafe.allocateMemory(88L);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V, A> PendingFuture<V, A> getByOverlapped(long paramLong) {
/*  90 */     return this.ioCache.remove(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void implClose() throws IOException {
/*  96 */     closesocket0(this.handle);
/*     */ 
/*     */     
/*  99 */     this.ioCache.close();
/*     */ 
/*     */     
/* 102 */     this.iocp.disassociate(this.completionKey);
/*     */ 
/*     */     
/* 105 */     unsafe.freeMemory(this.dataBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public AsynchronousChannelGroupImpl group() {
/* 110 */     return this.iocp;
/*     */   }
/*     */ 
/*     */   
/*     */   private class AcceptTask
/*     */     implements Runnable, Iocp.ResultHandler
/*     */   {
/*     */     private final WindowsAsynchronousSocketChannelImpl channel;
/*     */     
/*     */     private final AccessControlContext acc;
/*     */     
/*     */     private final PendingFuture<AsynchronousSocketChannel, Object> result;
/*     */ 
/*     */     
/*     */     AcceptTask(WindowsAsynchronousSocketChannelImpl param1WindowsAsynchronousSocketChannelImpl, AccessControlContext param1AccessControlContext, PendingFuture<AsynchronousSocketChannel, Object> param1PendingFuture) {
/* 125 */       this.channel = param1WindowsAsynchronousSocketChannelImpl;
/* 126 */       this.acc = param1AccessControlContext;
/* 127 */       this.result = param1PendingFuture;
/*     */     }
/*     */     
/*     */     void enableAccept() {
/* 131 */       WindowsAsynchronousServerSocketChannelImpl.this.accepting.set(false);
/*     */     }
/*     */     
/*     */     void closeChildChannel() {
/*     */       try {
/* 136 */         this.channel.close();
/* 137 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void finishAccept() throws IOException {
/* 147 */       WindowsAsynchronousServerSocketChannelImpl.updateAcceptContext(WindowsAsynchronousServerSocketChannelImpl.this.handle, this.channel.handle());
/*     */       
/* 149 */       InetSocketAddress inetSocketAddress1 = Net.localAddress(this.channel.fd);
/* 150 */       final InetSocketAddress remote = Net.remoteAddress(this.channel.fd);
/* 151 */       this.channel.setConnected(inetSocketAddress1, inetSocketAddress2);
/*     */ 
/*     */       
/* 154 */       if (this.acc != null) {
/* 155 */         AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */               public Void run() {
/* 157 */                 SecurityManager securityManager = System.getSecurityManager();
/* 158 */                 securityManager.checkAccept(remote.getAddress().getHostAddress(), remote
/* 159 */                     .getPort());
/* 160 */                 return null;
/*     */               }
/*     */             }this.acc);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 171 */       long l = 0L;
/*     */ 
/*     */       
/*     */       try {
/* 175 */         WindowsAsynchronousServerSocketChannelImpl.this.begin();
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 180 */           this.channel.begin();
/*     */           
/* 182 */           synchronized (this.result) {
/* 183 */             l = WindowsAsynchronousServerSocketChannelImpl.this.ioCache.add(this.result);
/*     */             
/* 185 */             int i = WindowsAsynchronousServerSocketChannelImpl.accept0(WindowsAsynchronousServerSocketChannelImpl.this.handle, this.channel.handle(), l, WindowsAsynchronousServerSocketChannelImpl.this.dataBuffer);
/* 186 */             if (i == -2) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/* 191 */             finishAccept();
/*     */ 
/*     */             
/* 194 */             enableAccept();
/* 195 */             this.result.setResult(this.channel);
/*     */           } 
/*     */         } finally {
/*     */           
/* 199 */           this.channel.end();
/*     */         } 
/* 201 */       } catch (Throwable throwable) {
/*     */         
/* 203 */         if (l != 0L)
/* 204 */           WindowsAsynchronousServerSocketChannelImpl.this.ioCache.remove(l); 
/* 205 */         closeChildChannel();
/* 206 */         if (throwable instanceof ClosedChannelException)
/* 207 */           throwable = new AsynchronousCloseException(); 
/* 208 */         if (!(throwable instanceof IOException) && !(throwable instanceof SecurityException))
/* 209 */           throwable = new IOException(throwable); 
/* 210 */         enableAccept();
/* 211 */         this.result.setFailure(throwable);
/*     */       } finally {
/*     */         
/* 214 */         WindowsAsynchronousServerSocketChannelImpl.this.end();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 220 */       if (this.result.isCancelled()) {
/* 221 */         closeChildChannel();
/*     */       }
/*     */ 
/*     */       
/* 225 */       Invoker.invokeIndirectly(this.result);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void completed(int param1Int, boolean param1Boolean) {
/*     */       try {
/* 235 */         if (WindowsAsynchronousServerSocketChannelImpl.this.iocp.isShutdown()) {
/* 236 */           throw new IOException(new ShutdownChannelGroupException());
/*     */         }
/*     */ 
/*     */         
/*     */         try {
/* 241 */           WindowsAsynchronousServerSocketChannelImpl.this.begin();
/*     */           try {
/* 243 */             this.channel.begin();
/* 244 */             finishAccept();
/*     */           } finally {
/* 246 */             this.channel.end();
/*     */           } 
/*     */         } finally {
/* 249 */           WindowsAsynchronousServerSocketChannelImpl.this.end();
/*     */         } 
/*     */ 
/*     */         
/* 253 */         enableAccept();
/* 254 */         this.result.setResult(this.channel);
/* 255 */       } catch (Throwable throwable) {
/* 256 */         enableAccept();
/* 257 */         closeChildChannel();
/* 258 */         if (throwable instanceof ClosedChannelException)
/* 259 */           throwable = new AsynchronousCloseException(); 
/* 260 */         if (!(throwable instanceof IOException) && !(throwable instanceof SecurityException))
/* 261 */           throwable = new IOException(throwable); 
/* 262 */         this.result.setFailure(throwable);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 267 */       if (this.result.isCancelled()) {
/* 268 */         closeChildChannel();
/*     */       }
/*     */ 
/*     */       
/* 272 */       Invoker.invokeIndirectly(this.result);
/*     */     }
/*     */ 
/*     */     
/*     */     public void failed(int param1Int, IOException param1IOException) {
/* 277 */       enableAccept();
/* 278 */       closeChildChannel();
/*     */ 
/*     */       
/* 281 */       if (WindowsAsynchronousServerSocketChannelImpl.this.isOpen()) {
/* 282 */         this.result.setFailure(param1IOException);
/*     */       } else {
/* 284 */         this.result.setFailure(new AsynchronousCloseException());
/*     */       } 
/* 286 */       Invoker.invokeIndirectly(this.result);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Future<AsynchronousSocketChannel> implAccept(Object paramObject, CompletionHandler<AsynchronousSocketChannel, Object> paramCompletionHandler) {
/* 294 */     if (!isOpen()) {
/* 295 */       ClosedChannelException closedChannelException = new ClosedChannelException();
/* 296 */       if (paramCompletionHandler == null)
/* 297 */         return CompletedFuture.withFailure(closedChannelException); 
/* 298 */       Invoker.invokeIndirectly(this, paramCompletionHandler, paramObject, (AsynchronousSocketChannel)null, closedChannelException);
/* 299 */       return null;
/*     */     } 
/* 301 */     if (isAcceptKilled()) {
/* 302 */       throw new RuntimeException("Accept not allowed due to cancellation");
/*     */     }
/*     */     
/* 305 */     if (this.localAddress == null) {
/* 306 */       throw new NotYetBoundException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 312 */     WindowsAsynchronousSocketChannelImpl windowsAsynchronousSocketChannelImpl = null;
/* 313 */     IOException iOException = null;
/*     */     try {
/* 315 */       begin();
/* 316 */       windowsAsynchronousSocketChannelImpl = new WindowsAsynchronousSocketChannelImpl(this.iocp, false);
/* 317 */     } catch (IOException iOException1) {
/* 318 */       iOException = iOException1;
/*     */     } finally {
/* 320 */       end();
/*     */     } 
/* 322 */     if (iOException != null) {
/* 323 */       if (paramCompletionHandler == null)
/* 324 */         return CompletedFuture.withFailure(iOException); 
/* 325 */       Invoker.invokeIndirectly(this, paramCompletionHandler, paramObject, (AsynchronousSocketChannel)null, iOException);
/* 326 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     AccessControlContext accessControlContext = (System.getSecurityManager() == null) ? null : AccessController.getContext();
/*     */     
/* 335 */     PendingFuture<AsynchronousSocketChannel, Object> pendingFuture = new PendingFuture<>(this, paramCompletionHandler, paramObject);
/*     */     
/* 337 */     AcceptTask acceptTask = new AcceptTask(windowsAsynchronousSocketChannelImpl, accessControlContext, pendingFuture);
/* 338 */     pendingFuture.setContext(acceptTask);
/*     */ 
/*     */     
/* 341 */     if (!this.accepting.compareAndSet(false, true)) {
/* 342 */       throw new AcceptPendingException();
/*     */     }
/*     */     
/* 345 */     if (Iocp.supportsThreadAgnosticIo()) {
/* 346 */       acceptTask.run();
/*     */     } else {
/* 348 */       Invoker.invokeOnThreadInThreadPool(this, acceptTask);
/*     */     } 
/* 350 */     return pendingFuture;
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
/*     */   static {
/* 366 */     IOUtil.load();
/* 367 */     initIDs();
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private static native int accept0(long paramLong1, long paramLong2, long paramLong3, long paramLong4) throws IOException;
/*     */   
/*     */   private static native void updateAcceptContext(long paramLong1, long paramLong2) throws IOException;
/*     */   
/*     */   private static native void closesocket0(long paramLong) throws IOException;
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\WindowsAsynchronousServerSocketChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */