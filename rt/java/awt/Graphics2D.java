/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.GlyphVector;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.BufferedImageOp;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.RenderedImage;
/*     */ import java.awt.image.renderable.RenderableImage;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.util.Map;
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
/*     */ public abstract class Graphics2D
/*     */   extends Graphics
/*     */ {
/*     */   public void draw3DRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 447 */     Paint paint = getPaint();
/* 448 */     Color color1 = getColor();
/* 449 */     Color color2 = color1.brighter();
/* 450 */     Color color3 = color1.darker();
/*     */     
/* 452 */     setColor(paramBoolean ? color2 : color3);
/*     */     
/* 454 */     fillRect(paramInt1, paramInt2, 1, paramInt4 + 1);
/*     */     
/* 456 */     fillRect(paramInt1 + 1, paramInt2, paramInt3 - 1, 1);
/* 457 */     setColor(paramBoolean ? color3 : color2);
/*     */     
/* 459 */     fillRect(paramInt1 + 1, paramInt2 + paramInt4, paramInt3, 1);
/*     */     
/* 461 */     fillRect(paramInt1 + paramInt3, paramInt2, 1, paramInt4);
/* 462 */     setPaint(paint);
/*     */   }
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
/*     */   public void fill3DRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 484 */     Paint paint = getPaint();
/* 485 */     Color color1 = getColor();
/* 486 */     Color color2 = color1.brighter();
/* 487 */     Color color3 = color1.darker();
/*     */     
/* 489 */     if (!paramBoolean) {
/* 490 */       setColor(color3);
/* 491 */     } else if (paint != color1) {
/* 492 */       setColor(color1);
/*     */     } 
/* 494 */     fillRect(paramInt1 + 1, paramInt2 + 1, paramInt3 - 2, paramInt4 - 2);
/* 495 */     setColor(paramBoolean ? color2 : color3);
/*     */     
/* 497 */     fillRect(paramInt1, paramInt2, 1, paramInt4);
/*     */     
/* 499 */     fillRect(paramInt1 + 1, paramInt2, paramInt3 - 2, 1);
/* 500 */     setColor(paramBoolean ? color3 : color2);
/*     */     
/* 502 */     fillRect(paramInt1 + 1, paramInt2 + paramInt4 - 1, paramInt3 - 1, 1);
/*     */     
/* 504 */     fillRect(paramInt1 + paramInt3 - 1, paramInt2, 1, paramInt4 - 1);
/* 505 */     setPaint(paint);
/*     */   }
/*     */   
/*     */   public abstract void draw(Shape paramShape);
/*     */   
/*     */   public abstract boolean drawImage(Image paramImage, AffineTransform paramAffineTransform, ImageObserver paramImageObserver);
/*     */   
/*     */   public abstract void drawImage(BufferedImage paramBufferedImage, BufferedImageOp paramBufferedImageOp, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void drawRenderedImage(RenderedImage paramRenderedImage, AffineTransform paramAffineTransform);
/*     */   
/*     */   public abstract void drawRenderableImage(RenderableImage paramRenderableImage, AffineTransform paramAffineTransform);
/*     */   
/*     */   public abstract void drawString(String paramString, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void drawString(String paramString, float paramFloat1, float paramFloat2);
/*     */   
/*     */   public abstract void drawString(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void drawString(AttributedCharacterIterator paramAttributedCharacterIterator, float paramFloat1, float paramFloat2);
/*     */   
/*     */   public abstract void drawGlyphVector(GlyphVector paramGlyphVector, float paramFloat1, float paramFloat2);
/*     */   
/*     */   public abstract void fill(Shape paramShape);
/*     */   
/*     */   public abstract boolean hit(Rectangle paramRectangle, Shape paramShape, boolean paramBoolean);
/*     */   
/*     */   public abstract GraphicsConfiguration getDeviceConfiguration();
/*     */   
/*     */   public abstract void setComposite(Composite paramComposite);
/*     */   
/*     */   public abstract void setPaint(Paint paramPaint);
/*     */   
/*     */   public abstract void setStroke(Stroke paramStroke);
/*     */   
/*     */   public abstract void setRenderingHint(RenderingHints.Key paramKey, Object paramObject);
/*     */   
/*     */   public abstract Object getRenderingHint(RenderingHints.Key paramKey);
/*     */   
/*     */   public abstract void setRenderingHints(Map<?, ?> paramMap);
/*     */   
/*     */   public abstract void addRenderingHints(Map<?, ?> paramMap);
/*     */   
/*     */   public abstract RenderingHints getRenderingHints();
/*     */   
/*     */   public abstract void translate(int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void translate(double paramDouble1, double paramDouble2);
/*     */   
/*     */   public abstract void rotate(double paramDouble);
/*     */   
/*     */   public abstract void rotate(double paramDouble1, double paramDouble2, double paramDouble3);
/*     */   
/*     */   public abstract void scale(double paramDouble1, double paramDouble2);
/*     */   
/*     */   public abstract void shear(double paramDouble1, double paramDouble2);
/*     */   
/*     */   public abstract void transform(AffineTransform paramAffineTransform);
/*     */   
/*     */   public abstract void setTransform(AffineTransform paramAffineTransform);
/*     */   
/*     */   public abstract AffineTransform getTransform();
/*     */   
/*     */   public abstract Paint getPaint();
/*     */   
/*     */   public abstract Composite getComposite();
/*     */   
/*     */   public abstract void setBackground(Color paramColor);
/*     */   
/*     */   public abstract Color getBackground();
/*     */   
/*     */   public abstract Stroke getStroke();
/*     */   
/*     */   public abstract void clip(Shape paramShape);
/*     */   
/*     */   public abstract FontRenderContext getFontRenderContext();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\Graphics2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */