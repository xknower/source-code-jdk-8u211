/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.AWTEventMulticaster;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.event.WindowListener;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.awt.peer.WindowPeer;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.CausedFocusEvent;
/*     */ import sun.awt.DisplayChangedListener;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.Win32GraphicsConfig;
/*     */ import sun.awt.Win32GraphicsDevice;
/*     */ import sun.awt.Win32GraphicsEnvironment;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ public class WWindowPeer
/*     */   extends WPanelPeer
/*     */   implements WindowPeer, DisplayChangedListener
/*     */ {
/*  46 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.windows.WWindowPeer");
/*  47 */   private static final PlatformLogger screenLog = PlatformLogger.getLogger("sun.awt.windows.screen.WWindowPeer");
/*     */ 
/*     */ 
/*     */   
/*  51 */   private WWindowPeer modalBlocker = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isOpaque;
/*     */ 
/*     */ 
/*     */   
/*     */   private TranslucentWindowPainter painter;
/*     */ 
/*     */   
/*  62 */   private static final StringBuffer ACTIVE_WINDOWS_KEY = new StringBuffer("active_windows_list");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static PropertyChangeListener activeWindowListener = new ActiveWindowListener();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final PropertyChangeListener guiDisposedListener = new GuiDisposedListener();
/*     */   
/*     */   private WindowListener windowListener;
/*     */   
/*     */   private volatile Window.Type windowType;
/*     */   
/*     */   private volatile int sysX;
/*     */   
/*     */   private volatile int sysY;
/*     */   private volatile int sysW;
/*     */   private volatile int sysH;
/*     */   private float opacity;
/*     */   
/*     */   static {
/*  89 */     initIDs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void disposeImpl() {
/*  96 */     AppContext appContext = SunToolkit.targetToAppContext(this.target);
/*  97 */     synchronized (appContext) {
/*  98 */       List list = (List)appContext.get(ACTIVE_WINDOWS_KEY);
/*  99 */       if (list != null) {
/* 100 */         list.remove(this);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 105 */     GraphicsConfiguration graphicsConfiguration = getGraphicsConfiguration();
/* 106 */     ((Win32GraphicsDevice)graphicsConfiguration.getDevice()).removeDisplayChangedListener(this);
/*     */     
/* 108 */     synchronized (getStateLock()) {
/* 109 */       TranslucentWindowPainter translucentWindowPainter = this.painter;
/* 110 */       if (translucentWindowPainter != null) {
/* 111 */         translucentWindowPainter.flush();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 117 */     super.disposeImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toFront() {
/* 124 */     updateFocusableWindowState();
/* 125 */     _toFront();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlwaysOnTop(boolean paramBoolean) {
/* 135 */     if ((paramBoolean && ((Window)this.target).isVisible()) || !paramBoolean) {
/* 136 */       setAlwaysOnTopNative(paramBoolean);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAlwaysOnTopState() {
/* 142 */     setAlwaysOnTop(((Window)this.target).isAlwaysOnTop());
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateFocusableWindowState() {
/* 147 */     setFocusableWindow(((Window)this.target).isFocusableWindow());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String paramString) {
/* 155 */     if (paramString == null) {
/* 156 */       paramString = "";
/*     */     }
/* 158 */     _setTitle(paramString);
/*     */   }
/*     */   
/*     */   public void setResizable(boolean paramBoolean)
/*     */   {
/* 163 */     _setResizable(paramBoolean);
/*     */   } void initialize() { super.initialize(); updateInsets(this.insets_); Font font = ((Window)this.target).getFont(); if (font == null) { font = defaultFont; ((Window)this.target).setFont(font); setFont(font); }  GraphicsConfiguration graphicsConfiguration = getGraphicsConfiguration(); ((Win32GraphicsDevice)graphicsConfiguration.getDevice()).addDisplayChangedListener(this); initActiveWindowsTracking((Window)this.target); updateIconImages(); Shape shape = ((Window)this.target).getShape(); if (shape != null) applyShape(Region.getInstance(shape, null));  float f = ((Window)this.target).getOpacity(); if (f < 1.0F) setOpacity(f);  synchronized (getStateLock()) { this.isOpaque = true; setOpaque(((Window)this.target).isOpaque()); }  } void preCreate(WComponentPeer paramWComponentPeer) { this.windowType = ((Window)this.target).getType(); }
/*     */   void create(WComponentPeer paramWComponentPeer) { preCreate(paramWComponentPeer); createAwtWindow(paramWComponentPeer); }
/*     */   final WComponentPeer getNativeParent() { Window window = ((Window)this.target).getOwner(); return (WComponentPeer)WToolkit.targetToPeer(window); }
/*     */   protected void realShow() { super.show(); }
/*     */   public void show() { updateFocusableWindowState(); boolean bool = ((Window)this.target).isAlwaysOnTop(); updateGC(); realShow(); updateMinimumSize(); if (((Window)this.target).isAlwaysOnTopSupported() && bool) setAlwaysOnTop(bool);  synchronized (getStateLock()) { if (!this.isOpaque) updateWindow(true);  }  WComponentPeer wComponentPeer = getNativeParent(); if (wComponentPeer != null && wComponentPeer.isLightweightFramePeer()) { Rectangle rectangle = getBounds(); handleExpose(0, 0, rectangle.width, rectangle.height); }  }
/*     */   public boolean requestWindowFocus(CausedFocusEvent.Cause paramCause) { if (!focusAllowedFor()) return false;  return requestWindowFocus((paramCause == CausedFocusEvent.Cause.MOUSE_EVENT)); }
/*     */   public boolean focusAllowedFor() { Window window = (Window)this.target; if (!window.isVisible() || !window.isEnabled() || !window.isFocusableWindow()) return false;  if (isModalBlocked()) return false;  return true; }
/* 171 */   WWindowPeer(Window paramWindow) { super(paramWindow);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     this.windowType = Window.Type.NORMAL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 583 */     this.sysX = 0;
/* 584 */     this.sysY = 0;
/* 585 */     this.sysW = 0;
/* 586 */     this.sysH = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 642 */     this.opacity = 1.0F; } void hide() { WindowListener windowListener = this.windowListener; if (windowListener != null) windowListener.windowClosing(new WindowEvent((Window)this.target, 201));  super.hide(); } void preprocessPostEvent(AWTEvent paramAWTEvent) { if (paramAWTEvent instanceof WindowEvent) { WindowListener windowListener = this.windowListener; if (windowListener != null) switch (paramAWTEvent.getID()) { case 201: windowListener.windowClosing((WindowEvent)paramAWTEvent); break;case 203: windowListener.windowIconified((WindowEvent)paramAWTEvent); break; }   }  } synchronized void addWindowListener(WindowListener paramWindowListener) { this.windowListener = AWTEventMulticaster.add(this.windowListener, paramWindowListener); }
/*     */   synchronized void removeWindowListener(WindowListener paramWindowListener) { this.windowListener = AWTEventMulticaster.remove(this.windowListener, paramWindowListener); }
/*     */   public void updateMinimumSize() { Dimension dimension = null; if (((Component)this.target).isMinimumSizeSet()) dimension = ((Component)this.target).getMinimumSize();  if (dimension != null) { int i = getSysMinWidth(); int j = getSysMinHeight(); int k = (dimension.width >= i) ? dimension.width : i; int m = (dimension.height >= j) ? dimension.height : j; setMinSize(k, m); } else { setMinSize(0, 0); }  }
/*     */   public void updateIconImages() { List<Image> list = ((Window)this.target).getIconImages(); if (list == null || list.size() == 0) { setIconImagesData((int[])null, 0, 0, (int[])null, 0, 0); } else { int i = getSysIconWidth(); int j = getSysIconHeight(); int k = getSysSmIconWidth(); int m = getSysSmIconHeight(); DataBufferInt dataBufferInt1 = SunToolkit.getScaledIconData(list, i, j); DataBufferInt dataBufferInt2 = SunToolkit.getScaledIconData(list, k, m); if (dataBufferInt1 != null && dataBufferInt2 != null) { setIconImagesData(dataBufferInt1.getData(), i, j, dataBufferInt2.getData(), k, m); } else { setIconImagesData((int[])null, 0, 0, (int[])null, 0, 0); }  }  }
/*     */   public boolean isModalBlocked() { return (this.modalBlocker != null); }
/* 647 */   public void setOpacity(float paramFloat) { if (!((SunToolkit)((Window)this.target).getToolkit()).isWindowOpacitySupported()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 652 */     if (paramFloat < 0.0F || paramFloat > 1.0F) {
/* 653 */       throw new IllegalArgumentException("The value of opacity should be in the range [0.0f .. 1.0f].");
/*     */     }
/*     */ 
/*     */     
/* 657 */     if ((this.opacity == 1.0F && paramFloat < 1.0F) || (this.opacity < 1.0F && paramFloat == 1.0F))
/*     */     {
/* 659 */       if (!Win32GraphicsEnvironment.isVistaOS())
/*     */       {
/*     */ 
/*     */         
/* 663 */         replaceSurfaceDataRecursively((Component)getTarget());
/*     */       }
/*     */     }
/* 666 */     this.opacity = paramFloat;
/*     */ 
/*     */     
/* 669 */     int i = (int)(paramFloat * 255.0F);
/* 670 */     if (i < 0) {
/* 671 */       i = 0;
/*     */     }
/* 673 */     if (i > 255) {
/* 674 */       i = 255;
/*     */     }
/*     */     
/* 677 */     setOpacity(i);
/*     */     
/* 679 */     synchronized (getStateLock())
/* 680 */     { if (!this.isOpaque && ((Window)this.target).isVisible())
/* 681 */         updateWindow(true);  }  }
/*     */   public void setModalBlocked(Dialog paramDialog, boolean paramBoolean) { synchronized (((Component)getTarget()).getTreeLock()) { WWindowPeer wWindowPeer = (WWindowPeer)paramDialog.getPeer(); if (paramBoolean) { this.modalBlocker = wWindowPeer; if (wWindowPeer instanceof WFileDialogPeer) { ((WFileDialogPeer)wWindowPeer).blockWindow(this); } else if (wWindowPeer instanceof WPrintDialogPeer) { ((WPrintDialogPeer)wWindowPeer).blockWindow(this); } else { modalDisable(paramDialog, wWindowPeer.getHWnd()); }  } else { this.modalBlocker = null; if (wWindowPeer instanceof WFileDialogPeer) { ((WFileDialogPeer)wWindowPeer).unblockWindow(this); } else if (wWindowPeer instanceof WPrintDialogPeer) { ((WPrintDialogPeer)wWindowPeer).unblockWindow(this); } else { modalEnable(paramDialog); }  }  }
/*     */      }
/*     */   public static long[] getActiveWindowHandles(Component paramComponent) { AppContext appContext = SunToolkit.targetToAppContext(paramComponent); if (appContext == null)
/*     */       return null;  synchronized (appContext) { List list = (List)appContext.get(ACTIVE_WINDOWS_KEY); if (list == null)
/*     */         return null;  long[] arrayOfLong = new long[list.size()]; for (byte b = 0; b < list.size(); b++)
/*     */         arrayOfLong[b] = ((WWindowPeer)list.get(b)).getHWnd();  return arrayOfLong; }
/*     */      } void draggedToNewScreen() { SunToolkit.executeOnEventHandlerThread(this.target, new Runnable() {
/*     */           public void run() { WWindowPeer.this.displayChanged(); }
/* 690 */         }); } public void setOpaque(boolean paramBoolean) { synchronized (getStateLock()) {
/* 691 */       if (this.isOpaque == paramBoolean) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 696 */     Window window = (Window)getTarget();
/*     */     
/* 698 */     if (!paramBoolean) {
/* 699 */       SunToolkit sunToolkit = (SunToolkit)window.getToolkit();
/* 700 */       if (!sunToolkit.isWindowTranslucencySupported() || 
/* 701 */         !sunToolkit.isTranslucencyCapable(window.getGraphicsConfiguration())) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 707 */     boolean bool = Win32GraphicsEnvironment.isVistaOS();
/*     */     
/* 709 */     if (this.isOpaque != paramBoolean && !bool)
/*     */     {
/*     */       
/* 712 */       replaceSurfaceDataRecursively(window);
/*     */     }
/*     */     
/* 715 */     synchronized (getStateLock()) {
/* 716 */       this.isOpaque = paramBoolean;
/* 717 */       setOpaqueImpl(paramBoolean);
/* 718 */       if (paramBoolean) {
/* 719 */         TranslucentWindowPainter translucentWindowPainter = this.painter;
/* 720 */         if (translucentWindowPainter != null) {
/* 721 */           translucentWindowPainter.flush();
/* 722 */           this.painter = null;
/*     */         } 
/*     */       } else {
/* 725 */         this.painter = TranslucentWindowPainter.createInstance(this);
/*     */       } 
/*     */     } 
/*     */     
/* 729 */     if (bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 735 */       Shape shape = window.getShape();
/* 736 */       if (shape != null) {
/* 737 */         window.setShape(shape);
/*     */       }
/*     */     } 
/*     */     
/* 741 */     if (window.isVisible())
/* 742 */       updateWindow(true);  }
/*     */   public void updateGC() { Win32GraphicsDevice win32GraphicsDevice2; int i = getScreenImOn(); if (screenLog.isLoggable(PlatformLogger.Level.FINER)) log.finer("Screen number: " + i);  Win32GraphicsDevice win32GraphicsDevice1 = (Win32GraphicsDevice)this.winGraphicsConfig.getDevice(); GraphicsDevice[] arrayOfGraphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices(); if (i >= arrayOfGraphicsDevice.length) { win32GraphicsDevice2 = (Win32GraphicsDevice)GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); } else { win32GraphicsDevice2 = (Win32GraphicsDevice)arrayOfGraphicsDevice[i]; }  this.winGraphicsConfig = (Win32GraphicsConfig)win32GraphicsDevice2.getDefaultConfiguration(); if (screenLog.isLoggable(PlatformLogger.Level.FINE) && this.winGraphicsConfig == null) screenLog.fine("Assertion (winGraphicsConfig != null) failed");  if (win32GraphicsDevice1 != win32GraphicsDevice2) { win32GraphicsDevice1.removeDisplayChangedListener(this); win32GraphicsDevice2.addDisplayChangedListener(this); }  AWTAccessor.getComponentAccessor().setGraphicsConfiguration((Component)this.target, this.winGraphicsConfig); }
/*     */   public void displayChanged() { updateGC(); }
/*     */   public void paletteChanged() {}
/*     */   public void grab() { nativeGrab(); }
/*     */   public void ungrab() { nativeUngrab(); }
/*     */   private final boolean hasWarningWindow() { return (((Window)this.target).getWarningString() != null); }
/*     */   boolean isTargetUndecorated() { return true; }
/* 750 */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) { this.sysX = paramInt1; this.sysY = paramInt2; this.sysW = paramInt3; this.sysH = paramInt4; super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5); } public void print(Graphics paramGraphics) { Shape shape = AWTAccessor.getWindowAccessor().getShape((Window)this.target); if (shape != null) paramGraphics.setClip(shape);  super.print(paramGraphics); } private void replaceSurfaceDataRecursively(Component paramComponent) { if (paramComponent instanceof Container) for (Component component : ((Container)paramComponent).getComponents()) replaceSurfaceDataRecursively(component);   ComponentPeer componentPeer = paramComponent.getPeer(); if (componentPeer instanceof WComponentPeer) ((WComponentPeer)componentPeer).replaceSurfaceDataLater();  } public final Graphics getTranslucentGraphics() { synchronized (getStateLock()) { return this.isOpaque ? null : this.painter.getBackBuffer(false).getGraphics(); }  } public void setBackground(Color paramColor) { super.setBackground(paramColor); synchronized (getStateLock()) { if (!this.isOpaque && ((Window)this.target).isVisible()) updateWindow(true);  }  } public void updateWindow() { updateWindow(false); }
/*     */ 
/*     */   
/*     */   private void updateWindow(boolean paramBoolean) {
/* 754 */     Window window = (Window)this.target;
/* 755 */     synchronized (getStateLock()) {
/* 756 */       if (this.isOpaque || !window.isVisible() || window
/* 757 */         .getWidth() <= 0 || window.getHeight() <= 0) {
/*     */         return;
/*     */       }
/*     */       
/* 761 */       TranslucentWindowPainter translucentWindowPainter = this.painter;
/* 762 */       if (translucentWindowPainter != null) {
/* 763 */         translucentWindowPainter.updateWindow(paramBoolean);
/* 764 */       } else if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 765 */         log.finer("Translucent window painter is null in updateWindow");
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
/*     */   private static void initActiveWindowsTracking(Window paramWindow) {
/* 777 */     AppContext appContext = AppContext.getAppContext();
/* 778 */     synchronized (appContext) {
/* 779 */       List list = (List)appContext.get(ACTIVE_WINDOWS_KEY);
/* 780 */       if (list == null) {
/* 781 */         list = new LinkedList();
/* 782 */         appContext.put(ACTIVE_WINDOWS_KEY, list);
/* 783 */         appContext.addPropertyChangeListener("guidisposed", guiDisposedListener);
/*     */         
/* 785 */         KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/* 786 */         keyboardFocusManager.addPropertyChangeListener("activeWindow", activeWindowListener);
/*     */       } 
/*     */     } 
/*     */   } private static native void initIDs(); private native void _toFront(); public native void toBack(); private native void setAlwaysOnTopNative(boolean paramBoolean); native void setFocusableWindow(boolean paramBoolean); private native void _setTitle(String paramString); private native void _setResizable(boolean paramBoolean); native void createAwtWindow(WComponentPeer paramWComponentPeer); native void updateInsets(Insets paramInsets); static native int getSysMinWidth(); static native int getSysMinHeight(); static native int getSysIconWidth(); static native int getSysIconHeight(); static native int getSysSmIconWidth(); static native int getSysSmIconHeight(); native void setIconImagesData(int[] paramArrayOfint1, int paramInt1, int paramInt2, int[] paramArrayOfint2, int paramInt3, int paramInt4); synchronized native void reshapeFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4); private native boolean requestWindowFocus(boolean paramBoolean); native void setMinSize(int paramInt1, int paramInt2); native void modalDisable(Dialog paramDialog, long paramLong); native void modalEnable(Dialog paramDialog); private native int getScreenImOn();
/*     */   public final native void setFullScreenExclusiveModeState(boolean paramBoolean);
/*     */   private native void nativeGrab();
/*     */   private native void nativeUngrab();
/*     */   public native void repositionSecurityWarning();
/*     */   private native void setOpacity(int paramInt);
/*     */   private native void setOpaqueImpl(boolean paramBoolean);
/*     */   native void updateWindowImpl(int[] paramArrayOfint, int paramInt1, int paramInt2);
/*     */   private static class GuiDisposedListener implements PropertyChangeListener { private GuiDisposedListener() {}
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 799 */       boolean bool = ((Boolean)param1PropertyChangeEvent.getNewValue()).booleanValue();
/* 800 */       if (bool != true && WWindowPeer
/* 801 */         .log.isLoggable(PlatformLogger.Level.FINE)) {
/* 802 */         WWindowPeer.log.fine(" Assertion (newValue != true) failed for AppContext.GUI_DISPOSED ");
/*     */       }
/*     */       
/* 805 */       AppContext appContext = AppContext.getAppContext();
/* 806 */       synchronized (appContext) {
/* 807 */         appContext.remove(WWindowPeer.ACTIVE_WINDOWS_KEY);
/* 808 */         appContext.removePropertyChangeListener("guidisposed", this);
/*     */         
/* 810 */         KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/* 811 */         keyboardFocusManager.removePropertyChangeListener("activeWindow", WWindowPeer.activeWindowListener);
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ActiveWindowListener
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private ActiveWindowListener() {}
/*     */ 
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 825 */       Window window = (Window)param1PropertyChangeEvent.getNewValue();
/* 826 */       if (window == null) {
/*     */         return;
/*     */       }
/* 829 */       AppContext appContext = SunToolkit.targetToAppContext(window);
/* 830 */       synchronized (appContext) {
/* 831 */         WWindowPeer wWindowPeer = (WWindowPeer)window.getPeer();
/*     */         
/* 833 */         List<WWindowPeer> list = (List)appContext.get(WWindowPeer.ACTIVE_WINDOWS_KEY);
/* 834 */         if (list != null) {
/* 835 */           list.remove(wWindowPeer);
/* 836 */           list.add(wWindowPeer);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WWindowPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */