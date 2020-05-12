/*    */ package com.sun.java.swing.plaf.motif;
/*    */ 
/*    */ import java.awt.event.MouseEvent;
/*    */ import java.awt.event.MouseMotionListener;
/*    */ import javax.swing.MenuSelectionManager;
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
/*    */ class MotifMenuMouseMotionListener
/*    */   implements MouseMotionListener
/*    */ {
/*    */   public void mouseDragged(MouseEvent paramMouseEvent) {
/* 37 */     MenuSelectionManager.defaultManager().processMouseEvent(paramMouseEvent);
/*    */   }
/*    */   
/*    */   public void mouseMoved(MouseEvent paramMouseEvent) {
/* 41 */     MenuSelectionManager.defaultManager().processMouseEvent(paramMouseEvent);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\java\swing\plaf\motif\MotifMenuMouseMotionListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */