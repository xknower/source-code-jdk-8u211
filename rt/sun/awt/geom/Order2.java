/*     */ package sun.awt.geom;
/*     */ 
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Order2
/*     */   extends Curve
/*     */ {
/*     */   private double x0;
/*     */   private double y0;
/*     */   private double cx0;
/*     */   private double cy0;
/*     */   private double x1;
/*     */   private double y1;
/*     */   private double xmin;
/*     */   private double xmax;
/*     */   private double xcoeff0;
/*     */   private double xcoeff1;
/*     */   private double xcoeff2;
/*     */   private double ycoeff0;
/*     */   private double ycoeff1;
/*     */   private double ycoeff2;
/*     */   
/*     */   public static void insert(Vector paramVector, double[] paramArrayOfdouble, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt) {
/*  56 */     int i = getHorizontalParams(paramDouble2, paramDouble4, paramDouble6, paramArrayOfdouble);
/*  57 */     if (i == 0) {
/*     */ 
/*     */       
/*  60 */       addInstance(paramVector, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramInt);
/*     */       
/*     */       return;
/*     */     } 
/*  64 */     double d = paramArrayOfdouble[0];
/*  65 */     paramArrayOfdouble[0] = paramDouble1; paramArrayOfdouble[1] = paramDouble2;
/*  66 */     paramArrayOfdouble[2] = paramDouble3; paramArrayOfdouble[3] = paramDouble4;
/*  67 */     paramArrayOfdouble[4] = paramDouble5; paramArrayOfdouble[5] = paramDouble6;
/*  68 */     split(paramArrayOfdouble, 0, d);
/*  69 */     byte b = (paramInt == 1) ? 0 : 4;
/*  70 */     int j = 4 - b;
/*  71 */     addInstance(paramVector, paramArrayOfdouble[b], paramArrayOfdouble[b + 1], paramArrayOfdouble[b + 2], paramArrayOfdouble[b + 3], paramArrayOfdouble[b + 4], paramArrayOfdouble[b + 5], paramInt);
/*     */     
/*  73 */     addInstance(paramVector, paramArrayOfdouble[j], paramArrayOfdouble[j + 1], paramArrayOfdouble[j + 2], paramArrayOfdouble[j + 3], paramArrayOfdouble[j + 4], paramArrayOfdouble[j + 5], paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addInstance(Vector<Order2> paramVector, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt) {
/*  82 */     if (paramDouble2 > paramDouble6) {
/*  83 */       paramVector.add(new Order2(paramDouble5, paramDouble6, paramDouble3, paramDouble4, paramDouble1, paramDouble2, -paramInt));
/*  84 */     } else if (paramDouble6 > paramDouble2) {
/*  85 */       paramVector.add(new Order2(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramInt));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getHorizontalParams(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfdouble) {
/* 113 */     if (paramDouble1 <= paramDouble2 && paramDouble2 <= paramDouble3) {
/* 114 */       return 0;
/*     */     }
/* 116 */     paramDouble1 -= paramDouble2;
/* 117 */     paramDouble3 -= paramDouble2;
/* 118 */     double d1 = paramDouble1 + paramDouble3;
/*     */     
/* 120 */     if (d1 == 0.0D) {
/* 121 */       return 0;
/*     */     }
/* 123 */     double d2 = paramDouble1 / d1;
/*     */     
/* 125 */     if (d2 <= 0.0D || d2 >= 1.0D) {
/* 126 */       return 0;
/*     */     }
/* 128 */     paramArrayOfdouble[0] = d2;
/* 129 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void split(double[] paramArrayOfdouble, int paramInt, double paramDouble) {
/* 140 */     double d5 = paramArrayOfdouble[paramInt + 4];
/* 141 */     double d6 = paramArrayOfdouble[paramInt + 5];
/* 142 */     double d3 = paramArrayOfdouble[paramInt + 2];
/* 143 */     double d4 = paramArrayOfdouble[paramInt + 3];
/* 144 */     d5 = d3 + (d5 - d3) * paramDouble;
/* 145 */     d6 = d4 + (d6 - d4) * paramDouble;
/* 146 */     double d1 = paramArrayOfdouble[paramInt + 0];
/* 147 */     double d2 = paramArrayOfdouble[paramInt + 1];
/* 148 */     d1 += (d3 - d1) * paramDouble;
/* 149 */     d2 += (d4 - d2) * paramDouble;
/* 150 */     d3 = d1 + (d5 - d1) * paramDouble;
/* 151 */     d4 = d2 + (d6 - d2) * paramDouble;
/* 152 */     paramArrayOfdouble[paramInt + 2] = d1;
/* 153 */     paramArrayOfdouble[paramInt + 3] = d2;
/* 154 */     paramArrayOfdouble[paramInt + 4] = d3;
/* 155 */     paramArrayOfdouble[paramInt + 5] = d4;
/* 156 */     paramArrayOfdouble[paramInt + 6] = d5;
/* 157 */     paramArrayOfdouble[paramInt + 7] = d6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Order2(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt) {
/* 165 */     super(paramInt);
/*     */ 
/*     */ 
/*     */     
/* 169 */     if (paramDouble4 < paramDouble2) {
/* 170 */       paramDouble4 = paramDouble2;
/* 171 */     } else if (paramDouble4 > paramDouble6) {
/* 172 */       paramDouble4 = paramDouble6;
/*     */     } 
/* 174 */     this.x0 = paramDouble1;
/* 175 */     this.y0 = paramDouble2;
/* 176 */     this.cx0 = paramDouble3;
/* 177 */     this.cy0 = paramDouble4;
/* 178 */     this.x1 = paramDouble5;
/* 179 */     this.y1 = paramDouble6;
/* 180 */     this.xmin = Math.min(Math.min(paramDouble1, paramDouble5), paramDouble3);
/* 181 */     this.xmax = Math.max(Math.max(paramDouble1, paramDouble5), paramDouble3);
/* 182 */     this.xcoeff0 = paramDouble1;
/* 183 */     this.xcoeff1 = paramDouble3 + paramDouble3 - paramDouble1 - paramDouble1;
/* 184 */     this.xcoeff2 = paramDouble1 - paramDouble3 - paramDouble3 + paramDouble5;
/* 185 */     this.ycoeff0 = paramDouble2;
/* 186 */     this.ycoeff1 = paramDouble4 + paramDouble4 - paramDouble2 - paramDouble2;
/* 187 */     this.ycoeff2 = paramDouble2 - paramDouble4 - paramDouble4 + paramDouble6;
/*     */   }
/*     */   
/*     */   public int getOrder() {
/* 191 */     return 2;
/*     */   }
/*     */   
/*     */   public double getXTop() {
/* 195 */     return this.x0;
/*     */   }
/*     */   
/*     */   public double getYTop() {
/* 199 */     return this.y0;
/*     */   }
/*     */   
/*     */   public double getXBot() {
/* 203 */     return this.x1;
/*     */   }
/*     */   
/*     */   public double getYBot() {
/* 207 */     return this.y1;
/*     */   }
/*     */   
/*     */   public double getXMin() {
/* 211 */     return this.xmin;
/*     */   }
/*     */   
/*     */   public double getXMax() {
/* 215 */     return this.xmax;
/*     */   }
/*     */   
/*     */   public double getX0() {
/* 219 */     return (this.direction == 1) ? this.x0 : this.x1;
/*     */   }
/*     */   
/*     */   public double getY0() {
/* 223 */     return (this.direction == 1) ? this.y0 : this.y1;
/*     */   }
/*     */   
/*     */   public double getCX0() {
/* 227 */     return this.cx0;
/*     */   }
/*     */   
/*     */   public double getCY0() {
/* 231 */     return this.cy0;
/*     */   }
/*     */   
/*     */   public double getX1() {
/* 235 */     return (this.direction == -1) ? this.x0 : this.x1;
/*     */   }
/*     */   
/*     */   public double getY1() {
/* 239 */     return (this.direction == -1) ? this.y0 : this.y1;
/*     */   }
/*     */   
/*     */   public double XforY(double paramDouble) {
/* 243 */     if (paramDouble <= this.y0) {
/* 244 */       return this.x0;
/*     */     }
/* 246 */     if (paramDouble >= this.y1) {
/* 247 */       return this.x1;
/*     */     }
/* 249 */     return XforT(TforY(paramDouble));
/*     */   }
/*     */   
/*     */   public double TforY(double paramDouble) {
/* 253 */     if (paramDouble <= this.y0) {
/* 254 */       return 0.0D;
/*     */     }
/* 256 */     if (paramDouble >= this.y1) {
/* 257 */       return 1.0D;
/*     */     }
/* 259 */     return TforY(paramDouble, this.ycoeff0, this.ycoeff1, this.ycoeff2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double TforY(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 267 */     paramDouble2 -= paramDouble1;
/* 268 */     if (paramDouble4 == 0.0D) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 274 */       double d = -paramDouble2 / paramDouble3;
/* 275 */       if (d >= 0.0D && d <= 1.0D) {
/* 276 */         return d;
/*     */       }
/*     */     } else {
/*     */       
/* 280 */       double d = paramDouble3 * paramDouble3 - 4.0D * paramDouble4 * paramDouble2;
/*     */       
/* 282 */       if (d >= 0.0D) {
/* 283 */         d = Math.sqrt(d);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 290 */         if (paramDouble3 < 0.0D) {
/* 291 */           d = -d;
/*     */         }
/* 293 */         double d3 = (paramDouble3 + d) / -2.0D;
/*     */         
/* 295 */         double d4 = d3 / paramDouble4;
/* 296 */         if (d4 >= 0.0D && d4 <= 1.0D) {
/* 297 */           return d4;
/*     */         }
/* 299 */         if (d3 != 0.0D) {
/* 300 */           d4 = paramDouble2 / d3;
/* 301 */           if (d4 >= 0.0D && d4 <= 1.0D) {
/* 302 */             return d4;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     double d1 = paramDouble2;
/* 340 */     double d2 = paramDouble2 + paramDouble3 + paramDouble4;
/* 341 */     return (0.0D < (d1 + d2) / 2.0D) ? 0.0D : 1.0D;
/*     */   }
/*     */   
/*     */   public double XforT(double paramDouble) {
/* 345 */     return (this.xcoeff2 * paramDouble + this.xcoeff1) * paramDouble + this.xcoeff0;
/*     */   }
/*     */   
/*     */   public double YforT(double paramDouble) {
/* 349 */     return (this.ycoeff2 * paramDouble + this.ycoeff1) * paramDouble + this.ycoeff0;
/*     */   }
/*     */   
/*     */   public double dXforT(double paramDouble, int paramInt) {
/* 353 */     switch (paramInt) {
/*     */       case 0:
/* 355 */         return (this.xcoeff2 * paramDouble + this.xcoeff1) * paramDouble + this.xcoeff0;
/*     */       case 1:
/* 357 */         return 2.0D * this.xcoeff2 * paramDouble + this.xcoeff1;
/*     */       case 2:
/* 359 */         return 2.0D * this.xcoeff2;
/*     */     } 
/* 361 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double dYforT(double paramDouble, int paramInt) {
/* 366 */     switch (paramInt) {
/*     */       case 0:
/* 368 */         return (this.ycoeff2 * paramDouble + this.ycoeff1) * paramDouble + this.ycoeff0;
/*     */       case 1:
/* 370 */         return 2.0D * this.ycoeff2 * paramDouble + this.ycoeff1;
/*     */       case 2:
/* 372 */         return 2.0D * this.ycoeff2;
/*     */     } 
/* 374 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextVertical(double paramDouble1, double paramDouble2) {
/* 379 */     double d = -this.xcoeff1 / 2.0D * this.xcoeff2;
/* 380 */     if (d > paramDouble1 && d < paramDouble2) {
/* 381 */       return d;
/*     */     }
/* 383 */     return paramDouble2;
/*     */   }
/*     */   
/*     */   public void enlarge(Rectangle2D paramRectangle2D) {
/* 387 */     paramRectangle2D.add(this.x0, this.y0);
/* 388 */     double d = -this.xcoeff1 / 2.0D * this.xcoeff2;
/* 389 */     if (d > 0.0D && d < 1.0D) {
/* 390 */       paramRectangle2D.add(XforT(d), YforT(d));
/*     */     }
/* 392 */     paramRectangle2D.add(this.x1, this.y1);
/*     */   }
/*     */   public Curve getSubCurve(double paramDouble1, double paramDouble2, int paramInt) {
/*     */     double d1, d2;
/*     */     byte b;
/* 397 */     if (paramDouble1 <= this.y0) {
/* 398 */       if (paramDouble2 >= this.y1) {
/* 399 */         return getWithDirection(paramInt);
/*     */       }
/* 401 */       d1 = 0.0D;
/*     */     } else {
/* 403 */       d1 = TforY(paramDouble1, this.ycoeff0, this.ycoeff1, this.ycoeff2);
/*     */     } 
/* 405 */     if (paramDouble2 >= this.y1) {
/* 406 */       d2 = 1.0D;
/*     */     } else {
/* 408 */       d2 = TforY(paramDouble2, this.ycoeff0, this.ycoeff1, this.ycoeff2);
/*     */     } 
/* 410 */     double[] arrayOfDouble = new double[10];
/* 411 */     arrayOfDouble[0] = this.x0;
/* 412 */     arrayOfDouble[1] = this.y0;
/* 413 */     arrayOfDouble[2] = this.cx0;
/* 414 */     arrayOfDouble[3] = this.cy0;
/* 415 */     arrayOfDouble[4] = this.x1;
/* 416 */     arrayOfDouble[5] = this.y1;
/* 417 */     if (d2 < 1.0D) {
/* 418 */       split(arrayOfDouble, 0, d2);
/*     */     }
/*     */     
/* 421 */     if (d1 <= 0.0D) {
/* 422 */       b = 0;
/*     */     } else {
/* 424 */       split(arrayOfDouble, 0, d1 / d2);
/* 425 */       b = 4;
/*     */     } 
/* 427 */     return new Order2(arrayOfDouble[b + 0], paramDouble1, arrayOfDouble[b + 2], arrayOfDouble[b + 3], arrayOfDouble[b + 4], paramDouble2, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Curve getReversedCurve() {
/* 434 */     return new Order2(this.x0, this.y0, this.cx0, this.cy0, this.x1, this.y1, -this.direction);
/*     */   }
/*     */   
/*     */   public int getSegment(double[] paramArrayOfdouble) {
/* 438 */     paramArrayOfdouble[0] = this.cx0;
/* 439 */     paramArrayOfdouble[1] = this.cy0;
/* 440 */     if (this.direction == 1) {
/* 441 */       paramArrayOfdouble[2] = this.x1;
/* 442 */       paramArrayOfdouble[3] = this.y1;
/*     */     } else {
/* 444 */       paramArrayOfdouble[2] = this.x0;
/* 445 */       paramArrayOfdouble[3] = this.y0;
/*     */     } 
/* 447 */     return 2;
/*     */   }
/*     */   
/*     */   public String controlPointString() {
/* 451 */     return "(" + round(this.cx0) + ", " + round(this.cy0) + "), ";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\geom\Order2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */