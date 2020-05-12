/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.BufferedImageOp;
/*     */ import java.awt.image.ConvolveOp;
/*     */ import java.awt.image.LookupOp;
/*     */ import java.awt.image.RescaleOp;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.pipe.BufferedBufImgOps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class OGLBufImgOps
/*     */   extends BufferedBufImgOps
/*     */ {
/*     */   static boolean renderImageWithOp(SunGraphics2D paramSunGraphics2D, BufferedImage paramBufferedImage, BufferedImageOp paramBufferedImageOp, int paramInt1, int paramInt2) {
/*  55 */     if (paramBufferedImageOp instanceof ConvolveOp) {
/*  56 */       if (!isConvolveOpValid((ConvolveOp)paramBufferedImageOp)) {
/*  57 */         return false;
/*     */       }
/*  59 */     } else if (paramBufferedImageOp instanceof RescaleOp) {
/*  60 */       if (!isRescaleOpValid((RescaleOp)paramBufferedImageOp, paramBufferedImage)) {
/*  61 */         return false;
/*     */       }
/*  63 */     } else if (paramBufferedImageOp instanceof LookupOp) {
/*  64 */       if (!isLookupOpValid((LookupOp)paramBufferedImageOp, paramBufferedImage)) {
/*  65 */         return false;
/*     */       }
/*     */     } else {
/*     */       
/*  69 */       return false;
/*     */     } 
/*     */     
/*  72 */     SurfaceData surfaceData1 = paramSunGraphics2D.surfaceData;
/*  73 */     if (!(surfaceData1 instanceof OGLSurfaceData) || paramSunGraphics2D.interpolationType == 3 || paramSunGraphics2D.compositeState > 1)
/*     */     {
/*     */ 
/*     */       
/*  77 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  81 */     SurfaceData surfaceData2 = surfaceData1.getSourceSurfaceData(paramBufferedImage, 0, CompositeType.SrcOver, null);
/*     */     
/*  83 */     if (!(surfaceData2 instanceof OGLSurfaceData)) {
/*     */ 
/*     */       
/*  86 */       surfaceData2 = surfaceData1.getSourceSurfaceData(paramBufferedImage, 0, CompositeType.SrcOver, null);
/*     */       
/*  88 */       if (!(surfaceData2 instanceof OGLSurfaceData)) {
/*  89 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  95 */     OGLSurfaceData oGLSurfaceData = (OGLSurfaceData)surfaceData2;
/*  96 */     OGLGraphicsConfig oGLGraphicsConfig = oGLSurfaceData.getOGLGraphicsConfig();
/*  97 */     if (oGLSurfaceData.getType() != 3 || 
/*  98 */       !oGLGraphicsConfig.isCapPresent(262144))
/*     */     {
/* 100 */       return false;
/*     */     }
/*     */     
/* 103 */     int i = paramBufferedImage.getWidth();
/* 104 */     int j = paramBufferedImage.getHeight();
/* 105 */     OGLBlitLoops.IsoBlit(surfaceData2, surfaceData1, paramBufferedImage, paramBufferedImageOp, paramSunGraphics2D.composite, paramSunGraphics2D
/*     */         
/* 107 */         .getCompClip(), paramSunGraphics2D.transform, paramSunGraphics2D.interpolationType, 0, 0, i, j, paramInt1, paramInt2, (paramInt1 + i), (paramInt2 + j), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\opengl\OGLBufImgOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */