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
/*     */ final class ComboBoxArrowButtonPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   static final int BACKGROUND_ENABLED_MOUSEOVER = 3;
/*     */   static final int BACKGROUND_ENABLED_PRESSED = 4;
/*     */   static final int BACKGROUND_DISABLED_EDITABLE = 5;
/*     */   static final int BACKGROUND_ENABLED_EDITABLE = 6;
/*     */   static final int BACKGROUND_MOUSEOVER_EDITABLE = 7;
/*     */   static final int BACKGROUND_PRESSED_EDITABLE = 8;
/*     */   static final int BACKGROUND_SELECTED_EDITABLE = 9;
/*     */   static final int FOREGROUND_ENABLED = 10;
/*     */   static final int FOREGROUND_MOUSEOVER = 11;
/*     */   static final int FOREGROUND_DISABLED = 12;
/*     */   static final int FOREGROUND_PRESSED = 13;
/*     */   static final int FOREGROUND_SELECTED = 14;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  59 */   private Path2D path = new Path2D.Float();
/*  60 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  61 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  62 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private Color color1 = decodeColor("nimbusBlueGrey", -0.6111111F, -0.110526316F, -0.74509805F, -247);
/*  68 */   private Color color2 = decodeColor("nimbusBase", 0.021348298F, -0.56289876F, 0.2588235F, 0);
/*  69 */   private Color color3 = decodeColor("nimbusBase", 0.010237217F, -0.55799407F, 0.20784312F, 0);
/*  70 */   private Color color4 = new Color(255, 200, 0, 255);
/*  71 */   private Color color5 = decodeColor("nimbusBase", 0.021348298F, -0.59223604F, 0.35294116F, 0);
/*  72 */   private Color color6 = decodeColor("nimbusBase", 0.02391243F, -0.5774183F, 0.32549018F, 0);
/*  73 */   private Color color7 = decodeColor("nimbusBase", 0.021348298F, -0.56722116F, 0.3098039F, 0);
/*  74 */   private Color color8 = decodeColor("nimbusBase", 0.021348298F, -0.567841F, 0.31764704F, 0);
/*  75 */   private Color color9 = decodeColor("nimbusBlueGrey", -0.6111111F, -0.110526316F, -0.74509805F, -191);
/*  76 */   private Color color10 = decodeColor("nimbusBase", 5.1498413E-4F, -0.34585923F, -0.007843137F, 0);
/*  77 */   private Color color11 = decodeColor("nimbusBase", 5.1498413E-4F, -0.095173776F, -0.25882354F, 0);
/*  78 */   private Color color12 = decodeColor("nimbusBase", 0.004681647F, -0.6197143F, 0.43137252F, 0);
/*  79 */   private Color color13 = decodeColor("nimbusBase", 0.0023007393F, -0.46825016F, 0.27058822F, 0);
/*  80 */   private Color color14 = decodeColor("nimbusBase", 5.1498413E-4F, -0.43866998F, 0.24705881F, 0);
/*  81 */   private Color color15 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4625541F, 0.35686272F, 0);
/*  82 */   private Color color16 = decodeColor("nimbusBase", 0.0013483167F, -0.1769987F, -0.12156865F, 0);
/*  83 */   private Color color17 = decodeColor("nimbusBase", 0.059279382F, 0.3642857F, -0.43529415F, 0);
/*  84 */   private Color color18 = decodeColor("nimbusBase", 0.004681647F, -0.6198413F, 0.43921566F, 0);
/*  85 */   private Color color19 = decodeColor("nimbusBase", 0.0023007393F, -0.48084703F, 0.33725488F, 0);
/*  86 */   private Color color20 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4555341F, 0.3215686F, 0);
/*  87 */   private Color color21 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4757143F, 0.43137252F, 0);
/*  88 */   private Color color22 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.54901963F, 0);
/*  89 */   private Color color23 = decodeColor("nimbusBase", -3.528595E-5F, 0.018606722F, -0.23137257F, 0);
/*  90 */   private Color color24 = decodeColor("nimbusBase", -4.2033195E-4F, -0.38050595F, 0.20392156F, 0);
/*  91 */   private Color color25 = decodeColor("nimbusBase", 7.13408E-4F, -0.064285696F, 0.027450979F, 0);
/*  92 */   private Color color26 = decodeColor("nimbusBase", 0.0F, -0.00895375F, 0.007843137F, 0);
/*  93 */   private Color color27 = decodeColor("nimbusBase", 8.9377165E-4F, -0.13853917F, 0.14509803F, 0);
/*  94 */   private Color color28 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.37254906F, 0);
/*  95 */   private Color color29 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.5254902F, 0);
/*  96 */   private Color color30 = decodeColor("nimbusBase", 0.027408898F, -0.57391655F, 0.1490196F, 0);
/*  97 */   private Color color31 = decodeColor("nimbusBase", 0.0F, -0.6357143F, 0.45098037F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public ComboBoxArrowButtonPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/* 105 */     this.state = paramInt;
/* 106 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 112 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 115 */     switch (this.state) { case 5:
/* 116 */         paintBackgroundDisabledAndEditable(paramGraphics2D); break;
/* 117 */       case 6: paintBackgroundEnabledAndEditable(paramGraphics2D); break;
/* 118 */       case 7: paintBackgroundMouseOverAndEditable(paramGraphics2D); break;
/* 119 */       case 8: paintBackgroundPressedAndEditable(paramGraphics2D); break;
/* 120 */       case 9: paintBackgroundSelectedAndEditable(paramGraphics2D); break;
/* 121 */       case 10: paintForegroundEnabled(paramGraphics2D); break;
/* 122 */       case 11: paintForegroundMouseOver(paramGraphics2D); break;
/* 123 */       case 12: paintForegroundDisabled(paramGraphics2D); break;
/* 124 */       case 13: paintForegroundPressed(paramGraphics2D); break;
/* 125 */       case 14: paintForegroundSelected(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 134 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundDisabledAndEditable(Graphics2D paramGraphics2D) {
/* 138 */     this.path = decodePath1();
/* 139 */     paramGraphics2D.setPaint(this.color1);
/* 140 */     paramGraphics2D.fill(this.path);
/* 141 */     this.path = decodePath2();
/* 142 */     paramGraphics2D.setPaint(decodeGradient1(this.path));
/* 143 */     paramGraphics2D.fill(this.path);
/* 144 */     this.path = decodePath3();
/* 145 */     paramGraphics2D.setPaint(this.color4);
/* 146 */     paramGraphics2D.fill(this.path);
/* 147 */     this.path = decodePath4();
/* 148 */     paramGraphics2D.setPaint(decodeGradient2(this.path));
/* 149 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundEnabledAndEditable(Graphics2D paramGraphics2D) {
/* 154 */     this.path = decodePath1();
/* 155 */     paramGraphics2D.setPaint(this.color9);
/* 156 */     paramGraphics2D.fill(this.path);
/* 157 */     this.path = decodePath2();
/* 158 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/* 159 */     paramGraphics2D.fill(this.path);
/* 160 */     this.path = decodePath3();
/* 161 */     paramGraphics2D.setPaint(this.color4);
/* 162 */     paramGraphics2D.fill(this.path);
/* 163 */     this.path = decodePath4();
/* 164 */     paramGraphics2D.setPaint(decodeGradient4(this.path));
/* 165 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOverAndEditable(Graphics2D paramGraphics2D) {
/* 170 */     this.path = decodePath1();
/* 171 */     paramGraphics2D.setPaint(this.color9);
/* 172 */     paramGraphics2D.fill(this.path);
/* 173 */     this.path = decodePath2();
/* 174 */     paramGraphics2D.setPaint(decodeGradient5(this.path));
/* 175 */     paramGraphics2D.fill(this.path);
/* 176 */     this.path = decodePath3();
/* 177 */     paramGraphics2D.setPaint(this.color4);
/* 178 */     paramGraphics2D.fill(this.path);
/* 179 */     this.path = decodePath4();
/* 180 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 181 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressedAndEditable(Graphics2D paramGraphics2D) {
/* 186 */     this.path = decodePath1();
/* 187 */     paramGraphics2D.setPaint(this.color9);
/* 188 */     paramGraphics2D.fill(this.path);
/* 189 */     this.path = decodePath2();
/* 190 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/* 191 */     paramGraphics2D.fill(this.path);
/* 192 */     this.path = decodePath3();
/* 193 */     paramGraphics2D.setPaint(this.color4);
/* 194 */     paramGraphics2D.fill(this.path);
/* 195 */     this.path = decodePath4();
/* 196 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 197 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundSelectedAndEditable(Graphics2D paramGraphics2D) {
/* 202 */     this.path = decodePath1();
/* 203 */     paramGraphics2D.setPaint(this.color9);
/* 204 */     paramGraphics2D.fill(this.path);
/* 205 */     this.path = decodePath2();
/* 206 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/* 207 */     paramGraphics2D.fill(this.path);
/* 208 */     this.path = decodePath3();
/* 209 */     paramGraphics2D.setPaint(this.color4);
/* 210 */     paramGraphics2D.fill(this.path);
/* 211 */     this.path = decodePath4();
/* 212 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 213 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundEnabled(Graphics2D paramGraphics2D) {
/* 218 */     this.path = decodePath5();
/* 219 */     paramGraphics2D.setPaint(decodeGradient9(this.path));
/* 220 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundMouseOver(Graphics2D paramGraphics2D) {
/* 225 */     this.path = decodePath6();
/* 226 */     paramGraphics2D.setPaint(decodeGradient9(this.path));
/* 227 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundDisabled(Graphics2D paramGraphics2D) {
/* 232 */     this.path = decodePath7();
/* 233 */     paramGraphics2D.setPaint(this.color30);
/* 234 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundPressed(Graphics2D paramGraphics2D) {
/* 239 */     this.path = decodePath8();
/* 240 */     paramGraphics2D.setPaint(this.color31);
/* 241 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintForegroundSelected(Graphics2D paramGraphics2D) {
/* 246 */     this.path = decodePath7();
/* 247 */     paramGraphics2D.setPaint(this.color31);
/* 248 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Path2D decodePath1() {
/* 255 */     this.path.reset();
/* 256 */     this.path.moveTo(decodeX(0.0F), decodeY(2.0F));
/* 257 */     this.path.lineTo(decodeX(2.75F), decodeY(2.0F));
/* 258 */     this.path.lineTo(decodeX(2.75F), decodeY(2.25F));
/* 259 */     this.path.curveTo(decodeAnchorX(2.75F, 0.0F), decodeAnchorY(2.25F, 4.0F), decodeAnchorX(2.125F, 3.0F), decodeAnchorY(2.875F, 0.0F), decodeX(2.125F), decodeY(2.875F));
/* 260 */     this.path.lineTo(decodeX(0.0F), decodeY(2.875F));
/* 261 */     this.path.lineTo(decodeX(0.0F), decodeY(2.0F));
/* 262 */     this.path.closePath();
/* 263 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath2() {
/* 267 */     this.path.reset();
/* 268 */     this.path.moveTo(decodeX(0.0F), decodeY(0.25F));
/* 269 */     this.path.lineTo(decodeX(2.125F), decodeY(0.25F));
/* 270 */     this.path.curveTo(decodeAnchorX(2.125F, 3.0F), decodeAnchorY(0.25F, 0.0F), decodeAnchorX(2.75F, 0.0F), decodeAnchorY(0.875F, -3.0F), decodeX(2.75F), decodeY(0.875F));
/* 271 */     this.path.lineTo(decodeX(2.75F), decodeY(2.125F));
/* 272 */     this.path.curveTo(decodeAnchorX(2.75F, 0.0F), decodeAnchorY(2.125F, 3.0F), decodeAnchorX(2.125F, 3.0F), decodeAnchorY(2.75F, 0.0F), decodeX(2.125F), decodeY(2.75F));
/* 273 */     this.path.lineTo(decodeX(0.0F), decodeY(2.75F));
/* 274 */     this.path.lineTo(decodeX(0.0F), decodeY(0.25F));
/* 275 */     this.path.closePath();
/* 276 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath3() {
/* 280 */     this.path.reset();
/* 281 */     this.path.moveTo(decodeX(0.85294116F), decodeY(2.639706F));
/* 282 */     this.path.lineTo(decodeX(0.85294116F), decodeY(2.639706F));
/* 283 */     this.path.closePath();
/* 284 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath4() {
/* 288 */     this.path.reset();
/* 289 */     this.path.moveTo(decodeX(1.0F), decodeY(0.375F));
/* 290 */     this.path.lineTo(decodeX(2.0F), decodeY(0.375F));
/* 291 */     this.path.curveTo(decodeAnchorX(2.0F, 4.0F), decodeAnchorY(0.375F, 0.0F), decodeAnchorX(2.625F, 0.0F), decodeAnchorY(1.0F, -4.0F), decodeX(2.625F), decodeY(1.0F));
/* 292 */     this.path.lineTo(decodeX(2.625F), decodeY(2.0F));
/* 293 */     this.path.curveTo(decodeAnchorX(2.625F, 0.0F), decodeAnchorY(2.0F, 4.0F), decodeAnchorX(2.0F, 4.0F), decodeAnchorY(2.625F, 0.0F), decodeX(2.0F), decodeY(2.625F));
/* 294 */     this.path.lineTo(decodeX(1.0F), decodeY(2.625F));
/* 295 */     this.path.lineTo(decodeX(1.0F), decodeY(0.375F));
/* 296 */     this.path.closePath();
/* 297 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath5() {
/* 301 */     this.path.reset();
/* 302 */     this.path.moveTo(decodeX(0.9995915F), decodeY(1.3616071F));
/* 303 */     this.path.lineTo(decodeX(2.0F), decodeY(0.8333333F));
/* 304 */     this.path.lineTo(decodeX(2.0F), decodeY(1.8571429F));
/* 305 */     this.path.lineTo(decodeX(0.9995915F), decodeY(1.3616071F));
/* 306 */     this.path.closePath();
/* 307 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath6() {
/* 311 */     this.path.reset();
/* 312 */     this.path.moveTo(decodeX(1.00625F), decodeY(1.3526785F));
/* 313 */     this.path.lineTo(decodeX(2.0F), decodeY(0.8333333F));
/* 314 */     this.path.lineTo(decodeX(2.0F), decodeY(1.8571429F));
/* 315 */     this.path.lineTo(decodeX(1.00625F), decodeY(1.3526785F));
/* 316 */     this.path.closePath();
/* 317 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath7() {
/* 321 */     this.path.reset();
/* 322 */     this.path.moveTo(decodeX(1.0117648F), decodeY(1.3616071F));
/* 323 */     this.path.lineTo(decodeX(2.0F), decodeY(0.8333333F));
/* 324 */     this.path.lineTo(decodeX(2.0F), decodeY(1.8571429F));
/* 325 */     this.path.lineTo(decodeX(1.0117648F), decodeY(1.3616071F));
/* 326 */     this.path.closePath();
/* 327 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath8() {
/* 331 */     this.path.reset();
/* 332 */     this.path.moveTo(decodeX(1.0242647F), decodeY(1.3526785F));
/* 333 */     this.path.lineTo(decodeX(2.0F), decodeY(0.8333333F));
/* 334 */     this.path.lineTo(decodeX(2.0F), decodeY(1.8571429F));
/* 335 */     this.path.lineTo(decodeX(1.0242647F), decodeY(1.3526785F));
/* 336 */     this.path.closePath();
/* 337 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 343 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 344 */     float f1 = (float)rectangle2D.getX();
/* 345 */     float f2 = (float)rectangle2D.getY();
/* 346 */     float f3 = (float)rectangle2D.getWidth();
/* 347 */     float f4 = (float)rectangle2D.getHeight();
/* 348 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color2, 
/*     */ 
/*     */           
/* 351 */           decodeColor(this.color2, this.color3, 0.5F), this.color3 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 356 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 357 */     float f1 = (float)rectangle2D.getX();
/* 358 */     float f2 = (float)rectangle2D.getY();
/* 359 */     float f3 = (float)rectangle2D.getWidth();
/* 360 */     float f4 = (float)rectangle2D.getHeight();
/* 361 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.171875F, 0.34375F, 0.4815341F, 0.6193182F, 0.8096591F, 1.0F }, new Color[] { this.color5, 
/*     */ 
/*     */           
/* 364 */           decodeColor(this.color5, this.color6, 0.5F), this.color6, 
/*     */           
/* 366 */           decodeColor(this.color6, this.color7, 0.5F), this.color7, 
/*     */           
/* 368 */           decodeColor(this.color7, this.color8, 0.5F), this.color8 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 373 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 374 */     float f1 = (float)rectangle2D.getX();
/* 375 */     float f2 = (float)rectangle2D.getY();
/* 376 */     float f3 = (float)rectangle2D.getWidth();
/* 377 */     float f4 = (float)rectangle2D.getHeight();
/* 378 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color10, 
/*     */ 
/*     */           
/* 381 */           decodeColor(this.color10, this.color11, 0.5F), this.color11 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 386 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 387 */     float f1 = (float)rectangle2D.getX();
/* 388 */     float f2 = (float)rectangle2D.getY();
/* 389 */     float f3 = (float)rectangle2D.getWidth();
/* 390 */     float f4 = (float)rectangle2D.getHeight();
/* 391 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.12299465F, 0.44652405F, 0.5441176F, 0.64171124F, 0.8208556F, 1.0F }, new Color[] { this.color12, 
/*     */ 
/*     */           
/* 394 */           decodeColor(this.color12, this.color13, 0.5F), this.color13, 
/*     */           
/* 396 */           decodeColor(this.color13, this.color14, 0.5F), this.color14, 
/*     */           
/* 398 */           decodeColor(this.color14, this.color15, 0.5F), this.color15 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 403 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 404 */     float f1 = (float)rectangle2D.getX();
/* 405 */     float f2 = (float)rectangle2D.getY();
/* 406 */     float f3 = (float)rectangle2D.getWidth();
/* 407 */     float f4 = (float)rectangle2D.getHeight();
/* 408 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color16, 
/*     */ 
/*     */           
/* 411 */           decodeColor(this.color16, this.color17, 0.5F), this.color17 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 416 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 417 */     float f1 = (float)rectangle2D.getX();
/* 418 */     float f2 = (float)rectangle2D.getY();
/* 419 */     float f3 = (float)rectangle2D.getWidth();
/* 420 */     float f4 = (float)rectangle2D.getHeight();
/* 421 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.12299465F, 0.44652405F, 0.5441176F, 0.64171124F, 0.81283426F, 0.98395723F }, new Color[] { this.color18, 
/*     */ 
/*     */           
/* 424 */           decodeColor(this.color18, this.color19, 0.5F), this.color19, 
/*     */           
/* 426 */           decodeColor(this.color19, this.color20, 0.5F), this.color20, 
/*     */           
/* 428 */           decodeColor(this.color20, this.color21, 0.5F), this.color21 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 433 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 434 */     float f1 = (float)rectangle2D.getX();
/* 435 */     float f2 = (float)rectangle2D.getY();
/* 436 */     float f3 = (float)rectangle2D.getWidth();
/* 437 */     float f4 = (float)rectangle2D.getHeight();
/* 438 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color22, 
/*     */ 
/*     */           
/* 441 */           decodeColor(this.color22, this.color23, 0.5F), this.color23 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 446 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 447 */     float f1 = (float)rectangle2D.getX();
/* 448 */     float f2 = (float)rectangle2D.getY();
/* 449 */     float f3 = (float)rectangle2D.getWidth();
/* 450 */     float f4 = (float)rectangle2D.getHeight();
/* 451 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.12299465F, 0.44652405F, 0.5441176F, 0.64171124F, 0.8208556F, 1.0F }, new Color[] { this.color24, 
/*     */ 
/*     */           
/* 454 */           decodeColor(this.color24, this.color25, 0.5F), this.color25, 
/*     */           
/* 456 */           decodeColor(this.color25, this.color26, 0.5F), this.color26, 
/*     */           
/* 458 */           decodeColor(this.color26, this.color27, 0.5F), this.color27 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient9(Shape paramShape) {
/* 463 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 464 */     float f1 = (float)rectangle2D.getX();
/* 465 */     float f2 = (float)rectangle2D.getY();
/* 466 */     float f3 = (float)rectangle2D.getWidth();
/* 467 */     float f4 = (float)rectangle2D.getHeight();
/* 468 */     return decodeGradient(1.0F * f3 + f1, 0.5F * f4 + f2, 0.0F * f3 + f1, 0.5F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color28, 
/*     */ 
/*     */           
/* 471 */           decodeColor(this.color28, this.color29, 0.5F), this.color29 });
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\ComboBoxArrowButtonPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */