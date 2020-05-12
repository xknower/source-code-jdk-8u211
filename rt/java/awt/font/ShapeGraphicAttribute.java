/*     */ package java.awt.font;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ShapeGraphicAttribute
/*     */   extends GraphicAttribute
/*     */ {
/*     */   private Shape fShape;
/*     */   private boolean fStroke;
/*     */   public static final boolean STROKE = true;
/*     */   public static final boolean FILL = false;
/*     */   private Rectangle2D fShapeBounds;
/*     */   
/*     */   public ShapeGraphicAttribute(Shape paramShape, int paramInt, boolean paramBoolean) {
/*  92 */     super(paramInt);
/*     */     
/*  94 */     this.fShape = paramShape;
/*  95 */     this.fStroke = paramBoolean;
/*  96 */     this.fShapeBounds = this.fShape.getBounds2D();
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
/*     */   public float getAscent() {
/* 108 */     return (float)Math.max(0.0D, -this.fShapeBounds.getMinY());
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
/*     */   public float getDescent() {
/* 120 */     return (float)Math.max(0.0D, this.fShapeBounds.getMaxY());
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
/*     */   public float getAdvance() {
/* 132 */     return (float)Math.max(0.0D, this.fShapeBounds.getMaxX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Graphics2D paramGraphics2D, float paramFloat1, float paramFloat2) {
/* 141 */     paramGraphics2D.translate((int)paramFloat1, (int)paramFloat2);
/*     */     
/*     */     try {
/* 144 */       if (this.fStroke == true) {
/*     */         
/* 146 */         paramGraphics2D.draw(this.fShape);
/*     */       } else {
/*     */         
/* 149 */         paramGraphics2D.fill(this.fShape);
/*     */       } 
/*     */     } finally {
/*     */       
/* 153 */       paramGraphics2D.translate(-((int)paramFloat1), -((int)paramFloat2));
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
/*     */   public Rectangle2D getBounds() {
/* 168 */     Rectangle2D.Float float_ = new Rectangle2D.Float();
/* 169 */     float_.setRect(this.fShapeBounds);
/*     */     
/* 171 */     if (this.fStroke == true) {
/* 172 */       float_.width++;
/* 173 */       float_.height++;
/*     */     } 
/*     */     
/* 176 */     return float_;
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
/*     */   public Shape getOutline(AffineTransform paramAffineTransform) {
/* 192 */     return (paramAffineTransform == null) ? this.fShape : paramAffineTransform.createTransformedShape(this.fShape);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 202 */     return this.fShape.hashCode();
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
/*     */   public boolean equals(Object paramObject) {
/*     */     try {
/* 216 */       return equals((ShapeGraphicAttribute)paramObject);
/*     */     }
/* 218 */     catch (ClassCastException classCastException) {
/* 219 */       return false;
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
/*     */   public boolean equals(ShapeGraphicAttribute paramShapeGraphicAttribute) {
/* 234 */     if (paramShapeGraphicAttribute == null) {
/* 235 */       return false;
/*     */     }
/*     */     
/* 238 */     if (this == paramShapeGraphicAttribute) {
/* 239 */       return true;
/*     */     }
/*     */     
/* 242 */     if (this.fStroke != paramShapeGraphicAttribute.fStroke) {
/* 243 */       return false;
/*     */     }
/*     */     
/* 246 */     if (getAlignment() != paramShapeGraphicAttribute.getAlignment()) {
/* 247 */       return false;
/*     */     }
/*     */     
/* 250 */     if (!this.fShape.equals(paramShapeGraphicAttribute.fShape)) {
/* 251 */       return false;
/*     */     }
/*     */     
/* 254 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\font\ShapeGraphicAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */