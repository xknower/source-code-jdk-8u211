/*     */ package sun.java2d.d3d;
/*     */ 
/*     */ import java.awt.LinearGradientPaint;
/*     */ import java.awt.MultipleGradientPaint;
/*     */ import java.awt.TexturePaint;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class D3DPaints
/*     */ {
/*  48 */   private static Map<Integer, D3DPaints> impls = new HashMap<>(4, 1.0F);
/*     */ 
/*     */   
/*     */   static {
/*  52 */     impls.put(Integer.valueOf(2), new Gradient());
/*  53 */     impls.put(Integer.valueOf(3), new LinearGradient());
/*  54 */     impls.put(Integer.valueOf(4), new RadialGradient());
/*  55 */     impls.put(Integer.valueOf(5), new Texture());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract boolean isPaintValid(SunGraphics2D paramSunGraphics2D);
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isValid(SunGraphics2D paramSunGraphics2D) {
/*  66 */     D3DPaints d3DPaints = impls.get(Integer.valueOf(paramSunGraphics2D.paintState));
/*  67 */     return (d3DPaints != null && d3DPaints.isPaintValid(paramSunGraphics2D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Gradient
/*     */     extends D3DPaints
/*     */   {
/*     */     private Gradient() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isPaintValid(SunGraphics2D param1SunGraphics2D) {
/*  90 */       D3DSurfaceData d3DSurfaceData = (D3DSurfaceData)param1SunGraphics2D.surfaceData;
/*     */       
/*  92 */       D3DGraphicsDevice d3DGraphicsDevice = (D3DGraphicsDevice)d3DSurfaceData.getDeviceConfiguration().getDevice();
/*  93 */       return d3DGraphicsDevice.isCapPresent(65536);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Texture
/*     */     extends D3DPaints
/*     */   {
/*     */     private Texture() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isPaintValid(SunGraphics2D param1SunGraphics2D) {
/* 114 */       TexturePaint texturePaint = (TexturePaint)param1SunGraphics2D.paint;
/* 115 */       D3DSurfaceData d3DSurfaceData1 = (D3DSurfaceData)param1SunGraphics2D.surfaceData;
/* 116 */       BufferedImage bufferedImage = texturePaint.getImage();
/*     */ 
/*     */ 
/*     */       
/* 120 */       D3DGraphicsDevice d3DGraphicsDevice = (D3DGraphicsDevice)d3DSurfaceData1.getDeviceConfiguration().getDevice();
/* 121 */       int i = bufferedImage.getWidth();
/* 122 */       int j = bufferedImage.getHeight();
/* 123 */       if (!d3DGraphicsDevice.isCapPresent(32) && ((
/* 124 */         i & i - 1) != 0 || (j & j - 1) != 0)) {
/* 125 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 129 */       if (!d3DGraphicsDevice.isCapPresent(64) && i != j)
/*     */       {
/* 131 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 135 */       SurfaceData surfaceData = d3DSurfaceData1.getSourceSurfaceData(bufferedImage, 0, CompositeType.SrcOver, null);
/*     */       
/* 137 */       if (!(surfaceData instanceof D3DSurfaceData)) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 142 */         surfaceData = d3DSurfaceData1.getSourceSurfaceData(bufferedImage, 0, CompositeType.SrcOver, null);
/*     */         
/* 144 */         if (!(surfaceData instanceof D3DSurfaceData)) {
/* 145 */           return false;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 150 */       D3DSurfaceData d3DSurfaceData2 = (D3DSurfaceData)surfaceData;
/* 151 */       if (d3DSurfaceData2.getType() != 3) {
/* 152 */         return false;
/*     */       }
/*     */       
/* 155 */       return true;
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
/*     */   private static abstract class MultiGradient
/*     */     extends D3DPaints
/*     */   {
/*     */     public static final int MULTI_MAX_FRACTIONS_D3D = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isPaintValid(SunGraphics2D param1SunGraphics2D) {
/* 185 */       MultipleGradientPaint multipleGradientPaint = (MultipleGradientPaint)param1SunGraphics2D.paint;
/*     */ 
/*     */       
/* 188 */       if ((multipleGradientPaint.getFractions()).length > 8) {
/* 189 */         return false;
/*     */       }
/*     */       
/* 192 */       D3DSurfaceData d3DSurfaceData = (D3DSurfaceData)param1SunGraphics2D.surfaceData;
/*     */       
/* 194 */       D3DGraphicsDevice d3DGraphicsDevice = (D3DGraphicsDevice)d3DSurfaceData.getDeviceConfiguration().getDevice();
/* 195 */       if (!d3DGraphicsDevice.isCapPresent(65536)) {
/* 196 */         return false;
/*     */       }
/* 198 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LinearGradient
/*     */     extends MultiGradient
/*     */   {
/*     */     private LinearGradient() {}
/*     */     
/*     */     boolean isPaintValid(SunGraphics2D param1SunGraphics2D) {
/* 209 */       LinearGradientPaint linearGradientPaint = (LinearGradientPaint)param1SunGraphics2D.paint;
/*     */       
/* 211 */       if ((linearGradientPaint.getFractions()).length == 2 && linearGradientPaint
/* 212 */         .getCycleMethod() != MultipleGradientPaint.CycleMethod.REPEAT && linearGradientPaint
/* 213 */         .getColorSpace() != MultipleGradientPaint.ColorSpaceType.LINEAR_RGB) {
/*     */         
/* 215 */         D3DSurfaceData d3DSurfaceData = (D3DSurfaceData)param1SunGraphics2D.surfaceData;
/*     */         
/* 217 */         D3DGraphicsDevice d3DGraphicsDevice = (D3DGraphicsDevice)d3DSurfaceData.getDeviceConfiguration().getDevice();
/* 218 */         if (d3DGraphicsDevice.isCapPresent(65536))
/*     */         {
/*     */           
/* 221 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 225 */       return super.isPaintValid(param1SunGraphics2D);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RadialGradient extends MultiGradient {
/*     */     private RadialGradient() {}
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\d3d\D3DPaints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */