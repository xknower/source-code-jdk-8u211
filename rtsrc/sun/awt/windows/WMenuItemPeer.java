/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Font;
/*     */ import java.awt.MenuItem;
/*     */ import java.awt.MenuShortcut;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.peer.MenuItemPeer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WMenuItemPeer
/*     */   extends WObjectPeer
/*     */   implements MenuItemPeer
/*     */ {
/*  37 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.WMenuItemPeer");
/*     */   
/*     */   static {
/*  40 */     initIDs();
/*     */   }
/*     */ 
/*     */   
/*     */   String shortcutLabel;
/*     */   
/*     */   protected WMenuPeer parent;
/*     */   
/*     */   private final boolean isCheckbox;
/*     */ 
/*     */   
/*     */   protected void disposeImpl() {
/*  52 */     WToolkit.targetDisposedPeer(this.target, this);
/*  53 */     _dispose();
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean paramBoolean) {
/*  57 */     enable(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enable() {
/*  64 */     enable(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disable() {
/*  71 */     enable(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readShortcutLabel() {
/*  76 */     WMenuPeer wMenuPeer = this.parent;
/*  77 */     while (wMenuPeer != null && !(wMenuPeer instanceof WMenuBarPeer)) {
/*  78 */       wMenuPeer = wMenuPeer.parent;
/*     */     }
/*  80 */     if (wMenuPeer instanceof WMenuBarPeer) {
/*  81 */       MenuShortcut menuShortcut = ((MenuItem)this.target).getShortcut();
/*  82 */       this.shortcutLabel = (menuShortcut != null) ? menuShortcut.toString() : null;
/*     */     } else {
/*  84 */       this.shortcutLabel = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLabel(String paramString) {
/*  90 */     readShortcutLabel();
/*  91 */     _setLabel(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WMenuItemPeer() {
/* 100 */     this.isCheckbox = false;
/*     */   }
/*     */   WMenuItemPeer(MenuItem paramMenuItem) {
/* 103 */     this(paramMenuItem, false);
/*     */   }
/*     */   
/*     */   WMenuItemPeer(MenuItem paramMenuItem, boolean paramBoolean) {
/* 107 */     this.target = paramMenuItem;
/* 108 */     this.parent = (WMenuPeer)WToolkit.targetToPeer(paramMenuItem.getParent());
/* 109 */     this.isCheckbox = paramBoolean;
/* 110 */     this.parent.addChildPeer(this);
/* 111 */     create(this.parent);
/*     */     
/* 113 */     checkMenuCreation();
/*     */     
/* 115 */     readShortcutLabel();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void checkMenuCreation() {
/* 121 */     if (this.pData == 0L) {
/*     */       
/* 123 */       if (this.createError != null)
/*     */       {
/* 125 */         throw this.createError;
/*     */       }
/*     */ 
/*     */       
/* 129 */       throw new InternalError("couldn't create menu peer");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void postEvent(AWTEvent paramAWTEvent) {
/* 139 */     WToolkit.postEvent(WToolkit.targetToAppContext(this.target), paramAWTEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void handleAction(final long when, final int modifiers) {
/* 149 */     WToolkit.executeOnEventHandlerThread(this.target, new Runnable() {
/*     */           public void run() {
/* 151 */             WMenuItemPeer.this.postEvent(new ActionEvent(WMenuItemPeer.this.target, 1001, ((MenuItem)WMenuItemPeer.this.target)
/*     */                   
/* 153 */                   .getActionCommand(), when, modifiers));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   private static Font defaultMenuFont = AccessController.<Font>doPrivileged(new PrivilegedAction<Font>()
/*     */       {
/*     */         public Font run() {
/*     */           try {
/* 166 */             ResourceBundle resourceBundle = ResourceBundle.getBundle("sun.awt.windows.awtLocalization");
/* 167 */             return Font.decode(resourceBundle.getString("menuFont"));
/* 168 */           } catch (MissingResourceException missingResourceException) {
/* 169 */             if (WMenuItemPeer.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 170 */               WMenuItemPeer.log.fine("WMenuItemPeer: " + missingResourceException.getMessage() + ". Using default MenuItem font.", missingResourceException);
/*     */             }
/* 172 */             return new Font("SanSerif", 0, 11);
/*     */           } 
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   static Font getDefaultFont() {
/* 179 */     return defaultMenuFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font paramFont) {
/* 190 */     _setFont(paramFont);
/*     */   }
/*     */   
/*     */   private synchronized native void _dispose();
/*     */   
/*     */   public native void _setLabel(String paramString);
/*     */   
/*     */   native void create(WMenuPeer paramWMenuPeer);
/*     */   
/*     */   native void enable(boolean paramBoolean);
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private native void _setFont(Font paramFont);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WMenuItemPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */