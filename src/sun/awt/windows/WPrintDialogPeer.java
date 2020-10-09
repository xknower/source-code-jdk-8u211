/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Event;
/*     */ import java.awt.Font;
/*     */ import java.awt.Window;
/*     */ import java.awt.dnd.DropTarget;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.awt.peer.DialogPeer;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.CausedFocusEvent;
/*     */ import sun.java2d.pipe.Region;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WPrintDialogPeer
/*     */   extends WWindowPeer
/*     */   implements DialogPeer
/*     */ {
/*     */   private WComponentPeer parent;
/*     */   
/*     */   static {
/*  39 */     initIDs();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  44 */   private Vector<WWindowPeer> blockedWindows = new Vector<>();
/*     */   
/*     */   WPrintDialogPeer(WPrintDialog paramWPrintDialog) {
/*  47 */     super(paramWPrintDialog);
/*     */   }
/*     */ 
/*     */   
/*     */   void create(WComponentPeer paramWComponentPeer) {
/*  52 */     this.parent = paramWComponentPeer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkCreation() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void disposeImpl() {
/*  63 */     WToolkit.targetDisposedPeer(this.target, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show() {
/*  70 */     (new Thread(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/*  74 */               ((WPrintDialog)WPrintDialogPeer.this.target).setRetVal(WPrintDialogPeer.this._show());
/*  75 */             } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  80 */             ((WPrintDialog)WPrintDialogPeer.this.target).setVisible(false);
/*     */           }
/*  82 */         })).start();
/*     */   }
/*     */   
/*     */   synchronized void setHWnd(long paramLong) {
/*  86 */     this.hwnd = paramLong;
/*  87 */     for (WWindowPeer wWindowPeer : this.blockedWindows) {
/*  88 */       if (paramLong != 0L) {
/*  89 */         wWindowPeer.modalDisable((Dialog)this.target, paramLong); continue;
/*     */       } 
/*  91 */       wWindowPeer.modalEnable((Dialog)this.target);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void blockWindow(WWindowPeer paramWWindowPeer) {
/*  97 */     this.blockedWindows.add(paramWWindowPeer);
/*  98 */     if (this.hwnd != 0L)
/*  99 */       paramWWindowPeer.modalDisable((Dialog)this.target, this.hwnd); 
/*     */   }
/*     */   
/*     */   synchronized void unblockWindow(WWindowPeer paramWWindowPeer) {
/* 103 */     this.blockedWindows.remove(paramWWindowPeer);
/* 104 */     if (this.hwnd != 0L) {
/* 105 */       paramWWindowPeer.modalEnable((Dialog)this.target);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void blockWindows(List<Window> paramList) {
/* 111 */     for (Window window : paramList) {
/* 112 */       WWindowPeer wWindowPeer = (WWindowPeer)AWTAccessor.getComponentAccessor().getPeer(window);
/* 113 */       if (wWindowPeer != null) {
/* 114 */         blockWindow(wWindowPeer);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void initialize() {}
/*     */ 
/*     */   
/*     */   public void updateAlwaysOnTopState() {}
/*     */ 
/*     */   
/*     */   public void setResizable(boolean paramBoolean) {}
/*     */ 
/*     */   
/*     */   void hide() {}
/*     */ 
/*     */   
/*     */   void enable() {}
/*     */ 
/*     */   
/*     */   void disable() {}
/*     */   
/*     */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */   
/*     */   public boolean handleEvent(Event paramEvent) {
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForeground(Color paramColor) {}
/*     */ 
/*     */   
/*     */   public void setBackground(Color paramColor) {}
/*     */ 
/*     */   
/*     */   public boolean requestFocus(boolean paramBoolean1, boolean paramBoolean2) {
/* 152 */     return false;
/*     */   }
/*     */   public void setFont(Font paramFont) {}
/*     */   
/*     */   public void updateMinimumSize() {}
/*     */   
/*     */   public void updateIconImages() {}
/*     */   
/*     */   public boolean requestFocus(Component paramComponent, boolean paramBoolean1, boolean paramBoolean2, long paramLong, CausedFocusEvent.Cause paramCause) {
/* 161 */     return false;
/*     */   }
/*     */   
/*     */   public void updateFocusableWindowState() {}
/*     */   
/*     */   void start() {}
/*     */   
/*     */   public void beginValidate() {}
/*     */   
/*     */   public void endValidate() {}
/*     */   
/*     */   void invalidate(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */   
/*     */   public void addDropTarget(DropTarget paramDropTarget) {}
/*     */   
/*     */   public void removeDropTarget(DropTarget paramDropTarget) {}
/*     */   
/*     */   public void setZOrder(ComponentPeer paramComponentPeer) {}
/*     */   
/*     */   public void applyShape(Region paramRegion) {}
/*     */   
/*     */   public void setOpacity(float paramFloat) {}
/*     */   
/*     */   public void setOpaque(boolean paramBoolean) {}
/*     */   
/*     */   public void updateWindow(BufferedImage paramBufferedImage) {}
/*     */   
/*     */   public void createScreenSurface(boolean paramBoolean) {}
/*     */   
/*     */   public void replaceSurfaceData() {}
/*     */   
/*     */   private native boolean _show();
/*     */   
/*     */   public native void toFront();
/*     */   
/*     */   public native void toBack();
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WPrintDialogPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */