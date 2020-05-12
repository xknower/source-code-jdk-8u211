/*    */ package sun.java2d.opengl;
/*    */ 
/*    */ import java.awt.Composite;
/*    */ import sun.java2d.SurfaceData;
/*    */ import sun.java2d.loops.CompositeType;
/*    */ import sun.java2d.loops.GraphicsPrimitive;
/*    */ import sun.java2d.loops.GraphicsPrimitiveMgr;
/*    */ import sun.java2d.loops.SurfaceType;
/*    */ import sun.java2d.pipe.BufferedMaskBlit;
/*    */ import sun.java2d.pipe.Region;
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
/*    */ class OGLMaskBlit
/*    */   extends BufferedMaskBlit
/*    */ {
/*    */   static void register() {
/* 42 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = { new OGLMaskBlit(SurfaceType.IntArgb, CompositeType.SrcOver), new OGLMaskBlit(SurfaceType.IntArgbPre, CompositeType.SrcOver), new OGLMaskBlit(SurfaceType.IntRgb, CompositeType.SrcOver), new OGLMaskBlit(SurfaceType.IntRgb, CompositeType.SrcNoEa), new OGLMaskBlit(SurfaceType.IntBgr, CompositeType.SrcOver), new OGLMaskBlit(SurfaceType.IntBgr, CompositeType.SrcNoEa) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 50 */     GraphicsPrimitiveMgr.register(arrayOfGraphicsPrimitive);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private OGLMaskBlit(SurfaceType paramSurfaceType, CompositeType paramCompositeType) {
/* 56 */     super(OGLRenderQueue.getInstance(), paramSurfaceType, paramCompositeType, OGLSurfaceData.OpenGLSurface);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void validateContext(SurfaceData paramSurfaceData, Composite paramComposite, Region paramRegion) {
/* 64 */     OGLSurfaceData oGLSurfaceData = (OGLSurfaceData)paramSurfaceData;
/* 65 */     OGLContext.validateContext(oGLSurfaceData, oGLSurfaceData, paramRegion, paramComposite, null, null, null, 0);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\opengl\OGLMaskBlit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */