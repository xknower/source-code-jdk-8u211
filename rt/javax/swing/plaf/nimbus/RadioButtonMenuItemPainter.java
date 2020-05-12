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
/*     */ final class RadioButtonMenuItemPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   static final int BACKGROUND_MOUSEOVER = 3;
/*     */   static final int BACKGROUND_SELECTED_MOUSEOVER = 4;
/*     */   static final int CHECKICON_DISABLED_SELECTED = 5;
/*     */   static final int CHECKICON_ENABLED_SELECTED = 6;
/*     */   static final int CHECKICON_SELECTED_MOUSEOVER = 7;
/*     */   static final int ICON_DISABLED = 8;
/*     */   static final int ICON_ENABLED = 9;
/*     */   static final int ICON_MOUSEOVER = 10;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  55 */   private Path2D path = new Path2D.Float();
/*  56 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  57 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  58 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private Color color1 = decodeColor("nimbusSelection", 0.0F, 0.0F, 0.0F, 0);
/*  64 */   private Color color2 = decodeColor("nimbusBlueGrey", 0.0F, -0.08983666F, -0.17647058F, 0);
/*  65 */   private Color color3 = decodeColor("nimbusBlueGrey", 0.055555582F, -0.09663743F, -0.4627451F, 0);
/*  66 */   private Color color4 = decodeColor("nimbusBlueGrey", 0.0F, -0.110526316F, 0.25490195F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public RadioButtonMenuItemPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
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
/*  84 */     switch (this.state) { case 3:
/*  85 */         paintBackgroundMouseOver(paramGraphics2D); break;
/*  86 */       case 4: paintBackgroundSelectedAndMouseOver(paramGraphics2D); break;
/*  87 */       case 5: paintcheckIconDisabledAndSelected(paramGraphics2D); break;
/*  88 */       case 6: paintcheckIconEnabledAndSelected(paramGraphics2D); break;
/*  89 */       case 7: paintcheckIconSelectedAndMouseOver(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/*  98 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundMouseOver(Graphics2D paramGraphics2D) {
/* 102 */     this.rect = decodeRect1();
/* 103 */     paramGraphics2D.setPaint(this.color1);
/* 104 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelectedAndMouseOver(Graphics2D paramGraphics2D) {
/* 109 */     this.rect = decodeRect1();
/* 110 */     paramGraphics2D.setPaint(this.color1);
/* 111 */     paramGraphics2D.fill(this.rect);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintcheckIconDisabledAndSelected(Graphics2D paramGraphics2D) {
/* 116 */     this.path = decodePath1();
/* 117 */     paramGraphics2D.setPaint(this.color2);
/* 118 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintcheckIconEnabledAndSelected(Graphics2D paramGraphics2D) {
/* 123 */     this.path = decodePath2();
/* 124 */     paramGraphics2D.setPaint(this.color3);
/* 125 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintcheckIconSelectedAndMouseOver(Graphics2D paramGraphics2D) {
/* 130 */     this.path = decodePath2();
/* 131 */     paramGraphics2D.setPaint(this.color4);
/* 132 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle2D decodeRect1() {
/* 139 */     this.rect.setRect(decodeX(1.0F), 
/* 140 */         decodeY(1.0F), (
/* 141 */         decodeX(2.0F) - decodeX(1.0F)), (
/* 142 */         decodeY(2.0F) - decodeY(1.0F)));
/* 143 */     return this.rect;
/*     */   }
/*     */   
/*     */   private Path2D decodePath1() {
/* 147 */     this.path.reset();
/* 148 */     this.path.moveTo(decodeX(0.0F), decodeY(2.097561F));
/* 149 */     this.path.lineTo(decodeX(0.90975606F), decodeY(0.20243903F));
/* 150 */     this.path.lineTo(decodeX(3.0F), decodeY(2.102439F));
/* 151 */     this.path.lineTo(decodeX(0.90731704F), decodeY(3.0F));
/* 152 */     this.path.lineTo(decodeX(0.0F), decodeY(2.097561F));
/* 153 */     this.path.closePath();
/* 154 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath2() {
/* 158 */     this.path.reset();
/* 159 */     this.path.moveTo(decodeX(0.0024390244F), decodeY(2.097561F));
/* 160 */     this.path.lineTo(decodeX(0.90975606F), decodeY(0.20243903F));
/* 161 */     this.path.lineTo(decodeX(3.0F), decodeY(2.102439F));
/* 162 */     this.path.lineTo(decodeX(0.90731704F), decodeY(3.0F));
/* 163 */     this.path.lineTo(decodeX(0.0024390244F), decodeY(2.097561F));
/* 164 */     this.path.closePath();
/* 165 */     return this.path;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\RadioButtonMenuItemPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */