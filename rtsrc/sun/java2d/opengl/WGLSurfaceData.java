/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Image;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.ColorModel;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.windows.WComponentPeer;
/*     */ import sun.java2d.SurfaceData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WGLSurfaceData
/*     */   extends OGLSurfaceData
/*     */ {
/*     */   protected WComponentPeer peer;
/*     */   private WGLGraphicsConfig graphicsConfig;
/*     */   
/*     */   private native void initOps(long paramLong1, WComponentPeer paramWComponentPeer, long paramLong2);
/*     */   
/*     */   protected native boolean initPbuffer(long paramLong1, long paramLong2, boolean paramBoolean, int paramInt1, int paramInt2);
/*     */   
/*     */   protected WGLSurfaceData(WComponentPeer paramWComponentPeer, WGLGraphicsConfig paramWGLGraphicsConfig, ColorModel paramColorModel, int paramInt) {
/*  53 */     super(paramWGLGraphicsConfig, paramColorModel, paramInt);
/*  54 */     this.peer = paramWComponentPeer;
/*  55 */     this.graphicsConfig = paramWGLGraphicsConfig;
/*     */     
/*  57 */     long l1 = paramWGLGraphicsConfig.getNativeConfigInfo();
/*  58 */     long l2 = (paramWComponentPeer != null) ? paramWComponentPeer.getHWnd() : 0L;
/*     */     
/*  60 */     initOps(l1, paramWComponentPeer, l2);
/*     */   }
/*     */   
/*     */   public GraphicsConfiguration getDeviceConfiguration() {
/*  64 */     return this.graphicsConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WGLWindowSurfaceData createData(WComponentPeer paramWComponentPeer) {
/*  75 */     if (!paramWComponentPeer.isAccelCapable() || 
/*  76 */       !SunToolkit.isContainingTopLevelOpaque((Component)paramWComponentPeer.getTarget()))
/*     */     {
/*  78 */       return null;
/*     */     }
/*  80 */     WGLGraphicsConfig wGLGraphicsConfig = getGC(paramWComponentPeer);
/*  81 */     return new WGLWindowSurfaceData(paramWComponentPeer, wGLGraphicsConfig);
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
/*     */   public static WGLOffScreenSurfaceData createData(WComponentPeer paramWComponentPeer, Image paramImage, int paramInt) {
/*  95 */     if (!paramWComponentPeer.isAccelCapable() || 
/*  96 */       !SunToolkit.isContainingTopLevelOpaque((Component)paramWComponentPeer.getTarget()))
/*     */     {
/*  98 */       return null;
/*     */     }
/* 100 */     WGLGraphicsConfig wGLGraphicsConfig = getGC(paramWComponentPeer);
/* 101 */     Rectangle rectangle = paramWComponentPeer.getBounds();
/* 102 */     if (paramInt == 4) {
/* 103 */       return new WGLOffScreenSurfaceData(paramWComponentPeer, wGLGraphicsConfig, rectangle.width, rectangle.height, paramImage, paramWComponentPeer
/* 104 */           .getColorModel(), paramInt);
/*     */     }
/*     */     
/* 107 */     return new WGLVSyncOffScreenSurfaceData(paramWComponentPeer, wGLGraphicsConfig, rectangle.width, rectangle.height, paramImage, paramWComponentPeer
/* 108 */         .getColorModel(), paramInt);
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
/*     */   public static WGLOffScreenSurfaceData createData(WGLGraphicsConfig paramWGLGraphicsConfig, int paramInt1, int paramInt2, ColorModel paramColorModel, Image paramImage, int paramInt3) {
/* 122 */     return new WGLOffScreenSurfaceData(null, paramWGLGraphicsConfig, paramInt1, paramInt2, paramImage, paramColorModel, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static WGLGraphicsConfig getGC(WComponentPeer paramWComponentPeer) {
/* 127 */     if (paramWComponentPeer != null) {
/* 128 */       return (WGLGraphicsConfig)paramWComponentPeer.getGraphicsConfiguration();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 133 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 134 */     GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
/* 135 */     return (WGLGraphicsConfig)graphicsDevice.getDefaultConfiguration();
/*     */   }
/*     */   
/*     */   public static native boolean updateWindowAccelImpl(long paramLong, WComponentPeer paramWComponentPeer, int paramInt1, int paramInt2);
/*     */   
/*     */   public static class WGLWindowSurfaceData
/*     */     extends WGLSurfaceData
/*     */   {
/*     */     public WGLWindowSurfaceData(WComponentPeer param1WComponentPeer, WGLGraphicsConfig param1WGLGraphicsConfig) {
/* 144 */       super(param1WComponentPeer, param1WGLGraphicsConfig, param1WComponentPeer.getColorModel(), 1);
/*     */     }
/*     */     
/*     */     public SurfaceData getReplacement() {
/* 148 */       return this.peer.getSurfaceData();
/*     */     }
/*     */     
/*     */     public Rectangle getBounds() {
/* 152 */       Rectangle rectangle = this.peer.getBounds();
/* 153 */       rectangle.x = rectangle.y = 0;
/* 154 */       return rectangle;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getDestination() {
/* 161 */       return this.peer.getTarget();
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
/*     */   public static class WGLVSyncOffScreenSurfaceData
/*     */     extends WGLOffScreenSurfaceData
/*     */   {
/*     */     private WGLSurfaceData.WGLOffScreenSurfaceData flipSurface;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WGLVSyncOffScreenSurfaceData(WComponentPeer param1WComponentPeer, WGLGraphicsConfig param1WGLGraphicsConfig, int param1Int1, int param1Int2, Image param1Image, ColorModel param1ColorModel, int param1Int3) {
/* 185 */       super(param1WComponentPeer, param1WGLGraphicsConfig, param1Int1, param1Int2, param1Image, param1ColorModel, param1Int3);
/* 186 */       this.flipSurface = WGLSurfaceData.createData(param1WComponentPeer, param1Image, 4);
/*     */     }
/*     */     
/*     */     public SurfaceData getFlipSurface() {
/* 190 */       return this.flipSurface;
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() {
/* 195 */       this.flipSurface.flush();
/* 196 */       super.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class WGLOffScreenSurfaceData
/*     */     extends WGLSurfaceData
/*     */   {
/*     */     private Image offscreenImage;
/*     */     
/*     */     private int width;
/*     */     
/*     */     private int height;
/*     */ 
/*     */     
/*     */     public WGLOffScreenSurfaceData(WComponentPeer param1WComponentPeer, WGLGraphicsConfig param1WGLGraphicsConfig, int param1Int1, int param1Int2, Image param1Image, ColorModel param1ColorModel, int param1Int3) {
/* 212 */       super(param1WComponentPeer, param1WGLGraphicsConfig, param1ColorModel, param1Int3);
/*     */       
/* 214 */       this.width = param1Int1;
/* 215 */       this.height = param1Int2;
/* 216 */       this.offscreenImage = param1Image;
/*     */       
/* 218 */       initSurface(param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     public SurfaceData getReplacement() {
/* 222 */       return restoreContents(this.offscreenImage);
/*     */     }
/*     */     
/*     */     public Rectangle getBounds() {
/* 226 */       if (this.type == 4) {
/* 227 */         Rectangle rectangle = this.peer.getBounds();
/* 228 */         rectangle.x = rectangle.y = 0;
/* 229 */         return rectangle;
/*     */       } 
/* 231 */       return new Rectangle(this.width, this.height);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getDestination() {
/* 239 */       return this.offscreenImage;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\opengl\WGLSurfaceData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */