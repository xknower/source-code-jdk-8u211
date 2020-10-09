/*    */ package sun.java2d.pipe;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.Shape;
/*    */ import sun.java2d.SunGraphics2D;
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
/*    */ public class AlphaColorPipe
/*    */   implements CompositePipe, ParallelogramPipe
/*    */ {
/*    */   public Object startSequence(SunGraphics2D paramSunGraphics2D, Shape paramShape, Rectangle paramRectangle, int[] paramArrayOfint) {
/* 43 */     return paramSunGraphics2D;
/*    */   }
/*    */   
/*    */   public boolean needTile(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 47 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderPathTile(Object paramObject, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 53 */     SunGraphics2D sunGraphics2D = (SunGraphics2D)paramObject;
/*    */     
/* 55 */     sunGraphics2D.alphafill.MaskFill(sunGraphics2D, sunGraphics2D.getSurfaceData(), sunGraphics2D.composite, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfbyte, paramInt1, paramInt2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void skipTile(Object paramObject, int paramInt1, int paramInt2) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void endSequence(Object paramObject) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void fillParallelogram(SunGraphics2D paramSunGraphics2D, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10) {
/* 75 */     paramSunGraphics2D.alphafill.FillAAPgram(paramSunGraphics2D, paramSunGraphics2D.getSurfaceData(), paramSunGraphics2D.composite, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10);
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
/*    */   public void drawParallelogram(SunGraphics2D paramSunGraphics2D, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12) {
/* 87 */     paramSunGraphics2D.alphafill.DrawAAPgram(paramSunGraphics2D, paramSunGraphics2D.getSurfaceData(), paramSunGraphics2D.composite, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramDouble11, paramDouble12);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\pipe\AlphaColorPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */