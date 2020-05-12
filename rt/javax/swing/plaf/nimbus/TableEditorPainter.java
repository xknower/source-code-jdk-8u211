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
/*     */ final class TableEditorPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   static final int BACKGROUND_ENABLED_FOCUSED = 3;
/*     */   static final int BACKGROUND_SELECTED = 4;
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
/*  57 */   private Color color1 = decodeColor("nimbusLightBackground", 0.0F, 0.0F, 0.0F, 0);
/*  58 */   private Color color2 = decodeColor("nimbusFocus", 0.0F, 0.0F, 0.0F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public TableEditorPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  66 */     this.state = paramInt;
/*  67 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  73 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/*  76 */     switch (this.state) { case 2:
/*  77 */         paintBackgroundEnabled(paramGraphics2D); break;
/*  78 */       case 3: paintBackgroundEnabledAndFocused(paramGraphics2D);
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
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/*  91 */     this.rect = decodeRect1();
/*  92 */     paramGraphics2D.setPaint(this.color1);
/*  93 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndFocused(Graphics2D paramGraphics2D) {
/*  98 */     this.path = decodePath1();
/*  99 */     paramGraphics2D.setPaint(this.color2);
/* 100 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 107 */     this.rect.setRect(decodeX(0.0F), 
/* 108 */         decodeY(0.0F), (
/* 109 */         decodeX(3.0F) - decodeX(0.0F)), (
/* 110 */         decodeY(3.0F) - decodeY(0.0F)));
/* 111 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath1() {
/* 115 */     this.path.reset();
/* 116 */     this.path.moveTo(decodeX(0.0F), decodeY(0.0F));
/* 117 */     this.path.lineTo(decodeX(0.0F), decodeY(3.0F));
/* 118 */     this.path.lineTo(decodeX(3.0F), decodeY(3.0F));
/* 119 */     this.path.lineTo(decodeX(3.0F), decodeY(0.0F));
/* 120 */     this.path.lineTo(decodeX(0.24000001F), decodeY(0.0F));
/* 121 */     this.path.lineTo(decodeX(0.24000001F), decodeY(0.24000001F));
/* 122 */     this.path.lineTo(decodeX(2.7600007F), decodeY(0.24000001F));
/* 123 */     this.path.lineTo(decodeX(2.7600007F), decodeY(2.7599998F));
/* 124 */     this.path.lineTo(decodeX(0.24000001F), decodeY(2.7599998F));
/* 125 */     this.path.lineTo(decodeX(0.24000001F), decodeY(0.0F));
/* 126 */     this.path.lineTo(decodeX(0.0F), decodeY(0.0F));
/* 127 */     this.path.closePath();
/* 128 */     return this.path;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\TableEditorPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */