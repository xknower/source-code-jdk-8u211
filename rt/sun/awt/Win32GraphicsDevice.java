/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.AWTPermission;
/*     */ import java.awt.DisplayMode;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.event.WindowListener;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.peer.WindowPeer;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import sun.awt.windows.WWindowPeer;
/*     */ import sun.java2d.opengl.WGLGraphicsConfig;
/*     */ import sun.java2d.windows.WindowsFlags;
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
/*     */ public class Win32GraphicsDevice
/*     */   extends GraphicsDevice
/*     */   implements DisplayChangedListener
/*     */ {
/*     */   int screen;
/*     */   ColorModel dynamicColorModel;
/*     */   ColorModel colorModel;
/*     */   protected GraphicsConfiguration[] configs;
/*     */   protected GraphicsConfiguration defaultConfig;
/*     */   private final String idString;
/*     */   protected String descString;
/*     */   private boolean valid;
/*  72 */   private SunDisplayChanger topLevels = new SunDisplayChanger();
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean pfDisabled;
/*     */ 
/*     */ 
/*     */   
/*     */   private static AWTPermission fullScreenExclusivePermission;
/*     */ 
/*     */   
/*     */   private DisplayMode defaultDisplayMode;
/*     */ 
/*     */   
/*     */   private WindowListener fsWindowListener;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  91 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.awt.nopixfmt"));
/*     */     
/*  93 */     pfDisabled = (str != null);
/*  94 */     initIDs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Win32GraphicsDevice(int paramInt) {
/* 102 */     this.screen = paramInt;
/*     */ 
/*     */ 
/*     */     
/* 106 */     this.idString = "\\Display" + this.screen;
/*     */     
/* 108 */     this.descString = "Win32GraphicsDevice[screen=" + this.screen;
/* 109 */     this.valid = true;
/*     */     
/* 111 */     initDevice(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 121 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScreen() {
/* 128 */     return this.screen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 136 */     return this.valid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void invalidate(int paramInt) {
/* 145 */     this.valid = false;
/* 146 */     this.screen = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIDstring() {
/* 154 */     return this.idString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsConfiguration[] getConfigurations() {
/* 163 */     if (this.configs == null) {
/* 164 */       if (WindowsFlags.isOGLEnabled() && isDefaultDevice()) {
/* 165 */         this.defaultConfig = getDefaultConfiguration();
/* 166 */         if (this.defaultConfig != null) {
/* 167 */           this.configs = new GraphicsConfiguration[1];
/* 168 */           this.configs[0] = this.defaultConfig;
/* 169 */           return (GraphicsConfiguration[])this.configs.clone();
/*     */         } 
/*     */       } 
/*     */       
/* 173 */       int i = getMaxConfigs(this.screen);
/* 174 */       int j = getDefaultPixID(this.screen);
/* 175 */       Vector<GraphicsConfiguration> vector = new Vector(i);
/* 176 */       if (j == 0) {
/*     */         
/* 178 */         this.defaultConfig = Win32GraphicsConfig.getConfig(this, j);
/*     */         
/* 180 */         vector.addElement(this.defaultConfig);
/*     */       } else {
/*     */         
/* 183 */         for (byte b = 1; b <= i; b++) {
/* 184 */           if (isPixFmtSupported(b, this.screen)) {
/* 185 */             if (b == j) {
/* 186 */               this.defaultConfig = Win32GraphicsConfig.getConfig(this, b);
/*     */               
/* 188 */               vector.addElement(this.defaultConfig);
/*     */             } else {
/*     */               
/* 191 */               vector.addElement(Win32GraphicsConfig.getConfig(this, b));
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 197 */       this.configs = new GraphicsConfiguration[vector.size()];
/* 198 */       vector.copyInto((Object[])this.configs);
/*     */     } 
/* 200 */     return (GraphicsConfiguration[])this.configs.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMaxConfigs(int paramInt) {
/* 210 */     if (pfDisabled) {
/* 211 */       return 1;
/*     */     }
/* 213 */     return getMaxConfigsImpl(paramInt);
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
/*     */   protected int getDefaultPixID(int paramInt) {
/* 237 */     if (pfDisabled) {
/* 238 */       return 0;
/*     */     }
/* 240 */     return getDefaultPixIDImpl(paramInt);
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
/*     */   public GraphicsConfiguration getDefaultConfiguration() {
/* 255 */     if (this.defaultConfig == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 260 */       if (WindowsFlags.isOGLEnabled() && isDefaultDevice()) {
/* 261 */         int i = WGLGraphicsConfig.getDefaultPixFmt(this.screen);
/* 262 */         this.defaultConfig = WGLGraphicsConfig.getConfig(this, i);
/* 263 */         if (WindowsFlags.isOGLVerbose()) {
/* 264 */           if (this.defaultConfig != null) {
/* 265 */             System.out.print("OpenGL pipeline enabled");
/*     */           } else {
/* 267 */             System.out.print("Could not enable OpenGL pipeline");
/*     */           } 
/* 269 */           System.out.println(" for default config on screen " + this.screen);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 286 */       if (this.defaultConfig == null) {
/* 287 */         this.defaultConfig = Win32GraphicsConfig.getConfig(this, 0);
/*     */       }
/*     */     } 
/* 290 */     return this.defaultConfig;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 294 */     return this.valid ? (this.descString + "]") : (this.descString + ", removed]");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isDefaultDevice() {
/* 302 */     return 
/*     */       
/* 304 */       (this == GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
/*     */   }
/*     */   
/*     */   private static boolean isFSExclusiveModeAllowed() {
/* 308 */     SecurityManager securityManager = System.getSecurityManager();
/* 309 */     if (securityManager != null) {
/* 310 */       if (fullScreenExclusivePermission == null) {
/* 311 */         fullScreenExclusivePermission = new AWTPermission("fullScreenExclusive");
/*     */       }
/*     */       
/*     */       try {
/* 315 */         securityManager.checkPermission(fullScreenExclusivePermission);
/* 316 */       } catch (SecurityException securityException) {
/* 317 */         return false;
/*     */       } 
/*     */     } 
/* 320 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullScreenSupported() {
/* 328 */     return isFSExclusiveModeAllowed();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setFullScreenWindow(Window paramWindow) {
/* 333 */     Window window = getFullScreenWindow();
/* 334 */     if (paramWindow == window) {
/*     */       return;
/*     */     }
/* 337 */     if (!isFullScreenSupported()) {
/* 338 */       super.setFullScreenWindow(paramWindow);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 343 */     if (window != null) {
/*     */       
/* 345 */       if (this.defaultDisplayMode != null) {
/* 346 */         setDisplayMode(this.defaultDisplayMode);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 353 */         this.defaultDisplayMode = null;
/*     */       } 
/* 355 */       WWindowPeer wWindowPeer = (WWindowPeer)window.getPeer();
/* 356 */       if (wWindowPeer != null) {
/* 357 */         wWindowPeer.setFullScreenExclusiveModeState(false);
/*     */ 
/*     */ 
/*     */         
/* 361 */         synchronized (wWindowPeer) {
/* 362 */           exitFullScreenExclusive(this.screen, wWindowPeer);
/*     */         } 
/*     */       } 
/* 365 */       removeFSWindowListener(window);
/*     */     } 
/* 367 */     super.setFullScreenWindow(paramWindow);
/* 368 */     if (paramWindow != null) {
/*     */ 
/*     */       
/* 371 */       this.defaultDisplayMode = getDisplayMode();
/* 372 */       addFSWindowListener(paramWindow);
/*     */       
/* 374 */       WWindowPeer wWindowPeer = (WWindowPeer)paramWindow.getPeer();
/* 375 */       if (wWindowPeer != null) {
/* 376 */         synchronized (wWindowPeer) {
/* 377 */           enterFullScreenExclusive(this.screen, wWindowPeer);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 383 */         wWindowPeer.setFullScreenExclusiveModeState(true);
/*     */       } 
/*     */ 
/*     */       
/* 387 */       wWindowPeer.updateGC();
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
/*     */   public boolean isDisplayChangeSupported() {
/* 403 */     return (isFullScreenSupported() && getFullScreenWindow() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setDisplayMode(DisplayMode paramDisplayMode) {
/* 408 */     if (!isDisplayChangeSupported()) {
/* 409 */       super.setDisplayMode(paramDisplayMode);
/*     */       return;
/*     */     } 
/* 412 */     if (paramDisplayMode == null || (paramDisplayMode = getMatchingDisplayMode(paramDisplayMode)) == null) {
/* 413 */       throw new IllegalArgumentException("Invalid display mode");
/*     */     }
/* 415 */     if (getDisplayMode().equals(paramDisplayMode)) {
/*     */       return;
/*     */     }
/* 418 */     Window window = getFullScreenWindow();
/* 419 */     if (window != null) {
/* 420 */       WWindowPeer wWindowPeer = (WWindowPeer)window.getPeer();
/* 421 */       configDisplayMode(this.screen, wWindowPeer, paramDisplayMode.getWidth(), paramDisplayMode.getHeight(), paramDisplayMode
/* 422 */           .getBitDepth(), paramDisplayMode.getRefreshRate());
/*     */ 
/*     */       
/* 425 */       Rectangle rectangle = getDefaultConfiguration().getBounds();
/* 426 */       window.setBounds(rectangle.x, rectangle.y, paramDisplayMode
/* 427 */           .getWidth(), paramDisplayMode.getHeight());
/*     */     }
/*     */     else {
/*     */       
/* 431 */       throw new IllegalStateException("Must be in fullscreen mode in order to set display mode");
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
/*     */   public synchronized DisplayMode getDisplayMode() {
/* 444 */     return getCurrentDisplayMode(this.screen);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized DisplayMode[] getDisplayModes() {
/* 450 */     ArrayList<DisplayMode> arrayList = new ArrayList();
/* 451 */     enumDisplayModes(this.screen, arrayList);
/* 452 */     int i = arrayList.size();
/* 453 */     DisplayMode[] arrayOfDisplayMode = new DisplayMode[i];
/* 454 */     for (byte b = 0; b < i; b++) {
/* 455 */       arrayOfDisplayMode[b] = arrayList.get(b);
/*     */     }
/* 457 */     return arrayOfDisplayMode;
/*     */   }
/*     */   
/*     */   protected synchronized DisplayMode getMatchingDisplayMode(DisplayMode paramDisplayMode) {
/* 461 */     if (!isDisplayChangeSupported()) {
/* 462 */       return null;
/*     */     }
/* 464 */     DisplayMode[] arrayOfDisplayMode = getDisplayModes();
/* 465 */     for (DisplayMode displayMode : arrayOfDisplayMode) {
/* 466 */       if (paramDisplayMode.equals(displayMode) || (paramDisplayMode
/* 467 */         .getRefreshRate() == 0 && paramDisplayMode
/* 468 */         .getWidth() == displayMode.getWidth() && paramDisplayMode
/* 469 */         .getHeight() == displayMode.getHeight() && paramDisplayMode
/* 470 */         .getBitDepth() == displayMode.getBitDepth()))
/*     */       {
/* 472 */         return displayMode;
/*     */       }
/*     */     } 
/* 475 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayChanged() {
/* 484 */     this.dynamicColorModel = null;
/* 485 */     this.defaultConfig = null;
/* 486 */     this.configs = null;
/*     */     
/* 488 */     this.topLevels.notifyListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paletteChanged() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDisplayChangedListener(DisplayChangedListener paramDisplayChangedListener) {
/* 504 */     this.topLevels.add(paramDisplayChangedListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeDisplayChangedListener(DisplayChangedListener paramDisplayChangedListener) {
/* 511 */     this.topLevels.remove(paramDisplayChangedListener);
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
/*     */   public ColorModel getDynamicColorModel() {
/* 525 */     if (this.dynamicColorModel == null) {
/* 526 */       this.dynamicColorModel = makeColorModel(this.screen, true);
/*     */     }
/* 528 */     return this.dynamicColorModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorModel getColorModel() {
/* 535 */     if (this.colorModel == null) {
/* 536 */       this.colorModel = makeColorModel(this.screen, false);
/*     */     }
/* 538 */     return this.colorModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Win32FSWindowAdapter
/*     */     extends WindowAdapter
/*     */   {
/*     */     private Win32GraphicsDevice device;
/*     */ 
/*     */     
/*     */     private DisplayMode dm;
/*     */ 
/*     */     
/*     */     Win32FSWindowAdapter(Win32GraphicsDevice param1Win32GraphicsDevice) {
/* 553 */       this.device = param1Win32GraphicsDevice;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void setFSWindowsState(Window param1Window, int param1Int) {
/* 559 */       GraphicsDevice[] arrayOfGraphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
/*     */ 
/*     */       
/* 562 */       if (param1Window != null) {
/* 563 */         for (GraphicsDevice graphicsDevice : arrayOfGraphicsDevice) {
/* 564 */           if (param1Window == graphicsDevice.getFullScreenWindow()) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 570 */       for (GraphicsDevice graphicsDevice : arrayOfGraphicsDevice) {
/* 571 */         Window window = graphicsDevice.getFullScreenWindow();
/* 572 */         if (window instanceof Frame) {
/* 573 */           ((Frame)window).setExtendedState(param1Int);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void windowDeactivated(WindowEvent param1WindowEvent) {
/* 580 */       setFSWindowsState(param1WindowEvent.getOppositeWindow(), 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void windowActivated(WindowEvent param1WindowEvent) {
/* 585 */       setFSWindowsState(param1WindowEvent.getOppositeWindow(), 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void windowIconified(WindowEvent param1WindowEvent) {
/* 591 */       DisplayMode displayMode = this.device.defaultDisplayMode;
/* 592 */       if (displayMode != null) {
/* 593 */         this.dm = this.device.getDisplayMode();
/* 594 */         this.device.setDisplayMode(displayMode);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void windowDeiconified(WindowEvent param1WindowEvent) {
/* 601 */       if (this.dm != null) {
/* 602 */         this.device.setDisplayMode(this.dm);
/* 603 */         this.dm = null;
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
/*     */ 
/*     */   
/*     */   protected void addFSWindowListener(final Window w) {
/* 617 */     this.fsWindowListener = new Win32FSWindowAdapter(this);
/*     */ 
/*     */ 
/*     */     
/* 621 */     EventQueue.invokeLater(new Runnable() {
/*     */           public void run() {
/* 623 */             w.addWindowListener(Win32GraphicsDevice.this.fsWindowListener);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeFSWindowListener(Window paramWindow) {
/* 634 */     paramWindow.removeWindowListener(this.fsWindowListener);
/* 635 */     this.fsWindowListener = null;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   native void initDevice(int paramInt);
/*     */   
/*     */   private native int getMaxConfigsImpl(int paramInt);
/*     */   
/*     */   protected native boolean isPixFmtSupported(int paramInt1, int paramInt2);
/*     */   
/*     */   private native int getDefaultPixIDImpl(int paramInt);
/*     */   
/*     */   protected native void enterFullScreenExclusive(int paramInt, WindowPeer paramWindowPeer);
/*     */   
/*     */   protected native void exitFullScreenExclusive(int paramInt, WindowPeer paramWindowPeer);
/*     */   
/*     */   protected native DisplayMode getCurrentDisplayMode(int paramInt);
/*     */   
/*     */   protected native void configDisplayMode(int paramInt1, WindowPeer paramWindowPeer, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */   
/*     */   protected native void enumDisplayModes(int paramInt, ArrayList paramArrayList);
/*     */   
/*     */   private native ColorModel makeColorModel(int paramInt, boolean paramBoolean);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\Win32GraphicsDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */