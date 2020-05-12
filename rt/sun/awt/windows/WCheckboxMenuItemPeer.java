/*    */ package sun.awt.windows;
/*    */ 
/*    */ import java.awt.CheckboxMenuItem;
/*    */ import java.awt.event.ItemEvent;
/*    */ import java.awt.peer.CheckboxMenuItemPeer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ final class WCheckboxMenuItemPeer
/*    */   extends WMenuItemPeer
/*    */   implements CheckboxMenuItemPeer
/*    */ {
/*    */   public native void setState(boolean paramBoolean);
/*    */   
/*    */   WCheckboxMenuItemPeer(CheckboxMenuItem paramCheckboxMenuItem) {
/* 42 */     super(paramCheckboxMenuItem, true);
/* 43 */     setState(paramCheckboxMenuItem.getState());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleAction(final boolean state) {
/* 49 */     final CheckboxMenuItem target = (CheckboxMenuItem)this.target;
/* 50 */     WToolkit.executeOnEventHandlerThread(checkboxMenuItem, new Runnable()
/*    */         {
/*    */           public void run() {
/* 53 */             target.setState(state);
/* 54 */             WCheckboxMenuItemPeer.this.postEvent(new ItemEvent(target, 701, target
/* 55 */                   .getLabel(), state ? 1 : 2));
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WCheckboxMenuItemPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */