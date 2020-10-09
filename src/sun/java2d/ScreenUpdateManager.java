/*     */ package sun.java2d;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import sun.awt.Win32GraphicsConfig;
/*     */ import sun.awt.windows.WComponentPeer;
/*     */ import sun.java2d.d3d.D3DScreenUpdateManager;
/*     */ import sun.java2d.windows.WindowsFlags;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScreenUpdateManager
/*     */ {
/*     */   private static ScreenUpdateManager theInstance;
/*     */   
/*     */   public synchronized Graphics2D createGraphics(SurfaceData paramSurfaceData, WComponentPeer paramWComponentPeer, Color paramColor1, Color paramColor2, Font paramFont) {
/*  63 */     return new SunGraphics2D(paramSurfaceData, paramColor1, paramColor2, paramFont);
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
/*     */   public SurfaceData createScreenSurface(Win32GraphicsConfig paramWin32GraphicsConfig, WComponentPeer paramWComponentPeer, int paramInt, boolean paramBoolean) {
/*  86 */     return paramWin32GraphicsConfig.createSurfaceData(paramWComponentPeer, paramInt);
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
/*     */   public void dropScreenSurface(SurfaceData paramSurfaceData) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SurfaceData getReplacementScreenSurface(WComponentPeer paramWComponentPeer, SurfaceData paramSurfaceData) {
/* 113 */     SurfaceData surfaceData = paramWComponentPeer.getSurfaceData();
/* 114 */     if (surfaceData == null || surfaceData.isValid()) {
/* 115 */       return surfaceData;
/*     */     }
/* 117 */     paramWComponentPeer.replaceSurfaceData();
/* 118 */     return paramWComponentPeer.getSurfaceData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized ScreenUpdateManager getInstance() {
/* 127 */     if (theInstance == null) {
/* 128 */       if (WindowsFlags.isD3DEnabled()) {
/* 129 */         theInstance = new D3DScreenUpdateManager();
/*     */       } else {
/* 131 */         theInstance = new ScreenUpdateManager();
/*     */       } 
/*     */     }
/* 134 */     return theInstance;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\ScreenUpdateManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */