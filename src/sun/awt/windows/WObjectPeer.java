/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
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
/*     */ abstract class WObjectPeer
/*     */ {
/*     */   volatile long pData;
/*     */   private volatile boolean destroyed;
/*     */   volatile Object target;
/*     */   private volatile boolean disposed;
/*     */   
/*     */   static {
/*  33 */     initIDs();
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
/*  46 */   volatile Error createError = null;
/*     */ 
/*     */   
/*  49 */   private final Object stateLock = new Object();
/*     */   
/*     */   private volatile Map<WObjectPeer, WObjectPeer> childPeers;
/*     */   
/*     */   public static WObjectPeer getPeerForTarget(Object paramObject) {
/*  54 */     return (WObjectPeer)WToolkit.targetToPeer(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getData() {
/*  59 */     return this.pData;
/*     */   }
/*     */   
/*     */   public Object getTarget() {
/*  63 */     return this.target;
/*     */   }
/*     */   
/*     */   public final Object getStateLock() {
/*  67 */     return this.stateLock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void dispose() {
/*  76 */     boolean bool = false;
/*     */     
/*  78 */     synchronized (this) {
/*  79 */       if (!this.disposed) {
/*  80 */         this.disposed = bool = true;
/*     */       }
/*     */     } 
/*     */     
/*  84 */     if (bool) {
/*  85 */       if (this.childPeers != null) {
/*  86 */         disposeChildPeers();
/*     */       }
/*  88 */       disposeImpl();
/*     */     } 
/*     */   }
/*     */   protected final boolean isDisposed() {
/*  92 */     return this.disposed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void addChildPeer(WObjectPeer paramWObjectPeer) {
/* 102 */     synchronized (getStateLock()) {
/* 103 */       if (this.childPeers == null) {
/* 104 */         this.childPeers = new WeakHashMap<>();
/*     */       }
/* 106 */       if (isDisposed()) {
/* 107 */         throw new IllegalStateException("Parent peer is disposed");
/*     */       }
/* 109 */       this.childPeers.put(paramWObjectPeer, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void disposeChildPeers() {
/* 115 */     synchronized (getStateLock()) {
/* 116 */       for (WObjectPeer wObjectPeer : this.childPeers.keySet()) {
/* 117 */         if (wObjectPeer != null)
/*     */           try {
/* 119 */             wObjectPeer.dispose();
/*     */           }
/* 121 */           catch (Exception exception) {} 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void disposeImpl();
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WObjectPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */