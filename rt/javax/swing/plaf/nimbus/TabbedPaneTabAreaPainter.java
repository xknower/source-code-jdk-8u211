/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
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
/*     */ final class TabbedPaneTabAreaPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_ENABLED = 1;
/*     */   static final int BACKGROUND_DISABLED = 2;
/*     */   static final int BACKGROUND_ENABLED_MOUSEOVER = 3;
/*     */   static final int BACKGROUND_ENABLED_PRESSED = 4;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  49 */   private Path2D path = new Path2D.Float();
/*  50 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  51 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  52 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private Color color1 = new Color(255, 200, 0, 255);
/*  58 */   private Color color2 = decodeColor("nimbusBase", 0.08801502F, 0.3642857F, -0.4784314F, 0);
/*  59 */   private Color color3 = decodeColor("nimbusBase", 5.1498413E-4F, -0.45471883F, 0.31764704F, 0);
/*  60 */   private Color color4 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4633005F, 0.3607843F, 0);
/*  61 */   private Color color5 = decodeColor("nimbusBase", 0.05468172F, -0.58308274F, 0.19607842F, 0);
/*  62 */   private Color color6 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.54901963F, 0);
/*  63 */   private Color color7 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4690476F, 0.39215684F, 0);
/*  64 */   private Color color8 = decodeColor("nimbusBase", 5.1498413E-4F, -0.47635174F, 0.4352941F, 0);
/*  65 */   private Color color9 = decodeColor("nimbusBase", 0.0F, -0.05401492F, 0.05098039F, 0);
/*  66 */   private Color color10 = decodeColor("nimbusBase", 0.0F, -0.09303135F, 0.09411764F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedPaneTabAreaPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  74 */     this.state = paramInt;
/*  75 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  81 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/*  84 */     switch (this.state) { case 1:
/*  85 */         paintBackgroundEnabled(paramGraphics2D); break;
/*  86 */       case 2: paintBackgroundDisabled(paramGraphics2D); break;
/*  87 */       case 3: paintBackgroundEnabledAndMouseOver(paramGraphics2D); break;
/*  88 */       case 4: paintBackgroundEnabledAndPressed(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/*  97 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 101 */     this.rect = decodeRect1();
/* 102 */     paramGraphics2D.setPaint(this.color1);
/* 103 */     paramGraphics2D.fill(this.rect);
/* 104 */     this.rect = decodeRect2();
/* 105 */     paramGraphics2D.setPaint(decodeGradient1(this.rect));
/* 106 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundDisabled(Graphics2D paramGraphics2D) {
/* 111 */     this.rect = decodeRect2();
/* 112 */     paramGraphics2D.setPaint(decodeGradient2(this.rect));
/* 113 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndMouseOver(Graphics2D paramGraphics2D) {
/* 118 */     this.rect = decodeRect2();
/* 119 */     paramGraphics2D.setPaint(decodeGradient3(this.rect));
/* 120 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndPressed(Graphics2D paramGraphics2D) {
/* 125 */     this.rect = decodeRect2();
/* 126 */     paramGraphics2D.setPaint(decodeGradient4(this.rect));
/* 127 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 134 */     this.rect.setRect(decodeX(0.0F), 
/* 135 */         decodeY(1.0F), (
/* 136 */         decodeX(0.0F) - decodeX(0.0F)), (
/* 137 */         decodeY(1.0F) - decodeY(1.0F)));
/* 138 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Rectangle2D decodeRect2() {
/* 142 */     this.rect.setRect(decodeX(0.0F), 
/* 143 */         decodeY(2.1666667F), (
/* 144 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 145 */         decodeY(3.0F) - decodeY(2.1666667F)));
/* 146 */     return this.rect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 152 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 153 */     float f1 = (float)rectangle2D.getX();
/* 154 */     float f2 = (float)rectangle2D.getY();
/* 155 */     float f3 = (float)rectangle2D.getWidth();
/* 156 */     float f4 = (float)rectangle2D.getHeight();
/* 157 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.08387097F, 0.09677419F, 0.10967742F, 0.43709677F, 0.7645161F, 0.7758064F, 0.7870968F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 160 */           decodeColor(this.color2, this.color3, 0.5F), this.color3, 
/*     */           
/* 162 */           decodeColor(this.color3, this.color4, 0.5F), this.color4, 
/*     */           
/* 164 */           decodeColor(this.color4, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 169 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 170 */     float f1 = (float)rectangle2D.getX();
/* 171 */     float f2 = (float)rectangle2D.getY();
/* 172 */     float f3 = (float)rectangle2D.getWidth();
/* 173 */     float f4 = (float)rectangle2D.getHeight();
/* 174 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.08387097F, 0.09677419F, 0.10967742F, 0.43709677F, 0.7645161F, 0.7758064F, 0.7870968F }, new Color[] { this.color5, 
/*     */ 
/*     */           
/* 177 */           decodeColor(this.color5, this.color3, 0.5F), this.color3, 
/*     */           
/* 179 */           decodeColor(this.color3, this.color4, 0.5F), this.color4, 
/*     */           
/* 181 */           decodeColor(this.color4, this.color5, 0.5F), this.color5 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 186 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 187 */     float f1 = (float)rectangle2D.getX();
/* 188 */     float f2 = (float)rectangle2D.getY();
/* 189 */     float f3 = (float)rectangle2D.getWidth();
/* 190 */     float f4 = (float)rectangle2D.getHeight();
/* 191 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.08387097F, 0.09677419F, 0.10967742F, 0.43709677F, 0.7645161F, 0.7758064F, 0.7870968F }, new Color[] { this.color6, 
/*     */ 
/*     */           
/* 194 */           decodeColor(this.color6, this.color7, 0.5F), this.color7, 
/*     */           
/* 196 */           decodeColor(this.color7, this.color8, 0.5F), this.color8, 
/*     */           
/* 198 */           decodeColor(this.color8, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 203 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 204 */     float f1 = (float)rectangle2D.getX();
/* 205 */     float f2 = (float)rectangle2D.getY();
/* 206 */     float f3 = (float)rectangle2D.getWidth();
/* 207 */     float f4 = (float)rectangle2D.getHeight();
/* 208 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.08387097F, 0.09677419F, 0.10967742F, 0.43709677F, 0.7645161F, 0.7758064F, 0.7870968F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 211 */           decodeColor(this.color2, this.color9, 0.5F), this.color9, 
/*     */           
/* 213 */           decodeColor(this.color9, this.color10, 0.5F), this.color10, 
/*     */           
/* 215 */           decodeColor(this.color10, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\TabbedPaneTabAreaPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */