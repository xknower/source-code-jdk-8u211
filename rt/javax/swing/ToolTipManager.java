/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Insets;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToolTipManager
/*     */   extends MouseAdapter
/*     */   implements MouseMotionListener
/*     */ {
/*     */   Timer enterTimer;
/*     */   Timer exitTimer;
/*     */   Timer insideTimer;
/*     */   String toolTipText;
/*     */   Point preferredLocation;
/*     */   JComponent insideComponent;
/*     */   MouseEvent mouseEvent;
/*     */   boolean showImmediately;
/*  60 */   private static final Object TOOL_TIP_MANAGER_KEY = new Object();
/*     */   
/*     */   transient Popup tipWindow;
/*     */   
/*     */   private Window window;
/*     */   
/*     */   JToolTip tip;
/*     */   
/*  68 */   private Rectangle popupRect = null;
/*  69 */   private Rectangle popupFrameRect = null;
/*     */   
/*     */   boolean enabled = true;
/*     */   
/*     */   private boolean tipShowing = false;
/*  74 */   private FocusListener focusChangeListener = null;
/*  75 */   private MouseMotionListener moveBeforeEnterListener = null;
/*  76 */   private KeyListener accessibilityKeyListener = null;
/*     */   
/*     */   private KeyStroke postTip;
/*     */   
/*     */   private KeyStroke hideTip;
/*     */   
/*     */   protected boolean lightWeightPopupEnabled = true;
/*     */   protected boolean heavyWeightPopupEnabled = false;
/*     */   
/*     */   ToolTipManager() {
/*  86 */     this.enterTimer = new Timer(750, new insideTimerAction());
/*  87 */     this.enterTimer.setRepeats(false);
/*  88 */     this.exitTimer = new Timer(500, new outsideTimerAction());
/*  89 */     this.exitTimer.setRepeats(false);
/*  90 */     this.insideTimer = new Timer(4000, new stillInsideTimerAction());
/*  91 */     this.insideTimer.setRepeats(false);
/*     */     
/*  93 */     this.moveBeforeEnterListener = new MoveBeforeEnterListener();
/*  94 */     this.accessibilityKeyListener = new AccessibilityKeyListener();
/*     */     
/*  96 */     this.postTip = KeyStroke.getKeyStroke(112, 2);
/*  97 */     this.hideTip = KeyStroke.getKeyStroke(27, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean paramBoolean) {
/* 106 */     this.enabled = paramBoolean;
/* 107 */     if (!paramBoolean) {
/* 108 */       hideTipWindow();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 118 */     return this.enabled;
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
/*     */   public void setLightWeightPopupEnabled(boolean paramBoolean) {
/* 132 */     this.lightWeightPopupEnabled = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLightWeightPopupEnabled() {
/* 143 */     return this.lightWeightPopupEnabled;
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
/*     */   public void setInitialDelay(int paramInt) {
/* 156 */     this.enterTimer.setInitialDelay(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInitialDelay() {
/* 167 */     return this.enterTimer.getInitialDelay();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDismissDelay(int paramInt) {
/* 178 */     this.insideTimer.setInitialDelay(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDismissDelay() {
/* 189 */     return this.insideTimer.getInitialDelay();
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
/*     */   public void setReshowDelay(int paramInt) {
/* 207 */     this.exitTimer.setInitialDelay(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReshowDelay() {
/* 217 */     return this.exitTimer.getInitialDelay();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GraphicsConfiguration getDrawingGC(Point paramPoint) {
/* 224 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 225 */     GraphicsDevice[] arrayOfGraphicsDevice = graphicsEnvironment.getScreenDevices();
/* 226 */     for (GraphicsDevice graphicsDevice : arrayOfGraphicsDevice) {
/* 227 */       GraphicsConfiguration[] arrayOfGraphicsConfiguration = graphicsDevice.getConfigurations();
/* 228 */       for (GraphicsConfiguration graphicsConfiguration : arrayOfGraphicsConfiguration) {
/* 229 */         Rectangle rectangle = graphicsConfiguration.getBounds();
/* 230 */         if (rectangle.contains(paramPoint)) {
/* 231 */           return graphicsConfiguration;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 236 */     return null;
/*     */   }
/*     */   
/*     */   void showTipWindow() {
/* 240 */     if (this.insideComponent == null || !this.insideComponent.isShowing())
/*     */       return; 
/* 242 */     String str = UIManager.getString("ToolTipManager.enableToolTipMode");
/* 243 */     if ("activeApplication".equals(str)) {
/*     */       
/* 245 */       KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/* 246 */       if (keyboardFocusManager.getFocusedWindow() == null) {
/*     */         return;
/*     */       }
/*     */     } 
/* 250 */     if (this.enabled) {
/*     */       
/* 252 */       Point point2, point3, point1 = this.insideComponent.getLocationOnScreen();
/*     */ 
/*     */ 
/*     */       
/* 256 */       if (this.preferredLocation != null) {
/* 257 */         point3 = new Point(point1.x + this.preferredLocation.x, point1.y + this.preferredLocation.y);
/*     */       } else {
/*     */         
/* 260 */         point3 = this.mouseEvent.getLocationOnScreen();
/*     */       } 
/*     */       
/* 263 */       GraphicsConfiguration graphicsConfiguration = getDrawingGC(point3);
/* 264 */       if (graphicsConfiguration == null) {
/* 265 */         point3 = this.mouseEvent.getLocationOnScreen();
/* 266 */         graphicsConfiguration = getDrawingGC(point3);
/* 267 */         if (graphicsConfiguration == null) {
/* 268 */           graphicsConfiguration = this.insideComponent.getGraphicsConfiguration();
/*     */         }
/*     */       } 
/*     */       
/* 272 */       Rectangle rectangle = graphicsConfiguration.getBounds();
/*     */       
/* 274 */       Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(graphicsConfiguration);
/*     */       
/* 276 */       rectangle.x += insets.left;
/* 277 */       rectangle.y += insets.top;
/* 278 */       rectangle.width -= insets.left + insets.right;
/* 279 */       rectangle.height -= insets.top + insets.bottom;
/*     */       
/* 281 */       boolean bool = SwingUtilities.isLeftToRight(this.insideComponent);
/*     */ 
/*     */       
/* 284 */       hideTipWindow();
/*     */       
/* 286 */       this.tip = this.insideComponent.createToolTip();
/* 287 */       this.tip.setTipText(this.toolTipText);
/* 288 */       Dimension dimension = this.tip.getPreferredSize();
/*     */       
/* 290 */       if (this.preferredLocation != null) {
/* 291 */         point2 = point3;
/* 292 */         if (!bool) {
/* 293 */           point2.x -= dimension.width;
/*     */         }
/*     */       } else {
/*     */         
/* 297 */         point2 = new Point(point1.x + this.mouseEvent.getX(), point1.y + this.mouseEvent.getY() + 20);
/* 298 */         if (!bool && 
/* 299 */           point2.x - dimension.width >= 0) {
/* 300 */           point2.x -= dimension.width;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 307 */       if (this.popupRect == null) {
/* 308 */         this.popupRect = new Rectangle();
/*     */       }
/* 310 */       this.popupRect.setBounds(point2.x, point2.y, dimension.width, dimension.height);
/*     */ 
/*     */ 
/*     */       
/* 314 */       if (point2.x < rectangle.x) {
/* 315 */         point2.x = rectangle.x;
/*     */       }
/* 317 */       else if (point2.x - rectangle.x + dimension.width > rectangle.width) {
/* 318 */         point2.x = rectangle.x + Math.max(0, rectangle.width - dimension.width);
/*     */       } 
/*     */       
/* 321 */       if (point2.y < rectangle.y) {
/* 322 */         point2.y = rectangle.y;
/*     */       }
/* 324 */       else if (point2.y - rectangle.y + dimension.height > rectangle.height) {
/* 325 */         point2.y = rectangle.y + Math.max(0, rectangle.height - dimension.height);
/*     */       } 
/*     */       
/* 328 */       PopupFactory popupFactory = PopupFactory.getSharedInstance();
/*     */       
/* 330 */       if (this.lightWeightPopupEnabled) {
/* 331 */         int i = getPopupFitHeight(this.popupRect, this.insideComponent);
/* 332 */         int j = getPopupFitWidth(this.popupRect, this.insideComponent);
/* 333 */         if (j > 0 || i > 0) {
/* 334 */           popupFactory.setPopupType(1);
/*     */         } else {
/* 336 */           popupFactory.setPopupType(0);
/*     */         } 
/*     */       } else {
/*     */         
/* 340 */         popupFactory.setPopupType(1);
/*     */       } 
/* 342 */       this.tipWindow = popupFactory.getPopup(this.insideComponent, this.tip, point2.x, point2.y);
/*     */ 
/*     */       
/* 345 */       popupFactory.setPopupType(0);
/*     */       
/* 347 */       this.tipWindow.show();
/*     */       
/* 349 */       Window window = SwingUtilities.windowForComponent(this.insideComponent);
/*     */ 
/*     */       
/* 352 */       this.window = SwingUtilities.windowForComponent(this.tip);
/* 353 */       if (this.window != null && this.window != window) {
/* 354 */         this.window.addMouseListener(this);
/*     */       } else {
/*     */         
/* 357 */         this.window = null;
/*     */       } 
/*     */       
/* 360 */       this.insideTimer.start();
/* 361 */       this.tipShowing = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   void hideTipWindow() {
/* 366 */     if (this.tipWindow != null) {
/* 367 */       if (this.window != null) {
/* 368 */         this.window.removeMouseListener(this);
/* 369 */         this.window = null;
/*     */       } 
/* 371 */       this.tipWindow.hide();
/* 372 */       this.tipWindow = null;
/* 373 */       this.tipShowing = false;
/* 374 */       this.tip = null;
/* 375 */       this.insideTimer.stop();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ToolTipManager sharedInstance() {
/* 385 */     Object object = SwingUtilities.appContextGet(TOOL_TIP_MANAGER_KEY);
/* 386 */     if (object instanceof ToolTipManager) {
/* 387 */       return (ToolTipManager)object;
/*     */     }
/* 389 */     ToolTipManager toolTipManager = new ToolTipManager();
/* 390 */     SwingUtilities.appContextPut(TOOL_TIP_MANAGER_KEY, toolTipManager);
/* 391 */     return toolTipManager;
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
/*     */   public void registerComponent(JComponent paramJComponent) {
/* 408 */     paramJComponent.removeMouseListener(this);
/* 409 */     paramJComponent.addMouseListener(this);
/* 410 */     paramJComponent.removeMouseMotionListener(this.moveBeforeEnterListener);
/* 411 */     paramJComponent.addMouseMotionListener(this.moveBeforeEnterListener);
/* 412 */     paramJComponent.removeKeyListener(this.accessibilityKeyListener);
/* 413 */     paramJComponent.addKeyListener(this.accessibilityKeyListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterComponent(JComponent paramJComponent) {
/* 422 */     paramJComponent.removeMouseListener(this);
/* 423 */     paramJComponent.removeMouseMotionListener(this.moveBeforeEnterListener);
/* 424 */     paramJComponent.removeKeyListener(this.accessibilityKeyListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseEntered(MouseEvent paramMouseEvent) {
/* 435 */     initiateToolTip(paramMouseEvent);
/*     */   }
/*     */   
/*     */   private void initiateToolTip(MouseEvent paramMouseEvent) {
/* 439 */     if (paramMouseEvent.getSource() == this.window) {
/*     */       return;
/*     */     }
/* 442 */     JComponent jComponent = (JComponent)paramMouseEvent.getSource();
/* 443 */     jComponent.removeMouseMotionListener(this.moveBeforeEnterListener);
/*     */     
/* 445 */     this.exitTimer.stop();
/*     */     
/* 447 */     Point point = paramMouseEvent.getPoint();
/*     */     
/* 449 */     if (point.x < 0 || point.x >= jComponent
/* 450 */       .getWidth() || point.y < 0 || point.y >= jComponent
/*     */       
/* 452 */       .getHeight()) {
/*     */       return;
/*     */     }
/*     */     
/* 456 */     if (this.insideComponent != null) {
/* 457 */       this.enterTimer.stop();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 462 */     jComponent.removeMouseMotionListener(this);
/* 463 */     jComponent.addMouseMotionListener(this);
/*     */     
/* 465 */     boolean bool = (this.insideComponent == jComponent) ? true : false;
/*     */     
/* 467 */     this.insideComponent = jComponent;
/* 468 */     if (this.tipWindow != null) {
/* 469 */       this.mouseEvent = paramMouseEvent;
/* 470 */       if (this.showImmediately) {
/* 471 */         String str = jComponent.getToolTipText(paramMouseEvent);
/* 472 */         Point point1 = jComponent.getToolTipLocation(paramMouseEvent);
/*     */ 
/*     */         
/* 475 */         boolean bool1 = (this.preferredLocation != null) ? this.preferredLocation.equals(point1) : ((point1 == null) ? true : false);
/*     */ 
/*     */         
/* 478 */         if (!bool || !this.toolTipText.equals(str) || !bool1) {
/*     */           
/* 480 */           this.toolTipText = str;
/* 481 */           this.preferredLocation = point1;
/* 482 */           showTipWindow();
/*     */         } 
/*     */       } else {
/* 485 */         this.enterTimer.start();
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
/*     */   public void mouseExited(MouseEvent paramMouseEvent) {
/* 498 */     boolean bool = true;
/* 499 */     if (this.insideComponent == null);
/*     */ 
/*     */     
/* 502 */     if (this.window != null && paramMouseEvent.getSource() == this.window && this.insideComponent != null) {
/*     */ 
/*     */       
/* 505 */       Container container = this.insideComponent.getTopLevelAncestor();
/*     */       
/* 507 */       if (container != null) {
/* 508 */         Point point = paramMouseEvent.getPoint();
/* 509 */         SwingUtilities.convertPointToScreen(point, this.window);
/*     */         
/* 511 */         point.x -= container.getX();
/* 512 */         point.y -= container.getY();
/*     */         
/* 514 */         point = SwingUtilities.convertPoint(null, point, this.insideComponent);
/* 515 */         if (point.x >= 0 && point.x < this.insideComponent.getWidth() && point.y >= 0 && point.y < this.insideComponent
/* 516 */           .getHeight()) {
/* 517 */           bool = false;
/*     */         } else {
/* 519 */           bool = true;
/*     */         } 
/*     */       } 
/* 522 */     } else if (paramMouseEvent.getSource() == this.insideComponent && this.tipWindow != null) {
/* 523 */       Window window = SwingUtilities.getWindowAncestor(this.insideComponent);
/* 524 */       if (window != null) {
/* 525 */         Point point1 = SwingUtilities.convertPoint(this.insideComponent, paramMouseEvent
/* 526 */             .getPoint(), window);
/*     */         
/* 528 */         Rectangle rectangle = this.insideComponent.getTopLevelAncestor().getBounds();
/* 529 */         point1.x += rectangle.x;
/* 530 */         point1.y += rectangle.y;
/*     */         
/* 532 */         Point point2 = new Point(0, 0);
/* 533 */         SwingUtilities.convertPointToScreen(point2, this.tip);
/* 534 */         rectangle.x = point2.x;
/* 535 */         rectangle.y = point2.y;
/* 536 */         rectangle.width = this.tip.getWidth();
/* 537 */         rectangle.height = this.tip.getHeight();
/*     */         
/* 539 */         if (point1.x >= rectangle.x && point1.x < rectangle.x + rectangle.width && point1.y >= rectangle.y && point1.y < rectangle.y + rectangle.height) {
/*     */           
/* 541 */           bool = false;
/*     */         } else {
/* 543 */           bool = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 548 */     if (bool) {
/* 549 */       this.enterTimer.stop();
/* 550 */       if (this.insideComponent != null) {
/* 551 */         this.insideComponent.removeMouseMotionListener(this);
/*     */       }
/* 553 */       this.insideComponent = null;
/* 554 */       this.toolTipText = null;
/* 555 */       this.mouseEvent = null;
/* 556 */       hideTipWindow();
/* 557 */       this.exitTimer.restart();
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
/*     */   public void mousePressed(MouseEvent paramMouseEvent) {
/* 569 */     hideTipWindow();
/* 570 */     this.enterTimer.stop();
/* 571 */     this.showImmediately = false;
/* 572 */     this.insideComponent = null;
/* 573 */     this.mouseEvent = null;
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
/*     */   public void mouseDragged(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(MouseEvent paramMouseEvent) {
/* 594 */     if (this.tipShowing) {
/* 595 */       checkForTipChange(paramMouseEvent);
/*     */     }
/* 597 */     else if (this.showImmediately) {
/* 598 */       JComponent jComponent = (JComponent)paramMouseEvent.getSource();
/* 599 */       this.toolTipText = jComponent.getToolTipText(paramMouseEvent);
/* 600 */       if (this.toolTipText != null) {
/* 601 */         this.preferredLocation = jComponent.getToolTipLocation(paramMouseEvent);
/* 602 */         this.mouseEvent = paramMouseEvent;
/* 603 */         this.insideComponent = jComponent;
/* 604 */         this.exitTimer.stop();
/* 605 */         showTipWindow();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 610 */       this.insideComponent = (JComponent)paramMouseEvent.getSource();
/* 611 */       this.mouseEvent = paramMouseEvent;
/* 612 */       this.toolTipText = null;
/* 613 */       this.enterTimer.restart();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkForTipChange(MouseEvent paramMouseEvent) {
/* 622 */     JComponent jComponent = (JComponent)paramMouseEvent.getSource();
/* 623 */     String str = jComponent.getToolTipText(paramMouseEvent);
/* 624 */     Point point = jComponent.getToolTipLocation(paramMouseEvent);
/*     */     
/* 626 */     if (str != null || point != null) {
/* 627 */       this.mouseEvent = paramMouseEvent;
/* 628 */       if (((str != null && str.equals(this.toolTipText)) || str == null) && ((point != null && point
/* 629 */         .equals(this.preferredLocation)) || point == null)) {
/*     */         
/* 631 */         if (this.tipWindow != null) {
/* 632 */           this.insideTimer.restart();
/*     */         } else {
/* 634 */           this.enterTimer.restart();
/*     */         } 
/*     */       } else {
/* 637 */         this.toolTipText = str;
/* 638 */         this.preferredLocation = point;
/* 639 */         if (this.showImmediately) {
/* 640 */           hideTipWindow();
/* 641 */           showTipWindow();
/* 642 */           this.exitTimer.stop();
/*     */         } else {
/* 644 */           this.enterTimer.restart();
/*     */         } 
/*     */       } 
/*     */     } else {
/* 648 */       this.toolTipText = null;
/* 649 */       this.preferredLocation = null;
/* 650 */       this.mouseEvent = null;
/* 651 */       this.insideComponent = null;
/* 652 */       hideTipWindow();
/* 653 */       this.enterTimer.stop();
/* 654 */       this.exitTimer.restart();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected class insideTimerAction implements ActionListener {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 660 */       if (ToolTipManager.this.insideComponent != null && ToolTipManager.this.insideComponent.isShowing()) {
/*     */         
/* 662 */         if (ToolTipManager.this.toolTipText == null && ToolTipManager.this.mouseEvent != null) {
/* 663 */           ToolTipManager.this.toolTipText = ToolTipManager.this.insideComponent.getToolTipText(ToolTipManager.this.mouseEvent);
/* 664 */           ToolTipManager.this.preferredLocation = ToolTipManager.this.insideComponent.getToolTipLocation(ToolTipManager.this.mouseEvent);
/*     */         } 
/*     */         
/* 667 */         if (ToolTipManager.this.toolTipText != null) {
/* 668 */           ToolTipManager.this.showImmediately = true;
/* 669 */           ToolTipManager.this.showTipWindow();
/*     */         } else {
/*     */           
/* 672 */           ToolTipManager.this.insideComponent = null;
/* 673 */           ToolTipManager.this.toolTipText = null;
/* 674 */           ToolTipManager.this.preferredLocation = null;
/* 675 */           ToolTipManager.this.mouseEvent = null;
/* 676 */           ToolTipManager.this.hideTipWindow();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected class outsideTimerAction implements ActionListener {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 684 */       ToolTipManager.this.showImmediately = false;
/*     */     }
/*     */   }
/*     */   
/*     */   protected class stillInsideTimerAction implements ActionListener {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 690 */       ToolTipManager.this.hideTipWindow();
/* 691 */       ToolTipManager.this.enterTimer.stop();
/* 692 */       ToolTipManager.this.showImmediately = false;
/* 693 */       ToolTipManager.this.insideComponent = null;
/* 694 */       ToolTipManager.this.mouseEvent = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class MoveBeforeEnterListener
/*     */     extends MouseMotionAdapter
/*     */   {
/*     */     private MoveBeforeEnterListener() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 708 */       ToolTipManager.this.initiateToolTip(param1MouseEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   static Frame frameForComponent(Component paramComponent) {
/* 713 */     while (!(paramComponent instanceof Frame)) {
/* 714 */       paramComponent = paramComponent.getParent();
/*     */     }
/* 716 */     return (Frame)paramComponent;
/*     */   }
/*     */   
/*     */   private FocusListener createFocusChangeListener() {
/* 720 */     return new FocusAdapter() {
/*     */         public void focusLost(FocusEvent param1FocusEvent) {
/* 722 */           ToolTipManager.this.hideTipWindow();
/* 723 */           ToolTipManager.this.insideComponent = null;
/* 724 */           JComponent jComponent = (JComponent)param1FocusEvent.getSource();
/* 725 */           jComponent.removeFocusListener(ToolTipManager.this.focusChangeListener);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPopupFitWidth(Rectangle paramRectangle, Component paramComponent) {
/* 734 */     if (paramComponent != null)
/*     */     {
/* 736 */       for (Container container = paramComponent.getParent(); container != null; container = container.getParent()) {
/*     */         
/* 738 */         if (container instanceof JFrame || container instanceof JDialog || container instanceof JWindow)
/*     */         {
/* 740 */           return getWidthAdjust(container.getBounds(), paramRectangle); } 
/* 741 */         if (container instanceof JApplet || container instanceof JInternalFrame) {
/* 742 */           if (this.popupFrameRect == null) {
/* 743 */             this.popupFrameRect = new Rectangle();
/*     */           }
/* 745 */           Point point = container.getLocationOnScreen();
/* 746 */           this.popupFrameRect.setBounds(point.x, point.y, 
/* 747 */               (container.getBounds()).width, 
/* 748 */               (container.getBounds()).height);
/* 749 */           return getWidthAdjust(this.popupFrameRect, paramRectangle);
/*     */         } 
/*     */       } 
/*     */     }
/* 753 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPopupFitHeight(Rectangle paramRectangle, Component paramComponent) {
/* 759 */     if (paramComponent != null)
/*     */     {
/* 761 */       for (Container container = paramComponent.getParent(); container != null; container = container.getParent()) {
/* 762 */         if (container instanceof JFrame || container instanceof JDialog || container instanceof JWindow)
/*     */         {
/* 764 */           return getHeightAdjust(container.getBounds(), paramRectangle); } 
/* 765 */         if (container instanceof JApplet || container instanceof JInternalFrame) {
/* 766 */           if (this.popupFrameRect == null) {
/* 767 */             this.popupFrameRect = new Rectangle();
/*     */           }
/* 769 */           Point point = container.getLocationOnScreen();
/* 770 */           this.popupFrameRect.setBounds(point.x, point.y, 
/* 771 */               (container.getBounds()).width, 
/* 772 */               (container.getBounds()).height);
/* 773 */           return getHeightAdjust(this.popupFrameRect, paramRectangle);
/*     */         } 
/*     */       } 
/*     */     }
/* 777 */     return 0;
/*     */   }
/*     */   
/*     */   private int getHeightAdjust(Rectangle paramRectangle1, Rectangle paramRectangle2) {
/* 781 */     if (paramRectangle2.y >= paramRectangle1.y && paramRectangle2.y + paramRectangle2.height <= paramRectangle1.y + paramRectangle1.height) {
/* 782 */       return 0;
/*     */     }
/* 784 */     return paramRectangle2.y + paramRectangle2.height - paramRectangle1.y + paramRectangle1.height + 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getWidthAdjust(Rectangle paramRectangle1, Rectangle paramRectangle2) {
/* 793 */     if (paramRectangle2.x >= paramRectangle1.x && paramRectangle2.x + paramRectangle2.width <= paramRectangle1.x + paramRectangle1.width) {
/* 794 */       return 0;
/*     */     }
/*     */     
/* 797 */     return paramRectangle2.x + paramRectangle2.width - paramRectangle1.x + paramRectangle1.width + 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void show(JComponent paramJComponent) {
/* 806 */     if (this.tipWindow != null) {
/* 807 */       hideTipWindow();
/* 808 */       this.insideComponent = null;
/*     */     } else {
/*     */       
/* 811 */       hideTipWindow();
/* 812 */       this.enterTimer.stop();
/* 813 */       this.exitTimer.stop();
/* 814 */       this.insideTimer.stop();
/* 815 */       this.insideComponent = paramJComponent;
/* 816 */       if (this.insideComponent != null) {
/* 817 */         this.toolTipText = this.insideComponent.getToolTipText();
/* 818 */         this.preferredLocation = new Point(10, this.insideComponent.getHeight() + 10);
/*     */         
/* 820 */         showTipWindow();
/*     */         
/* 822 */         if (this.focusChangeListener == null) {
/* 823 */           this.focusChangeListener = createFocusChangeListener();
/*     */         }
/* 825 */         this.insideComponent.addFocusListener(this.focusChangeListener);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void hide(JComponent paramJComponent) {
/* 831 */     hideTipWindow();
/* 832 */     paramJComponent.removeFocusListener(this.focusChangeListener);
/* 833 */     this.preferredLocation = null;
/* 834 */     this.insideComponent = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class AccessibilityKeyListener
/*     */     extends KeyAdapter
/*     */   {
/*     */     private AccessibilityKeyListener() {}
/*     */ 
/*     */     
/*     */     public void keyPressed(KeyEvent param1KeyEvent) {
/* 846 */       if (!param1KeyEvent.isConsumed()) {
/* 847 */         JComponent jComponent = (JComponent)param1KeyEvent.getComponent();
/* 848 */         KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(param1KeyEvent);
/* 849 */         if (ToolTipManager.this.hideTip.equals(keyStroke)) {
/* 850 */           if (ToolTipManager.this.tipWindow != null) {
/* 851 */             ToolTipManager.this.hide(jComponent);
/* 852 */             param1KeyEvent.consume();
/*     */           } 
/* 854 */         } else if (ToolTipManager.this.postTip.equals(keyStroke)) {
/*     */           
/* 856 */           ToolTipManager.this.show(jComponent);
/* 857 */           param1KeyEvent.consume();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\ToolTipManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */