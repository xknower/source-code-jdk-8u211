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
/*     */ final class ScrollBarThumbPainter
/*     */   extends AbstractRegionPainter
/*     */ {
/*     */   static final int BACKGROUND_DISABLED = 1;
/*     */   static final int BACKGROUND_ENABLED = 2;
/*     */   static final int BACKGROUND_FOCUSED = 3;
/*     */   static final int BACKGROUND_MOUSEOVER = 4;
/*     */   static final int BACKGROUND_PRESSED = 5;
/*     */   private int state;
/*     */   private AbstractRegionPainter.PaintContext ctx;
/*  50 */   private Path2D path = new Path2D.Float();
/*  51 */   private Rectangle2D rect = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*  52 */   private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  53 */   private Ellipse2D ellipse = new Ellipse2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private Color color1 = decodeColor("nimbusBase", 5.1498413E-4F, 0.18061227F, -0.35686278F, 0);
/*  59 */   private Color color2 = decodeColor("nimbusBase", 5.1498413E-4F, -0.21018237F, -0.18039218F, 0);
/*  60 */   private Color color3 = decodeColor("nimbusBase", 7.13408E-4F, -0.53277314F, 0.25098038F, 0);
/*  61 */   private Color color4 = decodeColor("nimbusBase", -0.07865167F, -0.6317617F, 0.44313723F, 0);
/*  62 */   private Color color5 = decodeColor("nimbusBase", 5.1498413E-4F, -0.44340658F, 0.26666665F, 0);
/*  63 */   private Color color6 = decodeColor("nimbusBase", 5.1498413E-4F, -0.4669379F, 0.38039213F, 0);
/*  64 */   private Color color7 = decodeColor("nimbusBase", -0.07865167F, -0.56512606F, 0.45098037F, 0);
/*  65 */   private Color color8 = decodeColor("nimbusBase", -0.0017285943F, -0.362987F, 0.011764705F, 0);
/*  66 */   private Color color9 = decodeColor("nimbusBase", 5.2034855E-5F, -0.41753247F, 0.09803921F, -222);
/*  67 */   private Color color10 = new Color(255, 200, 0, 255);
/*  68 */   private Color color11 = decodeColor("nimbusBase", -0.0017285943F, -0.362987F, 0.011764705F, -255);
/*  69 */   private Color color12 = decodeColor("nimbusBase", 0.010237217F, -0.5621849F, 0.25098038F, 0);
/*  70 */   private Color color13 = decodeColor("nimbusBase", 0.08801502F, -0.6317773F, 0.4470588F, 0);
/*  71 */   private Color color14 = decodeColor("nimbusBase", 5.1498413E-4F, -0.45950285F, 0.34117645F, 0);
/*  72 */   private Color color15 = decodeColor("nimbusBase", -0.0017285943F, -0.48277313F, 0.45098037F, 0);
/*  73 */   private Color color16 = decodeColor("nimbusBase", 0.0F, -0.6357143F, 0.45098037F, 0);
/*  74 */   private Color color17 = decodeColor("nimbusBase", -0.57865167F, -0.6357143F, -0.54901963F, 0);
/*  75 */   private Color color18 = decodeColor("nimbusBase", 0.0013483167F, 0.29021162F, -0.33725494F, 0);
/*  76 */   private Color color19 = decodeColor("nimbusBase", 0.002908647F, -0.29012606F, -0.015686274F, 0);
/*  77 */   private Color color20 = decodeColor("nimbusBase", -8.738637E-4F, -0.40612245F, 0.21960783F, 0);
/*  78 */   private Color color21 = decodeColor("nimbusBase", 0.0F, -0.01765871F, 0.015686274F, 0);
/*  79 */   private Color color22 = decodeColor("nimbusBase", 0.0F, -0.12714285F, 0.1372549F, 0);
/*  80 */   private Color color23 = decodeColor("nimbusBase", 0.0018727183F, -0.23116884F, 0.31372547F, 0);
/*  81 */   private Color color24 = decodeColor("nimbusBase", -8.738637E-4F, -0.3579365F, -0.33725494F, 0);
/*  82 */   private Color color25 = decodeColor("nimbusBase", 0.004681647F, -0.3857143F, -0.36078435F, 0);
/*     */ 
/*     */   
/*     */   private Object[] componentColors;
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollBarThumbPainter(AbstractRegionPainter.PaintContext paramPaintContext, int paramInt) {
/*  90 */     this.state = paramInt;
/*  91 */     this.ctx = paramPaintContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  97 */     this.componentColors = paramArrayOfObject;
/*     */ 
/*     */     
/* 100 */     switch (this.state) { case 2:
/* 101 */         paintBackgroundEnabled(paramGraphics2D); break;
/* 102 */       case 4: paintBackgroundMouseOver(paramGraphics2D); break;
/* 103 */       case 5: paintBackgroundPressed(paramGraphics2D);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractRegionPainter.PaintContext getPaintContext() {
/* 112 */     return this.ctx;
/*     */   }
/*     */   
/*     */   private void paintBackgroundEnabled(Graphics2D paramGraphics2D) {
/* 116 */     this.path = decodePath1();
/* 117 */     paramGraphics2D.setPaint(decodeGradient1(this.path));
/* 118 */     paramGraphics2D.fill(this.path);
/* 119 */     this.path = decodePath2();
/* 120 */     paramGraphics2D.setPaint(decodeGradient2(this.path));
/* 121 */     paramGraphics2D.fill(this.path);
/* 122 */     this.path = decodePath3();
/* 123 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/* 124 */     paramGraphics2D.fill(this.path);
/* 125 */     this.path = decodePath4();
/* 126 */     paramGraphics2D.setPaint(this.color10);
/* 127 */     paramGraphics2D.fill(this.path);
/* 128 */     this.path = decodePath5();
/* 129 */     paramGraphics2D.setPaint(decodeGradient4(this.path));
/* 130 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundMouseOver(Graphics2D paramGraphics2D) {
/* 135 */     this.path = decodePath1();
/* 136 */     paramGraphics2D.setPaint(decodeGradient1(this.path));
/* 137 */     paramGraphics2D.fill(this.path);
/* 138 */     this.path = decodePath2();
/* 139 */     paramGraphics2D.setPaint(decodeGradient5(this.path));
/* 140 */     paramGraphics2D.fill(this.path);
/* 141 */     this.path = decodePath3();
/* 142 */     paramGraphics2D.setPaint(decodeGradient3(this.path));
/* 143 */     paramGraphics2D.fill(this.path);
/* 144 */     this.path = decodePath4();
/* 145 */     paramGraphics2D.setPaint(this.color10);
/* 146 */     paramGraphics2D.fill(this.path);
/* 147 */     this.path = decodePath5();
/* 148 */     paramGraphics2D.setPaint(decodeGradient4(this.path));
/* 149 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintBackgroundPressed(Graphics2D paramGraphics2D) {
/* 154 */     this.path = decodePath1();
/* 155 */     paramGraphics2D.setPaint(decodeGradient6(this.path));
/* 156 */     paramGraphics2D.fill(this.path);
/* 157 */     this.path = decodePath2();
/* 158 */     paramGraphics2D.setPaint(decodeGradient7(this.path));
/* 159 */     paramGraphics2D.fill(this.path);
/* 160 */     this.path = decodePath3();
/* 161 */     paramGraphics2D.setPaint(decodeGradient8(this.path));
/* 162 */     paramGraphics2D.fill(this.path);
/* 163 */     this.path = decodePath4();
/* 164 */     paramGraphics2D.setPaint(this.color10);
/* 165 */     paramGraphics2D.fill(this.path);
/* 166 */     this.path = decodePath6();
/* 167 */     paramGraphics2D.setPaint(decodeGradient9(this.path));
/* 168 */     paramGraphics2D.fill(this.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Path2D decodePath1() {
/* 175 */     this.path.reset();
/* 176 */     this.path.moveTo(decodeX(0.0F), decodeY(1.0F));
/* 177 */     this.path.lineTo(decodeX(0.0F), decodeY(1.0666667F));
/* 178 */     this.path.curveTo(decodeAnchorX(0.0F, 0.0F), decodeAnchorY(1.0666667F, 6.0F), decodeAnchorX(1.0F, -10.0F), decodeAnchorY(2.0F, 0.0F), decodeX(1.0F), decodeY(2.0F));
/* 179 */     this.path.lineTo(decodeX(2.0F), decodeY(2.0F));
/* 180 */     this.path.curveTo(decodeAnchorX(2.0F, 10.0F), decodeAnchorY(2.0F, 0.0F), decodeAnchorX(3.0F, 0.0F), decodeAnchorY(1.0666667F, 6.0F), decodeX(3.0F), decodeY(1.0666667F));
/* 181 */     this.path.lineTo(decodeX(3.0F), decodeY(1.0F));
/* 182 */     this.path.lineTo(decodeX(0.0F), decodeY(1.0F));
/* 183 */     this.path.closePath();
/* 184 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath2() {
/* 188 */     this.path.reset();
/* 189 */     this.path.moveTo(decodeX(0.06666667F), decodeY(1.0F));
/* 190 */     this.path.lineTo(decodeX(0.06666667F), decodeY(1.0666667F));
/* 191 */     this.path.curveTo(decodeAnchorX(0.06666667F, -0.045454547F), decodeAnchorY(1.0666667F, 8.454545F), decodeAnchorX(1.0F, -5.8636365F), decodeAnchorY(1.9333334F, 0.0F), decodeX(1.0F), decodeY(1.9333334F));
/* 192 */     this.path.lineTo(decodeX(2.0F), decodeY(1.9333334F));
/* 193 */     this.path.curveTo(decodeAnchorX(2.0F, 5.909091F), decodeAnchorY(1.9333334F, -3.5527137E-15F), decodeAnchorX(2.9333334F, -0.045454547F), decodeAnchorY(1.0666667F, 8.363636F), decodeX(2.9333334F), decodeY(1.0666667F));
/* 194 */     this.path.lineTo(decodeX(2.9333334F), decodeY(1.0F));
/* 195 */     this.path.lineTo(decodeX(0.06666667F), decodeY(1.0F));
/* 196 */     this.path.closePath();
/* 197 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath3() {
/* 201 */     this.path.reset();
/* 202 */     this.path.moveTo(decodeX(0.4F), decodeY(1.0F));
/* 203 */     this.path.lineTo(decodeX(0.06666667F), decodeY(1.0F));
/* 204 */     this.path.lineTo(decodeX(0.16060607F), decodeY(1.5090909F));
/* 205 */     this.path.curveTo(decodeAnchorX(0.16060607F, 0.0F), decodeAnchorY(1.5090909F, 0.0F), decodeAnchorX(0.2F, -0.95454544F), decodeAnchorY(1.1363636F, 1.5454545F), decodeX(0.2F), decodeY(1.1363636F));
/* 206 */     this.path.curveTo(decodeAnchorX(0.2F, 0.95454544F), decodeAnchorY(1.1363636F, -1.5454545F), decodeAnchorX(0.4F, 0.0F), decodeAnchorY(1.0F, 0.0F), decodeX(0.4F), decodeY(1.0F));
/* 207 */     this.path.closePath();
/* 208 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath4() {
/* 212 */     this.path.reset();
/* 213 */     this.path.moveTo(decodeX(2.4242425F), decodeY(1.5121212F));
/* 214 */     this.path.lineTo(decodeX(2.4242425F), decodeY(1.5121212F));
/* 215 */     this.path.closePath();
/* 216 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath5() {
/* 220 */     this.path.reset();
/* 221 */     this.path.moveTo(decodeX(2.9363637F), decodeY(1.0F));
/* 222 */     this.path.lineTo(decodeX(2.6030304F), decodeY(1.0F));
/* 223 */     this.path.curveTo(decodeAnchorX(2.6030304F, 0.0F), decodeAnchorY(1.0F, 0.0F), decodeAnchorX(2.778788F, -0.6818182F), decodeAnchorY(1.1333333F, -1.2272727F), decodeX(2.778788F), decodeY(1.1333333F));
/* 224 */     this.path.curveTo(decodeAnchorX(2.778788F, 0.6818182F), decodeAnchorY(1.1333333F, 1.2272727F), decodeAnchorX(2.8393939F, 0.0F), decodeAnchorY(1.5060606F, 0.0F), decodeX(2.8393939F), decodeY(1.5060606F));
/* 225 */     this.path.lineTo(decodeX(2.9363637F), decodeY(1.0F));
/* 226 */     this.path.closePath();
/* 227 */     return this.path;
/*     */   }
/*     */   
/*     */   private Path2D decodePath6() {
/* 231 */     this.path.reset();
/* 232 */     this.path.moveTo(decodeX(2.9363637F), decodeY(1.0F));
/* 233 */     this.path.lineTo(decodeX(2.5563636F), decodeY(1.0F));
/* 234 */     this.path.curveTo(decodeAnchorX(2.5563636F, 0.0F), decodeAnchorY(1.0F, 0.0F), decodeAnchorX(2.7587879F, -0.6818182F), decodeAnchorY(1.14F, -1.2272727F), decodeX(2.7587879F), decodeY(1.14F));
/* 235 */     this.path.curveTo(decodeAnchorX(2.7587879F, 0.6818182F), decodeAnchorY(1.14F, 1.2272727F), decodeAnchorX(2.8393939F, 0.0F), decodeAnchorY(1.5060606F, 0.0F), decodeX(2.8393939F), decodeY(1.5060606F));
/* 236 */     this.path.lineTo(decodeX(2.9363637F), decodeY(1.0F));
/* 237 */     this.path.closePath();
/* 238 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Paint decodeGradient1(Shape paramShape) {
/* 244 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 245 */     float f1 = (float)rectangle2D.getX();
/* 246 */     float f2 = (float)rectangle2D.getY();
/* 247 */     float f3 = (float)rectangle2D.getWidth();
/* 248 */     float f4 = (float)rectangle2D.getHeight();
/* 249 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color1, 
/*     */ 
/*     */           
/* 252 */           decodeColor(this.color1, this.color2, 0.5F), this.color2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient2(Shape paramShape) {
/* 257 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 258 */     float f1 = (float)rectangle2D.getX();
/* 259 */     float f2 = (float)rectangle2D.getY();
/* 260 */     float f3 = (float)rectangle2D.getWidth();
/* 261 */     float f4 = (float)rectangle2D.getHeight();
/* 262 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.038922157F, 0.0508982F, 0.06287425F, 0.19610777F, 0.32934132F, 0.48952097F, 0.6497006F, 0.8248503F, 1.0F }, new Color[] { this.color3, 
/*     */ 
/*     */           
/* 265 */           decodeColor(this.color3, this.color4, 0.5F), this.color4, 
/*     */           
/* 267 */           decodeColor(this.color4, this.color5, 0.5F), this.color5, 
/*     */           
/* 269 */           decodeColor(this.color5, this.color6, 0.5F), this.color6, 
/*     */           
/* 271 */           decodeColor(this.color6, this.color7, 0.5F), this.color7 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient3(Shape paramShape) {
/* 276 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 277 */     float f1 = (float)rectangle2D.getX();
/* 278 */     float f2 = (float)rectangle2D.getY();
/* 279 */     float f3 = (float)rectangle2D.getWidth();
/* 280 */     float f4 = (float)rectangle2D.getHeight();
/* 281 */     return decodeGradient(0.06818182F * f3 + f1, -0.005952381F * f4 + f2, 0.3689091F * f3 + f1, 0.23929171F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color8, 
/*     */ 
/*     */           
/* 284 */           decodeColor(this.color8, this.color9, 0.5F), this.color9 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient4(Shape paramShape) {
/* 289 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 290 */     float f1 = (float)rectangle2D.getX();
/* 291 */     float f2 = (float)rectangle2D.getY();
/* 292 */     float f3 = (float)rectangle2D.getWidth();
/* 293 */     float f4 = (float)rectangle2D.getHeight();
/* 294 */     return decodeGradient(0.9409091F * f3 + f1, 0.035928145F * f4 + f2, 0.5954546F * f3 + f1, 0.26347303F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color8, 
/*     */ 
/*     */           
/* 297 */           decodeColor(this.color8, this.color11, 0.5F), this.color11 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient5(Shape paramShape) {
/* 302 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 303 */     float f1 = (float)rectangle2D.getX();
/* 304 */     float f2 = (float)rectangle2D.getY();
/* 305 */     float f3 = (float)rectangle2D.getWidth();
/* 306 */     float f4 = (float)rectangle2D.getHeight();
/* 307 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.038922157F, 0.0508982F, 0.06287425F, 0.19610777F, 0.32934132F, 0.48952097F, 0.6497006F, 0.8248503F, 1.0F }, new Color[] { this.color12, 
/*     */ 
/*     */           
/* 310 */           decodeColor(this.color12, this.color13, 0.5F), this.color13, 
/*     */           
/* 312 */           decodeColor(this.color13, this.color14, 0.5F), this.color14, 
/*     */           
/* 314 */           decodeColor(this.color14, this.color15, 0.5F), this.color15, 
/*     */           
/* 316 */           decodeColor(this.color15, this.color16, 0.5F), this.color16 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient6(Shape paramShape) {
/* 321 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 322 */     float f1 = (float)rectangle2D.getX();
/* 323 */     float f2 = (float)rectangle2D.getY();
/* 324 */     float f3 = (float)rectangle2D.getWidth();
/* 325 */     float f4 = (float)rectangle2D.getHeight();
/* 326 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color17, 
/*     */ 
/*     */           
/* 329 */           decodeColor(this.color17, this.color18, 0.5F), this.color18 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient7(Shape paramShape) {
/* 334 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 335 */     float f1 = (float)rectangle2D.getX();
/* 336 */     float f2 = (float)rectangle2D.getY();
/* 337 */     float f3 = (float)rectangle2D.getWidth();
/* 338 */     float f4 = (float)rectangle2D.getHeight();
/* 339 */     return decodeGradient(0.5F * f3 + f1, 0.0F * f4 + f2, 0.5F * f3 + f1, 1.0F * f4 + f2, new float[] { 0.038922157F, 0.0508982F, 0.06287425F, 0.19610777F, 0.32934132F, 0.48952097F, 0.6497006F, 0.8248503F, 1.0F }, new Color[] { this.color19, 
/*     */ 
/*     */           
/* 342 */           decodeColor(this.color19, this.color20, 0.5F), this.color20, 
/*     */           
/* 344 */           decodeColor(this.color20, this.color21, 0.5F), this.color21, 
/*     */           
/* 346 */           decodeColor(this.color21, this.color22, 0.5F), this.color22, 
/*     */           
/* 348 */           decodeColor(this.color22, this.color23, 0.5F), this.color23 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient8(Shape paramShape) {
/* 353 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 354 */     float f1 = (float)rectangle2D.getX();
/* 355 */     float f2 = (float)rectangle2D.getY();
/* 356 */     float f3 = (float)rectangle2D.getWidth();
/* 357 */     float f4 = (float)rectangle2D.getHeight();
/* 358 */     return decodeGradient(0.06818182F * f3 + f1, -0.005952381F * f4 + f2, 0.3689091F * f3 + f1, 0.23929171F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color24, 
/*     */ 
/*     */           
/* 361 */           decodeColor(this.color24, this.color9, 0.5F), this.color9 });
/*     */   }
/*     */ 
/*     */   
/*     */   private Paint decodeGradient9(Shape paramShape) {
/* 366 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 367 */     float f1 = (float)rectangle2D.getX();
/* 368 */     float f2 = (float)rectangle2D.getY();
/* 369 */     float f3 = (float)rectangle2D.getWidth();
/* 370 */     float f4 = (float)rectangle2D.getHeight();
/* 371 */     return decodeGradient(0.9409091F * f3 + f1, 0.035928145F * f4 + f2, 0.37615633F * f3 + f1, 0.34910178F * f4 + f2, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { this.color25, 
/*     */ 
/*     */           
/* 374 */           decodeColor(this.color25, this.color11, 0.5F), this.color11 });
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\ScrollBarThumbPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */