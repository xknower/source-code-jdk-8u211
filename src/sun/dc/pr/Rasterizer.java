/*     */ package sun.dc.pr;
/*     */ 
/*     */ import sun.dc.DuctusRenderingEngine;
/*     */ import sun.dc.path.FastPathProducer;
/*     */ import sun.dc.path.PathConsumer;
/*     */ import sun.dc.path.PathError;
/*     */ import sun.dc.path.PathException;
/*     */ import sun.java2d.Disposer;
/*     */ import sun.java2d.DisposerRecord;
/*     */ import sun.java2d.pipe.AATileGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Rasterizer
/*     */   implements AATileGenerator
/*     */ {
/*     */   public static final int EOFILL = 1;
/*     */   public static final int NZFILL = 2;
/*     */   public static final int STROKE = 3;
/*     */   public static final int ROUND = 10;
/*     */   public static final int SQUARE = 20;
/*     */   public static final int BUTT = 30;
/*     */   public static final int BEVEL = 40;
/*     */   public static final int MITER = 50;
/*  77 */   public static final int TILE_SIZE = 1 << PathFiller.tileSizeL2S;
/*  78 */   public static final int TILE_SIZE_L2S = PathFiller.tileSizeL2S;
/*     */   
/*     */   public static final int MAX_ALPHA = 1000000;
/*     */   
/*     */   public static final int MAX_MITER = 10;
/*     */   
/*     */   public static final int MAX_WN = 63;
/*     */   
/*     */   public static final int TILE_IS_ALL_0 = 0;
/*     */   
/*     */   public static final int TILE_IS_ALL_1 = 1;
/*     */   
/*     */   public static final int TILE_IS_GENERAL = 2;
/*     */   
/*     */   private static final int BEG = 1;
/*     */   
/*     */   private static final int PAC_FILL = 2;
/*     */   
/*     */   private static final int PAC_STROKE = 3;
/*     */   
/*     */   private static final int PATH = 4;
/*     */   
/*     */   private static final int SUBPATH = 5;
/*     */   
/*     */   private static final int RAS = 6;
/*     */   
/*     */   private int state;
/*     */   private PathFiller filler;
/*     */   private PathStroker stroker;
/*     */   private PathDasher dasher;
/*     */   private PathConsumer curPC;
/*     */   
/*     */   private static class ConsumerDisposer
/*     */     implements DisposerRecord
/*     */   {
/*     */     PathConsumer filler;
/*     */     PathConsumer stroker;
/*     */     PathConsumer dasher;
/*     */     
/*     */     public ConsumerDisposer(PathConsumer param1PathConsumer1, PathConsumer param1PathConsumer2, PathConsumer param1PathConsumer3) {
/* 118 */       this.filler = param1PathConsumer1;
/* 119 */       this.stroker = param1PathConsumer2;
/* 120 */       this.dasher = param1PathConsumer3;
/*     */     }
/*     */     
/*     */     public void dispose() {
/* 124 */       this.filler.dispose();
/* 125 */       this.stroker.dispose();
/* 126 */       this.dasher.dispose();
/*     */     }
/*     */   }
/*     */   
/*     */   public Rasterizer() {
/* 131 */     this.state = 1;
/* 132 */     this.filler = new PathFiller();
/* 133 */     this.stroker = new PathStroker(this.filler);
/* 134 */     this.dasher = new PathDasher(this.stroker);
/* 135 */     Disposer.addRecord(this, new ConsumerDisposer(this.filler, this.stroker, this.dasher));
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
/*     */   public void setUsage(int paramInt) throws PRError {
/* 162 */     if (this.state != 1) {
/* 163 */       throw new PRError("setUsage: unexpected");
/*     */     }
/* 165 */     if (paramInt == 1) {
/* 166 */       this.filler.setFillMode(1);
/* 167 */       this.curPC = this.filler;
/* 168 */       this.state = 2;
/* 169 */     } else if (paramInt == 2) {
/* 170 */       this.filler.setFillMode(2);
/* 171 */       this.curPC = this.filler;
/* 172 */       this.state = 2;
/* 173 */     } else if (paramInt == 3) {
/* 174 */       this.curPC = this.stroker;
/* 175 */       this.filler.setFillMode(2);
/* 176 */       this.stroker.setPenDiameter(1.0F);
/* 177 */       this.stroker.setPenT4(null);
/* 178 */       this.stroker.setCaps(10);
/* 179 */       this.stroker.setCorners(10, 0.0F);
/* 180 */       this.state = 3;
/*     */     } else {
/* 182 */       throw new PRError("setUsage: unknown usage type");
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
/*     */   public void setPenDiameter(float paramFloat) throws PRError {
/* 204 */     if (this.state != 3) {
/* 205 */       throw new PRError("setPenDiameter: unexpected");
/*     */     }
/* 207 */     this.stroker.setPenDiameter(paramFloat);
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
/*     */   public void setPenT4(float[] paramArrayOffloat) throws PRError {
/* 229 */     if (this.state != 3) {
/* 230 */       throw new PRError("setPenT4: unexpected");
/*     */     }
/* 232 */     this.stroker.setPenT4(paramArrayOffloat);
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
/*     */   public void setPenFitting(float paramFloat, int paramInt) throws PRError {
/* 256 */     if (this.state != 3) {
/* 257 */       throw new PRError("setPenFitting: unexpected");
/*     */     }
/* 259 */     this.stroker.setPenFitting(paramFloat, paramInt);
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
/*     */   public void setPenDisplacement(float paramFloat1, float paramFloat2) throws PRError {
/* 278 */     if (this.state != 3) {
/* 279 */       throw new PRError("setPenDisplacement: unexpected");
/*     */     }
/* 281 */     float[] arrayOfFloat = { 1.0F, 0.0F, 0.0F, 1.0F, paramFloat1, paramFloat2 };
/* 282 */     this.stroker.setOutputT6(arrayOfFloat);
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
/*     */   public void setCaps(int paramInt) throws PRError {
/* 302 */     if (this.state != 3) {
/* 303 */       throw new PRError("setCaps: unexpected");
/*     */     }
/* 305 */     this.stroker.setCaps(paramInt);
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
/*     */   public void setCorners(int paramInt, float paramFloat) throws PRError {
/* 328 */     if (this.state != 3) {
/* 329 */       throw new PRError("setCorners: unexpected");
/*     */     }
/* 331 */     this.stroker.setCorners(paramInt, paramFloat);
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
/*     */   public void setDash(float[] paramArrayOffloat, float paramFloat) throws PRError {
/* 358 */     if (this.state != 3) {
/* 359 */       throw new PRError("setDash: unexpected");
/*     */     }
/* 361 */     this.dasher.setDash(paramArrayOffloat, paramFloat);
/* 362 */     this.curPC = this.dasher;
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
/*     */   public void setDashT4(float[] paramArrayOffloat) throws PRError {
/* 383 */     if (this.state != 3) {
/* 384 */       throw new PRError("setDashT4: unexpected");
/*     */     }
/* 386 */     this.dasher.setDashT4(paramArrayOffloat);
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
/*     */   public void beginPath(float[] paramArrayOffloat) throws PRError {
/* 403 */     beginPath();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginPath() throws PRError {
/* 409 */     if (this.state != 2 && this.state != 3) {
/* 410 */       throw new PRError("beginPath: unexpected");
/*     */     }
/*     */     try {
/* 413 */       this.curPC.beginPath();
/* 414 */       this.state = 4;
/* 415 */     } catch (PathError pathError) {
/* 416 */       throw new PRError(pathError.getMessage());
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
/*     */   public void beginSubpath(float paramFloat1, float paramFloat2) throws PRError {
/* 433 */     if (this.state != 4 && this.state != 5) {
/* 434 */       throw new PRError("beginSubpath: unexpected");
/*     */     }
/*     */     try {
/* 437 */       this.curPC.beginSubpath(paramFloat1, paramFloat2);
/* 438 */       this.state = 5;
/* 439 */     } catch (PathError pathError) {
/* 440 */       throw new PRError(pathError.getMessage());
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
/*     */   public void appendLine(float paramFloat1, float paramFloat2) throws PRError {
/* 456 */     if (this.state != 5) {
/* 457 */       throw new PRError("appendLine: unexpected");
/*     */     }
/*     */     try {
/* 460 */       this.curPC.appendLine(paramFloat1, paramFloat2);
/* 461 */     } catch (PathError pathError) {
/* 462 */       throw new PRError(pathError.getMessage());
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
/*     */   public void appendQuadratic(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) throws PRError {
/* 483 */     if (this.state != 5) {
/* 484 */       throw new PRError("appendQuadratic: unexpected");
/*     */     }
/*     */     try {
/* 487 */       this.curPC.appendQuadratic(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/* 488 */     } catch (PathError pathError) {
/* 489 */       throw new PRError(pathError.getMessage());
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
/*     */   public void appendCubic(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) throws PRError {
/* 514 */     if (this.state != 5) {
/* 515 */       throw new PRError("appendCubic: unexpected");
/*     */     }
/*     */     try {
/* 518 */       this.curPC.appendCubic(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/* 519 */     } catch (PathError pathError) {
/* 520 */       throw new PRError(pathError.getMessage());
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
/*     */   public void closedSubpath() throws PRError {
/* 534 */     if (this.state != 5)
/* 535 */       throw new PRError("closedSubpath: unexpected"); 
/*     */     try {
/* 537 */       this.curPC.closedSubpath();
/* 538 */     } catch (PathError pathError) {
/* 539 */       throw new PRError(pathError.getMessage());
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
/*     */   public void endPath() throws PRError, PRException {
/* 556 */     if (this.state != 4 && this.state != 5)
/* 557 */       throw new PRError("endPath: unexpected"); 
/*     */     try {
/* 559 */       this.curPC.endPath();
/* 560 */       this.state = 6;
/* 561 */     } catch (PathError pathError) {
/* 562 */       throw new PRError(pathError.getMessage());
/* 563 */     } catch (PathException pathException) {
/* 564 */       throw new PRException(pathException.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void useProxy(FastPathProducer paramFastPathProducer) throws PRError, PRException {
/* 575 */     if (this.state != 2 && this.state != 3) {
/* 576 */       throw new PRError("useProxy: unexpected");
/*     */     }
/*     */     try {
/* 579 */       this.curPC.useProxy(paramFastPathProducer);
/* 580 */       this.state = 6;
/* 581 */     } catch (PathError pathError) {
/* 582 */       throw new PRError(pathError.getMessage());
/* 583 */     } catch (PathException pathException) {
/* 584 */       throw new PRException(pathException.getMessage());
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
/*     */   public void getAlphaBox(int[] paramArrayOfint) throws PRError {
/* 610 */     this.filler.getAlphaBox(paramArrayOfint);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputArea(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2) throws PRError, PRException {
/* 659 */     this.filler.setOutputArea(paramFloat1, paramFloat2, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTileState() throws PRError {
/* 670 */     return this.filler.getTileState();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAlpha(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) throws PRError, PRException, InterruptedException {
/* 710 */     this.filler.writeAlpha(paramArrayOfbyte, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAlpha(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3) throws PRError, PRException, InterruptedException {
/* 718 */     this.filler.writeAlpha(paramArrayOfchar, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextTile() throws PRError {
/* 728 */     this.filler.nextTile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 736 */     this.state = 1;
/* 737 */     this.filler.reset();
/* 738 */     this.stroker.reset();
/* 739 */     this.dasher.reset();
/*     */   }
/*     */   
/*     */   public int getTileWidth() {
/* 743 */     return TILE_SIZE;
/*     */   }
/*     */   
/*     */   public int getTileHeight() {
/* 747 */     return TILE_SIZE;
/*     */   }
/*     */   
/*     */   public int getTypicalAlpha() {
/* 751 */     int i = this.filler.getTileState();
/* 752 */     switch (i) {
/*     */       case 0:
/* 754 */         i = 0;
/*     */         break;
/*     */       case 1:
/* 757 */         i = 255;
/*     */         break;
/*     */       case 2:
/* 760 */         i = 128;
/*     */         break;
/*     */     } 
/* 763 */     return i;
/*     */   }
/*     */   
/*     */   public void getAlpha(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 767 */     synchronized (Rasterizer.class) {
/*     */       try {
/* 769 */         this.filler.writeAlpha(paramArrayOfbyte, 1, paramInt2, paramInt1);
/* 770 */       } catch (PRException pRException) {
/*     */         
/* 772 */         throw new InternalError("Ductus AA error: " + pRException.getMessage());
/* 773 */       } catch (InterruptedException interruptedException) {
/* 774 */         Thread.currentThread().interrupt();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 780 */     DuctusRenderingEngine.dropRasterizer(this);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\dc\pr\Rasterizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */