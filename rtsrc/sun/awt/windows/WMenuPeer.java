/*    */ package sun.awt.windows;
/*    */ 
/*    */ import java.awt.Menu;
/*    */ import java.awt.MenuContainer;
/*    */ import java.awt.MenuItem;
/*    */ import java.awt.peer.MenuPeer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class WMenuPeer
/*    */   extends WMenuItemPeer
/*    */   implements MenuPeer
/*    */ {
/*    */   public native void addSeparator();
/*    */   
/*    */   public void addItem(MenuItem paramMenuItem) {
/* 38 */     WMenuItemPeer wMenuItemPeer = (WMenuItemPeer)WToolkit.targetToPeer(paramMenuItem);
/*    */   }
/*    */ 
/*    */   
/*    */   public native void delItem(int paramInt);
/*    */ 
/*    */   
/*    */   WMenuPeer() {}
/*    */   
/*    */   WMenuPeer(Menu paramMenu) {
/* 48 */     this.target = paramMenu;
/* 49 */     MenuContainer menuContainer = paramMenu.getParent();
/*    */     
/* 51 */     if (menuContainer instanceof java.awt.MenuBar) {
/* 52 */       WMenuBarPeer wMenuBarPeer = (WMenuBarPeer)WToolkit.targetToPeer(menuContainer);
/* 53 */       this.parent = wMenuBarPeer;
/* 54 */       wMenuBarPeer.addChildPeer(this);
/* 55 */       createMenu(wMenuBarPeer);
/*    */     }
/* 57 */     else if (menuContainer instanceof Menu) {
/* 58 */       this.parent = (WMenuPeer)WToolkit.targetToPeer(menuContainer);
/* 59 */       this.parent.addChildPeer(this);
/* 60 */       createSubMenu(this.parent);
/*    */     } else {
/*    */       
/* 63 */       throw new IllegalArgumentException("unknown menu container class");
/*    */     } 
/*    */     
/* 66 */     checkMenuCreation();
/*    */   }
/*    */   
/*    */   native void createMenu(WMenuBarPeer paramWMenuBarPeer);
/*    */   
/*    */   native void createSubMenu(WMenuPeer paramWMenuPeer);
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WMenuPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */