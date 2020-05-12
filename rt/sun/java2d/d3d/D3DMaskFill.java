/*    */ package sun.java2d.d3d;
/*    */ 
/*    */ import java.awt.Composite;
/*    */ import sun.java2d.InvalidPipeException;
/*    */ import sun.java2d.SunGraphics2D;
/*    */ import sun.java2d.loops.CompositeType;
/*    */ import sun.java2d.loops.GraphicsPrimitive;
/*    */ import sun.java2d.loops.GraphicsPrimitiveMgr;
/*    */ import sun.java2d.loops.SurfaceType;
/*    */ import sun.java2d.pipe.BufferedMaskFill;
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
/*    */ class D3DMaskFill
/*    */   extends BufferedMaskFill
/*    */ {
/*    */   static void register() {
/* 42 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = { new D3DMaskFill(SurfaceType.AnyColor, CompositeType.SrcOver), new D3DMaskFill(SurfaceType.OpaqueColor, CompositeType.SrcNoEa), new D3DMaskFill(SurfaceType.GradientPaint, CompositeType.SrcOver), new D3DMaskFill(SurfaceType.OpaqueGradientPaint, CompositeType.SrcNoEa), new D3DMaskFill(SurfaceType.LinearGradientPaint, CompositeType.SrcOver), new D3DMaskFill(SurfaceType.OpaqueLinearGradientPaint, CompositeType.SrcNoEa), new D3DMaskFill(SurfaceType.RadialGradientPaint, CompositeType.SrcOver), new D3DMaskFill(SurfaceType.OpaqueRadialGradientPaint, CompositeType.SrcNoEa), new D3DMaskFill(SurfaceType.TexturePaint, CompositeType.SrcOver), new D3DMaskFill(SurfaceType.OpaqueTexturePaint, CompositeType.SrcNoEa) };
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
/* 54 */     GraphicsPrimitiveMgr.register(arrayOfGraphicsPrimitive);
/*    */   }
/*    */   
/*    */   protected D3DMaskFill(SurfaceType paramSurfaceType, CompositeType paramCompositeType) {
/* 58 */     super(D3DRenderQueue.getInstance(), paramSurfaceType, paramCompositeType, D3DSurfaceData.D3DSurface);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected native void maskFill(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, byte[] paramArrayOfbyte);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void validateContext(SunGraphics2D paramSunGraphics2D, Composite paramComposite, int paramInt) {
/*    */     D3DSurfaceData d3DSurfaceData;
/*    */     try {
/* 73 */       d3DSurfaceData = (D3DSurfaceData)paramSunGraphics2D.surfaceData;
/* 74 */     } catch (ClassCastException classCastException) {
/* 75 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*    */     } 
/*    */     
/* 78 */     D3DContext.validateContext(d3DSurfaceData, d3DSurfaceData, paramSunGraphics2D
/* 79 */         .getCompClip(), paramComposite, null, paramSunGraphics2D.paint, paramSunGraphics2D, paramInt);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\d3d\D3DMaskFill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */