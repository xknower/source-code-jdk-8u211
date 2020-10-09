/*    */ package sun.java2d.d3d;
/*    */ 
/*    */ import java.awt.Composite;
/*    */ import sun.font.GlyphList;
/*    */ import sun.java2d.SunGraphics2D;
/*    */ import sun.java2d.loops.GraphicsPrimitive;
/*    */ import sun.java2d.pipe.BufferedTextPipe;
/*    */ import sun.java2d.pipe.RenderQueue;
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
/*    */ class D3DTextRenderer
/*    */   extends BufferedTextPipe
/*    */ {
/*    */   D3DTextRenderer(RenderQueue paramRenderQueue) {
/* 38 */     super(paramRenderQueue);
/*    */   }
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
/*    */   protected void validateContext(SunGraphics2D paramSunGraphics2D, Composite paramComposite) {
/* 51 */     D3DSurfaceData d3DSurfaceData = (D3DSurfaceData)paramSunGraphics2D.surfaceData;
/* 52 */     D3DContext.validateContext(d3DSurfaceData, d3DSurfaceData, paramSunGraphics2D
/* 53 */         .getCompClip(), paramComposite, null, paramSunGraphics2D.paint, paramSunGraphics2D, 0);
/*    */   }
/*    */   
/*    */   protected native void drawGlyphList(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt2, float paramFloat1, float paramFloat2, long[] paramArrayOflong, float[] paramArrayOffloat);
/*    */   
/*    */   D3DTextRenderer traceWrap() {
/* 59 */     return new Tracer(this);
/*    */   }
/*    */   
/*    */   private static class Tracer extends D3DTextRenderer {
/*    */     Tracer(D3DTextRenderer param1D3DTextRenderer) {
/* 64 */       super(param1D3DTextRenderer.rq);
/*    */     }
/*    */     protected void drawGlyphList(SunGraphics2D param1SunGraphics2D, GlyphList param1GlyphList) {
/* 67 */       GraphicsPrimitive.tracePrimitive("D3DDrawGlyphs");
/* 68 */       super.drawGlyphList(param1SunGraphics2D, param1GlyphList);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\d3d\D3DTextRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */