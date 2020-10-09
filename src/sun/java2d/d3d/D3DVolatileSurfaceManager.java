/*     */ package sun.java2d.d3d;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.ColorModel;
/*     */ import sun.awt.Win32GraphicsConfig;
/*     */ import sun.awt.image.SunVolatileImage;
/*     */ import sun.awt.image.SurfaceManager;
/*     */ import sun.awt.image.VolatileSurfaceManager;
/*     */ import sun.awt.windows.WComponentPeer;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.SurfaceData;
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
/*     */ public class D3DVolatileSurfaceManager
/*     */   extends VolatileSurfaceManager
/*     */ {
/*     */   private boolean accelerationEnabled;
/*     */   private int restoreCountdown;
/*     */   
/*     */   public D3DVolatileSurfaceManager(SunVolatileImage paramSunVolatileImage, Object paramObject) {
/*  51 */     super(paramSunVolatileImage, paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     int i = paramSunVolatileImage.getTransparency();
/*     */     
/*  63 */     D3DGraphicsDevice d3DGraphicsDevice = (D3DGraphicsDevice)paramSunVolatileImage.getGraphicsConfig().getDevice();
/*  64 */     this
/*     */ 
/*     */ 
/*     */       
/*  68 */       .accelerationEnabled = (i == 1 || (i == 3 && (d3DGraphicsDevice.isCapPresent(2) || d3DGraphicsDevice.isCapPresent(4))));
/*     */   }
/*     */   
/*     */   protected boolean isAccelerationEnabled() {
/*  72 */     return this.accelerationEnabled;
/*     */   }
/*     */   public void setAccelerationEnabled(boolean paramBoolean) {
/*  75 */     this.accelerationEnabled = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SurfaceData initAcceleratedSurface() {
/*     */     SurfaceData surfaceData;
/*  84 */     Component component = this.vImg.getComponent();
/*     */     
/*  86 */     WComponentPeer wComponentPeer = (component != null) ? (WComponentPeer)component.getPeer() : null;
/*     */     
/*     */     try {
/*  89 */       boolean bool = false;
/*  90 */       if (this.context instanceof Boolean) {
/*  91 */         bool = ((Boolean)this.context).booleanValue();
/*     */       }
/*     */       
/*  94 */       if (bool) {
/*     */         
/*  96 */         surfaceData = D3DSurfaceData.createData(wComponentPeer, this.vImg);
/*     */       } else {
/*     */         
/*  99 */         D3DGraphicsConfig d3DGraphicsConfig = (D3DGraphicsConfig)this.vImg.getGraphicsConfig();
/* 100 */         ColorModel colorModel = d3DGraphicsConfig.getColorModel(this.vImg.getTransparency());
/* 101 */         int i = this.vImg.getForcedAccelSurfaceType();
/*     */ 
/*     */         
/* 104 */         if (i == 0) {
/* 105 */           i = 5;
/*     */         }
/* 107 */         surfaceData = D3DSurfaceData.createData(d3DGraphicsConfig, this.vImg
/* 108 */             .getWidth(), this.vImg
/* 109 */             .getHeight(), colorModel, this.vImg, i);
/*     */       }
/*     */     
/*     */     }
/* 113 */     catch (NullPointerException nullPointerException) {
/* 114 */       surfaceData = null;
/* 115 */     } catch (OutOfMemoryError outOfMemoryError) {
/* 116 */       surfaceData = null;
/* 117 */     } catch (InvalidPipeException invalidPipeException) {
/* 118 */       surfaceData = null;
/*     */     } 
/*     */     
/* 121 */     return surfaceData;
/*     */   }
/*     */   
/*     */   protected boolean isConfigValid(GraphicsConfiguration paramGraphicsConfiguration) {
/* 125 */     return (paramGraphicsConfiguration == null || paramGraphicsConfiguration == this.vImg.getGraphicsConfig());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void setRestoreCountdown(int paramInt) {
/* 136 */     this.restoreCountdown = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void restoreAcceleratedSurface() {
/* 145 */     synchronized (this) {
/* 146 */       if (this.restoreCountdown > 0) {
/* 147 */         this.restoreCountdown--;
/* 148 */         throw new InvalidPipeException("Will attempt to restore surface  in " + this.restoreCountdown);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 154 */     SurfaceData surfaceData = initAcceleratedSurface();
/* 155 */     if (surfaceData != null) {
/* 156 */       this.sdAccel = surfaceData;
/*     */     } else {
/* 158 */       throw new InvalidPipeException("could not restore surface");
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
/*     */   public SurfaceData restoreContents() {
/* 170 */     acceleratedSurfaceLost();
/* 171 */     return super.restoreContents();
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
/*     */   static void handleVItoScreenOp(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2) {
/* 194 */     if (paramSurfaceData1 instanceof D3DSurfaceData && paramSurfaceData2 instanceof GDIWindowSurfaceData) {
/*     */ 
/*     */       
/* 197 */       D3DSurfaceData d3DSurfaceData = (D3DSurfaceData)paramSurfaceData1;
/*     */       
/* 199 */       SurfaceManager surfaceManager = SurfaceManager.getManager((Image)d3DSurfaceData.getDestination());
/* 200 */       if (surfaceManager instanceof D3DVolatileSurfaceManager) {
/* 201 */         D3DVolatileSurfaceManager d3DVolatileSurfaceManager = (D3DVolatileSurfaceManager)surfaceManager;
/* 202 */         if (d3DVolatileSurfaceManager != null) {
/* 203 */           d3DSurfaceData.setSurfaceLost(true);
/*     */           
/* 205 */           GDIWindowSurfaceData gDIWindowSurfaceData = (GDIWindowSurfaceData)paramSurfaceData2;
/* 206 */           WComponentPeer wComponentPeer = gDIWindowSurfaceData.getPeer();
/* 207 */           if (D3DScreenUpdateManager.canUseD3DOnScreen(wComponentPeer, (Win32GraphicsConfig)wComponentPeer
/* 208 */               .getGraphicsConfiguration(), wComponentPeer
/* 209 */               .getBackBuffersNum())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 215 */             d3DVolatileSurfaceManager.setRestoreCountdown(10);
/*     */           } else {
/* 217 */             d3DVolatileSurfaceManager.setAccelerationEnabled(false);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initContents() {
/* 226 */     if (this.vImg.getForcedAccelSurfaceType() != 3)
/* 227 */       super.initContents(); 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\d3d\D3DVolatileSurfaceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */