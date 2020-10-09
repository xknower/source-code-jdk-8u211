/*    */ package sun.java2d.d3d;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Image;
/*    */ import java.awt.geom.AffineTransform;
/*    */ import java.awt.image.AffineTransformOp;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.awt.image.BufferedImageOp;
/*    */ import sun.java2d.SunGraphics2D;
/*    */ import sun.java2d.SurfaceData;
/*    */ import sun.java2d.loops.SurfaceType;
/*    */ import sun.java2d.loops.TransformBlit;
/*    */ import sun.java2d.pipe.DrawImage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class D3DDrawImage
/*    */   extends DrawImage
/*    */ {
/*    */   protected void renderImageXform(SunGraphics2D paramSunGraphics2D, Image paramImage, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Color paramColor) {
/* 52 */     if (paramInt1 != 3) {
/* 53 */       SurfaceData surfaceData1 = paramSunGraphics2D.surfaceData;
/*    */       
/* 55 */       SurfaceData surfaceData2 = surfaceData1.getSourceSurfaceData(paramImage, 4, paramSunGraphics2D.imageComp, paramColor);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 60 */       if (surfaceData2 != null && !isBgOperation(surfaceData2, paramColor)) {
/* 61 */         SurfaceType surfaceType1 = surfaceData2.getSurfaceType();
/* 62 */         SurfaceType surfaceType2 = surfaceData1.getSurfaceType();
/* 63 */         TransformBlit transformBlit = TransformBlit.getFromCache(surfaceType1, paramSunGraphics2D.imageComp, surfaceType2);
/*    */ 
/*    */ 
/*    */         
/* 67 */         if (transformBlit != null) {
/* 68 */           transformBlit.Transform(surfaceData2, surfaceData1, paramSunGraphics2D.composite, paramSunGraphics2D
/* 69 */               .getCompClip(), paramAffineTransform, paramInt1, paramInt2, paramInt3, 0, 0, paramInt4 - paramInt2, paramInt5 - paramInt3);
/*    */ 
/*    */           
/*    */           return;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 77 */     super.renderImageXform(paramSunGraphics2D, paramImage, paramAffineTransform, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramColor);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void transformImage(SunGraphics2D paramSunGraphics2D, BufferedImage paramBufferedImage, BufferedImageOp paramBufferedImageOp, int paramInt1, int paramInt2) {
/* 85 */     if (paramBufferedImageOp != null) {
/* 86 */       if (paramBufferedImageOp instanceof AffineTransformOp) {
/* 87 */         AffineTransformOp affineTransformOp = (AffineTransformOp)paramBufferedImageOp;
/* 88 */         transformImage(paramSunGraphics2D, paramBufferedImage, paramInt1, paramInt2, affineTransformOp
/* 89 */             .getTransform(), affineTransformOp
/* 90 */             .getInterpolationType());
/*    */         return;
/*    */       } 
/* 93 */       if (D3DBufImgOps.renderImageWithOp(paramSunGraphics2D, paramBufferedImage, paramBufferedImageOp, paramInt1, paramInt2)) {
/*    */         return;
/*    */       }
/*    */       
/* 97 */       paramBufferedImage = paramBufferedImageOp.filter(paramBufferedImage, null);
/*    */     } 
/* 99 */     copyImage(paramSunGraphics2D, paramBufferedImage, paramInt1, paramInt2, null);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\d3d\D3DDrawImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */