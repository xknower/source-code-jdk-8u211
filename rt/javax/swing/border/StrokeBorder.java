/*     */ package javax.swing.border;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Paint;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.beans.ConstructorProperties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StrokeBorder
/*     */   extends AbstractBorder
/*     */ {
/*     */   private final BasicStroke stroke;
/*     */   private final Paint paint;
/*     */   
/*     */   public StrokeBorder(BasicStroke paramBasicStroke) {
/*  66 */     this(paramBasicStroke, null);
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
/*     */   @ConstructorProperties({"stroke", "paint"})
/*     */   public StrokeBorder(BasicStroke paramBasicStroke, Paint paramPaint) {
/*  81 */     if (paramBasicStroke == null) {
/*  82 */       throw new NullPointerException("border's stroke");
/*     */     }
/*  84 */     this.stroke = paramBasicStroke;
/*  85 */     this.paint = paramPaint;
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
/*     */   public void paintBorder(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 107 */     float f = this.stroke.getLineWidth();
/* 108 */     if (f > 0.0F) {
/* 109 */       paramGraphics = paramGraphics.create();
/* 110 */       if (paramGraphics instanceof Graphics2D) {
/* 111 */         Graphics2D graphics2D = (Graphics2D)paramGraphics;
/* 112 */         graphics2D.setStroke(this.stroke);
/* 113 */         graphics2D.setPaint((this.paint != null) ? this.paint : ((paramComponent == null) ? null : paramComponent.getForeground()));
/* 114 */         graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */         
/* 116 */         graphics2D.draw(new Rectangle2D.Float(paramInt1 + f / 2.0F, paramInt2 + f / 2.0F, paramInt3 - f, paramInt4 - f));
/*     */       } 
/* 118 */       paramGraphics.dispose();
/*     */     } 
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
/*     */   public Insets getBorderInsets(Component paramComponent, Insets paramInsets) {
/* 139 */     int i = (int)Math.ceil(this.stroke.getLineWidth());
/* 140 */     paramInsets.set(i, i, i, i);
/* 141 */     return paramInsets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicStroke getStroke() {
/* 151 */     return this.stroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getPaint() {
/* 162 */     return this.paint;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\border\StrokeBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */