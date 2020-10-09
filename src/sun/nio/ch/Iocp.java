/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.Channel;
/*     */ import java.nio.channels.ShutdownChannelGroupException;
/*     */ import java.nio.channels.spi.AsynchronousChannelProvider;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.security.action.GetPropertyAction;
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
/*     */ class Iocp
/*     */   extends AsynchronousChannelGroupImpl
/*     */ {
/*  47 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   
/*     */   private static final long INVALID_HANDLE_VALUE = -1L;
/*     */   
/*     */   private static final boolean supportsThreadAgnosticIo;
/*  52 */   private final ReadWriteLock keyToChannelLock = new ReentrantReadWriteLock();
/*  53 */   private final Map<Integer, OverlappedChannel> keyToChannel = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private int nextCompletionKey;
/*     */ 
/*     */   
/*     */   private final long port;
/*     */ 
/*     */   
/*     */   private boolean closed;
/*     */ 
/*     */   
/*  66 */   private final Set<Long> staleIoSet = new HashSet<>();
/*     */ 
/*     */ 
/*     */   
/*     */   Iocp(AsynchronousChannelProvider paramAsynchronousChannelProvider, ThreadPool paramThreadPool) throws IOException {
/*  71 */     super(paramAsynchronousChannelProvider, paramThreadPool);
/*  72 */     this
/*  73 */       .port = createIoCompletionPort(-1L, 0L, 0, fixedThreadCount());
/*  74 */     this.nextCompletionKey = 1;
/*     */   }
/*     */   
/*     */   Iocp start() {
/*  78 */     startThreads(new EventHandlerTask());
/*  79 */     return this;
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
/*     */   static boolean supportsThreadAgnosticIo() {
/*  97 */     return supportsThreadAgnosticIo;
/*     */   }
/*     */ 
/*     */   
/*     */   void implClose() {
/* 102 */     synchronized (this) {
/* 103 */       if (this.closed)
/*     */         return; 
/* 105 */       this.closed = true;
/*     */     } 
/* 107 */     close0(this.port);
/* 108 */     synchronized (this.staleIoSet) {
/* 109 */       for (Long long_ : this.staleIoSet) {
/* 110 */         unsafe.freeMemory(long_.longValue());
/*     */       }
/* 112 */       this.staleIoSet.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isEmpty() {
/* 118 */     this.keyToChannelLock.writeLock().lock();
/*     */     try {
/* 120 */       return this.keyToChannel.isEmpty();
/*     */     } finally {
/* 122 */       this.keyToChannelLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Object attachForeignChannel(final Channel channel, FileDescriptor paramFileDescriptor) throws IOException {
/* 130 */     int i = associate(new OverlappedChannel() {
/*     */           public <V, A> PendingFuture<V, A> getByOverlapped(long param1Long) {
/* 132 */             return null;
/*     */           }
/*     */           public void close() throws IOException {
/* 135 */             channel.close();
/*     */           }
/*     */         },  0L);
/* 138 */     return Integer.valueOf(i);
/*     */   }
/*     */ 
/*     */   
/*     */   final void detachForeignChannel(Object paramObject) {
/* 143 */     disassociate(((Integer)paramObject).intValue());
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
/*     */   void closeAllChannels() {
/*     */     byte b;
/* 157 */     OverlappedChannel[] arrayOfOverlappedChannel = new OverlappedChannel[32];
/*     */ 
/*     */     
/*     */     do {
/* 161 */       this.keyToChannelLock.writeLock().lock();
/* 162 */       b = 0;
/*     */       try {
/* 164 */         for (Integer integer : this.keyToChannel.keySet()) {
/* 165 */           arrayOfOverlappedChannel[b++] = this.keyToChannel.get(integer);
/* 166 */           if (b >= 32)
/*     */             break; 
/*     */         } 
/*     */       } finally {
/* 170 */         this.keyToChannelLock.writeLock().unlock();
/*     */       } 
/*     */ 
/*     */       
/* 174 */       for (byte b1 = 0; b1 < b; b1++) {
/*     */         try {
/* 176 */           arrayOfOverlappedChannel[b1].close();
/* 177 */         } catch (IOException iOException) {}
/*     */       } 
/* 179 */     } while (b > 0);
/*     */   }
/*     */   
/*     */   private void wakeup() {
/*     */     try {
/* 184 */       postQueuedCompletionStatus(this.port, 0);
/* 185 */     } catch (IOException iOException) {
/*     */       
/* 187 */       throw new AssertionError(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void executeOnHandlerTask(Runnable paramRunnable) {
/* 193 */     synchronized (this) {
/* 194 */       if (this.closed)
/* 195 */         throw new RejectedExecutionException(); 
/* 196 */       offerTask(paramRunnable);
/* 197 */       wakeup();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void shutdownHandlerTasks() {
/* 205 */     int i = threadCount();
/* 206 */     while (i-- > 0) {
/* 207 */       wakeup();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int associate(OverlappedChannel paramOverlappedChannel, long paramLong) throws IOException {
/*     */     int i;
/* 215 */     this.keyToChannelLock.writeLock().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 220 */       if (isShutdown()) {
/* 221 */         throw new ShutdownChannelGroupException();
/*     */       }
/*     */       
/*     */       do {
/* 225 */         i = this.nextCompletionKey++;
/* 226 */       } while (i == 0 || this.keyToChannel.containsKey(Integer.valueOf(i)));
/*     */ 
/*     */       
/* 229 */       if (paramLong != 0L) {
/* 230 */         createIoCompletionPort(paramLong, this.port, i, 0);
/*     */       }
/*     */ 
/*     */       
/* 234 */       this.keyToChannel.put(Integer.valueOf(i), paramOverlappedChannel);
/*     */     } finally {
/* 236 */       this.keyToChannelLock.writeLock().unlock();
/*     */     } 
/* 238 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void disassociate(int paramInt) {
/* 245 */     boolean bool = false;
/*     */     
/* 247 */     this.keyToChannelLock.writeLock().lock();
/*     */     try {
/* 249 */       this.keyToChannel.remove(Integer.valueOf(paramInt));
/*     */ 
/*     */       
/* 252 */       if (this.keyToChannel.isEmpty()) {
/* 253 */         bool = true;
/*     */       }
/*     */     } finally {
/* 256 */       this.keyToChannelLock.writeLock().unlock();
/*     */     } 
/*     */ 
/*     */     
/* 260 */     if (bool && isShutdown()) {
/*     */       try {
/* 262 */         shutdownNow();
/* 263 */       } catch (IOException iOException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void makeStale(Long paramLong) {
/* 272 */     synchronized (this.staleIoSet) {
/* 273 */       this.staleIoSet.add(paramLong);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkIfStale(long paramLong) {
/* 281 */     synchronized (this.staleIoSet) {
/* 282 */       boolean bool = this.staleIoSet.remove(Long.valueOf(paramLong));
/* 283 */       if (bool) {
/* 284 */         unsafe.freeMemory(paramLong);
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IOException translateErrorToIOException(int paramInt) {
/* 306 */     String str = getErrorMessage(paramInt);
/* 307 */     if (str == null)
/* 308 */       str = "Unknown error: 0x0" + Integer.toHexString(paramInt); 
/* 309 */     return new IOException(str);
/*     */   }
/*     */   
/*     */   private class EventHandlerTask
/*     */     implements Runnable
/*     */   {
/*     */     private EventHandlerTask() {}
/*     */     
/*     */     public void run() {
/* 318 */       Invoker.GroupAndInvokeCount groupAndInvokeCount = Invoker.getGroupAndInvokeCount();
/* 319 */       boolean bool1 = (groupAndInvokeCount != null) ? true : false;
/* 320 */       Iocp.CompletionStatus completionStatus = new Iocp.CompletionStatus();
/* 321 */       boolean bool2 = false;
/*     */ 
/*     */       
/*     */       try {
/*     */         while (true) {
/* 326 */           if (groupAndInvokeCount != null) {
/* 327 */             groupAndInvokeCount.resetInvokeCount();
/*     */           }
/*     */ 
/*     */           
/* 331 */           bool2 = false;
/*     */           try {
/* 333 */             Iocp.getQueuedCompletionStatus(Iocp.this.port, completionStatus);
/* 334 */           } catch (IOException iOException) {
/*     */             
/* 336 */             iOException.printStackTrace();
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 341 */           if (completionStatus.completionKey() == 0 && completionStatus
/* 342 */             .overlapped() == 0L) {
/*     */             
/* 344 */             Runnable runnable = Iocp.this.pollTask();
/* 345 */             if (runnable == null) {
/*     */               return;
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 352 */             bool2 = true;
/* 353 */             runnable.run();
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 358 */           Iocp.OverlappedChannel overlappedChannel = null;
/* 359 */           Iocp.this.keyToChannelLock.readLock().lock();
/*     */           
/* 361 */           try { overlappedChannel = (Iocp.OverlappedChannel)Iocp.this.keyToChannel.get(Integer.valueOf(completionStatus.completionKey()));
/* 362 */             if (overlappedChannel == null)
/* 363 */             { Iocp.this.checkIfStale(completionStatus.overlapped());
/*     */ 
/*     */ 
/*     */               
/* 367 */               Iocp.this.keyToChannelLock.readLock().unlock(); continue; }  } finally { Iocp.this.keyToChannelLock.readLock().unlock(); }
/*     */ 
/*     */ 
/*     */           
/* 371 */           PendingFuture<?, ?> pendingFuture = overlappedChannel.getByOverlapped(completionStatus.overlapped());
/* 372 */           if (pendingFuture == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 379 */             Iocp.this.checkIfStale(completionStatus.overlapped());
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 385 */           synchronized (pendingFuture) {
/* 386 */             if (pendingFuture.isDone()) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 393 */           int i = completionStatus.error();
/* 394 */           Iocp.ResultHandler resultHandler = (Iocp.ResultHandler)pendingFuture.getContext();
/* 395 */           bool2 = true;
/* 396 */           if (i == 0) {
/* 397 */             resultHandler.completed(completionStatus.bytesTransferred(), bool1); continue;
/*     */           } 
/* 399 */           resultHandler.failed(i, Iocp.translateErrorToIOException(i));
/*     */         }
/*     */       
/*     */       } finally {
/*     */         
/* 404 */         int i = Iocp.this.threadExit(this, bool2);
/* 405 */         if (i == 0 && Iocp.this.isShutdown()) {
/* 406 */           Iocp.this.implClose();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CompletionStatus
/*     */   {
/*     */     private int error;
/*     */     private int bytesTransferred;
/*     */     private int completionKey;
/*     */     private long overlapped;
/*     */     
/*     */     private CompletionStatus() {}
/*     */     
/*     */     int error() {
/* 422 */       return this.error;
/* 423 */     } int bytesTransferred() { return this.bytesTransferred; }
/* 424 */     int completionKey() { return this.completionKey; } long overlapped() {
/* 425 */       return this.overlapped;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 446 */     IOUtil.load();
/* 447 */     initIDs();
/*     */ 
/*     */     
/* 450 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("os.version"));
/*     */     
/* 452 */     String[] arrayOfString = str.split("\\.");
/* 453 */     supportsThreadAgnosticIo = (Integer.parseInt(arrayOfString[0]) >= 6);
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private static native long createIoCompletionPort(long paramLong1, long paramLong2, int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void close0(long paramLong);
/*     */   
/*     */   private static native void getQueuedCompletionStatus(long paramLong, CompletionStatus paramCompletionStatus) throws IOException;
/*     */   
/*     */   private static native void postQueuedCompletionStatus(long paramLong, int paramInt) throws IOException;
/*     */   
/*     */   private static native String getErrorMessage(int paramInt);
/*     */   
/*     */   static interface OverlappedChannel extends Closeable {
/*     */     <V, A> PendingFuture<V, A> getByOverlapped(long param1Long);
/*     */   }
/*     */   
/*     */   static interface ResultHandler {
/*     */     void completed(int param1Int, boolean param1Boolean);
/*     */     
/*     */     void failed(int param1Int, IOException param1IOException);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\Iocp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */