/*    */ package sun.awt.windows;
/*    */ 
/*    */ import java.awt.Menu;
/*    */ import java.awt.MenuBar;
/*    */ import java.awt.peer.MenuBarPeer;
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
/*    */ 
/*    */ 
/*    */ final class WMenuBarPeer
/*    */   extends WMenuPeer
/*    */   implements MenuBarPeer
/*    */ {
/*    */   final WFramePeer framePeer;
/*    */   
/*    */   public native void addMenu(Menu paramMenu);
/*    */   
/*    */   public native void delMenu(int paramInt);
/*    */   
/*    */   public void addHelpMenu(Menu paramMenu) {
/* 43 */     addMenu(paramMenu);
/*    */   }
/*    */ 
/*    */   
/*    */   WMenuBarPeer(MenuBar paramMenuBar) {
/* 48 */     this.target = paramMenuBar;
/* 49 */     this
/* 50 */       .framePeer = (WFramePeer)WToolkit.targetToPeer(paramMenuBar.getParent());
/* 51 */     if (this.framePeer != null) {
/* 52 */       this.framePeer.addChildPeer(this);
/*    */     }
/* 54 */     create(this.framePeer);
/*    */     
/* 56 */     checkMenuCreation();
/*    */   }
/*    */   
/*    */   native void create(WFramePeer paramWFramePeer);
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WMenuBarPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */