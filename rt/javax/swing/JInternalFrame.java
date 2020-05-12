/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FocusTraversalPolicy;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Rectangle;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.PropertyVetoException;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleValue;
/*      */ import javax.swing.event.InternalFrameEvent;
/*      */ import javax.swing.event.InternalFrameListener;
/*      */ import javax.swing.plaf.DesktopIconUI;
/*      */ import javax.swing.plaf.InternalFrameUI;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.swing.SwingUtilities2;
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
/*      */ public class JInternalFrame
/*      */   extends JComponent
/*      */   implements Accessible, WindowConstants, RootPaneContainer
/*      */ {
/*      */   private static final String uiClassID = "InternalFrameUI";
/*      */   protected JRootPane rootPane;
/*      */   protected boolean rootPaneCheckingEnabled = false;
/*      */   protected boolean closable;
/*      */   protected boolean isClosed;
/*      */   protected boolean maximizable;
/*      */   protected boolean isMaximum;
/*      */   protected boolean iconable;
/*      */   protected boolean isIcon;
/*      */   protected boolean resizable;
/*      */   protected boolean isSelected;
/*      */   protected Icon frameIcon;
/*      */   protected String title;
/*      */   protected JDesktopIcon desktopIcon;
/*      */   private Cursor lastCursor;
/*      */   private boolean opened;
/*  186 */   private Rectangle normalBounds = null;
/*      */   
/*  188 */   private int defaultCloseOperation = 2;
/*      */ 
/*      */   
/*      */   private Component lastFocusOwner;
/*      */ 
/*      */   
/*      */   public static final String CONTENT_PANE_PROPERTY = "contentPane";
/*      */ 
/*      */   
/*      */   public static final String MENU_BAR_PROPERTY = "JMenuBar";
/*      */ 
/*      */   
/*      */   public static final String TITLE_PROPERTY = "title";
/*      */ 
/*      */   
/*      */   public static final String LAYERED_PANE_PROPERTY = "layeredPane";
/*      */ 
/*      */   
/*      */   public static final String ROOT_PANE_PROPERTY = "rootPane";
/*      */ 
/*      */   
/*      */   public static final String GLASS_PANE_PROPERTY = "glassPane";
/*      */ 
/*      */   
/*      */   public static final String FRAME_ICON_PROPERTY = "frameIcon";
/*      */ 
/*      */   
/*      */   public static final String IS_SELECTED_PROPERTY = "selected";
/*      */ 
/*      */   
/*      */   public static final String IS_CLOSED_PROPERTY = "closed";
/*      */ 
/*      */   
/*      */   public static final String IS_MAXIMUM_PROPERTY = "maximum";
/*      */   
/*      */   public static final String IS_ICON_PROPERTY = "icon";
/*      */   
/*  225 */   private static final Object PROPERTY_CHANGE_LISTENER_KEY = new StringBuilder("InternalFramePropertyChangeListener"); boolean isDragging;
/*      */   boolean danger;
/*      */   
/*      */   private static void addPropertyChangeListenerIfNecessary() {
/*  229 */     if (AppContext.getAppContext().get(PROPERTY_CHANGE_LISTENER_KEY) == null) {
/*      */       
/*  231 */       FocusPropertyChangeListener focusPropertyChangeListener = new FocusPropertyChangeListener();
/*      */ 
/*      */       
/*  234 */       AppContext.getAppContext().put(PROPERTY_CHANGE_LISTENER_KEY, focusPropertyChangeListener);
/*      */ 
/*      */       
/*  237 */       KeyboardFocusManager.getCurrentKeyboardFocusManager()
/*  238 */         .addPropertyChangeListener(focusPropertyChangeListener);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static class FocusPropertyChangeListener implements PropertyChangeListener { private FocusPropertyChangeListener() {}
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  245 */       if (param1PropertyChangeEvent.getPropertyName() == "permanentFocusOwner") {
/*  246 */         JInternalFrame.updateLastFocusOwner((Component)param1PropertyChangeEvent.getNewValue());
/*      */       }
/*      */     } }
/*      */ 
/*      */   
/*      */   private static void updateLastFocusOwner(Component paramComponent) {
/*  252 */     if (paramComponent != null) {
/*  253 */       Component component = paramComponent;
/*  254 */       while (component != null && !(component instanceof java.awt.Window)) {
/*  255 */         if (component instanceof JInternalFrame)
/*      */         {
/*  257 */           ((JInternalFrame)component).setLastFocusOwner(paramComponent);
/*      */         }
/*  259 */         component = component.getParent();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JInternalFrame() {
/*  269 */     this("", false, false, false, false);
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
/*      */   public JInternalFrame(String paramString) {
/*  282 */     this(paramString, false, false, false, false);
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
/*      */   public JInternalFrame(String paramString, boolean paramBoolean) {
/*  294 */     this(paramString, paramBoolean, false, false, false);
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
/*      */   public JInternalFrame(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/*  307 */     this(paramString, paramBoolean1, paramBoolean2, false, false);
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
/*      */   public JInternalFrame(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/*  322 */     this(paramString, paramBoolean1, paramBoolean2, paramBoolean3, false);
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
/*      */   protected JRootPane createRootPane() {
/*  362 */     return new JRootPane();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InternalFrameUI getUI() {
/*  372 */     return (InternalFrameUI)this.ui;
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
/*      */   public void setUI(InternalFrameUI paramInternalFrameUI) {
/*  385 */     boolean bool = isRootPaneCheckingEnabled();
/*      */     try {
/*  387 */       setRootPaneCheckingEnabled(false);
/*  388 */       setUI(paramInternalFrameUI);
/*      */     } finally {
/*      */       
/*  391 */       setRootPaneCheckingEnabled(bool);
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
/*      */   public void updateUI() {
/*  404 */     setUI((InternalFrameUI)UIManager.getUI(this));
/*  405 */     invalidate();
/*  406 */     if (this.desktopIcon != null) {
/*  407 */       this.desktopIcon.updateUIWhenHidden();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void updateUIWhenHidden() {
/*  416 */     setUI((InternalFrameUI)UIManager.getUI(this));
/*  417 */     invalidate();
/*  418 */     Component[] arrayOfComponent = getComponents();
/*  419 */     if (arrayOfComponent != null) {
/*  420 */       for (Component component : arrayOfComponent) {
/*  421 */         SwingUtilities.updateComponentTreeUI(component);
/*      */       }
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
/*      */   public String getUIClassID() {
/*  440 */     return "InternalFrameUI";
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
/*      */   protected boolean isRootPaneCheckingEnabled() {
/*  456 */     return this.rootPaneCheckingEnabled;
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
/*      */   protected void setRootPaneCheckingEnabled(boolean paramBoolean) {
/*  476 */     this.rootPaneCheckingEnabled = paramBoolean;
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
/*      */   protected void addImpl(Component paramComponent, Object paramObject, int paramInt) {
/*  499 */     if (isRootPaneCheckingEnabled()) {
/*  500 */       getContentPane().add(paramComponent, paramObject, paramInt);
/*      */     } else {
/*      */       
/*  503 */       super.addImpl(paramComponent, paramObject, paramInt);
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
/*      */   public void remove(Component paramComponent) {
/*  518 */     int i = getComponentCount();
/*  519 */     super.remove(paramComponent);
/*  520 */     if (i == getComponentCount()) {
/*  521 */       getContentPane().remove(paramComponent);
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
/*      */   public void setLayout(LayoutManager paramLayoutManager) {
/*  537 */     if (isRootPaneCheckingEnabled()) {
/*  538 */       getContentPane().setLayout(paramLayoutManager);
/*      */     } else {
/*      */       
/*  541 */       super.setLayout(paramLayoutManager);
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
/*      */   @Deprecated
/*      */   public JMenuBar getMenuBar() {
/*  561 */     return getRootPane().getMenuBar();
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
/*      */   public JMenuBar getJMenuBar() {
/*  573 */     return getRootPane().getJMenuBar();
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
/*      */   @Deprecated
/*      */   public void setMenuBar(JMenuBar paramJMenuBar) {
/*  586 */     JMenuBar jMenuBar = getMenuBar();
/*  587 */     getRootPane().setJMenuBar(paramJMenuBar);
/*  588 */     firePropertyChange("JMenuBar", jMenuBar, paramJMenuBar);
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
/*      */   public void setJMenuBar(JMenuBar paramJMenuBar) {
/*  603 */     JMenuBar jMenuBar = getMenuBar();
/*  604 */     getRootPane().setJMenuBar(paramJMenuBar);
/*  605 */     firePropertyChange("JMenuBar", jMenuBar, paramJMenuBar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Container getContentPane() {
/*  614 */     return getRootPane().getContentPane();
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
/*      */   public void setContentPane(Container paramContainer) {
/*  634 */     Container container = getContentPane();
/*  635 */     getRootPane().setContentPane(paramContainer);
/*  636 */     firePropertyChange("contentPane", container, paramContainer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JLayeredPane getLayeredPane() {
/*  647 */     return getRootPane().getLayeredPane();
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
/*      */   public void setLayeredPane(JLayeredPane paramJLayeredPane) {
/*  665 */     JLayeredPane jLayeredPane = getLayeredPane();
/*  666 */     getRootPane().setLayeredPane(paramJLayeredPane);
/*  667 */     firePropertyChange("layeredPane", jLayeredPane, paramJLayeredPane);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component getGlassPane() {
/*  677 */     return getRootPane().getGlassPane();
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
/*      */   public void setGlassPane(Component paramComponent) {
/*  692 */     Component component = getGlassPane();
/*  693 */     getRootPane().setGlassPane(paramComponent);
/*  694 */     firePropertyChange("glassPane", component, paramComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JRootPane getRootPane() {
/*  704 */     return this.rootPane;
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
/*      */   protected void setRootPane(JRootPane paramJRootPane) {
/*  720 */     if (this.rootPane != null) {
/*  721 */       remove(this.rootPane);
/*      */     }
/*  723 */     JRootPane jRootPane = getRootPane();
/*  724 */     this.rootPane = paramJRootPane;
/*  725 */     if (this.rootPane != null) {
/*  726 */       boolean bool = isRootPaneCheckingEnabled();
/*      */       try {
/*  728 */         setRootPaneCheckingEnabled(false);
/*  729 */         add(this.rootPane, "Center");
/*      */       } finally {
/*      */         
/*  732 */         setRootPaneCheckingEnabled(bool);
/*      */       } 
/*      */     } 
/*  735 */     firePropertyChange("rootPane", jRootPane, paramJRootPane);
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
/*      */   public void setClosable(boolean paramBoolean) {
/*  748 */     Boolean bool1 = this.closable ? Boolean.TRUE : Boolean.FALSE;
/*  749 */     Boolean bool2 = paramBoolean ? Boolean.TRUE : Boolean.FALSE;
/*  750 */     this.closable = paramBoolean;
/*  751 */     firePropertyChange("closable", bool1, bool2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isClosable() {
/*  760 */     return this.closable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isClosed() {
/*  768 */     return this.isClosed;
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
/*      */   public void setClosed(boolean paramBoolean) throws PropertyVetoException {
/*  815 */     if (this.isClosed == paramBoolean) {
/*      */       return;
/*      */     }
/*      */     
/*  819 */     Boolean bool1 = this.isClosed ? Boolean.TRUE : Boolean.FALSE;
/*  820 */     Boolean bool2 = paramBoolean ? Boolean.TRUE : Boolean.FALSE;
/*  821 */     if (paramBoolean) {
/*  822 */       fireInternalFrameEvent(25550);
/*      */     }
/*  824 */     fireVetoableChange("closed", bool1, bool2);
/*  825 */     this.isClosed = paramBoolean;
/*  826 */     if (this.isClosed) {
/*  827 */       setVisible(false);
/*      */     }
/*  829 */     firePropertyChange("closed", bool1, bool2);
/*  830 */     if (this.isClosed) {
/*  831 */       dispose();
/*  832 */     } else if (!this.opened) {
/*      */     
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
/*      */   public void setResizable(boolean paramBoolean) {
/*  852 */     Boolean bool1 = this.resizable ? Boolean.TRUE : Boolean.FALSE;
/*  853 */     Boolean bool2 = paramBoolean ? Boolean.TRUE : Boolean.FALSE;
/*  854 */     this.resizable = paramBoolean;
/*  855 */     firePropertyChange("resizable", bool1, bool2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isResizable() {
/*  866 */     return this.isMaximum ? false : this.resizable;
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
/*      */   public void setIconifiable(boolean paramBoolean) {
/*  884 */     Boolean bool1 = this.iconable ? Boolean.TRUE : Boolean.FALSE;
/*  885 */     Boolean bool2 = paramBoolean ? Boolean.TRUE : Boolean.FALSE;
/*  886 */     this.iconable = paramBoolean;
/*  887 */     firePropertyChange("iconable", bool1, bool2);
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
/*      */   public boolean isIconifiable() {
/*  899 */     return this.iconable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isIcon() {
/*  908 */     return this.isIcon;
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
/*      */   public void setIcon(boolean paramBoolean) throws PropertyVetoException {
/*  933 */     if (this.isIcon == paramBoolean) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  943 */     firePropertyChange("ancestor", (Object)null, getParent());
/*      */     
/*  945 */     Boolean bool1 = this.isIcon ? Boolean.TRUE : Boolean.FALSE;
/*  946 */     Boolean bool2 = paramBoolean ? Boolean.TRUE : Boolean.FALSE;
/*  947 */     fireVetoableChange("icon", bool1, bool2);
/*  948 */     this.isIcon = paramBoolean;
/*  949 */     firePropertyChange("icon", bool1, bool2);
/*  950 */     if (paramBoolean) {
/*  951 */       fireInternalFrameEvent(25552);
/*      */     } else {
/*  953 */       fireInternalFrameEvent(25553);
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
/*      */   public void setMaximizable(boolean paramBoolean) {
/*  971 */     Boolean bool1 = this.maximizable ? Boolean.TRUE : Boolean.FALSE;
/*  972 */     Boolean bool2 = paramBoolean ? Boolean.TRUE : Boolean.FALSE;
/*  973 */     this.maximizable = paramBoolean;
/*  974 */     firePropertyChange("maximizable", bool1, bool2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMaximizable() {
/*  984 */     return this.maximizable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMaximum() {
/*  993 */     return this.isMaximum;
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
/*      */   public void setMaximum(boolean paramBoolean) throws PropertyVetoException {
/* 1013 */     if (this.isMaximum == paramBoolean) {
/*      */       return;
/*      */     }
/*      */     
/* 1017 */     Boolean bool1 = this.isMaximum ? Boolean.TRUE : Boolean.FALSE;
/* 1018 */     Boolean bool2 = paramBoolean ? Boolean.TRUE : Boolean.FALSE;
/* 1019 */     fireVetoableChange("maximum", bool1, bool2);
/*      */ 
/*      */ 
/*      */     
/* 1023 */     this.isMaximum = paramBoolean;
/* 1024 */     firePropertyChange("maximum", bool1, bool2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTitle() {
/* 1034 */     return this.title;
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
/*      */   public void setTitle(String paramString) {
/* 1049 */     String str = this.title;
/* 1050 */     this.title = paramString;
/* 1051 */     firePropertyChange("title", str, paramString);
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
/*      */   public void setSelected(boolean paramBoolean) throws PropertyVetoException {
/* 1088 */     if (paramBoolean && this.isSelected) {
/* 1089 */       restoreSubcomponentFocus();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1094 */     if (this.isSelected == paramBoolean || (paramBoolean && (this.isIcon ? 
/* 1095 */       !this.desktopIcon.isShowing() : !isShowing()))) {
/*      */       return;
/*      */     }
/*      */     
/* 1099 */     Boolean bool1 = this.isSelected ? Boolean.TRUE : Boolean.FALSE;
/* 1100 */     Boolean bool2 = paramBoolean ? Boolean.TRUE : Boolean.FALSE;
/* 1101 */     fireVetoableChange("selected", bool1, bool2);
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
/* 1114 */     if (paramBoolean) {
/* 1115 */       restoreSubcomponentFocus();
/*      */     }
/*      */     
/* 1118 */     this.isSelected = paramBoolean;
/* 1119 */     firePropertyChange("selected", bool1, bool2);
/* 1120 */     if (this.isSelected) {
/* 1121 */       fireInternalFrameEvent(25554);
/*      */     } else {
/* 1123 */       fireInternalFrameEvent(25555);
/* 1124 */     }  repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSelected() {
/* 1135 */     return this.isSelected;
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
/*      */   public void setFrameIcon(Icon paramIcon) {
/* 1158 */     Icon icon = this.frameIcon;
/* 1159 */     this.frameIcon = paramIcon;
/* 1160 */     firePropertyChange("frameIcon", icon, paramIcon);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getFrameIcon() {
/* 1171 */     return this.frameIcon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveToFront() {
/* 1179 */     if (isIcon()) {
/* 1180 */       if (getDesktopIcon().getParent() instanceof JLayeredPane) {
/* 1181 */         ((JLayeredPane)getDesktopIcon().getParent())
/* 1182 */           .moveToFront(getDesktopIcon());
/*      */       }
/*      */     }
/* 1185 */     else if (getParent() instanceof JLayeredPane) {
/* 1186 */       ((JLayeredPane)getParent()).moveToFront(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveToBack() {
/* 1195 */     if (isIcon()) {
/* 1196 */       if (getDesktopIcon().getParent() instanceof JLayeredPane) {
/* 1197 */         ((JLayeredPane)getDesktopIcon().getParent())
/* 1198 */           .moveToBack(getDesktopIcon());
/*      */       }
/*      */     }
/* 1201 */     else if (getParent() instanceof JLayeredPane) {
/* 1202 */       ((JLayeredPane)getParent()).moveToBack(this);
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
/*      */   public Cursor getLastCursor() {
/* 1215 */     return this.lastCursor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCursor(Cursor paramCursor) {
/* 1223 */     if (paramCursor == null) {
/* 1224 */       this.lastCursor = null;
/* 1225 */       super.setCursor(paramCursor);
/*      */       return;
/*      */     } 
/* 1228 */     int i = paramCursor.getType();
/* 1229 */     if (i != 4 && i != 5 && i != 6 && i != 7 && i != 8 && i != 9 && i != 10 && i != 11)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1237 */       this.lastCursor = paramCursor;
/*      */     }
/* 1239 */     super.setCursor(paramCursor);
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
/*      */   public void setLayer(Integer paramInteger) {
/* 1253 */     if (getParent() != null && getParent() instanceof JLayeredPane) {
/*      */ 
/*      */       
/* 1256 */       JLayeredPane jLayeredPane = (JLayeredPane)getParent();
/* 1257 */       jLayeredPane.setLayer(this, paramInteger.intValue(), jLayeredPane.getPosition(this));
/*      */     } else {
/*      */       
/* 1260 */       JLayeredPane.putLayer(this, paramInteger.intValue());
/* 1261 */       if (getParent() != null) {
/* 1262 */         getParent().repaint(getX(), getY(), getWidth(), getHeight());
/*      */       }
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
/*      */   public void setLayer(int paramInt) {
/* 1284 */     setLayer(Integer.valueOf(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLayer() {
/* 1295 */     return JLayeredPane.getLayer(this);
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
/*      */   public JDesktopPane getDesktopPane() {
/* 1310 */     Container container = getParent();
/* 1311 */     while (container != null && !(container instanceof JDesktopPane)) {
/* 1312 */       container = container.getParent();
/*      */     }
/* 1314 */     if (container == null) {
/*      */       
/* 1316 */       container = getDesktopIcon().getParent();
/* 1317 */       while (container != null && !(container instanceof JDesktopPane)) {
/* 1318 */         container = container.getParent();
/*      */       }
/*      */     } 
/* 1321 */     return (JDesktopPane)container;
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
/*      */   public void setDesktopIcon(JDesktopIcon paramJDesktopIcon) {
/* 1335 */     JDesktopIcon jDesktopIcon = getDesktopIcon();
/* 1336 */     this.desktopIcon = paramJDesktopIcon;
/* 1337 */     firePropertyChange("desktopIcon", jDesktopIcon, paramJDesktopIcon);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JDesktopIcon getDesktopIcon() {
/* 1348 */     return this.desktopIcon;
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
/*      */   public Rectangle getNormalBounds() {
/* 1368 */     if (this.normalBounds != null) {
/* 1369 */       return this.normalBounds;
/*      */     }
/* 1371 */     return getBounds();
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
/*      */   public void setNormalBounds(Rectangle paramRectangle) {
/* 1384 */     this.normalBounds = paramRectangle;
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
/*      */   public Component getFocusOwner() {
/* 1396 */     if (isSelected()) {
/* 1397 */       return this.lastFocusOwner;
/*      */     }
/* 1399 */     return null;
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
/*      */   public Component getMostRecentFocusOwner() {
/* 1424 */     if (isSelected()) {
/* 1425 */       return getFocusOwner();
/*      */     }
/*      */     
/* 1428 */     if (this.lastFocusOwner != null) {
/* 1429 */       return this.lastFocusOwner;
/*      */     }
/*      */     
/* 1432 */     FocusTraversalPolicy focusTraversalPolicy = getFocusTraversalPolicy();
/* 1433 */     if (focusTraversalPolicy instanceof InternalFrameFocusTraversalPolicy) {
/* 1434 */       return ((InternalFrameFocusTraversalPolicy)focusTraversalPolicy)
/* 1435 */         .getInitialComponent(this);
/*      */     }
/*      */     
/* 1438 */     Component component = focusTraversalPolicy.getDefaultComponent(this);
/* 1439 */     if (component != null) {
/* 1440 */       return component;
/*      */     }
/* 1442 */     return getContentPane();
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
/*      */   public void restoreSubcomponentFocus() {
/* 1454 */     if (isIcon()) {
/* 1455 */       SwingUtilities2.compositeRequestFocus(getDesktopIcon());
/*      */     } else {
/*      */       
/* 1458 */       Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
/* 1459 */       if (component == null || !SwingUtilities.isDescendingFrom(component, this)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1465 */         setLastFocusOwner(getMostRecentFocusOwner());
/* 1466 */         if (this.lastFocusOwner == null)
/*      */         {
/*      */ 
/*      */           
/* 1470 */           setLastFocusOwner(getContentPane());
/*      */         }
/* 1472 */         this.lastFocusOwner.requestFocus();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setLastFocusOwner(Component paramComponent) {
/* 1478 */     this.lastFocusOwner = paramComponent;
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
/*      */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1494 */     super.reshape(paramInt1, paramInt2, paramInt3, paramInt4);
/* 1495 */     validate();
/* 1496 */     repaint();
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
/*      */   public void addInternalFrameListener(InternalFrameListener paramInternalFrameListener) {
/* 1510 */     this.listenerList.add(InternalFrameListener.class, paramInternalFrameListener);
/*      */     
/* 1512 */     enableEvents(0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeInternalFrameListener(InternalFrameListener paramInternalFrameListener) {
/* 1522 */     this.listenerList.remove(InternalFrameListener.class, paramInternalFrameListener);
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
/*      */   public InternalFrameListener[] getInternalFrameListeners() {
/* 1537 */     return this.listenerList.<InternalFrameListener>getListeners(InternalFrameListener.class);
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
/*      */   protected void fireInternalFrameEvent(int paramInt) {
/* 1557 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1558 */     InternalFrameEvent internalFrameEvent = null;
/* 1559 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1560 */       if (arrayOfObject[i] == InternalFrameListener.class) {
/* 1561 */         if (internalFrameEvent == null) {
/* 1562 */           internalFrameEvent = new InternalFrameEvent(this, paramInt);
/*      */         }
/*      */         
/* 1565 */         switch (internalFrameEvent.getID()) {
/*      */           case 25549:
/* 1567 */             ((InternalFrameListener)arrayOfObject[i + 1]).internalFrameOpened(internalFrameEvent);
/*      */             break;
/*      */           case 25550:
/* 1570 */             ((InternalFrameListener)arrayOfObject[i + 1]).internalFrameClosing(internalFrameEvent);
/*      */             break;
/*      */           case 25551:
/* 1573 */             ((InternalFrameListener)arrayOfObject[i + 1]).internalFrameClosed(internalFrameEvent);
/*      */             break;
/*      */           case 25552:
/* 1576 */             ((InternalFrameListener)arrayOfObject[i + 1]).internalFrameIconified(internalFrameEvent);
/*      */             break;
/*      */           case 25553:
/* 1579 */             ((InternalFrameListener)arrayOfObject[i + 1]).internalFrameDeiconified(internalFrameEvent);
/*      */             break;
/*      */           case 25554:
/* 1582 */             ((InternalFrameListener)arrayOfObject[i + 1]).internalFrameActivated(internalFrameEvent);
/*      */             break;
/*      */           case 25555:
/* 1585 */             ((InternalFrameListener)arrayOfObject[i + 1]).internalFrameDeactivated(internalFrameEvent);
/*      */             break;
/*      */         } 
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doDefaultCloseAction() {
/* 1613 */     fireInternalFrameEvent(25550);
/* 1614 */     switch (this.defaultCloseOperation) {
/*      */ 
/*      */       
/*      */       case 1:
/* 1618 */         setVisible(false);
/* 1619 */         if (isSelected()) {
/*      */           try {
/* 1621 */             setSelected(false);
/* 1622 */           } catch (PropertyVetoException propertyVetoException) {}
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/*      */         try {
/* 1629 */           fireVetoableChange("closed", Boolean.FALSE, Boolean.TRUE);
/*      */           
/* 1631 */           this.isClosed = true;
/* 1632 */           setVisible(false);
/* 1633 */           firePropertyChange("closed", Boolean.FALSE, Boolean.TRUE);
/*      */           
/* 1635 */           dispose();
/* 1636 */         } catch (PropertyVetoException propertyVetoException) {}
/*      */         break;
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
/*      */   public void setDefaultCloseOperation(int paramInt) {
/* 1680 */     this.defaultCloseOperation = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDefaultCloseOperation() {
/* 1691 */     return this.defaultCloseOperation;
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
/*      */   public void pack() {
/*      */     try {
/* 1705 */       if (isIcon()) {
/* 1706 */         setIcon(false);
/* 1707 */       } else if (isMaximum()) {
/* 1708 */         setMaximum(false);
/*      */       } 
/* 1710 */     } catch (PropertyVetoException propertyVetoException) {
/*      */       return;
/*      */     } 
/* 1713 */     setSize(getPreferredSize());
/* 1714 */     validate();
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
/*      */   public void show() {
/* 1736 */     if (isVisible()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1742 */     if (!this.opened) {
/* 1743 */       fireInternalFrameEvent(25549);
/* 1744 */       this.opened = true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1749 */     getDesktopIcon().setVisible(true);
/*      */     
/* 1751 */     toFront();
/* 1752 */     super.show();
/*      */     
/* 1754 */     if (this.isIcon) {
/*      */       return;
/*      */     }
/*      */     
/* 1758 */     if (!isSelected()) {
/*      */       try {
/* 1760 */         setSelected(true);
/* 1761 */       } catch (PropertyVetoException propertyVetoException) {}
/*      */     }
/*      */   }
/*      */   
/*      */   public void hide() {
/* 1766 */     if (isIcon()) {
/* 1767 */       getDesktopIcon().setVisible(false);
/*      */     }
/* 1769 */     super.hide();
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
/*      */   public void dispose() {
/* 1790 */     if (isVisible()) {
/* 1791 */       setVisible(false);
/*      */     }
/* 1793 */     if (isSelected()) {
/*      */       try {
/* 1795 */         setSelected(false);
/* 1796 */       } catch (PropertyVetoException propertyVetoException) {}
/*      */     }
/* 1798 */     if (!this.isClosed) {
/* 1799 */       firePropertyChange("closed", Boolean.FALSE, Boolean.TRUE);
/* 1800 */       this.isClosed = true;
/*      */     } 
/* 1802 */     fireInternalFrameEvent(25551);
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
/*      */   public void toFront() {
/* 1815 */     moveToFront();
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
/*      */   public void toBack() {
/* 1828 */     moveToBack();
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
/*      */   public final void setFocusCycleRoot(boolean paramBoolean) {}
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
/*      */   public final boolean isFocusCycleRoot() {
/* 1855 */     return true;
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
/*      */   public final Container getFocusCycleRootAncestor() {
/* 1868 */     return null;
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
/*      */   public final String getWarningString() {
/* 1880 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1889 */     paramObjectOutputStream.defaultWriteObject();
/* 1890 */     if (getUIClassID().equals("InternalFrameUI")) {
/* 1891 */       byte b = JComponent.getWriteObjCounter(this);
/* 1892 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 1893 */       if (b == 0 && this.ui != null) {
/* 1894 */         boolean bool = isRootPaneCheckingEnabled();
/*      */         try {
/* 1896 */           setRootPaneCheckingEnabled(false);
/* 1897 */           this.ui.installUI(this);
/*      */         } finally {
/* 1899 */           setRootPaneCheckingEnabled(bool);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void compWriteObjectNotify() {
/* 1910 */     boolean bool = isRootPaneCheckingEnabled();
/*      */     try {
/* 1912 */       setRootPaneCheckingEnabled(false);
/* 1913 */       super.compWriteObjectNotify();
/*      */     } finally {
/*      */       
/* 1916 */       setRootPaneCheckingEnabled(bool);
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
/*      */   protected String paramString() {
/* 1932 */     String str15, str1 = (this.rootPane != null) ? this.rootPane.toString() : "";
/* 1933 */     String str2 = this.rootPaneCheckingEnabled ? "true" : "false";
/*      */     
/* 1935 */     String str3 = this.closable ? "true" : "false";
/* 1936 */     String str4 = this.isClosed ? "true" : "false";
/* 1937 */     String str5 = this.maximizable ? "true" : "false";
/* 1938 */     String str6 = this.isMaximum ? "true" : "false";
/* 1939 */     String str7 = this.iconable ? "true" : "false";
/* 1940 */     String str8 = this.isIcon ? "true" : "false";
/* 1941 */     String str9 = this.resizable ? "true" : "false";
/* 1942 */     String str10 = this.isSelected ? "true" : "false";
/*      */     
/* 1944 */     String str11 = (this.frameIcon != null) ? this.frameIcon.toString() : "";
/* 1945 */     String str12 = (this.title != null) ? this.title : "";
/*      */ 
/*      */     
/* 1948 */     String str13 = (this.desktopIcon != null) ? this.desktopIcon.toString() : "";
/* 1949 */     String str14 = this.opened ? "true" : "false";
/*      */     
/* 1951 */     if (this.defaultCloseOperation == 1)
/* 1952 */     { str15 = "HIDE_ON_CLOSE"; }
/* 1953 */     else if (this.defaultCloseOperation == 2)
/* 1954 */     { str15 = "DISPOSE_ON_CLOSE"; }
/* 1955 */     else if (this.defaultCloseOperation == 0)
/* 1956 */     { str15 = "DO_NOTHING_ON_CLOSE"; }
/* 1957 */     else { str15 = ""; }
/*      */     
/* 1959 */     return super.paramString() + ",closable=" + str3 + ",defaultCloseOperation=" + str15 + ",desktopIcon=" + str13 + ",frameIcon=" + str11 + ",iconable=" + str7 + ",isClosed=" + str4 + ",isIcon=" + str8 + ",isMaximum=" + str6 + ",isSelected=" + str10 + ",maximizable=" + str5 + ",opened=" + str14 + ",resizable=" + str9 + ",rootPane=" + str1 + ",rootPaneCheckingEnabled=" + str2 + ",title=" + str12;
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
/*      */   public JInternalFrame(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
/* 1979 */     this.isDragging = false;
/* 1980 */     this.danger = false; setRootPane(createRootPane()); setLayout(new BorderLayout()); this.title = paramString; this.resizable = paramBoolean1; this.closable = paramBoolean2; this.maximizable = paramBoolean3; this.isMaximum = false; this.iconable = paramBoolean4;
/*      */     this.isIcon = false;
/*      */     setVisible(false);
/*      */     setRootPaneCheckingEnabled(true);
/*      */     this.desktopIcon = new JDesktopIcon(this);
/*      */     updateUI();
/*      */     SunToolkit.checkAndSetPolicy(this);
/* 1987 */     addPropertyChangeListenerIfNecessary(); } protected void paintComponent(Graphics paramGraphics) { if (this.isDragging)
/*      */     {
/* 1989 */       this.danger = true;
/*      */     }
/*      */     
/* 1992 */     super.paintComponent(paramGraphics); }
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
/*      */   public AccessibleContext getAccessibleContext() {
/* 2015 */     if (this.accessibleContext == null) {
/* 2016 */       this.accessibleContext = new AccessibleJInternalFrame();
/*      */     }
/* 2018 */     return this.accessibleContext;
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
/*      */   protected class AccessibleJInternalFrame
/*      */     extends JComponent.AccessibleJComponent
/*      */     implements AccessibleValue
/*      */   {
/*      */     public String getAccessibleName() {
/* 2047 */       String str = this.accessibleName;
/*      */       
/* 2049 */       if (str == null) {
/* 2050 */         str = (String)JInternalFrame.this.getClientProperty("AccessibleName");
/*      */       }
/* 2052 */       if (str == null) {
/* 2053 */         str = JInternalFrame.this.getTitle();
/*      */       }
/* 2055 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleRole getAccessibleRole() {
/* 2066 */       return AccessibleRole.INTERNAL_FRAME;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleValue getAccessibleValue() {
/* 2078 */       return this;
/*      */     }
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
/*      */     public Number getCurrentAccessibleValue() {
/* 2093 */       return Integer.valueOf(JInternalFrame.this.getLayer());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean setCurrentAccessibleValue(Number param1Number) {
/* 2103 */       if (param1Number == null) {
/* 2104 */         return false;
/*      */       }
/* 2106 */       JInternalFrame.this.setLayer(new Integer(param1Number.intValue()));
/* 2107 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Number getMinimumAccessibleValue() {
/* 2117 */       return Integer.valueOf(-2147483648);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Number getMaximumAccessibleValue() {
/* 2127 */       return Integer.valueOf(2147483647);
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
/*      */   public static class JDesktopIcon
/*      */     extends JComponent
/*      */     implements Accessible
/*      */   {
/*      */     JInternalFrame internalFrame;
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
/*      */     public JDesktopIcon(JInternalFrame param1JInternalFrame) {
/* 2164 */       setVisible(false);
/* 2165 */       setInternalFrame(param1JInternalFrame);
/* 2166 */       updateUI();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DesktopIconUI getUI() {
/* 2176 */       return (DesktopIconUI)this.ui;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setUI(DesktopIconUI param1DesktopIconUI) {
/* 2186 */       setUI(param1DesktopIconUI);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JInternalFrame getInternalFrame() {
/* 2197 */       return this.internalFrame;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setInternalFrame(JInternalFrame param1JInternalFrame) {
/* 2208 */       this.internalFrame = param1JInternalFrame;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JDesktopPane getDesktopPane() {
/* 2219 */       if (getInternalFrame() != null)
/* 2220 */         return getInternalFrame().getDesktopPane(); 
/* 2221 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void updateUI() {
/* 2233 */       boolean bool = (this.ui != null) ? true : false;
/* 2234 */       setUI((DesktopIconUI)UIManager.getUI(this));
/* 2235 */       invalidate();
/*      */       
/* 2237 */       Dimension dimension = getPreferredSize();
/* 2238 */       setSize(dimension.width, dimension.height);
/*      */ 
/*      */       
/* 2241 */       if (this.internalFrame != null && this.internalFrame.getUI() != null) {
/* 2242 */         SwingUtilities.updateComponentTreeUI(this.internalFrame);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void updateUIWhenHidden() {
/* 2251 */       setUI((DesktopIconUI)UIManager.getUI(this));
/*      */       
/* 2253 */       Dimension dimension = getPreferredSize();
/* 2254 */       setSize(dimension.width, dimension.height);
/*      */       
/* 2256 */       invalidate();
/* 2257 */       Component[] arrayOfComponent = getComponents();
/* 2258 */       if (arrayOfComponent != null) {
/* 2259 */         for (Component component : arrayOfComponent) {
/* 2260 */           SwingUtilities.updateComponentTreeUI(component);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getUIClassID() {
/* 2274 */       return "DesktopIconUI";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 2280 */       param1ObjectOutputStream.defaultWriteObject();
/* 2281 */       if (getUIClassID().equals("DesktopIconUI")) {
/* 2282 */         byte b = JComponent.getWriteObjCounter(this);
/* 2283 */         b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 2284 */         if (b == 0 && this.ui != null) {
/* 2285 */           this.ui.installUI(this);
/*      */         }
/*      */       } 
/*      */     }
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
/*      */     public AccessibleContext getAccessibleContext() {
/* 2304 */       if (this.accessibleContext == null) {
/* 2305 */         this.accessibleContext = new AccessibleJDesktopIcon();
/*      */       }
/* 2307 */       return this.accessibleContext;
/*      */     }
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
/*      */     protected class AccessibleJDesktopIcon
/*      */       extends JComponent.AccessibleJComponent
/*      */       implements AccessibleValue
/*      */     {
/*      */       public AccessibleRole getAccessibleRole() {
/* 2336 */         return AccessibleRole.DESKTOP_ICON;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleValue getAccessibleValue() {
/* 2348 */         return this;
/*      */       }
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
/*      */       public Number getCurrentAccessibleValue() {
/* 2362 */         AccessibleContext accessibleContext = JInternalFrame.JDesktopIcon.this.getInternalFrame().getAccessibleContext();
/* 2363 */         AccessibleValue accessibleValue = accessibleContext.getAccessibleValue();
/* 2364 */         if (accessibleValue != null) {
/* 2365 */           return accessibleValue.getCurrentAccessibleValue();
/*      */         }
/* 2367 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean setCurrentAccessibleValue(Number param2Number) {
/* 2378 */         if (param2Number == null) {
/* 2379 */           return false;
/*      */         }
/* 2381 */         AccessibleContext accessibleContext = JInternalFrame.JDesktopIcon.this.getInternalFrame().getAccessibleContext();
/* 2382 */         AccessibleValue accessibleValue = accessibleContext.getAccessibleValue();
/* 2383 */         if (accessibleValue != null) {
/* 2384 */           return accessibleValue.setCurrentAccessibleValue(param2Number);
/*      */         }
/* 2386 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Number getMinimumAccessibleValue() {
/* 2397 */         AccessibleContext accessibleContext = JInternalFrame.JDesktopIcon.this.getInternalFrame().getAccessibleContext();
/* 2398 */         if (accessibleContext instanceof AccessibleValue) {
/* 2399 */           return ((AccessibleValue)accessibleContext).getMinimumAccessibleValue();
/*      */         }
/* 2401 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Number getMaximumAccessibleValue() {
/* 2412 */         AccessibleContext accessibleContext = JInternalFrame.JDesktopIcon.this.getInternalFrame().getAccessibleContext();
/* 2413 */         if (accessibleContext instanceof AccessibleValue) {
/* 2414 */           return ((AccessibleValue)accessibleContext).getMaximumAccessibleValue();
/*      */         }
/* 2416 */         return null;
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\JInternalFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */