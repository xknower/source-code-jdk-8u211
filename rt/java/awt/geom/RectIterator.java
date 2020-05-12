/*     */ package java.awt.geom;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RectIterator
/*     */   implements PathIterator
/*     */ {
/*     */   double x;
/*     */   double y;
/*     */   double w;
/*     */   double h;
/*     */   AffineTransform affine;
/*     */   int index;
/*     */   
/*     */   RectIterator(Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform) {
/*  42 */     this.x = paramRectangle2D.getX();
/*  43 */     this.y = paramRectangle2D.getY();
/*  44 */     this.w = paramRectangle2D.getWidth();
/*  45 */     this.h = paramRectangle2D.getHeight();
/*  46 */     this.affine = paramAffineTransform;
/*  47 */     if (this.w < 0.0D || this.h < 0.0D) {
/*  48 */       this.index = 6;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWindingRule() {
/*  59 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/*  67 */     return (this.index > 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void next() {
/*  76 */     this.index++;
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
/*     */   public int currentSegment(float[] paramArrayOffloat) {
/*  98 */     if (isDone()) {
/*  99 */       throw new NoSuchElementException("rect iterator out of bounds");
/*     */     }
/* 101 */     if (this.index == 5) {
/* 102 */       return 4;
/*     */     }
/* 104 */     paramArrayOffloat[0] = (float)this.x;
/* 105 */     paramArrayOffloat[1] = (float)this.y;
/* 106 */     if (this.index == 1 || this.index == 2) {
/* 107 */       paramArrayOffloat[0] = paramArrayOffloat[0] + (float)this.w;
/*     */     }
/* 109 */     if (this.index == 2 || this.index == 3) {
/* 110 */       paramArrayOffloat[1] = paramArrayOffloat[1] + (float)this.h;
/*     */     }
/* 112 */     if (this.affine != null) {
/* 113 */       this.affine.transform(paramArrayOffloat, 0, paramArrayOffloat, 0, 1);
/*     */     }
/* 115 */     return (this.index == 0) ? 0 : 1;
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
/*     */   public int currentSegment(double[] paramArrayOfdouble) {
/* 137 */     if (isDone()) {
/* 138 */       throw new NoSuchElementException("rect iterator out of bounds");
/*     */     }
/* 140 */     if (this.index == 5) {
/* 141 */       return 4;
/*     */     }
/* 143 */     paramArrayOfdouble[0] = this.x;
/* 144 */     paramArrayOfdouble[1] = this.y;
/* 145 */     if (this.index == 1 || this.index == 2) {
/* 146 */       paramArrayOfdouble[0] = paramArrayOfdouble[0] + this.w;
/*     */     }
/* 148 */     if (this.index == 2 || this.index == 3) {
/* 149 */       paramArrayOfdouble[1] = paramArrayOfdouble[1] + this.h;
/*     */     }
/* 151 */     if (this.affine != null) {
/* 152 */       this.affine.transform(paramArrayOfdouble, 0, paramArrayOfdouble, 0, 1);
/*     */     }
/* 154 */     return (this.index == 0) ? 0 : 1;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\geom\RectIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */