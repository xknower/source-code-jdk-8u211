/*     */ package sun.java2d.d3d;
/*     */ 
/*     */ import java.awt.AWTException;
/*     */ import java.awt.BufferCapabilities;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.ImageCapabilities;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.VolatileImage;
/*     */ import sun.awt.Win32GraphicsConfig;
/*     */ import sun.awt.image.SunVolatileImage;
/*     */ import sun.awt.image.SurfaceManager;
/*     */ import sun.awt.windows.WComponentPeer;
/*     */ import sun.java2d.Surface;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.pipe.BufferedContext;
/*     */ import sun.java2d.pipe.hw.AccelDeviceEventListener;
/*     */ import sun.java2d.pipe.hw.AccelDeviceEventNotifier;
/*     */ import sun.java2d.pipe.hw.AccelGraphicsConfig;
/*     */ import sun.java2d.pipe.hw.AccelSurface;
/*     */ import sun.java2d.pipe.hw.AccelTypedVolatileImage;
/*     */ import sun.java2d.pipe.hw.ContextCapabilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class D3DGraphicsConfig
/*     */   extends Win32GraphicsConfig
/*     */   implements AccelGraphicsConfig
/*     */ {
/*  59 */   private static ImageCapabilities imageCaps = new D3DImageCaps();
/*     */   
/*     */   private BufferCapabilities bufferCaps;
/*     */   private D3DGraphicsDevice device;
/*     */   
/*     */   protected D3DGraphicsConfig(D3DGraphicsDevice paramD3DGraphicsDevice) {
/*  65 */     super(paramD3DGraphicsDevice, 0);
/*  66 */     this.device = paramD3DGraphicsDevice;
/*     */   }
/*     */   
/*     */   public SurfaceData createManagedSurface(int paramInt1, int paramInt2, int paramInt3) {
/*  70 */     return D3DSurfaceData.createData(this, paramInt1, paramInt2, 
/*  71 */         getColorModel(paramInt3), null, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void displayChanged() {
/*  78 */     super.displayChanged();
/*     */ 
/*     */ 
/*     */     
/*  82 */     D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/*  83 */     d3DRenderQueue.lock();
/*     */     try {
/*  85 */       D3DContext.invalidateCurrentContext();
/*     */     } finally {
/*  87 */       d3DRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ColorModel getColorModel(int paramInt) {
/*     */     ColorSpace colorSpace;
/*  93 */     switch (paramInt) {
/*     */ 
/*     */       
/*     */       case 1:
/*  97 */         return new DirectColorModel(24, 16711680, 65280, 255);
/*     */       case 2:
/*  99 */         return new DirectColorModel(25, 16711680, 65280, 255, 16777216);
/*     */       case 3:
/* 101 */         colorSpace = ColorSpace.getInstance(1000);
/* 102 */         return new DirectColorModel(colorSpace, 32, 16711680, 65280, 255, -16777216, true, 3);
/*     */     } 
/*     */ 
/*     */     
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 112 */     return "D3DGraphicsConfig[dev=" + this.screen + ",pixfmt=" + this.visual + "]";
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
/*     */   public SurfaceData createSurfaceData(WComponentPeer paramWComponentPeer, int paramInt) {
/* 132 */     return super.createSurfaceData(paramWComponentPeer, paramInt);
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
/* 150 */     if (paramInt < 2 || paramInt > 4) {
/* 151 */       throw new AWTException("Only 2-4 buffers supported");
/*     */     }
/* 153 */     if (paramBufferCapabilities.getFlipContents() == BufferCapabilities.FlipContents.COPIED && paramInt != 2)
/*     */     {
/*     */       
/* 156 */       throw new AWTException("FlipContents.COPIED is onlysupported for 2 buffers");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VolatileImage createBackBuffer(WComponentPeer paramWComponentPeer) {
/* 167 */     Component component = (Component)paramWComponentPeer.getTarget();
/*     */ 
/*     */     
/* 170 */     int i = Math.max(1, component.getWidth());
/* 171 */     int j = Math.max(1, component.getHeight());
/* 172 */     return new SunVolatileImage(component, i, j, Boolean.TRUE);
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
/*     */   public void flip(WComponentPeer paramWComponentPeer, Component paramComponent, VolatileImage paramVolatileImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, BufferCapabilities.FlipContents paramFlipContents) {
/* 187 */     SurfaceManager surfaceManager = SurfaceManager.getManager(paramVolatileImage);
/* 188 */     SurfaceData surfaceData = surfaceManager.getPrimarySurfaceData();
/* 189 */     if (surfaceData instanceof D3DSurfaceData) {
/* 190 */       D3DSurfaceData d3DSurfaceData = (D3DSurfaceData)surfaceData;
/* 191 */       D3DSurfaceData.swapBuffers(d3DSurfaceData, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } else {
/*     */       
/* 194 */       Graphics graphics = paramWComponentPeer.getGraphics();
/*     */       try {
/* 196 */         graphics.drawImage(paramVolatileImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt1, paramInt2, paramInt3, paramInt4, null);
/*     */       
/*     */       }
/*     */       finally {
/*     */         
/* 201 */         graphics.dispose();
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     if (paramFlipContents == BufferCapabilities.FlipContents.BACKGROUND) {
/* 206 */       Graphics graphics = paramVolatileImage.getGraphics();
/*     */       try {
/* 208 */         graphics.setColor(paramComponent.getBackground());
/* 209 */         graphics.fillRect(0, 0, paramVolatileImage
/* 210 */             .getWidth(), paramVolatileImage
/* 211 */             .getHeight());
/*     */       } finally {
/* 213 */         graphics.dispose();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class D3DBufferCaps
/*     */     extends BufferCapabilities
/*     */   {
/*     */     public D3DBufferCaps() {
/* 222 */       super(D3DGraphicsConfig.imageCaps, D3DGraphicsConfig.imageCaps, BufferCapabilities.FlipContents.UNDEFINED);
/*     */     }
/*     */     
/*     */     public boolean isMultiBufferAvailable() {
/* 226 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferCapabilities getBufferCapabilities() {
/* 233 */     if (this.bufferCaps == null) {
/* 234 */       this.bufferCaps = new D3DBufferCaps();
/*     */     }
/* 236 */     return this.bufferCaps;
/*     */   }
/*     */   
/*     */   private static class D3DImageCaps extends ImageCapabilities {
/*     */     private D3DImageCaps() {
/* 241 */       super(true);
/*     */     }
/*     */     
/*     */     public boolean isTrueVolatile() {
/* 245 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ImageCapabilities getImageCapabilities() {
/* 251 */     return imageCaps;
/*     */   }
/*     */   
/*     */   D3DGraphicsDevice getD3DDevice() {
/* 255 */     return this.device;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public D3DContext getContext() {
/* 265 */     return this.device.getContext();
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
/* 278 */     if (paramInt4 == 4 || paramInt4 == 1 || paramInt4 == 0 || paramInt3 == 2)
/*     */     {
/*     */       
/* 281 */       return null;
/*     */     }
/* 283 */     boolean bool = (paramInt3 == 1) ? true : false;
/* 284 */     if (paramInt4 == 5) {
/* 285 */       byte b = bool ? 8 : 4;
/* 286 */       if (!this.device.isCapPresent(b)) {
/* 287 */         return null;
/*     */       }
/* 289 */     } else if (paramInt4 == 2 && 
/* 290 */       !bool && !this.device.isCapPresent(2)) {
/* 291 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 295 */     AccelTypedVolatileImage accelTypedVolatileImage = new AccelTypedVolatileImage(this, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     
/* 297 */     Surface surface = accelTypedVolatileImage.getDestSurface();
/* 298 */     if (!(surface instanceof AccelSurface) || ((AccelSurface)surface)
/* 299 */       .getType() != paramInt4) {
/*     */       
/* 301 */       accelTypedVolatileImage.flush();
/* 302 */       accelTypedVolatileImage = null;
/*     */     } 
/*     */     
/* 305 */     return accelTypedVolatileImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContextCapabilities getContextCapabilities() {
/* 315 */     return this.device.getContextCapabilities();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDeviceEventListener(AccelDeviceEventListener paramAccelDeviceEventListener) {
/* 320 */     AccelDeviceEventNotifier.addListener(paramAccelDeviceEventListener, this.device.getScreen());
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeDeviceEventListener(AccelDeviceEventListener paramAccelDeviceEventListener) {
/* 325 */     AccelDeviceEventNotifier.removeListener(paramAccelDeviceEventListener);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\d3d\D3DGraphicsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */