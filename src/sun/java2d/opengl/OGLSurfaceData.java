/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Composite;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.security.AccessController;
/*     */ import sun.awt.image.PixelConverter;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.SurfaceDataProxy;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.GraphicsPrimitive;
/*     */ import sun.java2d.loops.MaskFill;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ import sun.java2d.pipe.BufferedContext;
/*     */ import sun.java2d.pipe.ParallelogramPipe;
/*     */ import sun.java2d.pipe.PixelToParallelogramConverter;
/*     */ import sun.java2d.pipe.RenderBuffer;
/*     */ import sun.java2d.pipe.TextPipe;
/*     */ import sun.java2d.pipe.hw.AccelSurface;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class OGLSurfaceData
/*     */   extends SurfaceData
/*     */   implements AccelSurface
/*     */ {
/*     */   public static final int PBUFFER = 2;
/*     */   public static final int FBOBJECT = 5;
/*     */   public static final int PF_INT_ARGB = 0;
/*     */   public static final int PF_INT_ARGB_PRE = 1;
/*     */   public static final int PF_INT_RGB = 2;
/*     */   public static final int PF_INT_RGBX = 3;
/*     */   public static final int PF_INT_BGR = 4;
/*     */   public static final int PF_INT_BGRX = 5;
/*     */   public static final int PF_USHORT_565_RGB = 6;
/*     */   public static final int PF_USHORT_555_RGB = 7;
/*     */   public static final int PF_USHORT_555_RGBX = 8;
/*     */   public static final int PF_BYTE_GRAY = 9;
/*     */   public static final int PF_USHORT_GRAY = 10;
/*     */   public static final int PF_3BYTE_BGR = 11;
/*     */   private static final String DESC_OPENGL_SURFACE = "OpenGL Surface";
/*     */   private static final String DESC_OPENGL_SURFACE_RTT = "OpenGL Surface (render-to-texture)";
/*     */   private static final String DESC_OPENGL_TEXTURE = "OpenGL Texture";
/* 134 */   static final SurfaceType OpenGLSurface = SurfaceType.Any
/* 135 */     .deriveSubType("OpenGL Surface", PixelConverter.ArgbPre.instance);
/*     */   
/* 137 */   static final SurfaceType OpenGLSurfaceRTT = OpenGLSurface
/* 138 */     .deriveSubType("OpenGL Surface (render-to-texture)");
/* 139 */   static final SurfaceType OpenGLTexture = SurfaceType.Any
/* 140 */     .deriveSubType("OpenGL Texture");
/*     */ 
/*     */   
/*     */   private static boolean isFBObjectEnabled;
/*     */ 
/*     */   
/*     */   private static boolean isLCDShaderEnabled;
/*     */ 
/*     */   
/*     */   private static boolean isBIOpShaderEnabled;
/*     */ 
/*     */   
/*     */   private static boolean isGradShaderEnabled;
/*     */ 
/*     */   
/*     */   private OGLGraphicsConfig graphicsConfig;
/*     */ 
/*     */   
/*     */   protected int type;
/*     */ 
/*     */   
/*     */   private int nativeWidth;
/*     */ 
/*     */   
/*     */   private int nativeHeight;
/*     */ 
/*     */   
/*     */   protected static OGLRenderer oglRenderPipe;
/*     */ 
/*     */   
/*     */   protected static PixelToParallelogramConverter oglTxRenderPipe;
/*     */ 
/*     */   
/*     */   protected static ParallelogramPipe oglAAPgramPipe;
/*     */ 
/*     */   
/*     */   protected static OGLTextRenderer oglTextPipe;
/*     */ 
/*     */   
/*     */   protected static OGLDrawImage oglImagePipe;
/*     */ 
/*     */   
/*     */   static {
/* 183 */     if (!GraphicsEnvironment.isHeadless()) {
/*     */       
/* 185 */       String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.opengl.fbobject"));
/*     */ 
/*     */       
/* 188 */       isFBObjectEnabled = !"false".equals(str1);
/*     */ 
/*     */       
/* 191 */       String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.opengl.lcdshader"));
/*     */ 
/*     */       
/* 194 */       isLCDShaderEnabled = !"false".equals(str2);
/*     */ 
/*     */       
/* 197 */       String str3 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.opengl.biopshader"));
/*     */ 
/*     */       
/* 200 */       isBIOpShaderEnabled = !"false".equals(str3);
/*     */ 
/*     */       
/* 203 */       String str4 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.opengl.gradshader"));
/*     */ 
/*     */       
/* 206 */       isGradShaderEnabled = !"false".equals(str4);
/*     */       
/* 208 */       OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 209 */       oglImagePipe = new OGLDrawImage();
/* 210 */       oglTextPipe = new OGLTextRenderer(oGLRenderQueue);
/* 211 */       oglRenderPipe = new OGLRenderer(oGLRenderQueue);
/* 212 */       if (GraphicsPrimitive.tracingEnabled()) {
/* 213 */         oglTextPipe = oglTextPipe.traceWrap();
/*     */       }
/*     */ 
/*     */       
/* 217 */       oglAAPgramPipe = oglRenderPipe.getAAParallelogramPipe();
/* 218 */       oglTxRenderPipe = new PixelToParallelogramConverter(oglRenderPipe, oglRenderPipe, 1.0D, 0.25D, true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 223 */       OGLBlitLoops.register();
/* 224 */       OGLMaskFill.register();
/* 225 */       OGLMaskBlit.register();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected OGLSurfaceData(OGLGraphicsConfig paramOGLGraphicsConfig, ColorModel paramColorModel, int paramInt) {
/* 232 */     super(getCustomSurfaceType(paramInt), paramColorModel);
/* 233 */     this.graphicsConfig = paramOGLGraphicsConfig;
/* 234 */     this.type = paramInt;
/* 235 */     setBlitProxyKey(paramOGLGraphicsConfig.getProxyKey());
/*     */   }
/*     */ 
/*     */   
/*     */   public SurfaceDataProxy makeProxyFor(SurfaceData paramSurfaceData) {
/* 240 */     return OGLSurfaceDataProxy.createProxy(paramSurfaceData, this.graphicsConfig);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SurfaceType getCustomSurfaceType(int paramInt) {
/* 248 */     switch (paramInt) {
/*     */       case 3:
/* 250 */         return OpenGLTexture;
/*     */       case 5:
/* 252 */         return OpenGLSurfaceRTT;
/*     */     } 
/*     */     
/* 255 */     return OpenGLSurface;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initSurfaceNow(int paramInt1, int paramInt2) {
/* 265 */     boolean bool = (getTransparency() == 1) ? true : false;
/* 266 */     boolean bool1 = false;
/*     */     
/* 268 */     switch (this.type) {
/*     */       case 2:
/* 270 */         bool1 = initPbuffer(getNativeOps(), this.graphicsConfig
/* 271 */             .getNativeConfigInfo(), bool, paramInt1, paramInt2);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 277 */         bool1 = initTexture(getNativeOps(), bool, 
/* 278 */             isTexNonPow2Available(), 
/* 279 */             isTexRectAvailable(), paramInt1, paramInt2);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 284 */         bool1 = initFBObject(getNativeOps(), bool, 
/* 285 */             isTexNonPow2Available(), 
/* 286 */             isTexRectAvailable(), paramInt1, paramInt2);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 291 */         bool1 = initFlipBackbuffer(getNativeOps());
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     if (!bool1) {
/* 299 */       throw new OutOfMemoryError("can't create offscreen surface");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initSurface(final int width, final int height) {
/* 309 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 310 */     oGLRenderQueue.lock();
/*     */     try {
/* 312 */       switch (this.type) {
/*     */ 
/*     */         
/*     */         case 2:
/*     */         case 3:
/*     */         case 5:
/* 318 */           OGLContext.setScratchSurface(this.graphicsConfig);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 323 */       oGLRenderQueue.flushAndInvokeNow(new Runnable() {
/*     */             public void run() {
/* 325 */               OGLSurfaceData.this.initSurfaceNow(width, height);
/*     */             }
/*     */           });
/*     */     } finally {
/* 329 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final OGLContext getContext() {
/* 338 */     return this.graphicsConfig.getContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final OGLGraphicsConfig getOGLGraphicsConfig() {
/* 345 */     return this.graphicsConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getType() {
/* 352 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getTextureTarget() {
/* 361 */     return getTextureTarget(getNativeOps());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getTextureID() {
/* 370 */     return getTextureID(getNativeOps());
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
/*     */   public long getNativeResource(int paramInt) {
/* 391 */     if (paramInt == 3) {
/* 392 */       return getTextureID();
/*     */     }
/* 394 */     return 0L;
/*     */   }
/*     */   
/*     */   public Raster getRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 398 */     throw new InternalError("not implemented yet");
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
/*     */   public boolean canRenderLCDText(SunGraphics2D paramSunGraphics2D) {
/* 413 */     return (this.graphicsConfig
/* 414 */       .isCapPresent(131072) && paramSunGraphics2D.surfaceData
/* 415 */       .getTransparency() == 1 && paramSunGraphics2D.paintState <= 0 && (paramSunGraphics2D.compositeState <= 0 || (paramSunGraphics2D.compositeState <= 1 && 
/*     */ 
/*     */       
/* 418 */       canHandleComposite(paramSunGraphics2D.composite))));
/*     */   }
/*     */   
/*     */   private boolean canHandleComposite(Composite paramComposite) {
/* 422 */     if (paramComposite instanceof AlphaComposite) {
/* 423 */       AlphaComposite alphaComposite = (AlphaComposite)paramComposite;
/*     */       
/* 425 */       return (alphaComposite.getRule() == 3 && alphaComposite.getAlpha() >= 1.0F);
/*     */     } 
/* 427 */     return false;
/*     */   }
/*     */   
/*     */   public void validatePipe(SunGraphics2D paramSunGraphics2D) {
/*     */     TextPipe textPipe;
/* 432 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 441 */     if ((paramSunGraphics2D.compositeState <= 0 && paramSunGraphics2D.paintState <= 1) || (paramSunGraphics2D.compositeState == 1 && paramSunGraphics2D.paintState <= 1 && ((AlphaComposite)paramSunGraphics2D.composite)
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 448 */       .getRule() == 3) || (paramSunGraphics2D.compositeState == 2 && paramSunGraphics2D.paintState <= 1)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 455 */       textPipe = oglTextPipe;
/*     */     }
/*     */     else {
/*     */       
/* 459 */       super.validatePipe(paramSunGraphics2D);
/* 460 */       textPipe = paramSunGraphics2D.textpipe;
/* 461 */       bool = true;
/*     */     } 
/*     */     
/* 464 */     PixelToParallelogramConverter pixelToParallelogramConverter = null;
/* 465 */     OGLRenderer oGLRenderer = null;
/*     */     
/* 467 */     if (paramSunGraphics2D.antialiasHint != 2) {
/* 468 */       if (paramSunGraphics2D.paintState <= 1) {
/* 469 */         if (paramSunGraphics2D.compositeState <= 2) {
/* 470 */           pixelToParallelogramConverter = oglTxRenderPipe;
/* 471 */           oGLRenderer = oglRenderPipe;
/*     */         } 
/* 473 */       } else if (paramSunGraphics2D.compositeState <= 1 && 
/* 474 */         OGLPaints.isValid(paramSunGraphics2D)) {
/* 475 */         pixelToParallelogramConverter = oglTxRenderPipe;
/* 476 */         oGLRenderer = oglRenderPipe;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 481 */     else if (paramSunGraphics2D.paintState <= 1) {
/* 482 */       if (this.graphicsConfig.isCapPresent(256) && (paramSunGraphics2D.imageComp == CompositeType.SrcOverNoEa || paramSunGraphics2D.imageComp == CompositeType.SrcOver)) {
/*     */ 
/*     */ 
/*     */         
/* 486 */         if (!bool) {
/* 487 */           super.validatePipe(paramSunGraphics2D);
/* 488 */           bool = true;
/*     */         } 
/* 490 */         PixelToParallelogramConverter pixelToParallelogramConverter1 = new PixelToParallelogramConverter(paramSunGraphics2D.shapepipe, oglAAPgramPipe, 0.125D, 0.499D, false);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 495 */         paramSunGraphics2D.drawpipe = pixelToParallelogramConverter1;
/* 496 */         paramSunGraphics2D.fillpipe = pixelToParallelogramConverter1;
/* 497 */         paramSunGraphics2D.shapepipe = pixelToParallelogramConverter1;
/* 498 */       } else if (paramSunGraphics2D.compositeState == 2) {
/*     */         
/* 500 */         pixelToParallelogramConverter = oglTxRenderPipe;
/* 501 */         oGLRenderer = oglRenderPipe;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 507 */     if (pixelToParallelogramConverter != null) {
/* 508 */       if (paramSunGraphics2D.transformState >= 3) {
/* 509 */         paramSunGraphics2D.drawpipe = pixelToParallelogramConverter;
/* 510 */         paramSunGraphics2D.fillpipe = pixelToParallelogramConverter;
/* 511 */       } else if (paramSunGraphics2D.strokeState != 0) {
/* 512 */         paramSunGraphics2D.drawpipe = pixelToParallelogramConverter;
/* 513 */         paramSunGraphics2D.fillpipe = oGLRenderer;
/*     */       } else {
/* 515 */         paramSunGraphics2D.drawpipe = oGLRenderer;
/* 516 */         paramSunGraphics2D.fillpipe = oGLRenderer;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 522 */       paramSunGraphics2D.shapepipe = pixelToParallelogramConverter;
/*     */     }
/* 524 */     else if (!bool) {
/* 525 */       super.validatePipe(paramSunGraphics2D);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 530 */     paramSunGraphics2D.textpipe = textPipe;
/*     */ 
/*     */     
/* 533 */     paramSunGraphics2D.imagepipe = oglImagePipe;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MaskFill getMaskFill(SunGraphics2D paramSunGraphics2D) {
/* 538 */     if (paramSunGraphics2D.paintState > 1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 550 */       if (!OGLPaints.isValid(paramSunGraphics2D) || 
/* 551 */         !this.graphicsConfig.isCapPresent(16))
/*     */       {
/* 553 */         return null;
/*     */       }
/*     */     }
/* 556 */     return super.getMaskFill(paramSunGraphics2D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean copyArea(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 562 */     if (paramSunGraphics2D.transformState < 3 && paramSunGraphics2D.compositeState < 2) {
/*     */ 
/*     */       
/* 565 */       paramInt1 += paramSunGraphics2D.transX;
/* 566 */       paramInt2 += paramSunGraphics2D.transY;
/*     */       
/* 568 */       oglRenderPipe.copyArea(paramSunGraphics2D, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */       
/* 570 */       return true;
/*     */     } 
/* 572 */     return false;
/*     */   }
/*     */   
/*     */   public void flush() {
/* 576 */     invalidate();
/* 577 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 578 */     oGLRenderQueue.lock();
/*     */ 
/*     */     
/*     */     try {
/* 582 */       OGLContext.setScratchSurface(this.graphicsConfig);
/*     */       
/* 584 */       RenderBuffer renderBuffer = oGLRenderQueue.getBuffer();
/* 585 */       oGLRenderQueue.ensureCapacityAndAlignment(12, 4);
/* 586 */       renderBuffer.putInt(72);
/* 587 */       renderBuffer.putLong(getNativeOps());
/*     */ 
/*     */       
/* 590 */       oGLRenderQueue.flushNow();
/*     */     } finally {
/* 592 */       oGLRenderQueue.unlock();
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
/*     */   static void dispose(long paramLong1, long paramLong2) {
/* 605 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 606 */     oGLRenderQueue.lock();
/*     */ 
/*     */     
/*     */     try {
/* 610 */       OGLContext.setScratchSurface(paramLong2);
/*     */       
/* 612 */       RenderBuffer renderBuffer = oGLRenderQueue.getBuffer();
/* 613 */       oGLRenderQueue.ensureCapacityAndAlignment(12, 4);
/* 614 */       renderBuffer.putInt(73);
/* 615 */       renderBuffer.putLong(paramLong1);
/*     */ 
/*     */       
/* 618 */       oGLRenderQueue.flushNow();
/*     */     } finally {
/* 620 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   static void swapBuffers(long paramLong) {
/* 625 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 626 */     oGLRenderQueue.lock();
/*     */     try {
/* 628 */       RenderBuffer renderBuffer = oGLRenderQueue.getBuffer();
/* 629 */       oGLRenderQueue.ensureCapacityAndAlignment(12, 4);
/* 630 */       renderBuffer.putInt(80);
/* 631 */       renderBuffer.putLong(paramLong);
/* 632 */       oGLRenderQueue.flushNow();
/*     */     } finally {
/* 634 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isTexNonPow2Available() {
/* 643 */     return this.graphicsConfig.isCapPresent(32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isTexRectAvailable() {
/* 652 */     return this.graphicsConfig.isCapPresent(1048576);
/*     */   }
/*     */   
/*     */   public Rectangle getNativeBounds() {
/* 656 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 657 */     oGLRenderQueue.lock();
/*     */     try {
/* 659 */       return new Rectangle(this.nativeWidth, this.nativeHeight);
/*     */     } finally {
/* 661 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isOnScreen() {
/* 672 */     return (getType() == 1);
/*     */   }
/*     */   
/*     */   protected native boolean initTexture(long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt1, int paramInt2);
/*     */   
/*     */   protected native boolean initFBObject(long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt1, int paramInt2);
/*     */   
/*     */   protected native boolean initFlipBackbuffer(long paramLong);
/*     */   
/*     */   protected abstract boolean initPbuffer(long paramLong1, long paramLong2, boolean paramBoolean, int paramInt1, int paramInt2);
/*     */   
/*     */   private native int getTextureTarget(long paramLong);
/*     */   
/*     */   private native int getTextureID(long paramLong);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\opengl\OGLSurfaceData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */