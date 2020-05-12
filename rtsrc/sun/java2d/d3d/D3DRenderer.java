/*     */ package sun.java2d.d3d;
/*     */ 
/*     */ import java.awt.geom.Path2D;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.loops.GraphicsPrimitive;
/*     */ import sun.java2d.pipe.BufferedRenderPipe;
/*     */ import sun.java2d.pipe.ParallelogramPipe;
/*     */ import sun.java2d.pipe.RenderQueue;
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
/*     */ class D3DRenderer
/*     */   extends BufferedRenderPipe
/*     */ {
/*     */   D3DRenderer(RenderQueue paramRenderQueue) {
/*  43 */     super(paramRenderQueue);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void validateContext(SunGraphics2D paramSunGraphics2D) {
/*     */     D3DSurfaceData d3DSurfaceData;
/*  49 */     boolean bool = (paramSunGraphics2D.paint.getTransparency() == 1) ? true : false;
/*     */ 
/*     */     
/*     */     try {
/*  53 */       d3DSurfaceData = (D3DSurfaceData)paramSunGraphics2D.surfaceData;
/*  54 */     } catch (ClassCastException classCastException) {
/*  55 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*  57 */     D3DContext.validateContext(d3DSurfaceData, d3DSurfaceData, paramSunGraphics2D
/*  58 */         .getCompClip(), paramSunGraphics2D.composite, null, paramSunGraphics2D.paint, paramSunGraphics2D, bool);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void validateContextAA(SunGraphics2D paramSunGraphics2D) {
/*     */     D3DSurfaceData d3DSurfaceData;
/*  64 */     boolean bool = false;
/*     */     
/*     */     try {
/*  67 */       d3DSurfaceData = (D3DSurfaceData)paramSunGraphics2D.surfaceData;
/*  68 */     } catch (ClassCastException classCastException) {
/*  69 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*  71 */     D3DContext.validateContext(d3DSurfaceData, d3DSurfaceData, paramSunGraphics2D
/*  72 */         .getCompClip(), paramSunGraphics2D.composite, null, paramSunGraphics2D.paint, paramSunGraphics2D, bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void copyArea(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  79 */     this.rq.lock();
/*     */     try {
/*     */       D3DSurfaceData d3DSurfaceData;
/*  82 */       boolean bool = (paramSunGraphics2D.surfaceData.getTransparency() == 1) ? true : false;
/*     */ 
/*     */       
/*     */       try {
/*  86 */         d3DSurfaceData = (D3DSurfaceData)paramSunGraphics2D.surfaceData;
/*  87 */       } catch (ClassCastException classCastException) {
/*  88 */         throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */       } 
/*  90 */       D3DContext.validateContext(d3DSurfaceData, d3DSurfaceData, paramSunGraphics2D
/*  91 */           .getCompClip(), paramSunGraphics2D.composite, null, null, null, bool);
/*     */ 
/*     */       
/*  94 */       this.rq.ensureCapacity(28);
/*  95 */       this.buf.putInt(30);
/*  96 */       this.buf.putInt(paramInt1).putInt(paramInt2).putInt(paramInt3).putInt(paramInt4);
/*  97 */       this.buf.putInt(paramInt5).putInt(paramInt6);
/*     */     } finally {
/*  99 */       this.rq.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected native void drawPoly(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3);
/*     */ 
/*     */   
/*     */   D3DRenderer traceWrap() {
/* 108 */     return new Tracer(this);
/*     */   }
/*     */   
/*     */   private class Tracer
/*     */     extends D3DRenderer {
/*     */     Tracer(D3DRenderer param1D3DRenderer1) {
/* 114 */       super(param1D3DRenderer1.rq);
/* 115 */       this.d3dr = param1D3DRenderer1;
/*     */     } private D3DRenderer d3dr;
/*     */     public ParallelogramPipe getAAParallelogramPipe() {
/* 118 */       final ParallelogramPipe realpipe = this.d3dr.getAAParallelogramPipe();
/* 119 */       return new ParallelogramPipe()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void fillParallelogram(SunGraphics2D param2SunGraphics2D, double param2Double1, double param2Double2, double param2Double3, double param2Double4, double param2Double5, double param2Double6, double param2Double7, double param2Double8, double param2Double9, double param2Double10)
/*     */           {
/* 127 */             GraphicsPrimitive.tracePrimitive("D3DFillAAParallelogram");
/* 128 */             realpipe.fillParallelogram(param2SunGraphics2D, param2Double1, param2Double2, param2Double3, param2Double4, param2Double5, param2Double6, param2Double7, param2Double8, param2Double9, param2Double10);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void drawParallelogram(SunGraphics2D param2SunGraphics2D, double param2Double1, double param2Double2, double param2Double3, double param2Double4, double param2Double5, double param2Double6, double param2Double7, double param2Double8, double param2Double9, double param2Double10, double param2Double11, double param2Double12) {
/* 140 */             GraphicsPrimitive.tracePrimitive("D3DDrawAAParallelogram");
/* 141 */             realpipe.drawParallelogram(param2SunGraphics2D, param2Double1, param2Double2, param2Double3, param2Double4, param2Double5, param2Double6, param2Double7, param2Double8, param2Double9, param2Double10, param2Double11, param2Double12);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void validateContext(SunGraphics2D param1SunGraphics2D) {
/* 150 */       this.d3dr.validateContext(param1SunGraphics2D);
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawLine(SunGraphics2D param1SunGraphics2D, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 155 */       GraphicsPrimitive.tracePrimitive("D3DDrawLine");
/* 156 */       this.d3dr.drawLine(param1SunGraphics2D, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */     public void drawRect(SunGraphics2D param1SunGraphics2D, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 159 */       GraphicsPrimitive.tracePrimitive("D3DDrawRect");
/* 160 */       this.d3dr.drawRect(param1SunGraphics2D, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void drawPoly(SunGraphics2D param1SunGraphics2D, int[] param1ArrayOfint1, int[] param1ArrayOfint2, int param1Int, boolean param1Boolean) {
/* 166 */       GraphicsPrimitive.tracePrimitive("D3DDrawPoly");
/* 167 */       this.d3dr.drawPoly(param1SunGraphics2D, param1ArrayOfint1, param1ArrayOfint2, param1Int, param1Boolean);
/*     */     }
/*     */     public void fillRect(SunGraphics2D param1SunGraphics2D, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 170 */       GraphicsPrimitive.tracePrimitive("D3DFillRect");
/* 171 */       this.d3dr.fillRect(param1SunGraphics2D, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawPath(SunGraphics2D param1SunGraphics2D, Path2D.Float param1Float, int param1Int1, int param1Int2) {
/* 176 */       GraphicsPrimitive.tracePrimitive("D3DDrawPath");
/* 177 */       this.d3dr.drawPath(param1SunGraphics2D, param1Float, param1Int1, param1Int2);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void fillPath(SunGraphics2D param1SunGraphics2D, Path2D.Float param1Float, int param1Int1, int param1Int2) {
/* 182 */       GraphicsPrimitive.tracePrimitive("D3DFillPath");
/* 183 */       this.d3dr.fillPath(param1SunGraphics2D, param1Float, param1Int1, param1Int2);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void fillSpans(SunGraphics2D param1SunGraphics2D, SpanIterator param1SpanIterator, int param1Int1, int param1Int2) {
/* 188 */       GraphicsPrimitive.tracePrimitive("D3DFillSpans");
/* 189 */       this.d3dr.fillSpans(param1SunGraphics2D, param1SpanIterator, param1Int1, param1Int2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fillParallelogram(SunGraphics2D param1SunGraphics2D, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8, double param1Double9, double param1Double10) {
/* 198 */       GraphicsPrimitive.tracePrimitive("D3DFillParallelogram");
/* 199 */       this.d3dr.fillParallelogram(param1SunGraphics2D, param1Double1, param1Double2, param1Double3, param1Double4, param1Double5, param1Double6, param1Double7, param1Double8, param1Double9, param1Double10);
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
/*     */     public void drawParallelogram(SunGraphics2D param1SunGraphics2D, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8, double param1Double9, double param1Double10, double param1Double11, double param1Double12) {
/* 211 */       GraphicsPrimitive.tracePrimitive("D3DDrawParallelogram");
/* 212 */       this.d3dr.drawParallelogram(param1SunGraphics2D, param1Double1, param1Double2, param1Double3, param1Double4, param1Double5, param1Double6, param1Double7, param1Double8, param1Double9, param1Double10, param1Double11, param1Double12);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void copyArea(SunGraphics2D param1SunGraphics2D, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 219 */       GraphicsPrimitive.tracePrimitive("D3DCopyArea");
/* 220 */       this.d3dr.copyArea(param1SunGraphics2D, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\d3d\D3DRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */