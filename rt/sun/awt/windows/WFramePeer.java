/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.MenuBar;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.peer.FramePeer;
/*     */ import java.security.AccessController;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.im.InputMethodManager;
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
/*     */ class WFramePeer
/*     */   extends WWindowPeer
/*     */   implements FramePeer
/*     */ {
/*     */   static {
/*  37 */     initIDs();
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
/*     */   public void setExtendedState(int paramInt) {
/*  51 */     AWTAccessor.getFrameAccessor().setExtendedState((Frame)this.target, paramInt);
/*     */   }
/*     */   public int getExtendedState() {
/*  54 */     return AWTAccessor.getFrameAccessor().getExtendedState((Frame)this.target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final boolean keepOnMinimize = "true".equals(
/*  63 */       AccessController.doPrivileged(new GetPropertyAction("sun.awt.keepWorkingSetOnMinimize")));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaximizedBounds(Rectangle paramRectangle) {
/*  69 */     if (paramRectangle == null) {
/*  70 */       clearMaximizedBounds();
/*     */     } else {
/*  72 */       Rectangle rectangle = (Rectangle)paramRectangle.clone();
/*  73 */       adjustMaximizedBounds(rectangle);
/*  74 */       setMaximizedBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
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
/*     */   private void adjustMaximizedBounds(Rectangle paramRectangle) {
/*  90 */     GraphicsConfiguration graphicsConfiguration1 = getGraphicsConfiguration();
/*     */ 
/*     */     
/*  93 */     GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
/*  94 */     GraphicsConfiguration graphicsConfiguration2 = graphicsDevice.getDefaultConfiguration();
/*     */     
/*  96 */     if (graphicsConfiguration1 != null && graphicsConfiguration1 != graphicsConfiguration2) {
/*  97 */       Rectangle rectangle1 = graphicsConfiguration1.getBounds();
/*  98 */       Rectangle rectangle2 = graphicsConfiguration2.getBounds();
/*     */       
/* 100 */       boolean bool = (rectangle1.width - rectangle2.width > 0 || rectangle1.height - rectangle2.height > 0) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 106 */       if (bool) {
/* 107 */         paramRectangle.width -= rectangle1.width - rectangle2.width;
/* 108 */         paramRectangle.height -= rectangle1.height - rectangle2.height;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean updateGraphicsData(GraphicsConfiguration paramGraphicsConfiguration) {
/* 115 */     boolean bool = super.updateGraphicsData(paramGraphicsConfiguration);
/*     */     
/* 117 */     Rectangle rectangle = AWTAccessor.getFrameAccessor().getMaximizedBounds((Frame)this.target);
/* 118 */     if (rectangle != null) {
/* 119 */       setMaximizedBounds(rectangle);
/*     */     }
/* 121 */     return bool;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isTargetUndecorated() {
/* 126 */     return ((Frame)this.target).isUndecorated();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 131 */     if (((Frame)this.target).isUndecorated()) {
/* 132 */       super.reshape(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } else {
/* 134 */       reshapeFrame(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 140 */     Dimension dimension = new Dimension();
/* 141 */     if (!((Frame)this.target).isUndecorated()) {
/* 142 */       dimension.setSize(getSysMinWidth(), getSysMinHeight());
/*     */     }
/* 144 */     if (((Frame)this.target).getMenuBar() != null) {
/* 145 */       dimension.height += getSysMenuHeight();
/*     */     }
/* 147 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMenuBar(MenuBar paramMenuBar) {
/* 155 */     WMenuBarPeer wMenuBarPeer = (WMenuBarPeer)WToolkit.targetToPeer(paramMenuBar);
/* 156 */     if (wMenuBarPeer != null) {
/* 157 */       if (wMenuBarPeer.framePeer != this) {
/* 158 */         paramMenuBar.removeNotify();
/* 159 */         paramMenuBar.addNotify();
/* 160 */         wMenuBarPeer = (WMenuBarPeer)WToolkit.targetToPeer(paramMenuBar);
/* 161 */         if (wMenuBarPeer != null && wMenuBarPeer.framePeer != this) {
/* 162 */           throw new IllegalStateException("Wrong parent peer");
/*     */         }
/*     */       } 
/* 165 */       if (wMenuBarPeer != null) {
/* 166 */         addChildPeer(wMenuBarPeer);
/*     */       }
/*     */     } 
/* 169 */     setMenuBar0(wMenuBarPeer);
/* 170 */     updateInsets(this.insets_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WFramePeer(Frame paramFrame) {
/* 181 */     super(paramFrame);
/*     */     
/* 183 */     InputMethodManager inputMethodManager = InputMethodManager.getInstance();
/* 184 */     String str = inputMethodManager.getTriggerMenuString();
/* 185 */     if (str != null)
/*     */     {
/* 187 */       pSetIMMOption(str);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void create(WComponentPeer paramWComponentPeer) {
/* 194 */     preCreate(paramWComponentPeer);
/* 195 */     createAwtFrame(paramWComponentPeer);
/*     */   }
/*     */ 
/*     */   
/*     */   void initialize() {
/* 200 */     super.initialize();
/*     */     
/* 202 */     Frame frame = (Frame)this.target;
/*     */     
/* 204 */     if (frame.getTitle() != null) {
/* 205 */       setTitle(frame.getTitle());
/*     */     }
/* 207 */     setResizable(frame.isResizable());
/* 208 */     setState(frame.getExtendedState());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void notifyIMMOptionChange() {
/* 215 */     InputMethodManager.getInstance().notifyChangeRequest((Component)this.target);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBoundsPrivate(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 220 */     setBounds(paramInt1, paramInt2, paramInt3, paramInt4, 3);
/*     */   }
/*     */   
/*     */   public Rectangle getBoundsPrivate() {
/* 224 */     return getBounds();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void emulateActivation(boolean paramBoolean) {
/* 230 */     synthesizeWmActivate(paramBoolean);
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   public native void setState(int paramInt);
/*     */   
/*     */   public native int getState();
/*     */   
/*     */   private native void setMaximizedBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   private native void clearMaximizedBounds();
/*     */   
/*     */   private native void setMenuBar0(WMenuBarPeer paramWMenuBarPeer);
/*     */   
/*     */   native void createAwtFrame(WComponentPeer paramWComponentPeer);
/*     */   
/*     */   private static native int getSysMenuHeight();
/*     */   
/*     */   native void pSetIMMOption(String paramString);
/*     */   
/*     */   private native void synthesizeWmActivate(boolean paramBoolean);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WFramePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */