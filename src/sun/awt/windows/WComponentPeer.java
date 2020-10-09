/*      */ package sun.awt.windows;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.AWTException;
/*      */ import java.awt.BufferCapabilities;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.Image;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.SystemColor;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.dnd.DropTarget;
/*      */ import java.awt.dnd.peer.DropTargetPeer;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.InputEvent;
/*      */ import java.awt.event.InvocationEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.PaintEvent;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.awt.image.ImageProducer;
/*      */ import java.awt.image.VolatileImage;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.awt.peer.ContainerPeer;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.CausedFocusEvent;
/*      */ import sun.awt.PaintEventDispatcher;
/*      */ import sun.awt.RepaintArea;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.Win32GraphicsConfig;
/*      */ import sun.awt.Win32GraphicsEnvironment;
/*      */ import sun.awt.image.SunVolatileImage;
/*      */ import sun.awt.image.ToolkitImage;
/*      */ import sun.java2d.InvalidPipeException;
/*      */ import sun.java2d.ScreenUpdateManager;
/*      */ import sun.java2d.SurfaceData;
/*      */ import sun.java2d.pipe.Region;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class WComponentPeer
/*      */   extends WObjectPeer
/*      */   implements ComponentPeer, DropTargetPeer
/*      */ {
/*      */   protected volatile long hwnd;
/*   71 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.windows.WComponentPeer");
/*   72 */   private static final PlatformLogger shapeLog = PlatformLogger.getLogger("sun.awt.windows.shape.WComponentPeer");
/*   73 */   private static final PlatformLogger focusLog = PlatformLogger.getLogger("sun.awt.windows.focus.WComponentPeer");
/*      */   
/*      */   SurfaceData surfaceData;
/*      */   
/*      */   private RepaintArea paintArea;
/*      */   
/*      */   protected Win32GraphicsConfig winGraphicsConfig;
/*      */   
/*      */   boolean isLayouting = false;
/*      */   
/*      */   boolean paintPending = false;
/*   84 */   int oldWidth = -1;
/*   85 */   int oldHeight = -1;
/*   86 */   private int numBackBuffers = 0;
/*   87 */   private VolatileImage backBuffer = null;
/*   88 */   private BufferCapabilities backBufferCaps = null;
/*      */   
/*      */   private Color foreground;
/*      */   
/*      */   private Color background;
/*      */   
/*      */   private Font font;
/*      */   int nDropTargets;
/*      */   long nativeDropTargetContext;
/*      */   
/*      */   public boolean canDetermineObscurity() {
/*   99 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getHWnd() {
/*  112 */     return this.hwnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVisible(boolean paramBoolean) {
/*  122 */     if (paramBoolean) {
/*  123 */       show();
/*      */     } else {
/*  125 */       hide();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void show() {
/*  130 */     Dimension dimension = ((Component)this.target).getSize();
/*  131 */     this.oldHeight = dimension.height;
/*  132 */     this.oldWidth = dimension.width;
/*  133 */     pShow();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnabled(boolean paramBoolean) {
/*  139 */     if (paramBoolean) {
/*  140 */       enable();
/*      */     } else {
/*  142 */       disable();
/*      */     } 
/*      */   }
/*      */   
/*  146 */   public int serialNum = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final double BANDING_DIVISOR = 4.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  156 */     this.paintPending = (paramInt3 != this.oldWidth || paramInt4 != this.oldHeight);
/*      */     
/*  158 */     if ((paramInt5 & 0x4000) != 0) {
/*  159 */       reshapeNoCheck(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } else {
/*  161 */       reshape(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } 
/*  163 */     if (paramInt3 != this.oldWidth || paramInt4 != this.oldHeight) {
/*      */ 
/*      */       
/*      */       try {
/*  167 */         replaceSurfaceData();
/*  168 */       } catch (InvalidPipeException invalidPipeException) {}
/*      */ 
/*      */       
/*  171 */       this.oldWidth = paramInt3;
/*  172 */       this.oldHeight = paramInt4;
/*      */     } 
/*      */     
/*  175 */     this.serialNum++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void dynamicallyLayoutContainer() {
/*  185 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  186 */       Container container1 = WToolkit.getNativeContainer((Component)this.target);
/*  187 */       if (container1 != null) {
/*  188 */         log.fine("Assertion (parent == null) failed");
/*      */       }
/*      */     } 
/*  191 */     final Container cont = (Container)this.target;
/*      */     
/*  193 */     WToolkit.executeOnEventHandlerThread(container, new Runnable()
/*      */         {
/*      */           public void run()
/*      */           {
/*  197 */             cont.invalidate();
/*  198 */             cont.validate();
/*      */             
/*  200 */             if (WComponentPeer.this.surfaceData instanceof sun.java2d.d3d.D3DSurfaceData.D3DWindowSurfaceData || WComponentPeer.this.surfaceData instanceof sun.java2d.opengl.OGLSurfaceData) {
/*      */               
/*      */               try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  208 */                 WComponentPeer.this.replaceSurfaceData();
/*  209 */               } catch (InvalidPipeException invalidPipeException) {}
/*      */             }
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void paintDamagedAreaImmediately() {
/*  228 */     updateWindow();
/*      */ 
/*      */     
/*  231 */     SunToolkit.flushPendingEvents();
/*      */     
/*  233 */     this.paintArea.paint(this.target, shouldClearRectBeforePaint());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paint(Graphics paramGraphics) {
/*  240 */     ((Component)this.target).paint(paramGraphics);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void repaint(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void print(Graphics paramGraphics) {
/*  253 */     Component component = (Component)this.target;
/*      */ 
/*      */ 
/*      */     
/*  257 */     int i = component.getWidth();
/*  258 */     int j = component.getHeight();
/*      */     
/*  260 */     int k = (int)(j / 4.0D);
/*  261 */     if (k == 0) {
/*  262 */       k = j;
/*      */     }
/*      */     int m;
/*  265 */     for (m = 0; m < j; m += k) {
/*  266 */       int n = m + k - 1;
/*  267 */       if (n >= j) {
/*  268 */         n = j - 1;
/*      */       }
/*  270 */       int i1 = n - m + 1;
/*      */       
/*  272 */       Color color = component.getBackground();
/*  273 */       int[] arrayOfInt = createPrintedPixels(0, m, i, i1, (color == null) ? 255 : color
/*  274 */           .getAlpha());
/*  275 */       if (arrayOfInt != null) {
/*  276 */         BufferedImage bufferedImage = new BufferedImage(i, i1, 2);
/*      */         
/*  278 */         bufferedImage.setRGB(0, 0, i, i1, arrayOfInt, 0, i);
/*  279 */         paramGraphics.drawImage(bufferedImage, 0, m, null);
/*  280 */         bufferedImage.flush();
/*      */       } 
/*      */     } 
/*      */     
/*  284 */     component.print(paramGraphics);
/*      */   }
/*      */ 
/*      */   
/*      */   public void coalescePaintEvent(PaintEvent paramPaintEvent) {
/*  289 */     Rectangle rectangle = paramPaintEvent.getUpdateRect();
/*  290 */     if (!(paramPaintEvent instanceof sun.awt.event.IgnorePaintEvent)) {
/*  291 */       this.paintArea.add(rectangle, paramPaintEvent.getID());
/*      */     }
/*      */     
/*  294 */     if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/*  295 */       switch (paramPaintEvent.getID()) {
/*      */         case 801:
/*  297 */           log.finest("coalescePaintEvent: UPDATE: add: x = " + rectangle.x + ", y = " + rectangle.y + ", width = " + rectangle.width + ", height = " + rectangle.height);
/*      */           return;
/*      */         
/*      */         case 800:
/*  301 */           log.finest("coalescePaintEvent: PAINT: add: x = " + rectangle.x + ", y = " + rectangle.y + ", width = " + rectangle.width + ", height = " + rectangle.height);
/*      */           return;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleJavaKeyEvent(KeyEvent paramKeyEvent) {
/*  313 */     return false;
/*      */   }
/*      */   public void handleJavaMouseEvent(MouseEvent paramMouseEvent) {
/*  316 */     switch (paramMouseEvent.getID()) {
/*      */       
/*      */       case 501:
/*  319 */         if (this.target == paramMouseEvent.getSource() && 
/*  320 */           !((Component)this.target).isFocusOwner() && 
/*  321 */           WKeyboardFocusManagerPeer.shouldFocusOnClick((Component)this.target))
/*      */         {
/*  323 */           WKeyboardFocusManagerPeer.requestFocusFor((Component)this.target, CausedFocusEvent.Cause.MOUSE_EVENT);
/*      */         }
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEvent(AWTEvent paramAWTEvent) {
/*  335 */     int i = paramAWTEvent.getID();
/*      */     
/*  337 */     if (paramAWTEvent instanceof InputEvent && !((InputEvent)paramAWTEvent).isConsumed() && ((Component)this.target)
/*  338 */       .isEnabled())
/*      */     {
/*  340 */       if (paramAWTEvent instanceof MouseEvent && !(paramAWTEvent instanceof java.awt.event.MouseWheelEvent)) {
/*  341 */         handleJavaMouseEvent((MouseEvent)paramAWTEvent);
/*  342 */       } else if (paramAWTEvent instanceof KeyEvent && 
/*  343 */         handleJavaKeyEvent((KeyEvent)paramAWTEvent)) {
/*      */         return;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  349 */     switch (i) {
/*      */       
/*      */       case 800:
/*  352 */         this.paintPending = false;
/*      */ 
/*      */ 
/*      */       
/*      */       case 801:
/*  357 */         if (!this.isLayouting && !this.paintPending) {
/*  358 */           this.paintArea.paint(this.target, shouldClearRectBeforePaint());
/*      */         }
/*      */         return;
/*      */       case 1004:
/*      */       case 1005:
/*  363 */         handleJavaFocusEvent((FocusEvent)paramAWTEvent);
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  369 */     nativeHandleEvent(paramAWTEvent);
/*      */   }
/*      */   
/*      */   void handleJavaFocusEvent(FocusEvent paramFocusEvent) {
/*  373 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  374 */       focusLog.finer(paramFocusEvent.toString());
/*      */     }
/*  376 */     setFocus((paramFocusEvent.getID() == 1004));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize() {
/*  383 */     return ((Component)this.target).getSize();
/*      */   }
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize() {
/*  388 */     return getMinimumSize();
/*      */   }
/*      */ 
/*      */   
/*      */   public void layout() {}
/*      */ 
/*      */   
/*      */   public Rectangle getBounds() {
/*  396 */     return ((Component)this.target).getBounds();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFocusable() {
/*  401 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GraphicsConfiguration getGraphicsConfiguration() {
/*  410 */     if (this.winGraphicsConfig != null) {
/*  411 */       return this.winGraphicsConfig;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  416 */     return ((Component)this.target).getGraphicsConfiguration();
/*      */   }
/*      */ 
/*      */   
/*      */   public SurfaceData getSurfaceData() {
/*  421 */     return this.surfaceData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void replaceSurfaceData() {
/*  434 */     replaceSurfaceData(this.numBackBuffers, this.backBufferCaps);
/*      */   }
/*      */ 
/*      */   
/*      */   public void createScreenSurface(boolean paramBoolean) {
/*  439 */     Win32GraphicsConfig win32GraphicsConfig = (Win32GraphicsConfig)getGraphicsConfiguration();
/*  440 */     ScreenUpdateManager screenUpdateManager = ScreenUpdateManager.getInstance();
/*      */     
/*  442 */     this.surfaceData = screenUpdateManager.createScreenSurface(win32GraphicsConfig, this, this.numBackBuffers, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void replaceSurfaceData(int paramInt, BufferCapabilities paramBufferCapabilities) {
/*  455 */     SurfaceData surfaceData = null;
/*  456 */     VolatileImage volatileImage = null;
/*  457 */     synchronized (((Component)this.target).getTreeLock()) {
/*  458 */       synchronized (this) {
/*  459 */         if (this.pData == 0L) {
/*      */           return;
/*      */         }
/*  462 */         this.numBackBuffers = paramInt;
/*  463 */         ScreenUpdateManager screenUpdateManager = ScreenUpdateManager.getInstance();
/*  464 */         surfaceData = this.surfaceData;
/*  465 */         screenUpdateManager.dropScreenSurface(surfaceData);
/*  466 */         createScreenSurface(true);
/*  467 */         if (surfaceData != null) {
/*  468 */           surfaceData.invalidate();
/*      */         }
/*      */         
/*  471 */         volatileImage = this.backBuffer;
/*  472 */         if (this.numBackBuffers > 0) {
/*      */           
/*  474 */           this.backBufferCaps = paramBufferCapabilities;
/*      */           
/*  476 */           Win32GraphicsConfig win32GraphicsConfig = (Win32GraphicsConfig)getGraphicsConfiguration();
/*  477 */           this.backBuffer = win32GraphicsConfig.createBackBuffer(this);
/*  478 */         } else if (this.backBuffer != null) {
/*  479 */           this.backBufferCaps = null;
/*  480 */           this.backBuffer = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  486 */     if (surfaceData != null) {
/*  487 */       surfaceData.flush();
/*      */       
/*  489 */       surfaceData = null;
/*      */     } 
/*  491 */     if (volatileImage != null) {
/*  492 */       volatileImage.flush();
/*      */       
/*  494 */       surfaceData = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void replaceSurfaceDataLater() {
/*  499 */     Runnable runnable = new Runnable()
/*      */       {
/*      */ 
/*      */         
/*      */         public void run()
/*      */         {
/*  505 */           if (!WComponentPeer.this.isDisposed()) {
/*      */             try {
/*  507 */               WComponentPeer.this.replaceSurfaceData();
/*  508 */             } catch (InvalidPipeException invalidPipeException) {}
/*      */           }
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*  514 */     Component component = (Component)this.target;
/*      */     
/*  516 */     if (!PaintEventDispatcher.getPaintEventDispatcher().queueSurfaceDataReplacing(component, runnable)) {
/*  517 */       postEvent(new InvocationEvent(component, runnable));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean updateGraphicsData(GraphicsConfiguration paramGraphicsConfiguration) {
/*  523 */     this.winGraphicsConfig = (Win32GraphicsConfig)paramGraphicsConfiguration;
/*      */     try {
/*  525 */       replaceSurfaceData();
/*  526 */     } catch (InvalidPipeException invalidPipeException) {}
/*      */ 
/*      */     
/*  529 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorModel getColorModel() {
/*  535 */     GraphicsConfiguration graphicsConfiguration = getGraphicsConfiguration();
/*  536 */     if (graphicsConfiguration != null) {
/*  537 */       return graphicsConfiguration.getColorModel();
/*      */     }
/*      */     
/*  540 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorModel getDeviceColorModel() {
/*  547 */     Win32GraphicsConfig win32GraphicsConfig = (Win32GraphicsConfig)getGraphicsConfiguration();
/*  548 */     if (win32GraphicsConfig != null) {
/*  549 */       return win32GraphicsConfig.getDeviceColorModel();
/*      */     }
/*      */     
/*  552 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorModel getColorModel(int paramInt) {
/*  559 */     GraphicsConfiguration graphicsConfiguration = getGraphicsConfiguration();
/*  560 */     if (graphicsConfiguration != null) {
/*  561 */       return graphicsConfiguration.getColorModel(paramInt);
/*      */     }
/*      */     
/*  564 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  569 */   static final Font defaultFont = new Font("Dialog", 0, 12);
/*      */   private int updateX1;
/*      */   private int updateY1;
/*      */   
/*      */   public Graphics getGraphics() {
/*  574 */     if (isDisposed()) {
/*  575 */       return null;
/*      */     }
/*      */     
/*  578 */     Component component = (Component)getTarget();
/*  579 */     Window window = SunToolkit.getContainingWindow(component);
/*  580 */     if (window != null) {
/*      */       
/*  582 */       Graphics graphics = ((WWindowPeer)window.getPeer()).getTranslucentGraphics();
/*      */       
/*  584 */       if (graphics != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  589 */         int i = 0, j = 0;
/*  590 */         for (Component component1 = component; component1 != window; component1 = component1.getParent()) {
/*  591 */           i += component1.getX();
/*  592 */           j += component1.getY();
/*      */         } 
/*      */         
/*  595 */         graphics.translate(i, j);
/*  596 */         graphics.clipRect(0, 0, component.getWidth(), component.getHeight());
/*      */         
/*  598 */         return graphics;
/*      */       } 
/*      */     } 
/*      */     
/*  602 */     SurfaceData surfaceData = this.surfaceData;
/*  603 */     if (surfaceData != null) {
/*      */       
/*  605 */       Color color1 = this.background;
/*  606 */       if (color1 == null) {
/*  607 */         color1 = SystemColor.window;
/*      */       }
/*  609 */       Color color2 = this.foreground;
/*  610 */       if (color2 == null) {
/*  611 */         color2 = SystemColor.windowText;
/*      */       }
/*  613 */       Font font = this.font;
/*  614 */       if (font == null) {
/*  615 */         font = defaultFont;
/*      */       }
/*      */       
/*  618 */       ScreenUpdateManager screenUpdateManager = ScreenUpdateManager.getInstance();
/*  619 */       return screenUpdateManager.createGraphics(surfaceData, this, color2, color1, font);
/*      */     } 
/*      */     
/*  622 */     return null;
/*      */   }
/*      */   private int updateX2; private int updateY2; private volatile boolean isAccelCapable;
/*      */   public FontMetrics getFontMetrics(Font paramFont) {
/*  626 */     return WFontMetrics.getFontMetrics(paramFont);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void disposeImpl() {
/*  632 */     SurfaceData surfaceData = this.surfaceData;
/*  633 */     this.surfaceData = null;
/*  634 */     ScreenUpdateManager.getInstance().dropScreenSurface(surfaceData);
/*  635 */     surfaceData.invalidate();
/*      */     
/*  637 */     WToolkit.targetDisposedPeer(this.target, this);
/*  638 */     _dispose();
/*      */   }
/*      */   
/*      */   public void disposeLater() {
/*  642 */     postEvent(new InvocationEvent(this.target, new Runnable()
/*      */           {
/*      */             public void run() {
/*  645 */               WComponentPeer.this.dispose();
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void setForeground(Color paramColor) {
/*  652 */     this.foreground = paramColor;
/*  653 */     _setForeground(paramColor.getRGB());
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void setBackground(Color paramColor) {
/*  658 */     this.background = paramColor;
/*  659 */     _setBackground(paramColor.getRGB());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getBackgroundNoSync() {
/*  669 */     return this.background;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setFont(Font paramFont) {
/*  677 */     this.font = paramFont;
/*  678 */     _setFont(paramFont);
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateCursorImmediately() {
/*  683 */     WGlobalCursorManager.getCursorManager().updateCursorImmediately();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requestFocus(Component paramComponent, boolean paramBoolean1, boolean paramBoolean2, long paramLong, CausedFocusEvent.Cause paramCause) {
/*      */     Window window;
/*      */     WWindowPeer wWindowPeer;
/*      */     boolean bool;
/*  694 */     if (WKeyboardFocusManagerPeer.processSynchronousLightweightTransfer((Component)this.target, paramComponent, paramBoolean1, paramBoolean2, paramLong))
/*      */     {
/*      */       
/*  697 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  701 */     int i = WKeyboardFocusManagerPeer.shouldNativelyFocusHeavyweight((Component)this.target, paramComponent, paramBoolean1, paramBoolean2, paramLong, paramCause);
/*      */ 
/*      */ 
/*      */     
/*  705 */     switch (i) {
/*      */       case 0:
/*  707 */         return false;
/*      */       case 2:
/*  709 */         if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  710 */           focusLog.finer("Proceeding with request to " + paramComponent + " in " + this.target);
/*      */         }
/*  712 */         window = SunToolkit.getContainingWindow((Component)this.target);
/*  713 */         if (window == null) {
/*  714 */           return rejectFocusRequestHelper("WARNING: Parent window is null");
/*      */         }
/*  716 */         wWindowPeer = (WWindowPeer)window.getPeer();
/*  717 */         if (wWindowPeer == null) {
/*  718 */           return rejectFocusRequestHelper("WARNING: Parent window's peer is null");
/*      */         }
/*  720 */         bool = wWindowPeer.requestWindowFocus(paramCause);
/*      */         
/*  722 */         if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  723 */           focusLog.finer("Requested window focus: " + bool);
/*      */         }
/*      */ 
/*      */         
/*  727 */         if (!bool || !window.isFocused()) {
/*  728 */           return rejectFocusRequestHelper("Waiting for asynchronous processing of the request");
/*      */         }
/*  730 */         return WKeyboardFocusManagerPeer.deliverFocus(paramComponent, (Component)this.target, paramBoolean1, paramBoolean2, paramLong, paramCause);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  738 */         return true;
/*      */     } 
/*  740 */     return false;
/*      */   }
/*      */   
/*      */   private boolean rejectFocusRequestHelper(String paramString) {
/*  744 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  745 */       focusLog.finer(paramString);
/*      */     }
/*  747 */     WKeyboardFocusManagerPeer.removeLastFocusRequest((Component)this.target);
/*  748 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Image createImage(ImageProducer paramImageProducer) {
/*  753 */     return new ToolkitImage(paramImageProducer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Image createImage(int paramInt1, int paramInt2) {
/*  759 */     Win32GraphicsConfig win32GraphicsConfig = (Win32GraphicsConfig)getGraphicsConfiguration();
/*  760 */     return win32GraphicsConfig.createAcceleratedImage((Component)this.target, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public VolatileImage createVolatileImage(int paramInt1, int paramInt2) {
/*  765 */     return new SunVolatileImage((Component)this.target, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean prepareImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
/*  770 */     return Toolkit.getDefaultToolkit().prepareImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*      */   }
/*      */ 
/*      */   
/*      */   public int checkImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
/*  775 */     return Toolkit.getDefaultToolkit().checkImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  781 */     return getClass().getName() + "[" + this.target + "]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   WComponentPeer getNativeParent() {
/*  808 */     Container container = SunToolkit.getNativeContainer((Component)this.target);
/*  809 */     return (WComponentPeer)WToolkit.targetToPeer(container);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkCreation() {
/*  814 */     if (this.hwnd == 0L || this.pData == 0L) {
/*      */       
/*  816 */       if (this.createError != null)
/*      */       {
/*  818 */         throw this.createError;
/*      */       }
/*      */ 
/*      */       
/*  822 */       throw new InternalError("couldn't create component peer");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void initialize() {
/*  830 */     if (((Component)this.target).isVisible()) {
/*  831 */       show();
/*      */     }
/*  833 */     Color color = ((Component)this.target).getForeground();
/*  834 */     if (color != null) {
/*  835 */       setForeground(color);
/*      */     }
/*      */     
/*  838 */     Font font = ((Component)this.target).getFont();
/*  839 */     if (font != null) {
/*  840 */       setFont(font);
/*      */     }
/*  842 */     if (!((Component)this.target).isEnabled()) {
/*  843 */       disable();
/*      */     }
/*  845 */     Rectangle rectangle = ((Component)this.target).getBounds();
/*  846 */     setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void handleRepaint(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void handleExpose(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  863 */     postPaintIfNecessary(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handlePaint(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  874 */     postPaintIfNecessary(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */   
/*      */   private void postPaintIfNecessary(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  878 */     if (!AWTAccessor.getComponentAccessor().getIgnoreRepaint((Component)this.target)) {
/*      */       
/*  880 */       PaintEvent paintEvent = PaintEventDispatcher.getPaintEventDispatcher().createPaintEvent((Component)this.target, paramInt1, paramInt2, paramInt3, paramInt4);
/*  881 */       if (paintEvent != null) {
/*  882 */         postEvent(paintEvent);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void postEvent(AWTEvent paramAWTEvent) {
/*  891 */     preprocessPostEvent(paramAWTEvent);
/*  892 */     WToolkit.postEvent(WToolkit.targetToAppContext(this.target), paramAWTEvent);
/*      */   }
/*      */ 
/*      */   
/*      */   void preprocessPostEvent(AWTEvent paramAWTEvent) {}
/*      */ 
/*      */   
/*      */   public void beginLayout() {
/*  900 */     this.isLayouting = true;
/*      */   }
/*      */   
/*      */   public void endLayout() {
/*  904 */     if (!this.paintArea.isEmpty() && !this.paintPending && 
/*  905 */       !((Component)this.target).getIgnoreRepaint())
/*      */     {
/*  907 */       postEvent(new PaintEvent((Component)this.target, 800, new Rectangle()));
/*      */     }
/*      */     
/*  910 */     this.isLayouting = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension preferredSize() {
/*  920 */     return getPreferredSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addDropTarget(DropTarget paramDropTarget) {
/*  929 */     if (this.nDropTargets == 0) {
/*  930 */       this.nativeDropTargetContext = addNativeDropTarget();
/*      */     }
/*  932 */     this.nDropTargets++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removeDropTarget(DropTarget paramDropTarget) {
/*  941 */     this.nDropTargets--;
/*  942 */     if (this.nDropTargets == 0) {
/*  943 */       removeNativeDropTarget();
/*  944 */       this.nativeDropTargetContext = 0L;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handlesWheelScrolling() {
/*  965 */     return nativeHandlesWheelScrolling();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPaintPending() {
/*  971 */     return (this.paintPending && this.isLayouting);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void createBuffers(int paramInt, BufferCapabilities paramBufferCapabilities) throws AWTException {
/*  985 */     Win32GraphicsConfig win32GraphicsConfig = (Win32GraphicsConfig)getGraphicsConfiguration();
/*  986 */     win32GraphicsConfig.assertOperationSupported((Component)this.target, paramInt, paramBufferCapabilities);
/*      */ 
/*      */     
/*      */     try {
/*  990 */       replaceSurfaceData(paramInt - 1, paramBufferCapabilities);
/*  991 */     } catch (InvalidPipeException invalidPipeException) {
/*  992 */       throw new AWTException(invalidPipeException.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void destroyBuffers() {
/*  998 */     replaceSurfaceData(0, (BufferCapabilities)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flip(int paramInt1, int paramInt2, int paramInt3, int paramInt4, BufferCapabilities.FlipContents paramFlipContents) {
/* 1005 */     VolatileImage volatileImage = this.backBuffer;
/* 1006 */     if (volatileImage == null) {
/* 1007 */       throw new IllegalStateException("Buffers have not been created");
/*      */     }
/*      */     
/* 1010 */     Win32GraphicsConfig win32GraphicsConfig = (Win32GraphicsConfig)getGraphicsConfiguration();
/* 1011 */     win32GraphicsConfig.flip(this, (Component)this.target, volatileImage, paramInt1, paramInt2, paramInt3, paramInt4, paramFlipContents);
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized Image getBackBuffer() {
/* 1016 */     VolatileImage volatileImage = this.backBuffer;
/* 1017 */     if (volatileImage == null) {
/* 1018 */       throw new IllegalStateException("Buffers have not been created");
/*      */     }
/* 1020 */     return volatileImage;
/*      */   }
/*      */   public BufferCapabilities getBackBufferCaps() {
/* 1023 */     return this.backBufferCaps;
/*      */   }
/*      */   public int getBackBuffersNum() {
/* 1026 */     return this.numBackBuffers;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldClearRectBeforePaint() {
/* 1032 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reparent(ContainerPeer paramContainerPeer) {
/* 1042 */     pSetParent(paramContainerPeer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReparentSupported() {
/* 1050 */     return true;
/*      */   }
/*      */   
/*      */   public void setBoundsOperation(int paramInt) {}
/*      */   
/*      */   WComponentPeer(Component paramComponent) {
/* 1056 */     this.isAccelCapable = true;
/*      */     this.target = paramComponent;
/*      */     this.paintArea = new RepaintArea();
/*      */     create(getNativeParent());
/*      */     checkCreation();
/*      */     createScreenSurface(false);
/*      */     initialize();
/*      */     start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAccelCapable() {
/* 1078 */     if (!this.isAccelCapable || 
/* 1079 */       !isContainingTopLevelAccelCapable((Component)this.target))
/*      */     {
/* 1081 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1085 */     boolean bool = SunToolkit.isContainingTopLevelTranslucent((Component)this.target);
/*      */ 
/*      */     
/* 1088 */     return (!bool || Win32GraphicsEnvironment.isVistaOS());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disableAcceleration() {
/* 1095 */     this.isAccelCapable = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean isContainingTopLevelAccelCapable(Component paramComponent) {
/* 1109 */     while (paramComponent != null && !(paramComponent instanceof WEmbeddedFrame)) {
/* 1110 */       paramComponent = paramComponent.getParent();
/*      */     }
/* 1112 */     if (paramComponent == null) {
/* 1113 */       return true;
/*      */     }
/* 1115 */     return ((WEmbeddedFramePeer)paramComponent.getPeer()).isAccelCapable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyShape(Region paramRegion) {
/* 1125 */     if (shapeLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1126 */       shapeLog.finer("*** INFO: Setting shape: PEER: " + this + "; TARGET: " + this.target + "; SHAPE: " + paramRegion);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1131 */     if (paramRegion != null) {
/* 1132 */       setRectangularShape(paramRegion.getLoX(), paramRegion.getLoY(), paramRegion.getHiX(), paramRegion.getHiY(), 
/* 1133 */           paramRegion.isRectangular() ? null : paramRegion);
/*      */     } else {
/* 1135 */       setRectangularShape(0, 0, 0, 0, (Region)null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setZOrder(ComponentPeer paramComponentPeer) {
/* 1145 */     long l = (paramComponentPeer != null) ? ((WComponentPeer)paramComponentPeer).getHWnd() : 0L;
/*      */     
/* 1147 */     setZOrder(l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLightweightFramePeer() {
/* 1153 */     return false;
/*      */   }
/*      */   
/*      */   public native boolean isObscured();
/*      */   
/*      */   private synchronized native void pShow();
/*      */   
/*      */   synchronized native void hide();
/*      */   
/*      */   synchronized native void enable();
/*      */   
/*      */   synchronized native void disable();
/*      */   
/*      */   public native Point getLocationOnScreen();
/*      */   
/*      */   private native void reshapeNoCheck(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*      */   
/*      */   synchronized native void updateWindow();
/*      */   
/*      */   private native int[] createPrintedPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*      */   
/*      */   public synchronized native void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*      */   
/*      */   native void nativeHandleEvent(AWTEvent paramAWTEvent);
/*      */   
/*      */   native void setFocus(boolean paramBoolean);
/*      */   
/*      */   private synchronized native void _dispose();
/*      */   
/*      */   private native void _setForeground(int paramInt);
/*      */   
/*      */   private native void _setBackground(int paramInt);
/*      */   
/*      */   synchronized native void _setFont(Font paramFont);
/*      */   
/*      */   abstract void create(WComponentPeer paramWComponentPeer);
/*      */   
/*      */   synchronized native void start();
/*      */   
/*      */   public native void beginValidate();
/*      */   
/*      */   public native void endValidate();
/*      */   
/*      */   native long addNativeDropTarget();
/*      */   
/*      */   native void removeNativeDropTarget();
/*      */   
/*      */   native boolean nativeHandlesWheelScrolling();
/*      */   
/*      */   native void pSetParent(ComponentPeer paramComponentPeer);
/*      */   
/*      */   native void setRectangularShape(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Region paramRegion);
/*      */   
/*      */   private native void setZOrder(long paramLong);
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WComponentPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */