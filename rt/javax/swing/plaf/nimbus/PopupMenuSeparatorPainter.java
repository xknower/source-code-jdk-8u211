/*    */ package javax.swing.plaf.nimbus;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.geom.Ellipse2D;
/*    */ import java.awt.geom.Path2D;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ import java.awt.geom.RoundRectangle2D;
/*    */ import javax.swing.JComponent;
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
/*    */ final class PopupMenuSeparatorPainter
/*    */   extends AbstractRegionPainter
/*    */ {
/*    */   static final int BACKGROUND_ENABLED = 1;
/*    */   private int state;
/*    */   private AbstractRegionPainter.PaintContext ctx;
/* 46 */   private Path2D path = new Path2D.Float();
/* 47 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/* 48 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/* 49 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   private Color color1 = decodeColor("nimbusBlueGrey", -0.008547008F, -0.03830409F, -0.039215684F, 0);
/*    */ 
/*    */   
/*    */   private Object[] componentColors;
/*    */ 
/*    */ 
/*    */   
/*    */   public PopupMenuSeparatorPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/* 62 */     this.state = paramInt;
/* 63 */     this.ctx = paramPaintContext;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 69 */     this.componentColors = paramArrayOfObject;
/*    */ 
/*    */     
/* 72 */     switch (this.state) { case 1:
/* 73 */         paintBackgroundEnabled(paramGraphics2D);
/*    */         break; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 82 */     return this.ctx;
/*    */   }
/*    */   
/*    */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 86 */     this.rect = decodeRect1();
/* 87 */     paramGraphics2D.setPaint(this.color1);
/* 88 */     paramGraphics2D.fill(this.rect);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Rectangle2D decodeRect1() {
/* 95 */     this.rect.setRect(decodeX(0.0F), 
/* 96 */         decodeY(1.0F), (
/* 97 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 98 */         decodeY(2.0F) - decodeY(1.0F)));
/* 99 */     return this.rect;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\PopupMenuSeparatorPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */