/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.AWTException;
/*     */ import java.awt.BufferCapabilities;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.ImageCapabilities;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.VolatileImage;
/*     */ import sun.awt.Win32GraphicsConfig;
/*     */ import sun.awt.Win32GraphicsDevice;
/*     */ import sun.awt.image.SunVolatileImage;
/*     */ import sun.awt.image.SurfaceManager;
/*     */ import sun.awt.windows.WComponentPeer;
/*     */ import sun.java2d.Disposer;
/*     */ import sun.java2d.DisposerRecord;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.Surface;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.pipe.BufferedContext;
/*     */ import sun.java2d.pipe.hw.AccelDeviceEventListener;
/*     */ import sun.java2d.pipe.hw.AccelDeviceEventNotifier;
/*     */ import sun.java2d.pipe.hw.AccelSurface;
/*     */ import sun.java2d.pipe.hw.AccelTypedVolatileImage;
/*     */ import sun.java2d.pipe.hw.ContextCapabilities;
/*     */ import sun.java2d.windows.GDIWindowSurfaceData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WGLGraphicsConfig
/*     */   extends Win32GraphicsConfig
/*     */   implements OGLGraphicsConfig
/*     */ {
/*     */   protected static boolean wglAvailable;
/*  67 */   private static ImageCapabilities imageCaps = new WGLImageCaps();
/*     */   
/*     */   private BufferCapabilities bufferCaps;
/*     */   private long pConfigInfo;
/*     */   private ContextCapabilities oglCaps;
/*     */   private OGLContext context;
/*  73 */   private Object disposerReferent = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  81 */     wglAvailable = initWGL();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WGLGraphicsConfig(Win32GraphicsDevice paramWin32GraphicsDevice, int paramInt, long paramLong, ContextCapabilities paramContextCapabilities) {
/*  87 */     super(paramWin32GraphicsDevice, paramInt);
/*  88 */     this.pConfigInfo = paramLong;
/*  89 */     this.oglCaps = paramContextCapabilities;
/*  90 */     this.context = new OGLContext(OGLRenderQueue.getInstance(), this);
/*     */ 
/*     */ 
/*     */     
/*  94 */     Disposer.addRecord(this.disposerReferent, new WGLGCDisposerRecord(this.pConfigInfo, paramWin32GraphicsDevice
/*     */           
/*  96 */           .getScreen()));
/*     */   }
/*     */   
/*     */   public Object getProxyKey() {
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public SurfaceData createManagedSurface(int paramInt1, int paramInt2, int paramInt3) {
/* 104 */     return WGLSurfaceData.createData(this, paramInt1, paramInt2, 
/* 105 */         getColorModel(paramInt3), null, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WGLGraphicsConfig getConfig(Win32GraphicsDevice paramWin32GraphicsDevice, int paramInt) {
/* 113 */     if (!wglAvailable) {
/* 114 */       return null;
/*     */     }
/*     */     
/* 117 */     long l = 0L;
/* 118 */     final String[] ids = new String[1];
/* 119 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 120 */     oGLRenderQueue.lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 125 */       OGLContext.invalidateCurrentContext();
/*     */       
/* 127 */       WGLGetConfigInfo wGLGetConfigInfo = new WGLGetConfigInfo(paramWin32GraphicsDevice.getScreen(), paramInt);
/* 128 */       oGLRenderQueue.flushAndInvokeNow(wGLGetConfigInfo);
/* 129 */       l = wGLGetConfigInfo.getConfigInfo();
/* 130 */       if (l != 0L) {
/* 131 */         OGLContext.setScratchSurface(l);
/* 132 */         oGLRenderQueue.flushAndInvokeNow(new Runnable() {
/*     */               public void run() {
/* 134 */                 ids[0] = OGLContext.getOGLIdString();
/*     */               }
/*     */             });
/*     */       } 
/*     */     } finally {
/* 139 */       oGLRenderQueue.unlock();
/*     */     } 
/* 141 */     if (l == 0L) {
/* 142 */       return null;
/*     */     }
/*     */     
/* 145 */     int i = getOGLCapabilities(l);
/* 146 */     OGLContext.OGLContextCaps oGLContextCaps = new OGLContext.OGLContextCaps(i, arrayOfString[0]);
/*     */     
/* 148 */     return new WGLGraphicsConfig(paramWin32GraphicsDevice, paramInt, l, oGLContextCaps);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class WGLGetConfigInfo
/*     */     implements Runnable
/*     */   {
/*     */     private int screen;
/*     */     private int pixfmt;
/*     */     private long cfginfo;
/*     */     
/*     */     private WGLGetConfigInfo(int param1Int1, int param1Int2) {
/* 160 */       this.screen = param1Int1;
/* 161 */       this.pixfmt = param1Int2;
/*     */     }
/*     */     public void run() {
/* 164 */       this.cfginfo = WGLGraphicsConfig.getWGLConfigInfo(this.screen, this.pixfmt);
/*     */     }
/*     */     public long getConfigInfo() {
/* 167 */       return this.cfginfo;
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isWGLAvailable() {
/* 172 */     return wglAvailable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isCapPresent(int paramInt) {
/* 181 */     return ((this.oglCaps.getCaps() & paramInt) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public final long getNativeConfigInfo() {
/* 186 */     return this.pConfigInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final OGLContext getContext() {
/* 196 */     return this.context;
/*     */   }
/*     */   
/*     */   private static class WGLGCDisposerRecord implements DisposerRecord { private long pCfgInfo;
/*     */     private int screen;
/*     */     
/*     */     public WGLGCDisposerRecord(long param1Long, int param1Int) {
/* 203 */       this.pCfgInfo = param1Long;
/*     */     }
/*     */     public void dispose() {
/* 206 */       OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 207 */       oGLRenderQueue.lock();
/*     */       try {
/* 209 */         oGLRenderQueue.flushAndInvokeNow(new Runnable()
/*     */             {
/*     */               public void run() {
/* 212 */                 AccelDeviceEventNotifier.eventOccured(WGLGraphicsConfig.WGLGCDisposerRecord.this.screen, 0);
/*     */ 
/*     */                 
/* 215 */                 AccelDeviceEventNotifier.eventOccured(WGLGraphicsConfig.WGLGCDisposerRecord.this.screen, 1);
/*     */               }
/*     */             });
/*     */       } finally {
/*     */         
/* 220 */         oGLRenderQueue.unlock();
/*     */       } 
/* 222 */       if (this.pCfgInfo != 0L) {
/* 223 */         OGLRenderQueue.disposeGraphicsConfig(this.pCfgInfo);
/* 224 */         this.pCfgInfo = 0L;
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void displayChanged() {
/* 231 */     super.displayChanged();
/*     */ 
/*     */ 
/*     */     
/* 235 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 236 */     oGLRenderQueue.lock();
/*     */     try {
/* 238 */       OGLContext.invalidateCurrentContext();
/*     */     } finally {
/* 240 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ColorModel getColorModel(int paramInt) {
/*     */     ColorSpace colorSpace;
/* 246 */     switch (paramInt) {
/*     */ 
/*     */       
/*     */       case 1:
/* 250 */         return new DirectColorModel(24, 16711680, 65280, 255);
/*     */       case 2:
/* 252 */         return new DirectColorModel(25, 16711680, 65280, 255, 16777216);
/*     */       case 3:
/* 254 */         colorSpace = ColorSpace.getInstance(1000);
/* 255 */         return new DirectColorModel(colorSpace, 32, 16711680, 65280, 255, -16777216, true, 3);
/*     */     } 
/*     */ 
/*     */     
/* 259 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 265 */     return "WGLGraphicsConfig[dev=" + this.screen + ",pixfmt=" + this.visual + "]";
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
/*     */   public SurfaceData createSurfaceData(WComponentPeer paramWComponentPeer, int paramInt) {
/*     */     GDIWindowSurfaceData gDIWindowSurfaceData;
/* 284 */     WGLSurfaceData.WGLWindowSurfaceData wGLWindowSurfaceData = WGLSurfaceData.createData(paramWComponentPeer);
/* 285 */     if (wGLWindowSurfaceData == null) {
/* 286 */       gDIWindowSurfaceData = GDIWindowSurfaceData.createData(paramWComponentPeer);
/*     */     }
/* 288 */     return gDIWindowSurfaceData;
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
/*     */   public void assertOperationSupported(Component paramComponent, int paramInt, BufferCapabilities paramBufferCapabilities) throws AWTException {
/* 306 */     if (paramInt > 2) {
/* 307 */       throw new AWTException("Only double or single buffering is supported");
/*     */     }
/*     */     
/* 310 */     BufferCapabilities bufferCapabilities = getBufferCapabilities();
/* 311 */     if (!bufferCapabilities.isPageFlipping()) {
/* 312 */       throw new AWTException("Page flipping is not supported");
/*     */     }
/* 314 */     if (paramBufferCapabilities.getFlipContents() == BufferCapabilities.FlipContents.PRIOR) {
/* 315 */       throw new AWTException("FlipContents.PRIOR is not supported");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VolatileImage createBackBuffer(WComponentPeer paramWComponentPeer) {
/* 325 */     Component component = (Component)paramWComponentPeer.getTarget();
/*     */ 
/*     */     
/* 328 */     int i = Math.max(1, component.getWidth());
/* 329 */     int j = Math.max(1, component.getHeight());
/* 330 */     return new SunVolatileImage(component, i, j, Boolean.TRUE);
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
/*     */   public void flip(WComponentPeer paramWComponentPeer, Component paramComponent, VolatileImage paramVolatileImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, BufferCapabilities.FlipContents paramFlipContents) {
/* 344 */     if (paramFlipContents == BufferCapabilities.FlipContents.COPIED) {
/* 345 */       SurfaceManager surfaceManager = SurfaceManager.getManager(paramVolatileImage);
/* 346 */       SurfaceData surfaceData = surfaceManager.getPrimarySurfaceData();
/*     */       
/* 348 */       if (surfaceData instanceof WGLSurfaceData.WGLVSyncOffScreenSurfaceData) {
/* 349 */         WGLSurfaceData.WGLVSyncOffScreenSurfaceData wGLVSyncOffScreenSurfaceData = (WGLSurfaceData.WGLVSyncOffScreenSurfaceData)surfaceData;
/*     */         
/* 351 */         SurfaceData surfaceData1 = wGLVSyncOffScreenSurfaceData.getFlipSurface();
/* 352 */         SunGraphics2D sunGraphics2D = new SunGraphics2D(surfaceData1, Color.black, Color.white, null);
/*     */         
/*     */         try {
/* 355 */           sunGraphics2D.drawImage(paramVolatileImage, 0, 0, (ImageObserver)null);
/*     */         } finally {
/* 357 */           sunGraphics2D.dispose();
/*     */         } 
/*     */       } else {
/* 360 */         Graphics graphics = paramWComponentPeer.getGraphics();
/*     */         try {
/* 362 */           graphics.drawImage(paramVolatileImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt1, paramInt2, paramInt3, paramInt4, null);
/*     */         
/*     */         }
/*     */         finally {
/*     */           
/* 367 */           graphics.dispose();
/*     */         } 
/*     */         return;
/*     */       } 
/* 371 */     } else if (paramFlipContents == BufferCapabilities.FlipContents.PRIOR) {
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 376 */     OGLSurfaceData.swapBuffers(paramWComponentPeer.getData());
/*     */     
/* 378 */     if (paramFlipContents == BufferCapabilities.FlipContents.BACKGROUND) {
/* 379 */       Graphics graphics = paramVolatileImage.getGraphics();
/*     */       try {
/* 381 */         graphics.setColor(paramComponent.getBackground());
/* 382 */         graphics.fillRect(0, 0, paramVolatileImage
/* 383 */             .getWidth(), paramVolatileImage
/* 384 */             .getHeight());
/*     */       } finally {
/* 386 */         graphics.dispose();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class WGLBufferCaps extends BufferCapabilities {
/*     */     public WGLBufferCaps(boolean param1Boolean) {
/* 393 */       super(WGLGraphicsConfig.imageCaps, WGLGraphicsConfig.imageCaps, param1Boolean ? BufferCapabilities.FlipContents.UNDEFINED : null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferCapabilities getBufferCapabilities() {
/* 400 */     if (this.bufferCaps == null) {
/* 401 */       boolean bool = isCapPresent(65536);
/* 402 */       this.bufferCaps = new WGLBufferCaps(bool);
/*     */     } 
/* 404 */     return this.bufferCaps;
/*     */   }
/*     */   
/*     */   private static class WGLImageCaps extends ImageCapabilities {
/*     */     private WGLImageCaps() {
/* 409 */       super(true);
/*     */     }
/*     */     public boolean isTrueVolatile() {
/* 412 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ImageCapabilities getImageCapabilities() {
/* 418 */     return imageCaps;
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
/*     */   public VolatileImage createCompatibleVolatileImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 431 */     if (paramInt4 == 4 || paramInt4 == 1 || paramInt4 == 0 || paramInt3 == 2)
/*     */     {
/*     */       
/* 434 */       return null;
/*     */     }
/*     */     
/* 437 */     if (paramInt4 == 5) {
/* 438 */       if (!isCapPresent(12)) {
/* 439 */         return null;
/*     */       }
/* 441 */     } else if (paramInt4 == 2) {
/* 442 */       boolean bool = (paramInt3 == 1) ? true : false;
/* 443 */       if (!bool && !isCapPresent(2)) {
/* 444 */         return null;
/*     */       }
/*     */     } 
/*     */     
/* 448 */     AccelTypedVolatileImage accelTypedVolatileImage = new AccelTypedVolatileImage(this, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     
/* 450 */     Surface surface = accelTypedVolatileImage.getDestSurface();
/* 451 */     if (!(surface instanceof AccelSurface) || ((AccelSurface)surface)
/* 452 */       .getType() != paramInt4) {
/*     */       
/* 454 */       accelTypedVolatileImage.flush();
/* 455 */       accelTypedVolatileImage = null;
/*     */     } 
/*     */     
/* 458 */     return accelTypedVolatileImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContextCapabilities getContextCapabilities() {
/* 468 */     return this.oglCaps;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDeviceEventListener(AccelDeviceEventListener paramAccelDeviceEventListener) {
/* 473 */     AccelDeviceEventNotifier.addListener(paramAccelDeviceEventListener, this.screen.getScreen());
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeDeviceEventListener(AccelDeviceEventListener paramAccelDeviceEventListener) {
/* 478 */     AccelDeviceEventNotifier.removeListener(paramAccelDeviceEventListener);
/*     */   }
/*     */   
/*     */   public static native int getDefaultPixFmt(int paramInt);
/*     */   
/*     */   private static native boolean initWGL();
/*     */   
/*     */   private static native long getWGLConfigInfo(int paramInt1, int paramInt2);
/*     */   
/*     */   private static native int getOGLCapabilities(long paramLong);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\opengl\WGLGraphicsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */