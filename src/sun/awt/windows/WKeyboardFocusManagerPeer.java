/*    */ package sun.awt.windows;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Window;
/*    */ import java.awt.peer.ComponentPeer;
/*    */ import sun.awt.CausedFocusEvent;
/*    */ import sun.awt.KeyboardFocusManagerPeerImpl;
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
/*    */ final class WKeyboardFocusManagerPeer
/*    */   extends KeyboardFocusManagerPeerImpl
/*    */ {
/* 39 */   private static final WKeyboardFocusManagerPeer inst = new WKeyboardFocusManagerPeer();
/*    */   
/*    */   public static WKeyboardFocusManagerPeer getInstance() {
/* 42 */     return inst;
/*    */   }
/*    */   static native void setNativeFocusOwner(ComponentPeer paramComponentPeer);
/*    */   static native Component getNativeFocusOwner();
/*    */   
/*    */   static native Window getNativeFocusedWindow();
/*    */   
/*    */   public void setCurrentFocusOwner(Component paramComponent) {
/* 50 */     setNativeFocusOwner((paramComponent != null) ? paramComponent.getPeer() : null);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getCurrentFocusOwner() {
/* 55 */     return getNativeFocusOwner();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCurrentFocusedWindow(Window paramWindow) {
/* 61 */     throw new RuntimeException("not implemented");
/*    */   }
/*    */ 
/*    */   
/*    */   public Window getCurrentFocusedWindow() {
/* 66 */     return getNativeFocusedWindow();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean deliverFocus(Component paramComponent1, Component paramComponent2, boolean paramBoolean1, boolean paramBoolean2, long paramLong, CausedFocusEvent.Cause paramCause) {
/* 77 */     return KeyboardFocusManagerPeerImpl.deliverFocus(paramComponent1, paramComponent2, paramBoolean1, paramBoolean2, paramLong, paramCause, 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 83 */         getNativeFocusOwner());
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WKeyboardFocusManagerPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */