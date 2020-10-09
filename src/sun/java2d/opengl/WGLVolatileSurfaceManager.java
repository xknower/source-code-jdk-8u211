/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.BufferCapabilities;
/*     */ import java.awt.Component;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.image.ColorModel;
/*     */ import sun.awt.image.SunVolatileImage;
/*     */ import sun.awt.image.VolatileSurfaceManager;
/*     */ import sun.awt.windows.WComponentPeer;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.pipe.hw.ExtendedBufferCapabilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WGLVolatileSurfaceManager
/*     */   extends VolatileSurfaceManager
/*     */ {
/*     */   private boolean accelerationEnabled;
/*     */   
/*     */   public WGLVolatileSurfaceManager(SunVolatileImage paramSunVolatileImage, Object paramObject) {
/*  49 */     super(paramSunVolatileImage, paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     int i = paramSunVolatileImage.getTransparency();
/*  60 */     WGLGraphicsConfig wGLGraphicsConfig = (WGLGraphicsConfig)paramSunVolatileImage.getGraphicsConfig();
/*  61 */     this
/*     */ 
/*     */ 
/*     */       
/*  65 */       .accelerationEnabled = (i == 1 || (i == 3 && (wGLGraphicsConfig.isCapPresent(12) || wGLGraphicsConfig.isCapPresent(2))));
/*     */   }
/*     */   
/*     */   protected boolean isAccelerationEnabled() {
/*  69 */     return this.accelerationEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SurfaceData initAcceleratedSurface() {
/*     */     SurfaceData surfaceData;
/*  78 */     Component component = this.vImg.getComponent();
/*     */     
/*  80 */     WComponentPeer wComponentPeer = (component != null) ? (WComponentPeer)component.getPeer() : null;
/*     */     
/*     */     try {
/*  83 */       boolean bool = false;
/*  84 */       boolean bool1 = false;
/*  85 */       if (this.context instanceof Boolean) {
/*  86 */         bool1 = ((Boolean)this.context).booleanValue();
/*  87 */         if (bool1) {
/*  88 */           BufferCapabilities bufferCapabilities = wComponentPeer.getBackBufferCaps();
/*  89 */           if (bufferCapabilities instanceof ExtendedBufferCapabilities) {
/*  90 */             ExtendedBufferCapabilities extendedBufferCapabilities = (ExtendedBufferCapabilities)bufferCapabilities;
/*     */             
/*  92 */             if (extendedBufferCapabilities.getVSync() == ExtendedBufferCapabilities.VSyncType.VSYNC_ON && extendedBufferCapabilities
/*  93 */               .getFlipContents() == BufferCapabilities.FlipContents.COPIED) {
/*     */               
/*  95 */               bool = true;
/*  96 */               bool1 = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 102 */       if (bool1) {
/*     */         
/* 104 */         surfaceData = WGLSurfaceData.createData(wComponentPeer, this.vImg, 4);
/*     */       } else {
/*     */         
/* 107 */         WGLGraphicsConfig wGLGraphicsConfig = (WGLGraphicsConfig)this.vImg.getGraphicsConfig();
/* 108 */         ColorModel colorModel = wGLGraphicsConfig.getColorModel(this.vImg.getTransparency());
/* 109 */         int i = this.vImg.getForcedAccelSurfaceType();
/*     */ 
/*     */         
/* 112 */         if (i == 0) {
/* 113 */           i = wGLGraphicsConfig.isCapPresent(12) ? 5 : 2;
/*     */         }
/*     */         
/* 116 */         if (bool) {
/* 117 */           surfaceData = WGLSurfaceData.createData(wComponentPeer, this.vImg, i);
/*     */         } else {
/* 119 */           surfaceData = WGLSurfaceData.createData(wGLGraphicsConfig, this.vImg
/* 120 */               .getWidth(), this.vImg
/* 121 */               .getHeight(), colorModel, this.vImg, i);
/*     */         }
/*     */       
/*     */       } 
/* 125 */     } catch (NullPointerException nullPointerException) {
/* 126 */       surfaceData = null;
/* 127 */     } catch (OutOfMemoryError outOfMemoryError) {
/* 128 */       surfaceData = null;
/*     */     } 
/*     */     
/* 131 */     return surfaceData;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isConfigValid(GraphicsConfiguration paramGraphicsConfiguration) {
/* 136 */     return (paramGraphicsConfiguration == null || (paramGraphicsConfiguration instanceof WGLGraphicsConfig && paramGraphicsConfiguration == this.vImg
/*     */       
/* 138 */       .getGraphicsConfig()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void initContents() {
/* 143 */     if (this.vImg.getForcedAccelSurfaceType() != 3)
/* 144 */       super.initContents(); 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\opengl\WGLVolatileSurfaceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */