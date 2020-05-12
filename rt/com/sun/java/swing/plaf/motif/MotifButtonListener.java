/*    */ package com.sun.java.swing.plaf.motif;
/*    */ 
/*    */ import javax.swing.AbstractButton;
/*    */ import javax.swing.plaf.basic.BasicButtonListener;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MotifButtonListener
/*    */   extends BasicButtonListener
/*    */ {
/*    */   public MotifButtonListener(AbstractButton paramAbstractButton) {
/* 43 */     super(paramAbstractButton);
/*    */   }
/*    */   
/*    */   protected void checkOpacity(AbstractButton paramAbstractButton) {
/* 47 */     paramAbstractButton.setOpaque(false);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\java\swing\plaf\motif\MotifButtonListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */