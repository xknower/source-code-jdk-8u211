/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.nio.channels.AsynchronousCloseException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PendingIoCache
/*     */ {
/*  38 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*  39 */   private static final int addressSize = unsafe.addressSize();
/*     */   
/*     */   private static int dependsArch(int paramInt1, int paramInt2) {
/*  42 */     return (addressSize == 4) ? paramInt1 : paramInt2;
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
/*  54 */   private static final int SIZEOF_OVERLAPPED = dependsArch(20, 32);
/*     */ 
/*     */   
/*     */   private boolean closed;
/*     */ 
/*     */   
/*     */   private boolean closePending;
/*     */ 
/*     */   
/*  63 */   private final Map<Long, PendingFuture> pendingIoMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private long[] overlappedCache = new long[4];
/*  69 */   private int overlappedCacheCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long add(PendingFuture<?, ?> paramPendingFuture) {
/*  75 */     synchronized (this) {
/*  76 */       long l; if (this.closed) {
/*  77 */         throw new AssertionError("Should not get here");
/*     */       }
/*  79 */       if (this.overlappedCacheCount > 0) {
/*  80 */         l = this.overlappedCache[--this.overlappedCacheCount];
/*     */       } else {
/*  82 */         l = unsafe.allocateMemory(SIZEOF_OVERLAPPED);
/*     */       } 
/*  84 */       this.pendingIoMap.put(Long.valueOf(l), paramPendingFuture);
/*  85 */       return l;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   <V, A> PendingFuture<V, A> remove(long paramLong) {
/*  91 */     synchronized (this) {
/*  92 */       PendingFuture<V, A> pendingFuture = this.pendingIoMap.remove(Long.valueOf(paramLong));
/*  93 */       if (pendingFuture != null) {
/*  94 */         if (this.overlappedCacheCount < this.overlappedCache.length) {
/*  95 */           this.overlappedCache[this.overlappedCacheCount++] = paramLong;
/*     */         } else {
/*     */           
/*  98 */           unsafe.freeMemory(paramLong);
/*     */         } 
/*     */         
/* 101 */         if (this.closePending) {
/* 102 */           notifyAll();
/*     */         }
/*     */       } 
/* 105 */       return pendingFuture;
/*     */     } 
/*     */   }
/*     */   
/*     */   void close() {
/* 110 */     synchronized (this) {
/* 111 */       if (this.closed) {
/*     */         return;
/*     */       }
/*     */       
/* 115 */       if (!this.pendingIoMap.isEmpty()) {
/* 116 */         clearPendingIoMap();
/*     */       }
/*     */       
/* 119 */       while (this.overlappedCacheCount > 0) {
/* 120 */         unsafe.freeMemory(this.overlappedCache[--this.overlappedCacheCount]);
/*     */       }
/*     */ 
/*     */       
/* 124 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void clearPendingIoMap() {
/* 129 */     assert Thread.holdsLock(this);
/*     */ 
/*     */     
/* 132 */     this.closePending = true;
/*     */     try {
/* 134 */       wait(50L);
/* 135 */     } catch (InterruptedException interruptedException) {
/* 136 */       Thread.currentThread().interrupt();
/*     */     } 
/* 138 */     this.closePending = false;
/* 139 */     if (this.pendingIoMap.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 144 */     for (Long long_ : this.pendingIoMap.keySet()) {
/* 145 */       PendingFuture pendingFuture = this.pendingIoMap.get(long_);
/* 146 */       assert !pendingFuture.isDone();
/*     */ 
/*     */       
/* 149 */       Iocp iocp = (Iocp)((Groupable)pendingFuture.channel()).group();
/* 150 */       iocp.makeStale(long_);
/*     */ 
/*     */       
/* 153 */       final Iocp.ResultHandler rh = (Iocp.ResultHandler)pendingFuture.getContext();
/* 154 */       Runnable runnable = new Runnable() {
/*     */           public void run() {
/* 156 */             rh.failed(-1, new AsynchronousCloseException());
/*     */           }
/*     */         };
/* 159 */       iocp.executeOnPooledThread(runnable);
/*     */     } 
/* 161 */     this.pendingIoMap.clear();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\PendingIoCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */