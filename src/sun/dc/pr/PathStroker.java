/*     */ package sun.dc.pr;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.awt.geom.PathConsumer2D;
/*     */ import sun.dc.path.FastPathProducer;
/*     */ import sun.dc.path.PathConsumer;
/*     */ import sun.dc.path.PathError;
/*     */ import sun.dc.path.PathException;
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
/*     */ public class PathStroker
/*     */   implements PathConsumer
/*     */ {
/*     */   public static final int ROUND = 10;
/*     */   public static final int SQUARE = 20;
/*     */   public static final int BUTT = 30;
/*     */   public static final int BEVEL = 40;
/*     */   public static final int MITER = 50;
/*     */   private PathConsumer dest;
/*     */   private PathConsumer2D dest2D;
/*     */   private long cData;
/*     */   
/*     */   public PathStroker(PathConsumer paramPathConsumer) {
/*  60 */     if (paramPathConsumer == null) {
/*  61 */       throw new InternalError("null dest for path");
/*     */     }
/*  63 */     this.dest = paramPathConsumer;
/*  64 */     cInitialize(paramPathConsumer);
/*  65 */     reset();
/*     */   }
/*     */   public PathStroker(PathConsumer2D paramPathConsumer2D) {
/*  68 */     if (paramPathConsumer2D == null) {
/*  69 */       throw new InternalError("null dest for path");
/*     */     }
/*  71 */     this.dest2D = paramPathConsumer2D;
/*  72 */     cInitialize2D(paramPathConsumer2D);
/*  73 */     reset();
/*     */   }
/*     */   
/*     */   protected static void classFinalize() throws Throwable {
/*  77 */     cClassFinalize();
/*     */   }
/*     */   public native void dispose();
/*     */   public PathConsumer getConsumer() {
/*  81 */     return this.dest;
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
/*     */   public native void setPenDiameter(float paramFloat) throws PRError;
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
/*     */   public native void setPenT4(float[] paramArrayOffloat) throws PRError;
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
/*     */   public native void setPenFitting(float paramFloat, int paramInt) throws PRError;
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
/*     */   public native void setCaps(int paramInt) throws PRError;
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
/*     */   public native void setCorners(int paramInt, float paramFloat) throws PRError;
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
/*     */   public native void setOutputT6(float[] paramArrayOffloat) throws PRError;
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
/*     */   public native void setOutputConsumer(PathConsumer paramPathConsumer) throws PRError;
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
/*     */   public native void reset();
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
/*     */   public native void beginPath() throws PathError;
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
/*     */   public native void beginSubpath(float paramFloat1, float paramFloat2) throws PathError;
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
/*     */   public native void appendLine(float paramFloat1, float paramFloat2) throws PathError;
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
/*     */   public native void appendQuadratic(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) throws PathError;
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
/*     */   public native void appendCubic(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) throws PathError;
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
/*     */   public native void closedSubpath() throws PathError;
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
/*     */   public native void endPath() throws PathError, PathException;
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
/*     */   public void useProxy(FastPathProducer paramFastPathProducer) throws PathError, PathException {
/* 332 */     paramFastPathProducer.sendTo(this);
/*     */   }
/*     */   public native long getCPathConsumer();
/*     */   
/*     */   private static native void cClassInitialize();
/*     */   
/*     */   private static native void cClassFinalize();
/*     */   
/*     */   private native void cInitialize(PathConsumer paramPathConsumer);
/*     */   
/*     */   private native void cInitialize2D(PathConsumer2D paramPathConsumer2D);
/*     */   
/*     */   static {
/* 345 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 348 */             System.loadLibrary("dcpr");
/* 349 */             return null;
/*     */           }
/*     */         });
/* 352 */     cClassInitialize();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\dc\pr\PathStroker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */