/*     */ package sun.java2d.d3d;
/*     */ 
/*     */ import sun.java2d.ScreenUpdateManager;
/*     */ import sun.java2d.pipe.RenderBuffer;
/*     */ import sun.java2d.pipe.RenderQueue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class D3DRenderQueue
/*     */   extends RenderQueue
/*     */ {
/*     */   private static D3DRenderQueue theInstance;
/*     */   private static Thread rqThread;
/*     */   
/*     */   public static synchronized D3DRenderQueue getInstance() {
/*  50 */     if (theInstance == null) {
/*  51 */       theInstance = new D3DRenderQueue();
/*     */       
/*  53 */       theInstance.flushAndInvokeNow(new Runnable() {
/*     */             public void run() {
/*  55 */               D3DRenderQueue.rqThread = Thread.currentThread();
/*     */             }
/*     */           });
/*     */     } 
/*  59 */     return theInstance;
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
/*     */   public static void sync() {
/*  72 */     if (theInstance != null) {
/*     */ 
/*     */ 
/*     */       
/*  76 */       D3DScreenUpdateManager d3DScreenUpdateManager = (D3DScreenUpdateManager)ScreenUpdateManager.getInstance();
/*  77 */       d3DScreenUpdateManager.runUpdateNow();
/*     */       
/*  79 */       theInstance.lock();
/*     */       try {
/*  81 */         theInstance.ensureCapacity(4);
/*  82 */         theInstance.getBuffer().putInt(76);
/*  83 */         theInstance.flushNow();
/*     */       } finally {
/*  85 */         theInstance.unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void restoreDevices() {
/*  95 */     D3DRenderQueue d3DRenderQueue = getInstance();
/*  96 */     d3DRenderQueue.lock();
/*     */     try {
/*  98 */       d3DRenderQueue.ensureCapacity(4);
/*  99 */       d3DRenderQueue.getBuffer().putInt(77);
/* 100 */       d3DRenderQueue.flushNow();
/*     */     } finally {
/* 102 */       d3DRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRenderQueueThread() {
/* 111 */     return (Thread.currentThread() == rqThread);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void disposeGraphicsConfig(long paramLong) {
/* 119 */     D3DRenderQueue d3DRenderQueue = getInstance();
/* 120 */     d3DRenderQueue.lock();
/*     */     
/*     */     try {
/* 123 */       RenderBuffer renderBuffer = d3DRenderQueue.getBuffer();
/* 124 */       d3DRenderQueue.ensureCapacityAndAlignment(12, 4);
/* 125 */       renderBuffer.putInt(74);
/* 126 */       renderBuffer.putLong(paramLong);
/*     */ 
/*     */       
/* 129 */       d3DRenderQueue.flushNow();
/*     */     } finally {
/* 131 */       d3DRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void flushNow() {
/* 137 */     flushBuffer((Runnable)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flushAndInvokeNow(Runnable paramRunnable) {
/* 142 */     flushBuffer(paramRunnable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void flushBuffer(Runnable paramRunnable) {
/* 149 */     int i = this.buf.position();
/* 150 */     if (i > 0 || paramRunnable != null)
/*     */     {
/* 152 */       flushBuffer(this.buf.getAddress(), i, paramRunnable);
/*     */     }
/*     */     
/* 155 */     this.buf.clear();
/*     */     
/* 157 */     this.refSet.clear();
/*     */   }
/*     */   
/*     */   private native void flushBuffer(long paramLong, int paramInt, Runnable paramRunnable);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\d3d\D3DRenderQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */