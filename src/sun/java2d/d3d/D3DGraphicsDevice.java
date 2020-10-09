/*     */ package sun.java2d.d3d;
/*     */ 
/*     */ import java.awt.DisplayMode;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.event.WindowListener;
/*     */ import java.awt.peer.WindowPeer;
/*     */ import java.util.ArrayList;
/*     */ import sun.awt.Win32GraphicsDevice;
/*     */ import sun.awt.windows.WWindowPeer;
/*     */ import sun.java2d.pipe.hw.ContextCapabilities;
/*     */ import sun.java2d.windows.WindowsFlags;
/*     */ import sun.misc.PerfCounter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class D3DGraphicsDevice
/*     */   extends Win32GraphicsDevice
/*     */ {
/*     */   private D3DContext context;
/*     */   
/*     */   static {
/*  65 */     Toolkit.getDefaultToolkit();
/*  66 */   } private static boolean d3dAvailable = initD3D(); static {
/*  67 */     if (d3dAvailable) {
/*     */       
/*  69 */       pfDisabled = true;
/*  70 */       PerfCounter.getD3DAvailable().set(1L);
/*     */     } else {
/*  72 */       PerfCounter.getD3DAvailable().set(0L);
/*     */     } 
/*     */   }
/*     */   private ContextCapabilities d3dCaps; private boolean fsStatus;
/*     */   private Rectangle ownerOrigBounds;
/*     */   private boolean ownerWasVisible;
/*     */   private Window realFSWindow;
/*     */   private WindowListener fsWindowListener;
/*     */   private boolean fsWindowWasAlwaysOnTop;
/*     */   
/*     */   public static D3DGraphicsDevice createDevice(int paramInt) {
/*  83 */     if (!d3dAvailable) {
/*  84 */       return null;
/*     */     }
/*     */     
/*  87 */     ContextCapabilities contextCapabilities = getDeviceCaps(paramInt);
/*     */     
/*  89 */     if ((contextCapabilities.getCaps() & 0x40000) == 0) {
/*  90 */       if (WindowsFlags.isD3DVerbose()) {
/*  91 */         System.out.println("Could not enable Direct3D pipeline on screen " + paramInt);
/*     */       }
/*     */       
/*  94 */       return null;
/*     */     } 
/*  96 */     if (WindowsFlags.isD3DVerbose()) {
/*  97 */       System.out.println("Direct3D pipeline enabled on screen " + paramInt);
/*     */     }
/*     */     
/* 100 */     return new D3DGraphicsDevice(paramInt, contextCapabilities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ContextCapabilities getDeviceCaps(final int screen) {
/* 107 */     D3DContext.D3DContextCaps d3DContextCaps = null;
/* 108 */     D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/* 109 */     d3DRenderQueue.lock();
/*     */     class Result {
/*     */       int caps;
/*     */       String id;
/*     */     };
/*     */     try {
/* 115 */       final Result res = new Result();
/* 116 */       d3DRenderQueue.flushAndInvokeNow(new Runnable() {
/*     */             public void run() {
/* 118 */               res.caps = D3DGraphicsDevice.getDeviceCapsNative(screen);
/* 119 */               res.id = D3DGraphicsDevice.getDeviceIdNative(screen);
/*     */             }
/*     */           });
/* 122 */       d3DContextCaps = new D3DContext.D3DContextCaps(result.caps, result.id);
/*     */     } finally {
/* 124 */       d3DRenderQueue.unlock();
/*     */     } 
/*     */     
/* 127 */     return (d3DContextCaps != null) ? d3DContextCaps : new D3DContext.D3DContextCaps(0, null);
/*     */   }
/*     */   
/*     */   public final boolean isCapPresent(int paramInt) {
/* 131 */     return ((this.d3dCaps.getCaps() & paramInt) != 0);
/*     */   }
/*     */   
/*     */   private D3DGraphicsDevice(int paramInt, ContextCapabilities paramContextCapabilities) {
/* 135 */     super(paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     this.ownerOrigBounds = null;
/*     */     this.descString = "D3DGraphicsDevice[screen=" + paramInt;
/*     */     this.d3dCaps = paramContextCapabilities;
/*     */     this.context = new D3DContext(D3DRenderQueue.getInstance(), this);
/*     */   }
/*     */   
/*     */   public boolean isD3DEnabledOnDevice() {
/*     */     return (isValid() && isCapPresent(262144));
/*     */   }
/*     */   
/*     */   protected void enterFullScreenExclusive(final int screen, WindowPeer paramWindowPeer) {
/* 182 */     final WWindowPeer wpeer = (WWindowPeer)this.realFSWindow.getPeer();
/*     */     
/* 184 */     D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/* 185 */     d3DRenderQueue.lock();
/*     */     try {
/* 187 */       d3DRenderQueue.flushAndInvokeNow(new Runnable() {
/*     */             public void run() {
/* 189 */               long l = wpeer.getHWnd();
/* 190 */               if (l == 0L) {
/*     */                 
/* 192 */                 D3DGraphicsDevice.this.fsStatus = false;
/*     */                 return;
/*     */               } 
/* 195 */               D3DGraphicsDevice.this.fsStatus = D3DGraphicsDevice.enterFullScreenExclusiveNative(screen, l);
/*     */             }
/*     */           });
/*     */     } finally {
/* 199 */       d3DRenderQueue.unlock();
/*     */     } 
/* 201 */     if (!this.fsStatus)
/* 202 */       super.enterFullScreenExclusive(screen, paramWindowPeer); 
/*     */   }
/*     */   public static boolean isD3DAvailable() {
/*     */     return d3dAvailable;
/*     */   }
/*     */   
/*     */   protected void exitFullScreenExclusive(final int screen, WindowPeer paramWindowPeer) {
/* 209 */     if (this.fsStatus) {
/* 210 */       D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/* 211 */       d3DRenderQueue.lock();
/*     */       try {
/* 213 */         d3DRenderQueue.flushAndInvokeNow(new Runnable() {
/*     */               public void run() {
/* 215 */                 D3DGraphicsDevice.exitFullScreenExclusiveNative(screen);
/*     */               }
/*     */             });
/*     */       } finally {
/* 219 */         d3DRenderQueue.unlock();
/*     */       } 
/*     */     } else {
/* 222 */       super.exitFullScreenExclusive(screen, paramWindowPeer);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Frame getToplevelOwner(Window paramWindow) {
/*     */     Window window = paramWindow;
/*     */     while (window != null) {
/*     */       window = window.getOwner();
/*     */       if (window instanceof Frame)
/*     */         return (Frame)window; 
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   private static class D3DFSWindowAdapter extends WindowAdapter { private D3DFSWindowAdapter() {}
/*     */     
/*     */     public void windowDeactivated(WindowEvent param1WindowEvent) {
/* 239 */       D3DRenderQueue.getInstance(); D3DRenderQueue.restoreDevices();
/*     */     }
/*     */     
/*     */     public void windowActivated(WindowEvent param1WindowEvent) {
/* 243 */       D3DRenderQueue.getInstance(); D3DRenderQueue.restoreDevices();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addFSWindowListener(Window paramWindow) {
/* 251 */     if (!(paramWindow instanceof Frame) && !(paramWindow instanceof java.awt.Dialog) && (this
/* 252 */       .realFSWindow = getToplevelOwner(paramWindow)) != null) {
/*     */       
/* 254 */       this.ownerOrigBounds = this.realFSWindow.getBounds();
/* 255 */       WWindowPeer wWindowPeer = (WWindowPeer)this.realFSWindow.getPeer();
/*     */       
/* 257 */       this.ownerWasVisible = this.realFSWindow.isVisible();
/* 258 */       Rectangle rectangle = paramWindow.getBounds();
/*     */ 
/*     */       
/* 261 */       wWindowPeer.reshape(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/* 262 */       wWindowPeer.setVisible(true);
/*     */     } else {
/* 264 */       this.realFSWindow = paramWindow;
/*     */     } 
/*     */     
/* 267 */     this.fsWindowWasAlwaysOnTop = this.realFSWindow.isAlwaysOnTop();
/* 268 */     ((WWindowPeer)this.realFSWindow.getPeer()).setAlwaysOnTop(true);
/*     */     
/* 270 */     this.fsWindowListener = new D3DFSWindowAdapter();
/* 271 */     this.realFSWindow.addWindowListener(this.fsWindowListener);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void removeFSWindowListener(Window paramWindow) {
/* 276 */     this.realFSWindow.removeWindowListener(this.fsWindowListener);
/* 277 */     this.fsWindowListener = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     WWindowPeer wWindowPeer = (WWindowPeer)this.realFSWindow.getPeer();
/* 290 */     if (wWindowPeer != null) {
/* 291 */       if (this.ownerOrigBounds != null) {
/*     */ 
/*     */         
/* 294 */         if (this.ownerOrigBounds.width == 0) this.ownerOrigBounds.width = 1; 
/* 295 */         if (this.ownerOrigBounds.height == 0) this.ownerOrigBounds.height = 1; 
/* 296 */         wWindowPeer.reshape(this.ownerOrigBounds.x, this.ownerOrigBounds.y, this.ownerOrigBounds.width, this.ownerOrigBounds.height);
/*     */         
/* 298 */         if (!this.ownerWasVisible) {
/* 299 */           wWindowPeer.setVisible(false);
/*     */         }
/* 301 */         this.ownerOrigBounds = null;
/*     */       } 
/* 303 */       if (!this.fsWindowWasAlwaysOnTop) {
/* 304 */         wWindowPeer.setAlwaysOnTop(false);
/*     */       }
/*     */     } 
/*     */     
/* 308 */     this.realFSWindow = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected DisplayMode getCurrentDisplayMode(final int screen) {
/* 314 */     D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/* 315 */     d3DRenderQueue.lock();
/*     */     class Result { DisplayMode dm;
/*     */       
/* 318 */       Result() { this.dm = null; } };
/*     */     try {
/* 320 */       final Result res = new Result();
/* 321 */       d3DRenderQueue.flushAndInvokeNow(new Runnable() {
/*     */             public void run() {
/* 323 */               res.dm = D3DGraphicsDevice.getCurrentDisplayModeNative(screen);
/*     */             }
/*     */           });
/* 326 */       if (result.dm == null) {
/* 327 */         return super.getCurrentDisplayMode(screen);
/*     */       }
/* 329 */       return result.dm;
/*     */     } finally {
/* 331 */       d3DRenderQueue.unlock();
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
/*     */   protected void configDisplayMode(final int screen, WindowPeer paramWindowPeer, final int width, final int height, final int bitDepth, final int refreshRate) {
/* 344 */     if (!this.fsStatus) {
/* 345 */       super.configDisplayMode(screen, paramWindowPeer, width, height, bitDepth, refreshRate);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 350 */     final WWindowPeer wpeer = (WWindowPeer)this.realFSWindow.getPeer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 357 */     if (getFullScreenWindow() != this.realFSWindow) {
/* 358 */       Rectangle rectangle = getDefaultConfiguration().getBounds();
/* 359 */       wWindowPeer.reshape(rectangle.x, rectangle.y, width, height);
/*     */     } 
/*     */     
/* 362 */     D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/* 363 */     d3DRenderQueue.lock();
/*     */     try {
/* 365 */       d3DRenderQueue.flushAndInvokeNow(new Runnable() {
/*     */             public void run() {
/* 367 */               long l = wpeer.getHWnd();
/* 368 */               if (l == 0L) {
/*     */                 return;
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 374 */               D3DGraphicsDevice.configDisplayModeNative(screen, l, width, height, bitDepth, refreshRate);
/*     */             }
/*     */           });
/*     */     } finally {
/*     */       
/* 379 */       d3DRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void enumDisplayModes(final int screen, final ArrayList<DisplayMode> modes) {
/* 387 */     D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/* 388 */     d3DRenderQueue.lock();
/*     */     try {
/* 390 */       d3DRenderQueue.flushAndInvokeNow(new Runnable() {
/*     */             public void run() {
/* 392 */               D3DGraphicsDevice.enumDisplayModesNative(screen, modes);
/*     */             }
/*     */           });
/* 395 */       if (modes.size() == 0) {
/* 396 */         modes.add(getCurrentDisplayModeNative(screen));
/*     */       }
/*     */     } finally {
/* 399 */       d3DRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAvailableAcceleratedMemory() {
/* 406 */     D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/* 407 */     d3DRenderQueue.lock();
/*     */     class Result { long mem;
/*     */       
/* 410 */       Result() { this.mem = 0L; } };
/*     */     try {
/* 412 */       final Result res = new Result();
/* 413 */       d3DRenderQueue.flushAndInvokeNow(new Runnable() {
/*     */             public void run() {
/* 415 */               res.mem = D3DGraphicsDevice.getAvailableAcceleratedMemoryNative(D3DGraphicsDevice.this.getScreen());
/*     */             }
/*     */           });
/* 418 */       return (int)result.mem;
/*     */     } finally {
/* 420 */       d3DRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public GraphicsConfiguration[] getConfigurations() {
/* 426 */     if (this.configs == null && 
/* 427 */       isD3DEnabledOnDevice()) {
/* 428 */       this.defaultConfig = getDefaultConfiguration();
/* 429 */       if (this.defaultConfig != null) {
/* 430 */         this.configs = new GraphicsConfiguration[1];
/* 431 */         this.configs[0] = this.defaultConfig;
/* 432 */         return (GraphicsConfiguration[])this.configs.clone();
/*     */       } 
/*     */     } 
/*     */     
/* 436 */     return super.getConfigurations();
/*     */   }
/*     */ 
/*     */   
/*     */   public GraphicsConfiguration getDefaultConfiguration() {
/* 441 */     if (this.defaultConfig == null) {
/* 442 */       if (isD3DEnabledOnDevice()) {
/* 443 */         this.defaultConfig = new D3DGraphicsConfig(this);
/*     */       } else {
/* 445 */         this.defaultConfig = super.getDefaultConfiguration();
/*     */       } 
/*     */     }
/* 448 */     return this.defaultConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isD3DAvailableOnDevice(final int screen) {
/* 454 */     if (!d3dAvailable) {
/* 455 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 460 */     D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/* 461 */     d3DRenderQueue.lock();
/*     */     class Result { boolean avail;
/*     */       
/* 464 */       Result() { this.avail = false; } };
/*     */     try {
/* 466 */       final Result res = new Result();
/* 467 */       d3DRenderQueue.flushAndInvokeNow(new Runnable() {
/*     */             public void run() {
/* 469 */               res.avail = D3DGraphicsDevice.isD3DAvailableOnDeviceNative(screen);
/*     */             }
/*     */           });
/* 472 */       return result.avail;
/*     */     } finally {
/* 474 */       d3DRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   D3DContext getContext() {
/* 479 */     return this.context;
/*     */   }
/*     */   
/*     */   ContextCapabilities getContextCapabilities() {
/* 483 */     return this.d3dCaps;
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayChanged() {
/* 488 */     super.displayChanged();
/*     */ 
/*     */     
/* 491 */     if (d3dAvailable) {
/* 492 */       this.d3dCaps = getDeviceCaps(getScreen());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void invalidate(int paramInt) {
/* 498 */     super.invalidate(paramInt);
/*     */ 
/*     */     
/* 501 */     this.d3dCaps = new D3DContext.D3DContextCaps(0, null);
/*     */   }
/*     */   
/*     */   private static native boolean initD3D();
/*     */   
/*     */   private static native int getDeviceCapsNative(int paramInt);
/*     */   
/*     */   private static native String getDeviceIdNative(int paramInt);
/*     */   
/*     */   private static native boolean enterFullScreenExclusiveNative(int paramInt, long paramLong);
/*     */   
/*     */   private static native boolean exitFullScreenExclusiveNative(int paramInt);
/*     */   
/*     */   private static native DisplayMode getCurrentDisplayModeNative(int paramInt);
/*     */   
/*     */   private static native void configDisplayModeNative(int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */   
/*     */   private static native void enumDisplayModesNative(int paramInt, ArrayList paramArrayList);
/*     */   
/*     */   private static native long getAvailableAcceleratedMemoryNative(int paramInt);
/*     */   
/*     */   private static native boolean isD3DAvailableOnDeviceNative(int paramInt);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\d3d\D3DGraphicsDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */