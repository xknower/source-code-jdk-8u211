/*     */ package sun.java2d.windows;
/*     */ 
/*     */ import java.awt.Composite;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Path2D;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.loops.GraphicsPrimitive;
/*     */ import sun.java2d.pipe.LoopPipe;
/*     */ import sun.java2d.pipe.PixelDrawPipe;
/*     */ import sun.java2d.pipe.PixelFillPipe;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.java2d.pipe.ShapeDrawPipe;
/*     */ import sun.java2d.pipe.ShapeSpanIterator;
/*     */ import sun.java2d.pipe.SpanIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDIRenderer
/*     */   implements PixelDrawPipe, PixelFillPipe, ShapeDrawPipe
/*     */ {
/*     */   native void doDrawLine(GDIWindowSurfaceData paramGDIWindowSurfaceData, Region paramRegion, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */   
/*     */   public void drawLine(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  56 */     int i = paramSunGraphics2D.transX;
/*  57 */     int j = paramSunGraphics2D.transY;
/*     */     try {
/*  59 */       doDrawLine((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/*  60 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, paramInt1 + i, paramInt2 + j, paramInt3 + i, paramInt4 + j);
/*     */     }
/*  62 */     catch (ClassCastException classCastException) {
/*  63 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void doDrawRect(GDIWindowSurfaceData paramGDIWindowSurfaceData, Region paramRegion, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     try {
/*  75 */       doDrawRect((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/*  76 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4);
/*     */     }
/*  78 */     catch (ClassCastException classCastException) {
/*  79 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   native void doDrawRoundRect(GDIWindowSurfaceData paramGDIWindowSurfaceData, Region paramRegion, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*     */     try {
/*  93 */       doDrawRoundRect((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/*  94 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     
/*     */     }
/*  97 */     catch (ClassCastException classCastException) {
/*  98 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void doDrawOval(GDIWindowSurfaceData paramGDIWindowSurfaceData, Region paramRegion, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     try {
/* 110 */       doDrawOval((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/* 111 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4);
/*     */     }
/* 113 */     catch (ClassCastException classCastException) {
/* 114 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   native void doDrawArc(GDIWindowSurfaceData paramGDIWindowSurfaceData, Region paramRegion, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*     */     try {
/* 128 */       doDrawArc((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/* 129 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     
/*     */     }
/* 132 */     catch (ClassCastException classCastException) {
/* 133 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   native void doDrawPoly(GDIWindowSurfaceData paramGDIWindowSurfaceData, Region paramRegion, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt4, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPolyline(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*     */     try {
/* 148 */       doDrawPoly((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/* 149 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, paramSunGraphics2D.transX, paramSunGraphics2D.transY, paramArrayOfint1, paramArrayOfint2, paramInt, false);
/*     */     }
/* 151 */     catch (ClassCastException classCastException) {
/* 152 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*     */     try {
/* 161 */       doDrawPoly((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/* 162 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, paramSunGraphics2D.transX, paramSunGraphics2D.transY, paramArrayOfint1, paramArrayOfint2, paramInt, true);
/*     */     }
/* 164 */     catch (ClassCastException classCastException) {
/* 165 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void doFillRect(GDIWindowSurfaceData paramGDIWindowSurfaceData, Region paramRegion, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     try {
/* 177 */       doFillRect((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/* 178 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4);
/*     */     }
/* 180 */     catch (ClassCastException classCastException) {
/* 181 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   native void doFillRoundRect(GDIWindowSurfaceData paramGDIWindowSurfaceData, Region paramRegion, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*     */     try {
/* 195 */       doFillRoundRect((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/* 196 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     
/*     */     }
/* 199 */     catch (ClassCastException classCastException) {
/* 200 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void doFillOval(GDIWindowSurfaceData paramGDIWindowSurfaceData, Region paramRegion, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     try {
/* 212 */       doFillOval((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/* 213 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4);
/*     */     }
/* 215 */     catch (ClassCastException classCastException) {
/* 216 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   native void doFillArc(GDIWindowSurfaceData paramGDIWindowSurfaceData, Region paramRegion, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*     */     try {
/* 230 */       doFillArc((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/* 231 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     
/*     */     }
/* 234 */     catch (ClassCastException classCastException) {
/* 235 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   native void doFillPoly(GDIWindowSurfaceData paramGDIWindowSurfaceData, Region paramRegion, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*     */     try {
/* 250 */       doFillPoly((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/* 251 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, paramSunGraphics2D.transX, paramSunGraphics2D.transY, paramArrayOfint1, paramArrayOfint2, paramInt);
/*     */     }
/* 253 */     catch (ClassCastException classCastException) {
/* 254 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void doShape(GDIWindowSurfaceData paramGDIWindowSurfaceData, Region paramRegion, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, Path2D.Float paramFloat, boolean paramBoolean);
/*     */ 
/*     */   
/*     */   void doShape(SunGraphics2D paramSunGraphics2D, Shape paramShape, boolean paramBoolean) {
/*     */     Path2D.Float float_;
/*     */     boolean bool1;
/*     */     boolean bool2;
/* 267 */     if (paramSunGraphics2D.transformState <= 1) {
/* 268 */       if (paramShape instanceof Path2D.Float) {
/* 269 */         float_ = (Path2D.Float)paramShape;
/*     */       } else {
/* 271 */         float_ = new Path2D.Float(paramShape);
/*     */       } 
/* 273 */       bool1 = paramSunGraphics2D.transX;
/* 274 */       bool2 = paramSunGraphics2D.transY;
/*     */     } else {
/* 276 */       float_ = new Path2D.Float(paramShape, paramSunGraphics2D.transform);
/* 277 */       bool1 = false;
/* 278 */       bool2 = false;
/*     */     } 
/*     */     try {
/* 281 */       doShape((GDIWindowSurfaceData)paramSunGraphics2D.surfaceData, paramSunGraphics2D
/* 282 */           .getCompClip(), paramSunGraphics2D.composite, paramSunGraphics2D.eargb, bool1, bool2, float_, paramBoolean);
/*     */     }
/* 284 */     catch (ClassCastException classCastException) {
/* 285 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doFillSpans(SunGraphics2D paramSunGraphics2D, SpanIterator paramSpanIterator) {
/*     */     GDIWindowSurfaceData gDIWindowSurfaceData;
/* 294 */     int[] arrayOfInt = new int[4];
/*     */     
/*     */     try {
/* 297 */       gDIWindowSurfaceData = (GDIWindowSurfaceData)paramSunGraphics2D.surfaceData;
/* 298 */     } catch (ClassCastException classCastException) {
/* 299 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/* 301 */     Region region = paramSunGraphics2D.getCompClip();
/* 302 */     Composite composite = paramSunGraphics2D.composite;
/* 303 */     int i = paramSunGraphics2D.eargb;
/* 304 */     while (paramSpanIterator.nextSpan(arrayOfInt)) {
/* 305 */       doFillRect(gDIWindowSurfaceData, region, composite, i, arrayOfInt[0], arrayOfInt[1], arrayOfInt[2] - arrayOfInt[0], arrayOfInt[3] - arrayOfInt[1]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/* 311 */     if (paramSunGraphics2D.strokeState == 0) {
/* 312 */       doShape(paramSunGraphics2D, paramShape, false);
/* 313 */     } else if (paramSunGraphics2D.strokeState < 3) {
/* 314 */       ShapeSpanIterator shapeSpanIterator = LoopPipe.getStrokeSpans(paramSunGraphics2D, paramShape);
/*     */       try {
/* 316 */         doFillSpans(paramSunGraphics2D, shapeSpanIterator);
/*     */       } finally {
/* 318 */         shapeSpanIterator.dispose();
/*     */       } 
/*     */     } else {
/* 321 */       doShape(paramSunGraphics2D, paramSunGraphics2D.stroke.createStrokedShape(paramShape), true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fill(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/* 326 */     doShape(paramSunGraphics2D, paramShape, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public native void devCopyArea(GDIWindowSurfaceData paramGDIWindowSurfaceData, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   
/*     */   public GDIRenderer traceWrap() {
/* 335 */     return new Tracer();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Tracer
/*     */     extends GDIRenderer
/*     */   {
/*     */     void doDrawLine(GDIWindowSurfaceData param1GDIWindowSurfaceData, Region param1Region, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 343 */       GraphicsPrimitive.tracePrimitive("GDIDrawLine");
/* 344 */       super.doDrawLine(param1GDIWindowSurfaceData, param1Region, param1Composite, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void doDrawRect(GDIWindowSurfaceData param1GDIWindowSurfaceData, Region param1Region, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 350 */       GraphicsPrimitive.tracePrimitive("GDIDrawRect");
/* 351 */       super.doDrawRect(param1GDIWindowSurfaceData, param1Region, param1Composite, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void doDrawRoundRect(GDIWindowSurfaceData param1GDIWindowSurfaceData, Region param1Region, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7) {
/* 358 */       GraphicsPrimitive.tracePrimitive("GDIDrawRoundRect");
/* 359 */       super.doDrawRoundRect(param1GDIWindowSurfaceData, param1Region, param1Composite, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6, param1Int7);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void doDrawOval(GDIWindowSurfaceData param1GDIWindowSurfaceData, Region param1Region, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 366 */       GraphicsPrimitive.tracePrimitive("GDIDrawOval");
/* 367 */       super.doDrawOval(param1GDIWindowSurfaceData, param1Region, param1Composite, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void doDrawArc(GDIWindowSurfaceData param1GDIWindowSurfaceData, Region param1Region, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7) {
/* 374 */       GraphicsPrimitive.tracePrimitive("GDIDrawArc");
/* 375 */       super.doDrawArc(param1GDIWindowSurfaceData, param1Region, param1Composite, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6, param1Int7);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void doDrawPoly(GDIWindowSurfaceData param1GDIWindowSurfaceData, Region param1Region, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int[] param1ArrayOfint1, int[] param1ArrayOfint2, int param1Int4, boolean param1Boolean) {
/* 384 */       GraphicsPrimitive.tracePrimitive("GDIDrawPoly");
/* 385 */       super.doDrawPoly(param1GDIWindowSurfaceData, param1Region, param1Composite, param1Int1, param1Int2, param1Int3, param1ArrayOfint1, param1ArrayOfint2, param1Int4, param1Boolean);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void doFillRect(GDIWindowSurfaceData param1GDIWindowSurfaceData, Region param1Region, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 392 */       GraphicsPrimitive.tracePrimitive("GDIFillRect");
/* 393 */       super.doFillRect(param1GDIWindowSurfaceData, param1Region, param1Composite, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void doFillRoundRect(GDIWindowSurfaceData param1GDIWindowSurfaceData, Region param1Region, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7) {
/* 400 */       GraphicsPrimitive.tracePrimitive("GDIFillRoundRect");
/* 401 */       super.doFillRoundRect(param1GDIWindowSurfaceData, param1Region, param1Composite, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6, param1Int7);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void doFillOval(GDIWindowSurfaceData param1GDIWindowSurfaceData, Region param1Region, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 408 */       GraphicsPrimitive.tracePrimitive("GDIFillOval");
/* 409 */       super.doFillOval(param1GDIWindowSurfaceData, param1Region, param1Composite, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void doFillArc(GDIWindowSurfaceData param1GDIWindowSurfaceData, Region param1Region, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7) {
/* 416 */       GraphicsPrimitive.tracePrimitive("GDIFillArc");
/* 417 */       super.doFillArc(param1GDIWindowSurfaceData, param1Region, param1Composite, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6, param1Int7);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void doFillPoly(GDIWindowSurfaceData param1GDIWindowSurfaceData, Region param1Region, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int[] param1ArrayOfint1, int[] param1ArrayOfint2, int param1Int4) {
/* 426 */       GraphicsPrimitive.tracePrimitive("GDIFillPoly");
/* 427 */       super.doFillPoly(param1GDIWindowSurfaceData, param1Region, param1Composite, param1Int1, param1Int2, param1Int3, param1ArrayOfint1, param1ArrayOfint2, param1Int4);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void doShape(GDIWindowSurfaceData param1GDIWindowSurfaceData, Region param1Region, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, Path2D.Float param1Float, boolean param1Boolean) {
/* 435 */       GraphicsPrimitive.tracePrimitive(param1Boolean ? "GDIFillShape" : "GDIDrawShape");
/*     */ 
/*     */       
/* 438 */       super.doShape(param1GDIWindowSurfaceData, param1Region, param1Composite, param1Int1, param1Int2, param1Int3, param1Float, param1Boolean);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void devCopyArea(GDIWindowSurfaceData param1GDIWindowSurfaceData, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 446 */       GraphicsPrimitive.tracePrimitive("GDICopyArea");
/* 447 */       super.devCopyArea(param1GDIWindowSurfaceData, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\windows\GDIRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */