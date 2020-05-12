/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Path2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.geom.RoundRectangle2D;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TextPanePainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   static final int BACKGROUND_SELECTED = 3;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  48 */   private Path2D path = new Path2D.Float();
/*  49 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  50 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  51 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private Color color1 = decodeColor("nimbusBlueGrey", -0.015872955F, -0.07995863F, 0.15294117F, 0);
/*  57 */   private Color color2 = decodeColor("nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public TextPanePainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  65 */     this.state = paramInt;
/*  66 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  72 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/*  75 */     switch (this.state) { case 1:
/*  76 */         paintBackgroundDisabled(paramGraphics2D); break;
/*  77 */       case 2: paintBackgroundEnabled(paramGraphics2D); break;
/*  78 */       case 3: paintBackgroundSelected(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/*  87 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/*  91 */     this.rect = decodeRect1();
/*  92 */     paramGraphics2D.setPaint(this.color1);
/*  93 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/*  98 */     this.rect = decodeRect1();
/*  99 */     paramGraphics2D.setPaint(this.color2);
/* 100 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelected(Graphics2D paramGraphics2D) {
/* 105 */     this.rect = decodeRect1();
/* 106 */     paramGraphics2D.setPaint(this.color2);
/* 107 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 114 */     this.rect.setRect(decodeX(0.0F), 
/* 115 */         decodeY(0.0F), (
/* 116 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 117 */         decodeY(3.0F) - decodeY(0.0F)));
/* 118 */     return this.rect;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\TextPanePainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */