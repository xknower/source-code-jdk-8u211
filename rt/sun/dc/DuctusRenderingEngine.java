/*     */ package sun.dc;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Path2D;
/*     */ import java.awt.geom.PathIterator;
/*     */ import sun.awt.geom.PathConsumer2D;
/*     */ import sun.dc.path.FastPathProducer;
/*     */ import sun.dc.path.PathConsumer;
/*     */ import sun.dc.path.PathException;
/*     */ import sun.dc.pr.PRException;
/*     */ import sun.dc.pr.PathDasher;
/*     */ import sun.dc.pr.PathStroker;
/*     */ import sun.dc.pr.Rasterizer;
/*     */ import sun.java2d.pipe.AATileGenerator;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.java2d.pipe.RenderingEngine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DuctusRenderingEngine
/*     */   extends RenderingEngine
/*     */ {
/*     */   static final float PenUnits = 0.01F;
/*     */   static final int MinPenUnits = 100;
/*     */   static final int MinPenUnitsAA = 20;
/*     */   static final float MinPenSizeAA = 0.19999999F;
/*     */   static final float UPPER_BND = 1.7014117E38F;
/*     */   static final float LOWER_BND = -1.7014117E38F;
/*  56 */   private static final int[] RasterizerCaps = new int[] { 30, 10, 20 };
/*     */ 
/*     */ 
/*     */   
/*  60 */   private static final int[] RasterizerCorners = new int[] { 50, 10, 40 };
/*     */   
/*     */   private static Rasterizer theRasterizer;
/*     */   
/*     */   static float[] getTransformMatrix(AffineTransform paramAffineTransform) {
/*  65 */     float[] arrayOfFloat = new float[4];
/*  66 */     double[] arrayOfDouble = new double[6];
/*  67 */     paramAffineTransform.getMatrix(arrayOfDouble);
/*  68 */     for (byte b = 0; b < 4; b++) {
/*  69 */       arrayOfFloat[b] = (float)arrayOfDouble[b];
/*     */     }
/*  71 */     return arrayOfFloat;
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
/*     */   public Shape createStrokedShape(Shape paramShape, float paramFloat1, int paramInt1, int paramInt2, float paramFloat2, float[] paramArrayOffloat, float paramFloat3) {
/*  86 */     FillAdapter fillAdapter = new FillAdapter();
/*  87 */     PathStroker pathStroker = new PathStroker(fillAdapter);
/*  88 */     PathDasher pathDasher = null;
/*     */     
/*     */     try {
/*     */       PathStroker pathStroker1;
/*     */       
/*  93 */       pathStroker.setPenDiameter(paramFloat1);
/*  94 */       pathStroker.setPenT4(null);
/*  95 */       pathStroker.setCaps(RasterizerCaps[paramInt1]);
/*  96 */       pathStroker.setCorners(RasterizerCorners[paramInt2], paramFloat2);
/*  97 */       if (paramArrayOffloat != null) {
/*  98 */         pathDasher = new PathDasher(pathStroker);
/*  99 */         pathDasher.setDash(paramArrayOffloat, paramFloat3);
/* 100 */         pathDasher.setDashT4(null);
/* 101 */         PathDasher pathDasher1 = pathDasher;
/*     */       } else {
/* 103 */         pathStroker1 = pathStroker;
/*     */       } 
/*     */       
/* 106 */       feedConsumer(pathStroker1, paramShape.getPathIterator(null));
/*     */     } finally {
/* 108 */       pathStroker.dispose();
/* 109 */       if (pathDasher != null) {
/* 110 */         pathDasher.dispose();
/*     */       }
/*     */     } 
/*     */     
/* 114 */     return fillAdapter.getShape();
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
/*     */   public void strokeTo(Shape paramShape, AffineTransform paramAffineTransform, BasicStroke paramBasicStroke, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, PathConsumer2D paramPathConsumer2D) {
/*     */     PathConsumer pathConsumer;
/* 129 */     PathStroker pathStroker1 = new PathStroker(paramPathConsumer2D);
/* 130 */     PathStroker pathStroker2 = pathStroker1;
/*     */     
/* 132 */     float[] arrayOfFloat1 = null;
/* 133 */     if (!paramBoolean1) {
/* 134 */       pathStroker1.setPenDiameter(paramBasicStroke.getLineWidth());
/* 135 */       if (paramAffineTransform != null) {
/* 136 */         arrayOfFloat1 = getTransformMatrix(paramAffineTransform);
/*     */       }
/* 138 */       pathStroker1.setPenT4(arrayOfFloat1);
/* 139 */       pathStroker1.setPenFitting(0.01F, 100);
/*     */     } 
/* 141 */     pathStroker1.setCaps(RasterizerCaps[paramBasicStroke.getEndCap()]);
/* 142 */     pathStroker1.setCorners(RasterizerCorners[paramBasicStroke.getLineJoin()], paramBasicStroke
/* 143 */         .getMiterLimit());
/* 144 */     float[] arrayOfFloat2 = paramBasicStroke.getDashArray();
/* 145 */     if (arrayOfFloat2 != null) {
/* 146 */       PathDasher pathDasher = new PathDasher(pathStroker1);
/* 147 */       pathDasher.setDash(arrayOfFloat2, paramBasicStroke.getDashPhase());
/* 148 */       if (paramAffineTransform != null && arrayOfFloat1 == null) {
/* 149 */         arrayOfFloat1 = getTransformMatrix(paramAffineTransform);
/*     */       }
/* 151 */       pathDasher.setDashT4(arrayOfFloat1);
/* 152 */       pathConsumer = pathDasher;
/*     */     } 
/*     */     
/*     */     try {
/* 156 */       PathIterator pathIterator = paramShape.getPathIterator(paramAffineTransform);
/*     */       
/* 158 */       feedConsumer(pathIterator, pathConsumer, paramBoolean2, 0.25F);
/* 159 */     } catch (PathException pathException) {
/* 160 */       throw new InternalError("Unable to Stroke shape (" + pathException
/* 161 */           .getMessage() + ")", pathException);
/*     */     } finally {
/* 163 */       while (pathConsumer != null && pathConsumer != paramPathConsumer2D) {
/* 164 */         PathConsumer pathConsumer1 = pathConsumer.getConsumer();
/* 165 */         pathConsumer.dispose();
/* 166 */         pathConsumer = pathConsumer1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void feedConsumer(PathIterator paramPathIterator, PathConsumer paramPathConsumer, boolean paramBoolean, float paramFloat) throws PathException {
/* 178 */     paramPathConsumer.beginPath();
/* 179 */     boolean bool1 = false;
/* 180 */     boolean bool2 = false;
/* 181 */     boolean bool3 = false;
/* 182 */     float f1 = 0.0F;
/* 183 */     float f2 = 0.0F;
/* 184 */     float[] arrayOfFloat = new float[6];
/* 185 */     float f3 = 0.5F - paramFloat;
/* 186 */     float f4 = 0.0F;
/* 187 */     float f5 = 0.0F;
/*     */     
/* 189 */     while (!paramPathIterator.isDone()) {
/* 190 */       int i = paramPathIterator.currentSegment(arrayOfFloat);
/* 191 */       if (bool1 == true) {
/* 192 */         bool1 = false;
/* 193 */         if (i != 0) {
/*     */           
/* 195 */           paramPathConsumer.beginSubpath(f1, f2);
/* 196 */           bool3 = true;
/*     */         } 
/*     */       } 
/* 199 */       if (paramBoolean) {
/*     */         byte b;
/* 201 */         switch (i) {
/*     */           case 3:
/* 203 */             b = 4;
/*     */             break;
/*     */           case 2:
/* 206 */             b = 2;
/*     */             break;
/*     */           case 0:
/*     */           case 1:
/* 210 */             b = 0;
/*     */             break;
/*     */           
/*     */           default:
/* 214 */             b = -1;
/*     */             break;
/*     */         } 
/* 217 */         if (b >= 0) {
/* 218 */           float f6 = arrayOfFloat[b];
/* 219 */           float f7 = arrayOfFloat[b + 1];
/* 220 */           float f8 = (float)Math.floor((f6 + f3)) + paramFloat;
/* 221 */           float f9 = (float)Math.floor((f7 + f3)) + paramFloat;
/* 222 */           arrayOfFloat[b] = f8;
/* 223 */           arrayOfFloat[b + 1] = f9;
/* 224 */           f8 -= f6;
/* 225 */           f9 -= f7;
/* 226 */           switch (i) {
/*     */             case 3:
/* 228 */               arrayOfFloat[0] = arrayOfFloat[0] + f4;
/* 229 */               arrayOfFloat[1] = arrayOfFloat[1] + f5;
/* 230 */               arrayOfFloat[2] = arrayOfFloat[2] + f8;
/* 231 */               arrayOfFloat[3] = arrayOfFloat[3] + f9;
/*     */               break;
/*     */             case 2:
/* 234 */               arrayOfFloat[0] = arrayOfFloat[0] + (f8 + f4) / 2.0F;
/* 235 */               arrayOfFloat[1] = arrayOfFloat[1] + (f9 + f5) / 2.0F;
/*     */               break;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 242 */           f4 = f8;
/* 243 */           f5 = f9;
/*     */         } 
/*     */       } 
/* 246 */       switch (i) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 0:
/* 254 */           if (arrayOfFloat[0] < 1.7014117E38F && arrayOfFloat[0] > -1.7014117E38F && arrayOfFloat[1] < 1.7014117E38F && arrayOfFloat[1] > -1.7014117E38F) {
/*     */ 
/*     */             
/* 257 */             f1 = arrayOfFloat[0];
/* 258 */             f2 = arrayOfFloat[1];
/* 259 */             paramPathConsumer.beginSubpath(f1, f2);
/* 260 */             bool3 = true;
/* 261 */             bool2 = false; break;
/*     */           } 
/* 263 */           bool2 = true;
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 273 */           if (arrayOfFloat[0] < 1.7014117E38F && arrayOfFloat[0] > -1.7014117E38F && arrayOfFloat[1] < 1.7014117E38F && arrayOfFloat[1] > -1.7014117E38F) {
/*     */ 
/*     */             
/* 276 */             if (bool2) {
/* 277 */               paramPathConsumer.beginSubpath(arrayOfFloat[0], arrayOfFloat[1]);
/* 278 */               bool3 = true;
/* 279 */               bool2 = false; break;
/*     */             } 
/* 281 */             paramPathConsumer.appendLine(arrayOfFloat[0], arrayOfFloat[1]);
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 295 */           if (arrayOfFloat[2] < 1.7014117E38F && arrayOfFloat[2] > -1.7014117E38F && arrayOfFloat[3] < 1.7014117E38F && arrayOfFloat[3] > -1.7014117E38F) {
/*     */ 
/*     */             
/* 298 */             if (bool2) {
/* 299 */               paramPathConsumer.beginSubpath(arrayOfFloat[2], arrayOfFloat[3]);
/* 300 */               bool3 = true;
/* 301 */               bool2 = false; break;
/*     */             } 
/* 303 */             if (arrayOfFloat[0] < 1.7014117E38F && arrayOfFloat[0] > -1.7014117E38F && arrayOfFloat[1] < 1.7014117E38F && arrayOfFloat[1] > -1.7014117E38F) {
/*     */ 
/*     */               
/* 306 */               paramPathConsumer.appendQuadratic(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
/*     */               break;
/*     */             } 
/* 309 */             paramPathConsumer.appendLine(arrayOfFloat[2], arrayOfFloat[3]);
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 3:
/* 324 */           if (arrayOfFloat[4] < 1.7014117E38F && arrayOfFloat[4] > -1.7014117E38F && arrayOfFloat[5] < 1.7014117E38F && arrayOfFloat[5] > -1.7014117E38F) {
/*     */ 
/*     */             
/* 327 */             if (bool2) {
/* 328 */               paramPathConsumer.beginSubpath(arrayOfFloat[4], arrayOfFloat[5]);
/* 329 */               bool3 = true;
/* 330 */               bool2 = false; break;
/*     */             } 
/* 332 */             if (arrayOfFloat[0] < 1.7014117E38F && arrayOfFloat[0] > -1.7014117E38F && arrayOfFloat[1] < 1.7014117E38F && arrayOfFloat[1] > -1.7014117E38F && arrayOfFloat[2] < 1.7014117E38F && arrayOfFloat[2] > -1.7014117E38F && arrayOfFloat[3] < 1.7014117E38F && arrayOfFloat[3] > -1.7014117E38F) {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 337 */               paramPathConsumer.appendCubic(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
/*     */               
/*     */               break;
/*     */             } 
/* 341 */             paramPathConsumer.appendLine(arrayOfFloat[4], arrayOfFloat[5]);
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case 4:
/* 347 */           if (bool3) {
/* 348 */             paramPathConsumer.closedSubpath();
/* 349 */             bool3 = false;
/* 350 */             bool1 = true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 354 */       paramPathIterator.next();
/*     */     } 
/*     */     
/* 357 */     paramPathConsumer.endPath();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized Rasterizer getRasterizer() {
/* 363 */     Rasterizer rasterizer = theRasterizer;
/* 364 */     if (rasterizer == null) {
/* 365 */       rasterizer = new Rasterizer();
/*     */     } else {
/* 367 */       theRasterizer = null;
/*     */     } 
/* 369 */     return rasterizer;
/*     */   }
/*     */   
/*     */   public static synchronized void dropRasterizer(Rasterizer paramRasterizer) {
/* 373 */     paramRasterizer.reset();
/* 374 */     theRasterizer = paramRasterizer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinimumAAPenSize() {
/* 382 */     return 0.19999999F;
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
/*     */   public AATileGenerator getAATileGenerator(Shape paramShape, AffineTransform paramAffineTransform, Region paramRegion, BasicStroke paramBasicStroke, boolean paramBoolean1, boolean paramBoolean2, int[] paramArrayOfint) {
/* 397 */     Rasterizer rasterizer = getRasterizer();
/* 398 */     PathIterator pathIterator = paramShape.getPathIterator(paramAffineTransform);
/*     */     
/* 400 */     if (paramBasicStroke != null) {
/* 401 */       float[] arrayOfFloat1 = null;
/* 402 */       rasterizer.setUsage(3);
/* 403 */       if (paramBoolean1) {
/* 404 */         rasterizer.setPenDiameter(0.19999999F);
/*     */       } else {
/* 406 */         rasterizer.setPenDiameter(paramBasicStroke.getLineWidth());
/* 407 */         if (paramAffineTransform != null) {
/* 408 */           arrayOfFloat1 = getTransformMatrix(paramAffineTransform);
/* 409 */           rasterizer.setPenT4(arrayOfFloat1);
/*     */         } 
/* 411 */         rasterizer.setPenFitting(0.01F, 20);
/*     */       } 
/* 413 */       rasterizer.setCaps(RasterizerCaps[paramBasicStroke.getEndCap()]);
/* 414 */       rasterizer.setCorners(RasterizerCorners[paramBasicStroke.getLineJoin()], paramBasicStroke
/* 415 */           .getMiterLimit());
/* 416 */       float[] arrayOfFloat2 = paramBasicStroke.getDashArray();
/* 417 */       if (arrayOfFloat2 != null) {
/* 418 */         rasterizer.setDash(arrayOfFloat2, paramBasicStroke.getDashPhase());
/* 419 */         if (paramAffineTransform != null && arrayOfFloat1 == null) {
/* 420 */           arrayOfFloat1 = getTransformMatrix(paramAffineTransform);
/*     */         }
/* 422 */         rasterizer.setDashT4(arrayOfFloat1);
/*     */       } 
/*     */     } else {
/* 425 */       rasterizer.setUsage((pathIterator.getWindingRule() == 0) ? 1 : 2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 430 */     rasterizer.beginPath();
/*     */     
/* 432 */     boolean bool1 = false;
/* 433 */     boolean bool2 = false;
/* 434 */     boolean bool3 = false;
/* 435 */     float f1 = 0.0F;
/* 436 */     float f2 = 0.0F;
/* 437 */     float[] arrayOfFloat = new float[6];
/* 438 */     float f3 = 0.0F;
/* 439 */     float f4 = 0.0F;
/*     */     
/* 441 */     while (!pathIterator.isDone()) {
/* 442 */       int i = pathIterator.currentSegment(arrayOfFloat);
/* 443 */       if (bool1 == true) {
/* 444 */         bool1 = false;
/* 445 */         if (i != 0) {
/*     */           
/* 447 */           rasterizer.beginSubpath(f1, f2);
/* 448 */           bool3 = true;
/*     */         } 
/*     */       } 
/* 451 */       if (paramBoolean2) {
/*     */         byte b;
/* 453 */         switch (i) {
/*     */           case 3:
/* 455 */             b = 4;
/*     */             break;
/*     */           case 2:
/* 458 */             b = 2;
/*     */             break;
/*     */           case 0:
/*     */           case 1:
/* 462 */             b = 0;
/*     */             break;
/*     */           
/*     */           default:
/* 466 */             b = -1;
/*     */             break;
/*     */         } 
/* 469 */         if (b >= 0) {
/* 470 */           float f5 = arrayOfFloat[b];
/* 471 */           float f6 = arrayOfFloat[b + 1];
/* 472 */           float f7 = (float)Math.floor(f5) + 0.5F;
/* 473 */           float f8 = (float)Math.floor(f6) + 0.5F;
/* 474 */           arrayOfFloat[b] = f7;
/* 475 */           arrayOfFloat[b + 1] = f8;
/* 476 */           f7 -= f5;
/* 477 */           f8 -= f6;
/* 478 */           switch (i) {
/*     */             case 3:
/* 480 */               arrayOfFloat[0] = arrayOfFloat[0] + f3;
/* 481 */               arrayOfFloat[1] = arrayOfFloat[1] + f4;
/* 482 */               arrayOfFloat[2] = arrayOfFloat[2] + f7;
/* 483 */               arrayOfFloat[3] = arrayOfFloat[3] + f8;
/*     */               break;
/*     */             case 2:
/* 486 */               arrayOfFloat[0] = arrayOfFloat[0] + (f7 + f3) / 2.0F;
/* 487 */               arrayOfFloat[1] = arrayOfFloat[1] + (f8 + f4) / 2.0F;
/*     */               break;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 494 */           f3 = f7;
/* 495 */           f4 = f8;
/*     */         } 
/*     */       } 
/* 498 */       switch (i) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 0:
/* 507 */           if (arrayOfFloat[0] < 1.7014117E38F && arrayOfFloat[0] > -1.7014117E38F && arrayOfFloat[1] < 1.7014117E38F && arrayOfFloat[1] > -1.7014117E38F) {
/*     */ 
/*     */             
/* 510 */             f1 = arrayOfFloat[0];
/* 511 */             f2 = arrayOfFloat[1];
/* 512 */             rasterizer.beginSubpath(f1, f2);
/* 513 */             bool3 = true;
/* 514 */             bool2 = false; break;
/*     */           } 
/* 516 */           bool2 = true;
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 527 */           if (arrayOfFloat[0] < 1.7014117E38F && arrayOfFloat[0] > -1.7014117E38F && arrayOfFloat[1] < 1.7014117E38F && arrayOfFloat[1] > -1.7014117E38F) {
/*     */ 
/*     */             
/* 530 */             if (bool2) {
/* 531 */               rasterizer.beginSubpath(arrayOfFloat[0], arrayOfFloat[1]);
/* 532 */               bool3 = true;
/* 533 */               bool2 = false; break;
/*     */             } 
/* 535 */             rasterizer.appendLine(arrayOfFloat[0], arrayOfFloat[1]);
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 550 */           if (arrayOfFloat[2] < 1.7014117E38F && arrayOfFloat[2] > -1.7014117E38F && arrayOfFloat[3] < 1.7014117E38F && arrayOfFloat[3] > -1.7014117E38F) {
/*     */ 
/*     */             
/* 553 */             if (bool2) {
/* 554 */               rasterizer.beginSubpath(arrayOfFloat[2], arrayOfFloat[3]);
/* 555 */               bool3 = true;
/* 556 */               bool2 = false; break;
/*     */             } 
/* 558 */             if (arrayOfFloat[0] < 1.7014117E38F && arrayOfFloat[0] > -1.7014117E38F && arrayOfFloat[1] < 1.7014117E38F && arrayOfFloat[1] > -1.7014117E38F) {
/*     */ 
/*     */               
/* 561 */               rasterizer.appendQuadratic(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
/*     */               break;
/*     */             } 
/* 564 */             rasterizer.appendLine(arrayOfFloat[2], arrayOfFloat[3]);
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 3:
/* 580 */           if (arrayOfFloat[4] < 1.7014117E38F && arrayOfFloat[4] > -1.7014117E38F && arrayOfFloat[5] < 1.7014117E38F && arrayOfFloat[5] > -1.7014117E38F) {
/*     */ 
/*     */             
/* 583 */             if (bool2) {
/* 584 */               rasterizer.beginSubpath(arrayOfFloat[4], arrayOfFloat[5]);
/* 585 */               bool3 = true;
/* 586 */               bool2 = false; break;
/*     */             } 
/* 588 */             if (arrayOfFloat[0] < 1.7014117E38F && arrayOfFloat[0] > -1.7014117E38F && arrayOfFloat[1] < 1.7014117E38F && arrayOfFloat[1] > -1.7014117E38F && arrayOfFloat[2] < 1.7014117E38F && arrayOfFloat[2] > -1.7014117E38F && arrayOfFloat[3] < 1.7014117E38F && arrayOfFloat[3] > -1.7014117E38F) {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 593 */               rasterizer.appendCubic(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
/*     */               
/*     */               break;
/*     */             } 
/* 597 */             rasterizer.appendLine(arrayOfFloat[4], arrayOfFloat[5]);
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case 4:
/* 603 */           if (bool3) {
/* 604 */             rasterizer.closedSubpath();
/* 605 */             bool3 = false;
/* 606 */             bool1 = true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 610 */       pathIterator.next();
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 615 */       rasterizer.endPath();
/* 616 */       rasterizer.getAlphaBox(paramArrayOfint);
/* 617 */       paramRegion.clipBoxToBounds(paramArrayOfint);
/* 618 */       if (paramArrayOfint[0] >= paramArrayOfint[2] || paramArrayOfint[1] >= paramArrayOfint[3]) {
/* 619 */         dropRasterizer(rasterizer);
/* 620 */         return null;
/*     */       } 
/* 622 */       rasterizer.setOutputArea(paramArrayOfint[0], paramArrayOfint[1], paramArrayOfint[2] - paramArrayOfint[0], paramArrayOfint[3] - paramArrayOfint[1]);
/*     */     
/*     */     }
/* 625 */     catch (PRException pRException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 632 */       System.err.println("DuctusRenderingEngine.getAATileGenerator: " + pRException);
/*     */     } 
/*     */     
/* 635 */     return rasterizer;
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
/*     */   public AATileGenerator getAATileGenerator(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, Region paramRegion, int[] paramArrayOfint) {
/*     */     double d1, d2, d3, d4;
/* 651 */     boolean bool = (paramDouble7 > 0.0D && paramDouble8 > 0.0D) ? true : false;
/*     */     
/* 653 */     if (bool) {
/* 654 */       d1 = paramDouble3 * paramDouble7;
/* 655 */       d2 = paramDouble4 * paramDouble7;
/* 656 */       d3 = paramDouble5 * paramDouble8;
/* 657 */       d4 = paramDouble6 * paramDouble8;
/* 658 */       paramDouble1 -= (d1 + d3) / 2.0D;
/* 659 */       paramDouble2 -= (d2 + d4) / 2.0D;
/* 660 */       paramDouble3 += d1;
/* 661 */       paramDouble4 += d2;
/* 662 */       paramDouble5 += d3;
/* 663 */       paramDouble6 += d4;
/* 664 */       if (paramDouble7 > 1.0D && paramDouble8 > 1.0D)
/*     */       {
/* 666 */         bool = false;
/*     */       }
/*     */     } else {
/* 669 */       d1 = d2 = d3 = d4 = 0.0D;
/*     */     } 
/*     */     
/* 672 */     Rasterizer rasterizer = getRasterizer();
/*     */     
/* 674 */     rasterizer.setUsage(1);
/*     */     
/* 676 */     rasterizer.beginPath();
/* 677 */     rasterizer.beginSubpath((float)paramDouble1, (float)paramDouble2);
/* 678 */     rasterizer.appendLine((float)(paramDouble1 + paramDouble3), (float)(paramDouble2 + paramDouble4));
/* 679 */     rasterizer.appendLine((float)(paramDouble1 + paramDouble3 + paramDouble5), (float)(paramDouble2 + paramDouble4 + paramDouble6));
/* 680 */     rasterizer.appendLine((float)(paramDouble1 + paramDouble5), (float)(paramDouble2 + paramDouble6));
/* 681 */     rasterizer.closedSubpath();
/* 682 */     if (bool) {
/* 683 */       paramDouble1 += d1 + d3;
/* 684 */       paramDouble2 += d2 + d4;
/* 685 */       paramDouble3 -= 2.0D * d1;
/* 686 */       paramDouble4 -= 2.0D * d2;
/* 687 */       paramDouble5 -= 2.0D * d3;
/* 688 */       paramDouble6 -= 2.0D * d4;
/* 689 */       rasterizer.beginSubpath((float)paramDouble1, (float)paramDouble2);
/* 690 */       rasterizer.appendLine((float)(paramDouble1 + paramDouble3), (float)(paramDouble2 + paramDouble4));
/* 691 */       rasterizer.appendLine((float)(paramDouble1 + paramDouble3 + paramDouble5), (float)(paramDouble2 + paramDouble4 + paramDouble6));
/* 692 */       rasterizer.appendLine((float)(paramDouble1 + paramDouble5), (float)(paramDouble2 + paramDouble6));
/* 693 */       rasterizer.closedSubpath();
/*     */     } 
/*     */     
/*     */     try {
/* 697 */       rasterizer.endPath();
/* 698 */       rasterizer.getAlphaBox(paramArrayOfint);
/* 699 */       paramRegion.clipBoxToBounds(paramArrayOfint);
/* 700 */       if (paramArrayOfint[0] >= paramArrayOfint[2] || paramArrayOfint[1] >= paramArrayOfint[3]) {
/* 701 */         dropRasterizer(rasterizer);
/* 702 */         return null;
/*     */       } 
/* 704 */       rasterizer.setOutputArea(paramArrayOfint[0], paramArrayOfint[1], paramArrayOfint[2] - paramArrayOfint[0], paramArrayOfint[3] - paramArrayOfint[1]);
/*     */     
/*     */     }
/* 707 */     catch (PRException pRException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 714 */       System.err.println("DuctusRenderingEngine.getAATileGenerator: " + pRException);
/*     */     } 
/*     */     
/* 717 */     return rasterizer;
/*     */   }
/*     */   
/*     */   private void feedConsumer(PathConsumer paramPathConsumer, PathIterator paramPathIterator) {
/*     */     try {
/* 722 */       paramPathConsumer.beginPath();
/* 723 */       boolean bool = false;
/* 724 */       float f1 = 0.0F;
/* 725 */       float f2 = 0.0F;
/* 726 */       float[] arrayOfFloat = new float[6];
/*     */       
/* 728 */       while (!paramPathIterator.isDone()) {
/* 729 */         int i = paramPathIterator.currentSegment(arrayOfFloat);
/* 730 */         if (bool == true) {
/* 731 */           bool = false;
/* 732 */           if (i != 0)
/*     */           {
/* 734 */             paramPathConsumer.beginSubpath(f1, f2);
/*     */           }
/*     */         } 
/* 737 */         switch (i) {
/*     */           case 0:
/* 739 */             f1 = arrayOfFloat[0];
/* 740 */             f2 = arrayOfFloat[1];
/* 741 */             paramPathConsumer.beginSubpath(arrayOfFloat[0], arrayOfFloat[1]);
/*     */             break;
/*     */           case 1:
/* 744 */             paramPathConsumer.appendLine(arrayOfFloat[0], arrayOfFloat[1]);
/*     */             break;
/*     */           case 2:
/* 747 */             paramPathConsumer.appendQuadratic(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
/*     */             break;
/*     */           
/*     */           case 3:
/* 751 */             paramPathConsumer.appendCubic(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 4:
/* 756 */             paramPathConsumer.closedSubpath();
/* 757 */             bool = true;
/*     */             break;
/*     */         } 
/* 760 */         paramPathIterator.next();
/*     */       } 
/*     */       
/* 763 */       paramPathConsumer.endPath();
/* 764 */     } catch (PathException pathException) {
/* 765 */       throw new InternalError("Unable to Stroke shape (" + pathException
/* 766 */           .getMessage() + ")", pathException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class FillAdapter
/*     */     implements PathConsumer
/*     */   {
/*     */     boolean closed;
/*     */     
/* 777 */     Path2D.Float path = new Path2D.Float(1);
/*     */ 
/*     */     
/*     */     public Shape getShape() {
/* 781 */       return this.path;
/*     */     }
/*     */ 
/*     */     
/*     */     public void dispose() {}
/*     */     
/*     */     public PathConsumer getConsumer() {
/* 788 */       return null;
/*     */     }
/*     */     
/*     */     public void beginPath() {}
/*     */     
/*     */     public void beginSubpath(float param1Float1, float param1Float2) {
/* 794 */       if (this.closed) {
/* 795 */         this.path.closePath();
/* 796 */         this.closed = false;
/*     */       } 
/* 798 */       this.path.moveTo(param1Float1, param1Float2);
/*     */     }
/*     */     
/*     */     public void appendLine(float param1Float1, float param1Float2) {
/* 802 */       this.path.lineTo(param1Float1, param1Float2);
/*     */     }
/*     */     
/*     */     public void appendQuadratic(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 806 */       this.path.quadTo(param1Float1, param1Float2, param1Float3, param1Float4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void appendCubic(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
/* 812 */       this.path.curveTo(param1Float1, param1Float2, param1Float3, param1Float4, param1Float5, param1Float6);
/*     */     }
/*     */     
/*     */     public void closedSubpath() {
/* 816 */       this.closed = true;
/*     */     }
/*     */     
/*     */     public void endPath() {
/* 820 */       if (this.closed) {
/* 821 */         this.path.closePath();
/* 822 */         this.closed = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void useProxy(FastPathProducer param1FastPathProducer) throws PathException {
/* 829 */       param1FastPathProducer.sendTo(this);
/*     */     }
/*     */     
/*     */     public long getCPathConsumer() {
/* 833 */       return 0L;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\dc\DuctusRenderingEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */