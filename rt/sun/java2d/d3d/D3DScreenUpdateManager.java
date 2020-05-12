/*     */ package sun.java2d.d3d;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Window;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.Win32GraphicsConfig;
/*     */ import sun.awt.windows.WComponentPeer;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.ScreenUpdateManager;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.windows.GDIWindowSurfaceData;
/*     */ import sun.java2d.windows.WindowsFlags;
/*     */ import sun.misc.ThreadGroupUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class D3DScreenUpdateManager
/*     */   extends ScreenUpdateManager
/*     */   implements Runnable
/*     */ {
/*     */   private static final int MIN_WIN_SIZE = 150;
/*     */   private volatile boolean done;
/*     */   private volatile Thread screenUpdater;
/*     */   private boolean needsUpdateNow;
/*  78 */   private Object runLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<D3DSurfaceData.D3DWindowSurfaceData> d3dwSurfaces;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HashMap<D3DSurfaceData.D3DWindowSurfaceData, GDIWindowSurfaceData> gdiSurfaces;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public D3DScreenUpdateManager() {
/*  94 */     this.done = false;
/*  95 */     AccessController.doPrivileged(() -> {
/*     */           ThreadGroup threadGroup = ThreadGroupUtils.getRootThreadGroup();
/*     */ 
/*     */           
/*     */           Thread thread = new Thread(threadGroup, ());
/*     */           
/*     */           thread.setContextClassLoader(null);
/*     */           
/*     */           try {
/*     */             Runtime.getRuntime().addShutdownHook(thread);
/* 105 */           } catch (Exception exception) {
/*     */             this.done = true;
/*     */           } 
/*     */           return null;
/*     */         });
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
/*     */   public SurfaceData createScreenSurface(Win32GraphicsConfig paramWin32GraphicsConfig, WComponentPeer paramWComponentPeer, int paramInt, boolean paramBoolean) {
/*     */     GDIWindowSurfaceData gDIWindowSurfaceData;
/* 151 */     if (this.done || !(paramWin32GraphicsConfig instanceof D3DGraphicsConfig)) {
/* 152 */       return super.createScreenSurface(paramWin32GraphicsConfig, paramWComponentPeer, paramInt, paramBoolean);
/*     */     }
/*     */     
/* 155 */     D3DSurfaceData d3DSurfaceData = null;
/*     */     
/* 157 */     if (canUseD3DOnScreen(paramWComponentPeer, paramWin32GraphicsConfig, paramInt)) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */         
/* 163 */         d3DSurfaceData = D3DSurfaceData.createData(paramWComponentPeer);
/* 164 */       } catch (InvalidPipeException invalidPipeException) {
/* 165 */         d3DSurfaceData = null;
/*     */       } 
/*     */     }
/* 168 */     if (d3DSurfaceData == null) {
/* 169 */       gDIWindowSurfaceData = GDIWindowSurfaceData.createData(paramWComponentPeer);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     if (paramBoolean)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 183 */       repaintPeerTarget(paramWComponentPeer);
/*     */     }
/*     */     
/* 186 */     return gDIWindowSurfaceData;
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
/*     */   public static boolean canUseD3DOnScreen(WComponentPeer paramWComponentPeer, Win32GraphicsConfig paramWin32GraphicsConfig, int paramInt) {
/* 210 */     if (!(paramWin32GraphicsConfig instanceof D3DGraphicsConfig)) {
/* 211 */       return false;
/*     */     }
/* 213 */     D3DGraphicsConfig d3DGraphicsConfig = (D3DGraphicsConfig)paramWin32GraphicsConfig;
/* 214 */     D3DGraphicsDevice d3DGraphicsDevice = d3DGraphicsConfig.getD3DDevice();
/* 215 */     String str = paramWComponentPeer.getClass().getName();
/* 216 */     Rectangle rectangle = paramWComponentPeer.getBounds();
/* 217 */     Component component = (Component)paramWComponentPeer.getTarget();
/* 218 */     Window window = d3DGraphicsDevice.getFullScreenWindow();
/*     */     
/* 220 */     return 
/* 221 */       (WindowsFlags.isD3DOnScreenEnabled() && d3DGraphicsDevice
/* 222 */       .isD3DEnabledOnDevice() && paramWComponentPeer
/* 223 */       .isAccelCapable() && (rectangle.width > 150 || rectangle.height > 150) && paramInt == 0 && (window == null || (window == component && 
/*     */ 
/*     */       
/* 226 */       !hasHWChildren(component))) && (str
/* 227 */       .equals("sun.awt.windows.WCanvasPeer") || str
/* 228 */       .equals("sun.awt.windows.WDialogPeer") || str
/* 229 */       .equals("sun.awt.windows.WPanelPeer") || str
/* 230 */       .equals("sun.awt.windows.WWindowPeer") || str
/* 231 */       .equals("sun.awt.windows.WFramePeer") || str
/* 232 */       .equals("sun.awt.windows.WEmbeddedFramePeer")));
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
/*     */   public Graphics2D createGraphics(SurfaceData paramSurfaceData, WComponentPeer paramWComponentPeer, Color paramColor1, Color paramColor2, Font paramFont) {
/* 257 */     if (!this.done && paramSurfaceData instanceof D3DSurfaceData.D3DWindowSurfaceData) {
/* 258 */       D3DSurfaceData.D3DWindowSurfaceData d3DWindowSurfaceData = (D3DSurfaceData.D3DWindowSurfaceData)paramSurfaceData;
/* 259 */       if (!d3DWindowSurfaceData.isSurfaceLost() || validate(d3DWindowSurfaceData)) {
/* 260 */         trackScreenSurface(d3DWindowSurfaceData);
/* 261 */         return new SunGraphics2D(paramSurfaceData, paramColor1, paramColor2, paramFont);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 267 */       paramSurfaceData = getGdiSurface(d3DWindowSurfaceData);
/*     */     } 
/* 269 */     return super.createGraphics(paramSurfaceData, paramWComponentPeer, paramColor1, paramColor2, paramFont);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void repaintPeerTarget(WComponentPeer paramWComponentPeer) {
/* 277 */     Component component = (Component)paramWComponentPeer.getTarget();
/* 278 */     Rectangle rectangle = AWTAccessor.getComponentAccessor().getBounds(component);
/*     */ 
/*     */ 
/*     */     
/* 282 */     paramWComponentPeer.handlePaint(0, 0, rectangle.width, rectangle.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void trackScreenSurface(SurfaceData paramSurfaceData) {
/* 291 */     if (!this.done && paramSurfaceData instanceof D3DSurfaceData.D3DWindowSurfaceData) {
/* 292 */       synchronized (this) {
/* 293 */         if (this.d3dwSurfaces == null) {
/* 294 */           this.d3dwSurfaces = new ArrayList<>();
/*     */         }
/* 296 */         D3DSurfaceData.D3DWindowSurfaceData d3DWindowSurfaceData = (D3DSurfaceData.D3DWindowSurfaceData)paramSurfaceData;
/* 297 */         if (!this.d3dwSurfaces.contains(d3DWindowSurfaceData)) {
/* 298 */           this.d3dwSurfaces.add(d3DWindowSurfaceData);
/*     */         }
/*     */       } 
/* 301 */       startUpdateThread();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void dropScreenSurface(SurfaceData paramSurfaceData) {
/* 307 */     if (this.d3dwSurfaces != null && paramSurfaceData instanceof D3DSurfaceData.D3DWindowSurfaceData) {
/* 308 */       D3DSurfaceData.D3DWindowSurfaceData d3DWindowSurfaceData = (D3DSurfaceData.D3DWindowSurfaceData)paramSurfaceData;
/* 309 */       removeGdiSurface(d3DWindowSurfaceData);
/* 310 */       this.d3dwSurfaces.remove(d3DWindowSurfaceData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SurfaceData getReplacementScreenSurface(WComponentPeer paramWComponentPeer, SurfaceData paramSurfaceData) {
/* 318 */     SurfaceData surfaceData = super.getReplacementScreenSurface(paramWComponentPeer, paramSurfaceData);
/*     */ 
/*     */ 
/*     */     
/* 322 */     trackScreenSurface(surfaceData);
/* 323 */     return surfaceData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeGdiSurface(D3DSurfaceData.D3DWindowSurfaceData paramD3DWindowSurfaceData) {
/* 333 */     if (this.gdiSurfaces != null) {
/* 334 */       GDIWindowSurfaceData gDIWindowSurfaceData = this.gdiSurfaces.get(paramD3DWindowSurfaceData);
/* 335 */       if (gDIWindowSurfaceData != null) {
/* 336 */         gDIWindowSurfaceData.invalidate();
/* 337 */         this.gdiSurfaces.remove(paramD3DWindowSurfaceData);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void startUpdateThread() {
/* 347 */     if (this.screenUpdater == null) {
/* 348 */       this.screenUpdater = AccessController.<Thread>doPrivileged(() -> {
/*     */             ThreadGroup threadGroup = ThreadGroupUtils.getRootThreadGroup();
/*     */             
/*     */             Thread thread = new Thread(threadGroup, this, "D3D Screen Updater");
/*     */             
/*     */             thread.setPriority(7);
/*     */             
/*     */             thread.setDaemon(true);
/*     */             
/*     */             return thread;
/*     */           });
/* 359 */       this.screenUpdater.start();
/*     */     } else {
/* 361 */       wakeUpUpdateThread();
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
/*     */   public void wakeUpUpdateThread() {
/* 377 */     synchronized (this.runLock) {
/* 378 */       this.runLock.notifyAll();
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
/*     */   public void runUpdateNow() {
/* 392 */     synchronized (this) {
/*     */ 
/*     */       
/* 395 */       if (this.done || this.screenUpdater == null || this.d3dwSurfaces == null || this.d3dwSurfaces
/* 396 */         .size() == 0) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 401 */     synchronized (this.runLock) {
/* 402 */       this.needsUpdateNow = true;
/* 403 */       this.runLock.notifyAll();
/* 404 */       while (this.needsUpdateNow) {
/*     */         try {
/* 406 */           this.runLock.wait();
/* 407 */         } catch (InterruptedException interruptedException) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void run() {
/* 413 */     while (!this.done) {
/* 414 */       synchronized (this.runLock) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 419 */         long l = (this.d3dwSurfaces.size() > 0) ? 100L : 0L;
/*     */ 
/*     */         
/* 422 */         if (!this.needsUpdateNow) {
/* 423 */           try { this.runLock.wait(l); }
/* 424 */           catch (InterruptedException interruptedException) {}
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 431 */       D3DSurfaceData.D3DWindowSurfaceData[] arrayOfD3DWindowSurfaceData = new D3DSurfaceData.D3DWindowSurfaceData[0];
/* 432 */       synchronized (this) {
/* 433 */         arrayOfD3DWindowSurfaceData = this.d3dwSurfaces.<D3DSurfaceData.D3DWindowSurfaceData>toArray(arrayOfD3DWindowSurfaceData);
/*     */       } 
/* 435 */       for (D3DSurfaceData.D3DWindowSurfaceData d3DWindowSurfaceData : arrayOfD3DWindowSurfaceData) {
/*     */ 
/*     */         
/* 438 */         if (d3DWindowSurfaceData.isValid() && (d3DWindowSurfaceData.isDirty() || d3DWindowSurfaceData.isSurfaceLost())) {
/* 439 */           if (!d3DWindowSurfaceData.isSurfaceLost()) {
/*     */ 
/*     */ 
/*     */             
/* 443 */             D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/* 444 */             d3DRenderQueue.lock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 453 */           else if (!validate(d3DWindowSurfaceData)) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 458 */             d3DWindowSurfaceData.getPeer().replaceSurfaceDataLater();
/*     */           } 
/*     */         }
/*     */       } 
/* 462 */       synchronized (this.runLock) {
/* 463 */         this.needsUpdateNow = false;
/* 464 */         this.runLock.notifyAll();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean validate(D3DSurfaceData.D3DWindowSurfaceData paramD3DWindowSurfaceData) {
/* 476 */     if (paramD3DWindowSurfaceData.isSurfaceLost()) {
/*     */       try {
/* 478 */         paramD3DWindowSurfaceData.restoreSurface();
/*     */ 
/*     */         
/* 481 */         Color color = paramD3DWindowSurfaceData.getPeer().getBackgroundNoSync();
/* 482 */         SunGraphics2D sunGraphics2D = new SunGraphics2D(paramD3DWindowSurfaceData, color, color, null);
/* 483 */         sunGraphics2D.fillRect(0, 0, (paramD3DWindowSurfaceData.getBounds()).width, (paramD3DWindowSurfaceData.getBounds()).height);
/* 484 */         sunGraphics2D.dispose();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 489 */         paramD3DWindowSurfaceData.markClean();
/*     */ 
/*     */         
/* 492 */         repaintPeerTarget(paramD3DWindowSurfaceData.getPeer());
/* 493 */       } catch (InvalidPipeException invalidPipeException) {
/* 494 */         return false;
/*     */       } 
/*     */     }
/* 497 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized SurfaceData getGdiSurface(D3DSurfaceData.D3DWindowSurfaceData paramD3DWindowSurfaceData) {
/* 508 */     if (this.gdiSurfaces == null) {
/* 509 */       this.gdiSurfaces = new HashMap<>();
/*     */     }
/*     */     
/* 512 */     GDIWindowSurfaceData gDIWindowSurfaceData = this.gdiSurfaces.get(paramD3DWindowSurfaceData);
/* 513 */     if (gDIWindowSurfaceData == null) {
/* 514 */       gDIWindowSurfaceData = GDIWindowSurfaceData.createData(paramD3DWindowSurfaceData.getPeer());
/* 515 */       this.gdiSurfaces.put(paramD3DWindowSurfaceData, gDIWindowSurfaceData);
/*     */     } 
/* 517 */     return gDIWindowSurfaceData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasHWChildren(Component paramComponent) {
/* 527 */     if (paramComponent instanceof Container) {
/* 528 */       for (Component component : ((Container)paramComponent).getComponents()) {
/* 529 */         if (component.getPeer() instanceof WComponentPeer || hasHWChildren(component)) {
/* 530 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 534 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\d3d\D3DScreenUpdateManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */