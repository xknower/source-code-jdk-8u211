/*      */ package sun.awt.windows;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.GlyphVector;
/*      */ import java.awt.font.TextLayout;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.NoninvertibleTransformException;
/*      */ import java.awt.geom.PathIterator;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ComponentSampleModel;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.awt.image.MultiPixelPackedSampleModel;
/*      */ import java.awt.image.SampleModel;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.security.AccessController;
/*      */ import java.util.Arrays;
/*      */ import sun.awt.image.ByteComponentRaster;
/*      */ import sun.awt.image.BytePackedRaster;
/*      */ import sun.font.CompositeFont;
/*      */ import sun.font.Font2D;
/*      */ import sun.font.FontUtilities;
/*      */ import sun.font.PhysicalFont;
/*      */ import sun.font.TrueTypeFont;
/*      */ import sun.print.PathGraphics;
/*      */ import sun.print.ProxyGraphics2D;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class WPathGraphics
/*      */   extends PathGraphics
/*      */ {
/*      */   private static final int DEFAULT_USER_RES = 72;
/*      */   private static final float MIN_DEVICE_LINEWIDTH = 1.2F;
/*      */   private static final float MAX_THINLINE_INCHES = 0.014F;
/*      */   private static boolean useGDITextLayout = true;
/*      */   private static boolean preferGDITextLayout = false;
/*      */   
/*      */   static {
/*   97 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.print.enableGDITextLayout"));
/*      */ 
/*      */ 
/*      */     
/*  101 */     if (str != null) {
/*  102 */       useGDITextLayout = Boolean.getBoolean(str);
/*  103 */       if (!useGDITextLayout && 
/*  104 */         str.equalsIgnoreCase("prefer")) {
/*  105 */         useGDITextLayout = true;
/*  106 */         preferGDITextLayout = true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   WPathGraphics(Graphics2D paramGraphics2D, PrinterJob paramPrinterJob, Printable paramPrintable, PageFormat paramPageFormat, int paramInt, boolean paramBoolean) {
/*  115 */     super(paramGraphics2D, paramPrinterJob, paramPrintable, paramPageFormat, paramInt, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Graphics create() {
/*  128 */     return new WPathGraphics((Graphics2D)getDelegate().create(), 
/*  129 */         getPrinterJob(), 
/*  130 */         getPrintable(), 
/*  131 */         getPageFormat(), 
/*  132 */         getPageIndex(), 
/*  133 */         canDoRedraws());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Shape paramShape) {
/*  153 */     Stroke stroke = getStroke();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  160 */     if (stroke instanceof BasicStroke) {
/*      */       
/*  162 */       BasicStroke basicStroke2 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  170 */       BasicStroke basicStroke1 = (BasicStroke)stroke;
/*  171 */       float f2 = basicStroke1.getLineWidth();
/*  172 */       Point2D.Float float_ = new Point2D.Float(f2, f2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  180 */       AffineTransform affineTransform = getTransform();
/*  181 */       affineTransform.deltaTransform(float_, float_);
/*  182 */       float f1 = Math.min(Math.abs(float_.x), 
/*  183 */           Math.abs(float_.y));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  189 */       if (f1 < 1.2F) {
/*      */         
/*  191 */         Point2D.Float float_1 = new Point2D.Float(1.2F, 1.2F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  202 */           AffineTransform affineTransform1 = affineTransform.createInverse();
/*  203 */           affineTransform1.deltaTransform(float_1, float_1);
/*      */           
/*  205 */           float f = Math.max(Math.abs(float_1.x), 
/*  206 */               Math.abs(float_1.y));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  217 */           basicStroke2 = new BasicStroke(f, basicStroke1.getEndCap(), basicStroke1.getLineJoin(), basicStroke1.getMiterLimit(), basicStroke1.getDashArray(), basicStroke1.getDashPhase());
/*  218 */           setStroke(basicStroke2);
/*      */         }
/*  220 */         catch (NoninvertibleTransformException noninvertibleTransformException) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  228 */       super.draw(paramShape);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  234 */       if (basicStroke2 != null) {
/*  235 */         setStroke(basicStroke1);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  242 */       super.draw(paramShape);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawString(String paramString, int paramInt1, int paramInt2) {
/*  260 */     drawString(paramString, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawString(String paramString, float paramFloat1, float paramFloat2) {
/*  265 */     drawString(paramString, paramFloat1, paramFloat2, getFont(), getFontRenderContext(), 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int platformFontCount(Font paramFont, String paramString) {
/*  283 */     AffineTransform affineTransform1 = getTransform();
/*  284 */     AffineTransform affineTransform2 = new AffineTransform(affineTransform1);
/*  285 */     affineTransform2.concatenate(getFont().getTransform());
/*  286 */     int i = affineTransform2.getType();
/*      */ 
/*      */     
/*  289 */     boolean bool = (i != 32 && (i & 0x40) == 0) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  294 */     if (!bool) {
/*  295 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  305 */     Font2D font2D = FontUtilities.getFont2D(paramFont);
/*  306 */     if (font2D instanceof CompositeFont || font2D instanceof TrueTypeFont)
/*      */     {
/*  308 */       return 1;
/*      */     }
/*  310 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isXP() {
/*  315 */     String str = System.getProperty("os.version");
/*  316 */     if (str != null) {
/*  317 */       Float float_ = Float.valueOf(str);
/*  318 */       return (float_.floatValue() >= 5.1F);
/*      */     } 
/*  320 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean strNeedsTextLayout(String paramString, Font paramFont) {
/*  330 */     char[] arrayOfChar = paramString.toCharArray();
/*  331 */     boolean bool = FontUtilities.isComplexText(arrayOfChar, 0, arrayOfChar.length);
/*  332 */     if (!bool)
/*  333 */       return false; 
/*  334 */     if (!useGDITextLayout) {
/*  335 */       return true;
/*      */     }
/*  337 */     if (preferGDITextLayout || (
/*  338 */       isXP() && FontUtilities.textLayoutIsCompatible(paramFont))) {
/*  339 */       return false;
/*      */     }
/*  341 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getAngle(Point2D.Double paramDouble) {
/*  351 */     double d = Math.toDegrees(Math.atan2(paramDouble.y, paramDouble.x));
/*  352 */     if (d < 0.0D) {
/*  353 */       d += 360.0D;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  361 */     if (d != 0.0D) {
/*  362 */       d = 360.0D - d;
/*      */     }
/*  364 */     return (int)Math.round(d * 10.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   private float getAwScale(double paramDouble1, double paramDouble2) {
/*  369 */     float f = (float)(paramDouble1 / paramDouble2);
/*      */     
/*  371 */     if (f > 0.999F && f < 1.001F) {
/*  372 */       f = 1.0F;
/*      */     }
/*  374 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawString(String paramString, float paramFloat1, float paramFloat2, Font paramFont, FontRenderContext paramFontRenderContext, float paramFloat3) {
/*  402 */     if (paramString.length() == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  406 */     if (WPrinterJob.shapeTextProp) {
/*  407 */       super.drawString(paramString, paramFloat1, paramFloat2, paramFont, paramFontRenderContext, paramFloat3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  422 */     boolean bool = strNeedsTextLayout(paramString, paramFont);
/*  423 */     if ((paramFont.hasLayoutAttributes() || bool) && !this.printingGlyphVector) {
/*      */       
/*  425 */       TextLayout textLayout = new TextLayout(paramString, paramFont, paramFontRenderContext);
/*  426 */       textLayout.draw(this, paramFloat1, paramFloat2); return;
/*      */     } 
/*  428 */     if (bool) {
/*  429 */       super.drawString(paramString, paramFloat1, paramFloat2, paramFont, paramFontRenderContext, paramFloat3);
/*      */       
/*      */       return;
/*      */     } 
/*  433 */     AffineTransform affineTransform1 = getTransform();
/*  434 */     AffineTransform affineTransform2 = new AffineTransform(affineTransform1);
/*  435 */     affineTransform2.concatenate(paramFont.getTransform());
/*  436 */     int i = affineTransform2.getType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  443 */     boolean bool1 = (i != 32 && (i & 0x40) == 0) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  448 */     WPrinterJob wPrinterJob = (WPrinterJob)getPrinterJob();
/*      */     try {
/*  450 */       wPrinterJob.setTextColor((Color)getPaint());
/*  451 */     } catch (ClassCastException classCastException) {
/*  452 */       bool1 = false;
/*      */     } 
/*      */     
/*  455 */     if (!bool1) {
/*  456 */       super.drawString(paramString, paramFloat1, paramFloat2, paramFont, paramFontRenderContext, paramFloat3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  468 */     Point2D.Float float_1 = new Point2D.Float(paramFloat1, paramFloat2);
/*  469 */     Point2D.Float float_2 = new Point2D.Float();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  474 */     if (paramFont.isTransformed()) {
/*  475 */       AffineTransform affineTransform = paramFont.getTransform();
/*  476 */       float f4 = (float)affineTransform.getTranslateX();
/*  477 */       float f5 = (float)affineTransform.getTranslateY();
/*  478 */       if (Math.abs(f4) < 1.0E-5D) f4 = 0.0F; 
/*  479 */       if (Math.abs(f5) < 1.0E-5D) f5 = 0.0F; 
/*  480 */       float_1.x += f4; float_1.y += f5;
/*      */     } 
/*  482 */     affineTransform1.transform(float_1, float_2);
/*      */     
/*  484 */     if (getClip() != null) {
/*  485 */       deviceClip(getClip().getPathIterator(affineTransform1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  495 */     float f1 = paramFont.getSize2D();
/*      */     
/*  497 */     double d1 = wPrinterJob.getXRes();
/*  498 */     double d2 = wPrinterJob.getYRes();
/*      */     
/*  500 */     double d3 = d2 / 72.0D;
/*      */     
/*  502 */     int j = getPageFormat().getOrientation();
/*  503 */     if (j == 0 || j == 2) {
/*      */ 
/*      */       
/*  506 */       double d = d1;
/*  507 */       d1 = d2;
/*  508 */       d2 = d;
/*      */     } 
/*      */     
/*  511 */     double d4 = d1 / 72.0D;
/*  512 */     double d5 = d2 / 72.0D;
/*  513 */     affineTransform2.scale(1.0D / d4, 1.0D / d5);
/*      */     
/*  515 */     Point2D.Double double_1 = new Point2D.Double(0.0D, 1.0D);
/*  516 */     affineTransform2.deltaTransform(double_1, double_1);
/*  517 */     double d6 = Math.sqrt(double_1.x * double_1.x + double_1.y * double_1.y);
/*  518 */     float f2 = (float)(f1 * d6 * d3);
/*      */     
/*  520 */     Point2D.Double double_2 = new Point2D.Double(1.0D, 0.0D);
/*  521 */     affineTransform2.deltaTransform(double_2, double_2);
/*  522 */     double d7 = Math.sqrt(double_2.x * double_2.x + double_2.y * double_2.y);
/*      */     
/*  524 */     float f3 = getAwScale(d7, d6);
/*  525 */     int k = getAngle(double_2);
/*      */     
/*  527 */     double_2 = new Point2D.Double(1.0D, 0.0D);
/*  528 */     affineTransform1.deltaTransform(double_2, double_2);
/*  529 */     double d8 = Math.sqrt(double_2.x * double_2.x + double_2.y * double_2.y);
/*  530 */     double_1 = new Point2D.Double(0.0D, 1.0D);
/*  531 */     affineTransform1.deltaTransform(double_1, double_1);
/*  532 */     double d9 = Math.sqrt(double_1.x * double_1.x + double_1.y * double_1.y);
/*      */     
/*  534 */     Font2D font2D = FontUtilities.getFont2D(paramFont);
/*  535 */     if (font2D instanceof TrueTypeFont) {
/*  536 */       textOut(paramString, paramFont, (TrueTypeFont)font2D, paramFontRenderContext, f2, k, f3, d8, d9, paramFloat1, paramFloat2, float_2.x, float_2.y, paramFloat3);
/*      */ 
/*      */     
/*      */     }
/*  540 */     else if (font2D instanceof CompositeFont) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  548 */       CompositeFont compositeFont = (CompositeFont)font2D;
/*  549 */       float f4 = paramFloat1, f5 = paramFloat2;
/*  550 */       float f6 = float_2.x, f7 = float_2.y;
/*  551 */       char[] arrayOfChar = paramString.toCharArray();
/*  552 */       int m = arrayOfChar.length;
/*  553 */       int[] arrayOfInt = new int[m];
/*  554 */       compositeFont.getMapper().charsToGlyphs(m, arrayOfChar, arrayOfInt);
/*      */       
/*  556 */       byte b1 = 0, b2 = 0; int n = 0;
/*  557 */       while (b2 < m) {
/*      */         
/*  559 */         b1 = b2;
/*  560 */         n = arrayOfInt[b1] >>> 24;
/*      */         
/*  562 */         while (b2 < m && arrayOfInt[b2] >>> 24 == n) {
/*  563 */           b2++;
/*      */         }
/*  565 */         String str = new String(arrayOfChar, b1, b2 - b1);
/*  566 */         PhysicalFont physicalFont = compositeFont.getSlotFont(n);
/*  567 */         textOut(str, paramFont, physicalFont, paramFontRenderContext, f2, k, f3, d8, d9, f4, f5, f6, f7, 0.0F);
/*      */ 
/*      */ 
/*      */         
/*  571 */         Rectangle2D rectangle2D = paramFont.getStringBounds(str, paramFontRenderContext);
/*  572 */         float f = (float)rectangle2D.getWidth();
/*  573 */         f4 += f;
/*  574 */         float_1.x += f;
/*  575 */         affineTransform1.transform(float_1, float_2);
/*  576 */         f6 = float_2.x;
/*  577 */         f7 = float_2.y;
/*      */       } 
/*      */     } else {
/*  580 */       super.drawString(paramString, paramFloat1, paramFloat2, paramFont, paramFontRenderContext, paramFloat3);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean printGlyphVector(GlyphVector paramGlyphVector, float paramFloat1, float paramFloat2) {
/*  593 */     if ((paramGlyphVector.getLayoutFlags() & 0x1) != 0) {
/*  594 */       return false;
/*      */     }
/*      */     
/*  597 */     if (paramGlyphVector.getNumGlyphs() == 0) {
/*  598 */       return true;
/*      */     }
/*      */     
/*  601 */     AffineTransform affineTransform1 = getTransform();
/*  602 */     AffineTransform affineTransform2 = new AffineTransform(affineTransform1);
/*  603 */     Font font = paramGlyphVector.getFont();
/*  604 */     affineTransform2.concatenate(font.getTransform());
/*  605 */     int i = affineTransform2.getType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  612 */     boolean bool = (i != 32 && (i & 0x40) == 0) ? true : false;
/*      */ 
/*      */ 
/*      */     
/*  616 */     WPrinterJob wPrinterJob = (WPrinterJob)getPrinterJob();
/*      */     try {
/*  618 */       wPrinterJob.setTextColor((Color)getPaint());
/*  619 */     } catch (ClassCastException classCastException) {
/*  620 */       bool = false;
/*      */     } 
/*      */     
/*  623 */     if (WPrinterJob.shapeTextProp || !bool) {
/*  624 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  629 */     Point2D.Float float_1 = new Point2D.Float(paramFloat1, paramFloat2);
/*      */     
/*  631 */     Point2D point2D = paramGlyphVector.getGlyphPosition(0);
/*  632 */     float_1.x += (float)point2D.getX();
/*  633 */     float_1.y += (float)point2D.getY();
/*  634 */     Point2D.Float float_2 = new Point2D.Float();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  639 */     if (font.isTransformed()) {
/*  640 */       AffineTransform affineTransform = font.getTransform();
/*  641 */       float f4 = (float)affineTransform.getTranslateX();
/*  642 */       float f5 = (float)affineTransform.getTranslateY();
/*  643 */       if (Math.abs(f4) < 1.0E-5D) f4 = 0.0F; 
/*  644 */       if (Math.abs(f5) < 1.0E-5D) f5 = 0.0F; 
/*  645 */       float_1.x += f4; float_1.y += f5;
/*      */     } 
/*  647 */     affineTransform1.transform(float_1, float_2);
/*      */     
/*  649 */     if (getClip() != null) {
/*  650 */       deviceClip(getClip().getPathIterator(affineTransform1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  660 */     float f1 = font.getSize2D();
/*      */     
/*  662 */     double d1 = wPrinterJob.getXRes();
/*  663 */     double d2 = wPrinterJob.getYRes();
/*      */     
/*  665 */     double d3 = d2 / 72.0D;
/*      */     
/*  667 */     int j = getPageFormat().getOrientation();
/*  668 */     if (j == 0 || j == 2) {
/*      */ 
/*      */       
/*  671 */       double d = d1;
/*  672 */       d1 = d2;
/*  673 */       d2 = d;
/*      */     } 
/*      */     
/*  676 */     double d4 = d1 / 72.0D;
/*  677 */     double d5 = d2 / 72.0D;
/*  678 */     affineTransform2.scale(1.0D / d4, 1.0D / d5);
/*      */     
/*  680 */     Point2D.Double double_1 = new Point2D.Double(0.0D, 1.0D);
/*  681 */     affineTransform2.deltaTransform(double_1, double_1);
/*  682 */     double d6 = Math.sqrt(double_1.x * double_1.x + double_1.y * double_1.y);
/*  683 */     float f2 = (float)(f1 * d6 * d3);
/*      */     
/*  685 */     Point2D.Double double_2 = new Point2D.Double(1.0D, 0.0D);
/*  686 */     affineTransform2.deltaTransform(double_2, double_2);
/*  687 */     double d7 = Math.sqrt(double_2.x * double_2.x + double_2.y * double_2.y);
/*      */     
/*  689 */     float f3 = getAwScale(d7, d6);
/*  690 */     int k = getAngle(double_2);
/*      */     
/*  692 */     double_2 = new Point2D.Double(1.0D, 0.0D);
/*  693 */     affineTransform1.deltaTransform(double_2, double_2);
/*  694 */     double d8 = Math.sqrt(double_2.x * double_2.x + double_2.y * double_2.y);
/*  695 */     double_1 = new Point2D.Double(0.0D, 1.0D);
/*  696 */     affineTransform1.deltaTransform(double_1, double_1);
/*  697 */     double d9 = Math.sqrt(double_1.x * double_1.x + double_1.y * double_1.y);
/*      */     
/*  699 */     int m = paramGlyphVector.getNumGlyphs();
/*  700 */     int[] arrayOfInt = paramGlyphVector.getGlyphCodes(0, m, null);
/*  701 */     float[] arrayOfFloat1 = paramGlyphVector.getGlyphPositions(0, m, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  714 */     byte b = 0; int n;
/*  715 */     for (n = 0; n < m; n++) {
/*  716 */       if ((arrayOfInt[n] & 0xFFFF) >= 65534)
/*      */       {
/*  718 */         b++;
/*      */       }
/*      */     } 
/*  721 */     if (b > 0) {
/*  722 */       n = m - b;
/*  723 */       int[] arrayOfInt1 = new int[n];
/*  724 */       float[] arrayOfFloat = new float[n * 2];
/*  725 */       byte b1 = 0;
/*  726 */       for (byte b2 = 0; b2 < m; b2++) {
/*  727 */         if ((arrayOfInt[b2] & 0xFFFF) < 65534) {
/*      */           
/*  729 */           arrayOfInt1[b1] = arrayOfInt[b2];
/*  730 */           arrayOfFloat[b1 * 2] = arrayOfFloat1[b2 * 2];
/*  731 */           arrayOfFloat[b1 * 2 + 1] = arrayOfFloat1[b2 * 2 + 1];
/*  732 */           b1++;
/*      */         } 
/*      */       } 
/*  735 */       m = n;
/*  736 */       arrayOfInt = arrayOfInt1;
/*  737 */       arrayOfFloat1 = arrayOfFloat;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  756 */     AffineTransform affineTransform3 = AffineTransform.getScaleInstance(d8, d9);
/*  757 */     float[] arrayOfFloat2 = new float[arrayOfFloat1.length];
/*      */     
/*  759 */     affineTransform3.transform(arrayOfFloat1, 0, arrayOfFloat2, 0, arrayOfFloat1.length / 2);
/*      */ 
/*      */ 
/*      */     
/*  763 */     Font2D font2D = FontUtilities.getFont2D(font);
/*  764 */     if (font2D instanceof TrueTypeFont) {
/*  765 */       String str = font2D.getFamilyName(null);
/*  766 */       int i1 = font.getStyle() | font2D.getStyle();
/*  767 */       if (!wPrinterJob.setFont(str, f2, i1, k, f3))
/*      */       {
/*  769 */         return false;
/*      */       }
/*  771 */       wPrinterJob.glyphsOut(arrayOfInt, float_2.x, float_2.y, arrayOfFloat2);
/*      */     }
/*  773 */     else if (font2D instanceof CompositeFont) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  781 */       CompositeFont compositeFont = (CompositeFont)font2D;
/*  782 */       float f4 = paramFloat1, f5 = paramFloat2;
/*  783 */       float f6 = float_2.x, f7 = float_2.y;
/*      */       
/*  785 */       byte b1 = 0, b2 = 0; int i1 = 0;
/*  786 */       while (b2 < m) {
/*      */         
/*  788 */         b1 = b2;
/*  789 */         i1 = arrayOfInt[b1] >>> 24;
/*      */         
/*  791 */         while (b2 < m && arrayOfInt[b2] >>> 24 == i1) {
/*  792 */           b2++;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  800 */         PhysicalFont physicalFont = compositeFont.getSlotFont(i1);
/*  801 */         if (!(physicalFont instanceof TrueTypeFont)) {
/*  802 */           return false;
/*      */         }
/*  804 */         String str = physicalFont.getFamilyName(null);
/*  805 */         int i2 = font.getStyle() | physicalFont.getStyle();
/*  806 */         if (!wPrinterJob.setFont(str, f2, i2, k, f3))
/*      */         {
/*  808 */           return false;
/*      */         }
/*      */         
/*  811 */         int[] arrayOfInt1 = Arrays.copyOfRange(arrayOfInt, b1, b2);
/*  812 */         float[] arrayOfFloat = Arrays.copyOfRange(arrayOfFloat2, b1 * 2, b2 * 2);
/*      */         
/*  814 */         if (b1 != 0) {
/*  815 */           Point2D.Float float_ = new Point2D.Float(paramFloat1 + arrayOfFloat1[b1 * 2], paramFloat2 + arrayOfFloat1[b1 * 2 + 1]);
/*      */ 
/*      */           
/*  818 */           affineTransform1.transform(float_, float_);
/*  819 */           f6 = float_.x;
/*  820 */           f7 = float_.y;
/*      */         } 
/*  822 */         wPrinterJob.glyphsOut(arrayOfInt1, f6, f7, arrayOfFloat);
/*      */       } 
/*      */     } else {
/*  825 */       return false;
/*      */     } 
/*  827 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void textOut(String paramString, Font paramFont, PhysicalFont paramPhysicalFont, FontRenderContext paramFontRenderContext, float paramFloat1, int paramInt, float paramFloat2, double paramDouble1, double paramDouble2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7) {
/*  838 */     String str = paramPhysicalFont.getFamilyName(null);
/*  839 */     int i = paramFont.getStyle() | paramPhysicalFont.getStyle();
/*  840 */     WPrinterJob wPrinterJob = (WPrinterJob)getPrinterJob();
/*  841 */     boolean bool = wPrinterJob.setFont(str, paramFloat1, i, paramInt, paramFloat2);
/*      */     
/*  843 */     if (!bool) {
/*  844 */       super.drawString(paramString, paramFloat3, paramFloat4, paramFont, paramFontRenderContext, paramFloat7);
/*      */       
/*      */       return;
/*      */     } 
/*  848 */     float[] arrayOfFloat = null;
/*  849 */     if (!okGDIMetrics(paramString, paramFont, paramFontRenderContext, paramDouble1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  857 */       paramString = wPrinterJob.removeControlChars(paramString);
/*  858 */       char[] arrayOfChar = paramString.toCharArray();
/*  859 */       int j = arrayOfChar.length;
/*  860 */       GlyphVector glyphVector = null;
/*  861 */       if (!FontUtilities.isComplexText(arrayOfChar, 0, j)) {
/*  862 */         glyphVector = paramFont.createGlyphVector(paramFontRenderContext, paramString);
/*      */       }
/*  864 */       if (glyphVector == null) {
/*  865 */         super.drawString(paramString, paramFloat3, paramFloat4, paramFont, paramFontRenderContext, paramFloat7);
/*      */         return;
/*      */       } 
/*  868 */       arrayOfFloat = glyphVector.getGlyphPositions(0, j, null);
/*  869 */       Point2D point2D = glyphVector.getGlyphPosition(glyphVector.getNumGlyphs());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  875 */       AffineTransform affineTransform = AffineTransform.getScaleInstance(paramDouble1, paramDouble2);
/*  876 */       float[] arrayOfFloat1 = new float[arrayOfFloat.length];
/*      */       
/*  878 */       affineTransform.transform(arrayOfFloat, 0, arrayOfFloat1, 0, arrayOfFloat.length / 2);
/*      */ 
/*      */       
/*  881 */       arrayOfFloat = arrayOfFloat1;
/*      */     } 
/*  883 */     wPrinterJob.textOut(paramString, paramFloat5, paramFloat6, arrayOfFloat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean okGDIMetrics(String paramString, Font paramFont, FontRenderContext paramFontRenderContext, double paramDouble) {
/*  901 */     Rectangle2D rectangle2D = paramFont.getStringBounds(paramString, paramFontRenderContext);
/*  902 */     double d = rectangle2D.getWidth();
/*  903 */     d = Math.round(d * paramDouble);
/*  904 */     int i = ((WPrinterJob)getPrinterJob()).getGDIAdvance(paramString);
/*  905 */     if (d > 0.0D && i > 0) {
/*  906 */       double d1 = Math.abs(i - d);
/*  907 */       double d2 = i / d;
/*  908 */       if (d2 < 1.0D) {
/*  909 */         d2 = 1.0D / d2;
/*      */       }
/*  911 */       return (d1 <= 1.0D || d2 < 1.01D);
/*      */     } 
/*  913 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean drawImageToPlatform(Image paramImage, AffineTransform paramAffineTransform, Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*  951 */     BufferedImage bufferedImage = getBufferedImage(paramImage);
/*  952 */     if (bufferedImage == null) {
/*  953 */       return true;
/*      */     }
/*      */     
/*  956 */     WPrinterJob wPrinterJob = (WPrinterJob)getPrinterJob();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  964 */     AffineTransform affineTransform = getTransform();
/*  965 */     if (paramAffineTransform == null) {
/*  966 */       paramAffineTransform = new AffineTransform();
/*      */     }
/*  968 */     affineTransform.concatenate(paramAffineTransform);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  988 */     double[] arrayOfDouble = new double[6];
/*  989 */     affineTransform.getMatrix(arrayOfDouble);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  999 */     Point2D.Float float_1 = new Point2D.Float(1.0F, 0.0F);
/* 1000 */     Point2D.Float float_2 = new Point2D.Float(0.0F, 1.0F);
/* 1001 */     affineTransform.deltaTransform(float_1, float_1);
/* 1002 */     affineTransform.deltaTransform(float_2, float_2);
/*      */     
/* 1004 */     Point2D.Float float_3 = new Point2D.Float(0.0F, 0.0F);
/* 1005 */     double d1 = float_1.distance(float_3);
/* 1006 */     double d2 = float_2.distance(float_3);
/*      */     
/* 1008 */     double d3 = wPrinterJob.getXRes();
/* 1009 */     double d4 = wPrinterJob.getYRes();
/* 1010 */     double d5 = d3 / 72.0D;
/* 1011 */     double d6 = d4 / 72.0D;
/*      */ 
/*      */     
/* 1014 */     int i = affineTransform.getType();
/* 1015 */     boolean bool = ((i & 0x30) != 0) ? true : false;
/*      */ 
/*      */     
/* 1018 */     if (bool) {
/* 1019 */       if (d1 > d5) d1 = d5; 
/* 1020 */       if (d2 > d6) d2 = d6;
/*      */     
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1026 */     if (d1 != 0.0D && d2 != 0.0D) {
/*      */ 
/*      */ 
/*      */       
/* 1030 */       AffineTransform affineTransform1 = new AffineTransform(arrayOfDouble[0] / d1, arrayOfDouble[1] / d2, arrayOfDouble[2] / d1, arrayOfDouble[3] / d2, arrayOfDouble[4] / d1, arrayOfDouble[5] / d2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1058 */       Rectangle2D.Float float_ = new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */ 
/*      */ 
/*      */       
/* 1062 */       Shape shape = affineTransform1.createTransformedShape(float_);
/* 1063 */       Rectangle2D rectangle2D = shape.getBounds2D();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1069 */       rectangle2D.setRect(rectangle2D.getX(), rectangle2D.getY(), rectangle2D
/* 1070 */           .getWidth() + 0.001D, rectangle2D
/* 1071 */           .getHeight() + 0.001D);
/*      */       
/* 1073 */       int j = (int)rectangle2D.getWidth();
/* 1074 */       int k = (int)rectangle2D.getHeight();
/*      */       
/* 1076 */       if (j > 0 && k > 0) {
/*      */         byte[] arrayOfByte;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1094 */         boolean bool1 = true;
/* 1095 */         if (!paramBoolean && hasTransparentPixels(bufferedImage)) {
/* 1096 */           bool1 = false;
/* 1097 */           if (isBitmaskTransparency(bufferedImage)) {
/* 1098 */             if (paramColor == null) {
/* 1099 */               if (drawBitmaskImage(bufferedImage, paramAffineTransform, paramColor, paramInt1, paramInt2, paramInt3, paramInt4))
/*      */               {
/*      */ 
/*      */                 
/* 1103 */                 return true;
/*      */               }
/* 1105 */             } else if (paramColor.getTransparency() == 1) {
/*      */               
/* 1107 */               bool1 = true;
/*      */             } 
/*      */           }
/* 1110 */           if (!canDoRedraws()) {
/* 1111 */             bool1 = true;
/*      */           
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/* 1117 */           paramColor = null;
/*      */         } 
/*      */ 
/*      */         
/* 1121 */         if ((paramInt1 + paramInt3 > bufferedImage.getWidth(null) || paramInt2 + paramInt4 > bufferedImage
/* 1122 */           .getHeight(null)) && 
/* 1123 */           canDoRedraws()) {
/* 1124 */           bool1 = false;
/*      */         }
/* 1126 */         if (!bool1) {
/*      */           
/* 1128 */           affineTransform.getMatrix(arrayOfDouble);
/* 1129 */           AffineTransform affineTransform2 = new AffineTransform(arrayOfDouble[0] / d5, arrayOfDouble[1] / d6, arrayOfDouble[2] / d5, arrayOfDouble[3] / d6, arrayOfDouble[4] / d5, arrayOfDouble[5] / d6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1138 */           Rectangle2D.Float float_5 = new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */ 
/*      */           
/* 1141 */           Shape shape2 = affineTransform.createTransformedShape(float_5);
/*      */ 
/*      */           
/* 1144 */           Rectangle2D rectangle2D1 = shape2.getBounds2D();
/*      */           
/* 1146 */           rectangle2D1.setRect(rectangle2D1.getX(), rectangle2D1.getY(), rectangle2D1
/* 1147 */               .getWidth() + 0.001D, rectangle2D1
/* 1148 */               .getHeight() + 0.001D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1155 */           int i4 = (int)rectangle2D1.getWidth();
/* 1156 */           int i5 = (int)rectangle2D1.getHeight();
/* 1157 */           int i6 = i4 * i5 * 3;
/* 1158 */           int i7 = 8388608;
/* 1159 */           double d7 = (d3 < d4) ? d3 : d4;
/* 1160 */           int i8 = (int)d7;
/* 1161 */           double d8 = 1.0D;
/*      */           
/* 1163 */           double d9 = i4 / j;
/* 1164 */           double d10 = i5 / k;
/* 1165 */           double d11 = (d9 > d10) ? d10 : d9;
/* 1166 */           int i9 = (int)(i8 / d11);
/* 1167 */           if (i9 < 72) i9 = 72;
/*      */           
/* 1169 */           while (i6 > i7 && i8 > i9) {
/* 1170 */             d8 *= 2.0D;
/* 1171 */             i8 /= 2;
/* 1172 */             i6 /= 4;
/*      */           } 
/* 1174 */           if (i8 < i9) {
/* 1175 */             d8 = d7 / i9;
/*      */           }
/*      */           
/* 1178 */           rectangle2D1.setRect(rectangle2D1.getX() / d8, rectangle2D1
/* 1179 */               .getY() / d8, rectangle2D1
/* 1180 */               .getWidth() / d8, rectangle2D1
/* 1181 */               .getHeight() / d8);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1192 */           wPrinterJob.saveState(getTransform(), getClip(), rectangle2D1, d8, d8);
/*      */           
/* 1194 */           return true;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1207 */         int m = 5;
/* 1208 */         IndexColorModel indexColorModel = null;
/*      */         
/* 1210 */         ColorModel colorModel = bufferedImage.getColorModel();
/* 1211 */         int n = bufferedImage.getType();
/* 1212 */         if (colorModel instanceof IndexColorModel && colorModel
/* 1213 */           .getPixelSize() <= 8 && (n == 12 || n == 13)) {
/*      */ 
/*      */           
/* 1216 */           indexColorModel = (IndexColorModel)colorModel;
/* 1217 */           m = n;
/*      */ 
/*      */ 
/*      */           
/* 1221 */           if (n == 12 && colorModel
/* 1222 */             .getPixelSize() == 2) {
/*      */             
/* 1224 */             int[] arrayOfInt = new int[16];
/* 1225 */             indexColorModel.getRGBs(arrayOfInt);
/*      */             
/* 1227 */             boolean bool3 = (indexColorModel.getTransparency() != 1) ? true : false;
/* 1228 */             int i4 = indexColorModel.getTransparentPixel();
/*      */             
/* 1230 */             indexColorModel = new IndexColorModel(4, 16, arrayOfInt, 0, bool3, i4, 0);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1237 */         int i1 = (int)rectangle2D.getWidth();
/* 1238 */         int i2 = (int)rectangle2D.getHeight();
/* 1239 */         BufferedImage bufferedImage1 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1266 */         boolean bool2 = true;
/* 1267 */         if (bool2) {
/* 1268 */           if (indexColorModel == null) {
/* 1269 */             bufferedImage1 = new BufferedImage(i1, i2, m);
/*      */           } else {
/* 1271 */             bufferedImage1 = new BufferedImage(i1, i2, m, indexColorModel);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1278 */           Graphics2D graphics2D = bufferedImage1.createGraphics();
/* 1279 */           graphics2D.clipRect(0, 0, bufferedImage1
/* 1280 */               .getWidth(), bufferedImage1
/* 1281 */               .getHeight());
/*      */           
/* 1283 */           graphics2D.translate(-rectangle2D.getX(), 
/* 1284 */               -rectangle2D.getY());
/* 1285 */           graphics2D.transform(affineTransform1);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1290 */           if (paramColor == null) {
/* 1291 */             paramColor = Color.white;
/*      */           }
/*      */           
/* 1294 */           graphics2D.drawImage(bufferedImage, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4, paramColor, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1302 */           graphics2D.dispose();
/*      */         } else {
/* 1304 */           bufferedImage1 = bufferedImage;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1319 */         Rectangle2D.Float float_4 = new Rectangle2D.Float((float)(rectangle2D.getX() * d1), (float)(rectangle2D.getY() * d2), (float)(rectangle2D.getWidth() * d1), (float)(rectangle2D.getHeight() * d2));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1324 */         WritableRaster writableRaster = bufferedImage1.getRaster();
/*      */         
/* 1326 */         if (writableRaster instanceof ByteComponentRaster) {
/* 1327 */           arrayOfByte = ((ByteComponentRaster)writableRaster).getDataStorage();
/* 1328 */         } else if (writableRaster instanceof BytePackedRaster) {
/* 1329 */           arrayOfByte = ((BytePackedRaster)writableRaster).getDataStorage();
/*      */         } else {
/* 1331 */           return false;
/*      */         } 
/*      */         
/* 1334 */         int i3 = 24;
/* 1335 */         SampleModel sampleModel = bufferedImage1.getSampleModel();
/* 1336 */         if (sampleModel instanceof ComponentSampleModel) {
/* 1337 */           ComponentSampleModel componentSampleModel = (ComponentSampleModel)sampleModel;
/* 1338 */           i3 = componentSampleModel.getPixelStride() * 8;
/* 1339 */         } else if (sampleModel instanceof MultiPixelPackedSampleModel) {
/* 1340 */           MultiPixelPackedSampleModel multiPixelPackedSampleModel = (MultiPixelPackedSampleModel)sampleModel;
/*      */           
/* 1342 */           i3 = multiPixelPackedSampleModel.getPixelBitStride();
/*      */         }
/* 1344 */         else if (indexColorModel != null) {
/* 1345 */           int i4 = bufferedImage1.getWidth();
/* 1346 */           int i5 = bufferedImage1.getHeight();
/* 1347 */           if (i4 > 0 && i5 > 0) {
/* 1348 */             i3 = arrayOfByte.length * 8 / i4 / i5;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1360 */         Shape shape1 = getClip();
/* 1361 */         clip(paramAffineTransform.createTransformedShape(float_));
/* 1362 */         deviceClip(getClip().getPathIterator(getTransform()));
/*      */         
/* 1364 */         wPrinterJob
/* 1365 */           .drawDIBImage(arrayOfByte, float_4.x, float_4.y, 
/* 1366 */             (float)Math.rint(float_4.width + 0.5D), 
/* 1367 */             (float)Math.rint(float_4.height + 0.5D), 0.0F, 0.0F, bufferedImage1
/*      */             
/* 1369 */             .getWidth(), bufferedImage1.getHeight(), i3, indexColorModel);
/*      */ 
/*      */         
/* 1372 */         setClip(shape1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1377 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void redrawRegion(Rectangle2D paramRectangle2D, double paramDouble1, double paramDouble2, Shape paramShape, AffineTransform paramAffineTransform) throws PrinterException {
/* 1389 */     WPrinterJob wPrinterJob = (WPrinterJob)getPrinterJob();
/* 1390 */     Printable printable = getPrintable();
/* 1391 */     PageFormat pageFormat = getPageFormat();
/* 1392 */     int i = getPageIndex();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1399 */     BufferedImage bufferedImage = new BufferedImage((int)paramRectangle2D.getWidth(), (int)paramRectangle2D.getHeight(), 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1408 */     Graphics2D graphics2D = bufferedImage.createGraphics();
/* 1409 */     ProxyGraphics2D proxyGraphics2D = new ProxyGraphics2D(graphics2D, wPrinterJob);
/* 1410 */     proxyGraphics2D.setColor(Color.white);
/* 1411 */     proxyGraphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
/* 1412 */     proxyGraphics2D.clipRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
/*      */     
/* 1414 */     proxyGraphics2D.translate(-paramRectangle2D.getX(), -paramRectangle2D.getY());
/*      */ 
/*      */ 
/*      */     
/* 1418 */     float f1 = (float)(wPrinterJob.getXRes() / paramDouble1);
/* 1419 */     float f2 = (float)(wPrinterJob.getYRes() / paramDouble2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1425 */     proxyGraphics2D.scale((f1 / 72.0F), (f2 / 72.0F));
/*      */ 
/*      */     
/* 1428 */     proxyGraphics2D.translate(
/* 1429 */         -wPrinterJob.getPhysicalPrintableX(pageFormat.getPaper()) / wPrinterJob
/* 1430 */         .getXRes() * 72.0D, 
/* 1431 */         -wPrinterJob.getPhysicalPrintableY(pageFormat.getPaper()) / wPrinterJob
/* 1432 */         .getYRes() * 72.0D);
/*      */     
/* 1434 */     proxyGraphics2D.transform(new AffineTransform(getPageFormat().getMatrix()));
/* 1435 */     proxyGraphics2D.setPaint(Color.black);
/*      */     
/* 1437 */     printable.print(proxyGraphics2D, pageFormat, i);
/*      */     
/* 1439 */     graphics2D.dispose();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1448 */     if (paramShape != null) {
/* 1449 */       deviceClip(paramShape.getPathIterator(paramAffineTransform));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1464 */     Rectangle2D.Float float_ = new Rectangle2D.Float((float)(paramRectangle2D.getX() * paramDouble1), (float)(paramRectangle2D.getY() * paramDouble2), (float)(paramRectangle2D.getWidth() * paramDouble1), (float)(paramRectangle2D.getHeight() * paramDouble2));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1470 */     ByteComponentRaster byteComponentRaster = (ByteComponentRaster)bufferedImage.getRaster();
/*      */     
/* 1472 */     wPrinterJob.drawImage3ByteBGR(byteComponentRaster.getDataStorage(), float_.x, float_.y, float_.width, float_.height, 0.0F, 0.0F, bufferedImage
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1477 */         .getWidth(), bufferedImage.getHeight());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void deviceFill(PathIterator paramPathIterator, Color paramColor) {
/* 1489 */     WPrinterJob wPrinterJob = (WPrinterJob)getPrinterJob();
/*      */     
/* 1491 */     convertToWPath(paramPathIterator);
/* 1492 */     wPrinterJob.selectSolidBrush(paramColor);
/* 1493 */     wPrinterJob.fillPath();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void deviceClip(PathIterator paramPathIterator) {
/* 1504 */     WPrinterJob wPrinterJob = (WPrinterJob)getPrinterJob();
/*      */     
/* 1506 */     convertToWPath(paramPathIterator);
/* 1507 */     wPrinterJob.selectClipPath();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void deviceFrameRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor) {
/* 1517 */     AffineTransform affineTransform = getTransform();
/*      */ 
/*      */     
/* 1520 */     int i = affineTransform.getType();
/* 1521 */     boolean bool = ((i & 0x30) != 0) ? true : false;
/*      */ 
/*      */ 
/*      */     
/* 1525 */     if (bool) {
/* 1526 */       draw(new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */       
/*      */       return;
/*      */     } 
/* 1530 */     Stroke stroke = getStroke();
/*      */     
/* 1532 */     if (stroke instanceof BasicStroke) {
/* 1533 */       BasicStroke basicStroke = (BasicStroke)stroke;
/*      */       
/* 1535 */       int j = basicStroke.getEndCap();
/* 1536 */       int k = basicStroke.getLineJoin();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1542 */       if (j == 2 && k == 0 && basicStroke
/*      */         
/* 1544 */         .getMiterLimit() == 10.0F) {
/*      */         
/* 1546 */         float f1 = basicStroke.getLineWidth();
/* 1547 */         Point2D.Float float_1 = new Point2D.Float(f1, f1);
/*      */ 
/*      */         
/* 1550 */         affineTransform.deltaTransform(float_1, float_1);
/* 1551 */         float f2 = Math.min(Math.abs(float_1.x), 
/* 1552 */             Math.abs(float_1.y));
/*      */ 
/*      */         
/* 1555 */         Point2D.Float float_2 = new Point2D.Float(paramInt1, paramInt2);
/* 1556 */         affineTransform.transform(float_2, float_2);
/*      */ 
/*      */         
/* 1559 */         Point2D.Float float_3 = new Point2D.Float((paramInt1 + paramInt3), (paramInt2 + paramInt4));
/*      */         
/* 1561 */         affineTransform.transform(float_3, float_3);
/*      */         
/* 1563 */         float f3 = (float)(float_3.getX() - float_2.getX());
/* 1564 */         float f4 = (float)(float_3.getY() - float_2.getY());
/*      */         
/* 1566 */         WPrinterJob wPrinterJob = (WPrinterJob)getPrinterJob();
/*      */ 
/*      */         
/* 1569 */         if (wPrinterJob.selectStylePen(j, k, f2, paramColor) == true) {
/*      */           
/* 1571 */           wPrinterJob.frameRect((float)float_2.getX(), 
/* 1572 */               (float)float_2.getY(), f3, f4);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1577 */           double d = Math.min(wPrinterJob.getXRes(), wPrinterJob
/* 1578 */               .getYRes());
/*      */           
/* 1580 */           if (f2 / d < 0.014000000432133675D) {
/*      */             
/* 1582 */             wPrinterJob.selectPen(f2, paramColor);
/* 1583 */             wPrinterJob.frameRect((float)float_2.getX(), 
/* 1584 */                 (float)float_2.getY(), f3, f4);
/*      */           } else {
/*      */             
/* 1587 */             draw(new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/* 1592 */         draw(new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void deviceFillRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor) {
/* 1609 */     AffineTransform affineTransform = getTransform();
/*      */ 
/*      */     
/* 1612 */     int i = affineTransform.getType();
/* 1613 */     boolean bool = ((i & 0x30) != 0) ? true : false;
/*      */ 
/*      */     
/* 1616 */     if (bool) {
/* 1617 */       fill(new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */       
/*      */       return;
/*      */     } 
/* 1621 */     Point2D.Float float_1 = new Point2D.Float(paramInt1, paramInt2);
/* 1622 */     affineTransform.transform(float_1, float_1);
/*      */     
/* 1624 */     Point2D.Float float_2 = new Point2D.Float((paramInt1 + paramInt3), (paramInt2 + paramInt4));
/* 1625 */     affineTransform.transform(float_2, float_2);
/*      */     
/* 1627 */     float f1 = (float)(float_2.getX() - float_1.getX());
/* 1628 */     float f2 = (float)(float_2.getY() - float_1.getY());
/*      */     
/* 1630 */     WPrinterJob wPrinterJob = (WPrinterJob)getPrinterJob();
/* 1631 */     wPrinterJob.fillRect((float)float_1.getX(), (float)float_1.getY(), f1, f2, paramColor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void deviceDrawLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor) {
/* 1643 */     Stroke stroke = getStroke();
/*      */     
/* 1645 */     if (stroke instanceof BasicStroke) {
/* 1646 */       BasicStroke basicStroke = (BasicStroke)stroke;
/*      */       
/* 1648 */       if (basicStroke.getDashArray() != null) {
/* 1649 */         draw(new Line2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */         
/*      */         return;
/*      */       } 
/* 1653 */       float f1 = basicStroke.getLineWidth();
/* 1654 */       Point2D.Float float_1 = new Point2D.Float(f1, f1);
/*      */       
/* 1656 */       AffineTransform affineTransform = getTransform();
/* 1657 */       affineTransform.deltaTransform(float_1, float_1);
/*      */       
/* 1659 */       float f2 = Math.min(Math.abs(float_1.x), 
/* 1660 */           Math.abs(float_1.y));
/*      */       
/* 1662 */       Point2D.Float float_2 = new Point2D.Float(paramInt1, paramInt2);
/* 1663 */       affineTransform.transform(float_2, float_2);
/*      */       
/* 1665 */       Point2D.Float float_3 = new Point2D.Float(paramInt3, paramInt4);
/* 1666 */       affineTransform.transform(float_3, float_3);
/*      */       
/* 1668 */       int i = basicStroke.getEndCap();
/* 1669 */       int j = basicStroke.getLineJoin();
/*      */ 
/*      */       
/* 1672 */       if (float_3.getX() == float_2.getX() && float_3
/* 1673 */         .getY() == float_2.getY())
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1678 */         i = 1;
/*      */       }
/*      */ 
/*      */       
/* 1682 */       WPrinterJob wPrinterJob = (WPrinterJob)getPrinterJob();
/*      */ 
/*      */       
/* 1685 */       if (wPrinterJob.selectStylePen(i, j, f2, paramColor)) {
/*      */         
/* 1687 */         wPrinterJob.moveTo((float)float_2.getX(), 
/* 1688 */             (float)float_2.getY());
/* 1689 */         wPrinterJob.lineTo((float)float_3.getX(), 
/* 1690 */             (float)float_3.getY());
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1700 */         double d = Math.min(wPrinterJob.getXRes(), wPrinterJob
/* 1701 */             .getYRes());
/*      */         
/* 1703 */         if (i == 1 || ((paramInt1 == paramInt3 || paramInt2 == paramInt4) && f2 / d < 0.014000000432133675D)) {
/*      */ 
/*      */ 
/*      */           
/* 1707 */           wPrinterJob.selectPen(f2, paramColor);
/* 1708 */           wPrinterJob.moveTo((float)float_2.getX(), 
/* 1709 */               (float)float_2.getY());
/* 1710 */           wPrinterJob.lineTo((float)float_3.getX(), 
/* 1711 */               (float)float_3.getY());
/*      */         } else {
/*      */           
/* 1714 */           draw(new Line2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void convertToWPath(PathIterator paramPathIterator) {
/*      */     byte b;
/* 1728 */     float[] arrayOfFloat = new float[6];
/*      */ 
/*      */     
/* 1731 */     WPrinterJob wPrinterJob = (WPrinterJob)getPrinterJob();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1737 */     if (paramPathIterator.getWindingRule() == 0) {
/* 1738 */       b = 1;
/*      */     } else {
/* 1740 */       b = 2;
/*      */     } 
/* 1742 */     wPrinterJob.setPolyFillMode(b);
/*      */     
/* 1744 */     wPrinterJob.beginPath();
/*      */     
/* 1746 */     while (!paramPathIterator.isDone()) {
/* 1747 */       int j, k; float f1, f2, f3, f4; int i = paramPathIterator.currentSegment(arrayOfFloat);
/*      */       
/* 1749 */       switch (i) {
/*      */         case 0:
/* 1751 */           wPrinterJob.moveTo(arrayOfFloat[0], arrayOfFloat[1]);
/*      */           break;
/*      */         
/*      */         case 1:
/* 1755 */           wPrinterJob.lineTo(arrayOfFloat[0], arrayOfFloat[1]);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1761 */           j = wPrinterJob.getPenX();
/* 1762 */           k = wPrinterJob.getPenY();
/* 1763 */           f1 = j + (arrayOfFloat[0] - j) * 2.0F / 3.0F;
/* 1764 */           f2 = k + (arrayOfFloat[1] - k) * 2.0F / 3.0F;
/* 1765 */           f3 = arrayOfFloat[2] - (arrayOfFloat[2] - arrayOfFloat[0]) * 2.0F / 3.0F;
/* 1766 */           f4 = arrayOfFloat[3] - (arrayOfFloat[3] - arrayOfFloat[1]) * 2.0F / 3.0F;
/* 1767 */           wPrinterJob.polyBezierTo(f1, f2, f3, f4, arrayOfFloat[2], arrayOfFloat[3]);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 3:
/* 1773 */           wPrinterJob.polyBezierTo(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 4:
/* 1779 */           wPrinterJob.closeFigure();
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1784 */       paramPathIterator.next();
/*      */     } 
/*      */     
/* 1787 */     wPrinterJob.endPath();
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WPathGraphics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */