/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.ClosedWatchServiceException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.WatchEvent;
/*     */ import java.nio.file.WatchKey;
/*     */ import java.nio.file.WatchService;
/*     */ import java.util.concurrent.LinkedBlockingDeque;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ abstract class AbstractWatchService
/*     */   implements WatchService
/*     */ {
/*  39 */   private final LinkedBlockingDeque<WatchKey> pendingKeys = new LinkedBlockingDeque<>();
/*     */ 
/*     */ 
/*     */   
/*  43 */   private final WatchKey CLOSE_KEY = new AbstractWatchKey(null, null)
/*     */     {
/*     */       public boolean isValid()
/*     */       {
/*  47 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public void cancel() {}
/*     */     };
/*     */ 
/*     */   
/*     */   private volatile boolean closed;
/*     */   
/*  57 */   private final Object closeLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract WatchKey register(Path paramPath, WatchEvent.Kind<?>[] paramArrayOfKind, WatchEvent.Modifier... paramVarArgs) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void enqueueKey(WatchKey paramWatchKey) {
/*  72 */     this.pendingKeys.offer(paramWatchKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkOpen() {
/*  79 */     if (this.closed) {
/*  80 */       throw new ClosedWatchServiceException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkKey(WatchKey paramWatchKey) {
/*  88 */     if (paramWatchKey == this.CLOSE_KEY)
/*     */     {
/*  90 */       enqueueKey(paramWatchKey);
/*     */     }
/*  92 */     checkOpen();
/*     */   }
/*     */ 
/*     */   
/*     */   public final WatchKey poll() {
/*  97 */     checkOpen();
/*  98 */     WatchKey watchKey = this.pendingKeys.poll();
/*  99 */     checkKey(watchKey);
/* 100 */     return watchKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final WatchKey poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 107 */     checkOpen();
/* 108 */     WatchKey watchKey = this.pendingKeys.poll(paramLong, paramTimeUnit);
/* 109 */     checkKey(watchKey);
/* 110 */     return watchKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final WatchKey take() throws InterruptedException {
/* 117 */     checkOpen();
/* 118 */     WatchKey watchKey = this.pendingKeys.take();
/* 119 */     checkKey(watchKey);
/* 120 */     return watchKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean isOpen() {
/* 127 */     return !this.closed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Object closeLock() {
/* 134 */     return this.closeLock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void implClose() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void close() throws IOException {
/* 147 */     synchronized (this.closeLock) {
/*     */       
/* 149 */       if (this.closed)
/*     */         return; 
/* 151 */       this.closed = true;
/*     */       
/* 153 */       implClose();
/*     */ 
/*     */ 
/*     */       
/* 157 */       this.pendingKeys.clear();
/* 158 */       this.pendingKeys.offer(this.CLOSE_KEY);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\AbstractWatchService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */