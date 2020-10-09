/*     */ package sun.java2d.d3d;
/*     */ 
/*     */ import java.awt.Composite;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.BufferedImageOp;
/*     */ import sun.java2d.ScreenUpdateManager;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.GraphicsPrimitive;
/*     */ import sun.java2d.loops.GraphicsPrimitiveMgr;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ import sun.java2d.pipe.Region;
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
/*     */ final class D3DBlitLoops
/*     */ {
/*     */   private static final int OFFSET_SRCTYPE = 16;
/*     */   private static final int OFFSET_HINT = 8;
/*     */   private static final int OFFSET_TEXTURE = 3;
/*     */   private static final int OFFSET_RTT = 2;
/*     */   private static final int OFFSET_XFORM = 1;
/*     */   private static final int OFFSET_ISOBLIT = 0;
/*     */   
/*     */   static void register() {
/*  54 */     D3DSwToSurfaceBlit d3DSwToSurfaceBlit = new D3DSwToSurfaceBlit(SurfaceType.IntArgbPre, 1);
/*     */ 
/*     */     
/*  57 */     D3DSwToTextureBlit d3DSwToTextureBlit = new D3DSwToTextureBlit(SurfaceType.IntArgbPre, 1);
/*     */ 
/*     */     
/*  60 */     D3DSwToSurfaceTransform d3DSwToSurfaceTransform = new D3DSwToSurfaceTransform(SurfaceType.IntArgbPre, 1);
/*     */ 
/*     */     
/*  63 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = { new D3DSurfaceToGDIWindowSurfaceBlit(), new D3DSurfaceToGDIWindowSurfaceScale(), new D3DSurfaceToGDIWindowSurfaceTransform(), new D3DSurfaceToSurfaceBlit(), new D3DSurfaceToSurfaceScale(), new D3DSurfaceToSurfaceTransform(), new D3DRTTSurfaceToSurfaceBlit(), new D3DRTTSurfaceToSurfaceScale(), new D3DRTTSurfaceToSurfaceTransform(), new D3DSurfaceToSwBlit(SurfaceType.IntArgb, 0), d3DSwToSurfaceBlit, new D3DSwToSurfaceBlit(SurfaceType.IntArgb, 0), new D3DSwToSurfaceBlit(SurfaceType.IntRgb, 3), new D3DSwToSurfaceBlit(SurfaceType.IntBgr, 4), new D3DSwToSurfaceBlit(SurfaceType.ThreeByteBgr, 9), new D3DSwToSurfaceBlit(SurfaceType.Ushort565Rgb, 5), new D3DSwToSurfaceBlit(SurfaceType.Ushort555Rgb, 6), new D3DSwToSurfaceBlit(SurfaceType.ByteIndexed, 7), new D3DGeneralBlit(D3DSurfaceData.D3DSurface, CompositeType.AnyAlpha, d3DSwToSurfaceBlit), new D3DSwToSurfaceScale(SurfaceType.IntArgb, 0), new D3DSwToSurfaceScale(SurfaceType.IntArgbPre, 1), new D3DSwToSurfaceScale(SurfaceType.IntRgb, 3), new D3DSwToSurfaceScale(SurfaceType.IntBgr, 4), new D3DSwToSurfaceScale(SurfaceType.ThreeByteBgr, 9), new D3DSwToSurfaceScale(SurfaceType.Ushort565Rgb, 5), new D3DSwToSurfaceScale(SurfaceType.Ushort555Rgb, 6), new D3DSwToSurfaceScale(SurfaceType.ByteIndexed, 7), new D3DSwToSurfaceTransform(SurfaceType.IntArgb, 0), new D3DSwToSurfaceTransform(SurfaceType.IntRgb, 3), new D3DSwToSurfaceTransform(SurfaceType.IntBgr, 4), new D3DSwToSurfaceTransform(SurfaceType.ThreeByteBgr, 9), new D3DSwToSurfaceTransform(SurfaceType.Ushort565Rgb, 5), new D3DSwToSurfaceTransform(SurfaceType.Ushort555Rgb, 6), new D3DSwToSurfaceTransform(SurfaceType.ByteIndexed, 7), d3DSwToSurfaceTransform, new D3DGeneralTransformedBlit(d3DSwToSurfaceTransform), new D3DTextureToSurfaceBlit(), new D3DTextureToSurfaceScale(), new D3DTextureToSurfaceTransform(), d3DSwToTextureBlit, new D3DSwToTextureBlit(SurfaceType.IntRgb, 3), new D3DSwToTextureBlit(SurfaceType.IntArgb, 0), new D3DSwToTextureBlit(SurfaceType.IntBgr, 4), new D3DSwToTextureBlit(SurfaceType.ThreeByteBgr, 9), new D3DSwToTextureBlit(SurfaceType.Ushort565Rgb, 5), new D3DSwToTextureBlit(SurfaceType.Ushort555Rgb, 6), new D3DSwToTextureBlit(SurfaceType.ByteIndexed, 7), new D3DGeneralBlit(D3DSurfaceData.D3DTexture, CompositeType.SrcNoEa, d3DSwToTextureBlit) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     GraphicsPrimitiveMgr.register(arrayOfGraphicsPrimitive);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static int createPackedParams(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt1, int paramInt2) {
/* 198 */     return paramInt2 << 16 | paramInt1 << 8 | (paramBoolean2 ? 1 : 0) << 3 | (paramBoolean3 ? 1 : 0) << 2 | (paramBoolean4 ? 1 : 0) << 1 | (paramBoolean1 ? 1 : 0) << 0;
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
/*     */ 
/*     */   
/*     */   private static void enqueueBlit(RenderQueue paramRenderQueue, SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 220 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/* 221 */     paramRenderQueue.ensureCapacityAndAlignment(72, 24);
/* 222 */     renderBuffer.putInt(31);
/* 223 */     renderBuffer.putInt(paramInt1);
/* 224 */     renderBuffer.putInt(paramInt2).putInt(paramInt3);
/* 225 */     renderBuffer.putInt(paramInt4).putInt(paramInt5);
/* 226 */     renderBuffer.putDouble(paramDouble1).putDouble(paramDouble2);
/* 227 */     renderBuffer.putDouble(paramDouble3).putDouble(paramDouble4);
/* 228 */     renderBuffer.putLong(paramSurfaceData1.getNativeOps());
/* 229 */     renderBuffer.putLong(paramSurfaceData2.getNativeOps());
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
/*     */   static void Blit(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt6, boolean paramBoolean) {
/* 241 */     int i = 0;
/* 242 */     if (paramSurfaceData1.getTransparency() == 1) {
/* 243 */       i |= 0x1;
/*     */     }
/*     */     
/* 246 */     D3DSurfaceData d3DSurfaceData = (D3DSurfaceData)paramSurfaceData2;
/* 247 */     D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/* 248 */     d3DRenderQueue.lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 253 */       d3DRenderQueue.addReference(paramSurfaceData1);
/*     */       
/* 255 */       if (paramBoolean) {
/*     */ 
/*     */         
/* 258 */         D3DContext.setScratchSurface(d3DSurfaceData.getContext());
/*     */       } else {
/* 260 */         D3DContext.validateContext(d3DSurfaceData, d3DSurfaceData, paramRegion, paramComposite, paramAffineTransform, null, null, i);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 265 */       int j = createPackedParams(false, paramBoolean, false, (paramAffineTransform != null), paramInt1, paramInt6);
/*     */ 
/*     */       
/* 268 */       enqueueBlit(d3DRenderQueue, paramSurfaceData1, paramSurfaceData2, j, paramInt2, paramInt3, paramInt4, paramInt5, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 275 */       d3DRenderQueue.flushNow();
/*     */     } finally {
/* 277 */       d3DRenderQueue.unlock();
/*     */     } 
/*     */     
/* 280 */     if (d3DSurfaceData.getType() == 1) {
/*     */ 
/*     */ 
/*     */       
/* 284 */       D3DScreenUpdateManager d3DScreenUpdateManager = (D3DScreenUpdateManager)ScreenUpdateManager.getInstance();
/* 285 */       d3DScreenUpdateManager.runUpdateNow();
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
/*     */   static void IsoBlit(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, BufferedImage paramBufferedImage, BufferedImageOp paramBufferedImageOp, Composite paramComposite, Region paramRegion, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean) {
/* 305 */     int i = 0;
/* 306 */     if (paramSurfaceData1.getTransparency() == 1) {
/* 307 */       i |= 0x1;
/*     */     }
/*     */     
/* 310 */     D3DSurfaceData d3DSurfaceData = (D3DSurfaceData)paramSurfaceData2;
/* 311 */     D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/* 312 */     boolean bool = false;
/* 313 */     d3DRenderQueue.lock();
/*     */     try {
/* 315 */       D3DSurfaceData d3DSurfaceData1 = (D3DSurfaceData)paramSurfaceData1;
/* 316 */       int j = d3DSurfaceData1.getType();
/* 317 */       D3DSurfaceData d3DSurfaceData2 = d3DSurfaceData1;
/* 318 */       if (j == 3) {
/* 319 */         bool = false;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 324 */         bool = true;
/*     */       } 
/*     */       
/* 327 */       D3DContext.validateContext(d3DSurfaceData2, d3DSurfaceData, paramRegion, paramComposite, paramAffineTransform, null, null, i);
/*     */ 
/*     */ 
/*     */       
/* 331 */       if (paramBufferedImageOp != null) {
/* 332 */         D3DBufImgOps.enableBufImgOp(d3DRenderQueue, d3DSurfaceData1, paramBufferedImage, paramBufferedImageOp);
/*     */       }
/*     */       
/* 335 */       int k = createPackedParams(true, paramBoolean, bool, (paramAffineTransform != null), paramInt1, 0);
/*     */ 
/*     */       
/* 338 */       enqueueBlit(d3DRenderQueue, paramSurfaceData1, paramSurfaceData2, k, paramInt2, paramInt3, paramInt4, paramInt5, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 343 */       if (paramBufferedImageOp != null) {
/* 344 */         D3DBufImgOps.disableBufImgOp(d3DRenderQueue, paramBufferedImageOp);
/*     */       }
/*     */     } finally {
/* 347 */       d3DRenderQueue.unlock();
/*     */     } 
/*     */     
/* 350 */     if (bool && d3DSurfaceData.getType() == 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 355 */       D3DScreenUpdateManager d3DScreenUpdateManager = (D3DScreenUpdateManager)ScreenUpdateManager.getInstance();
/* 356 */       d3DScreenUpdateManager.runUpdateNow();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\d3d\D3DBlitLoops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */