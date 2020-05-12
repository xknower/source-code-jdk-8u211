/*    */ package javax.swing.plaf.nimbus;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JInternalFrame;
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
/*    */ class InternalFrameTitlePaneIconifyButtonWindowNotFocusedState
/*    */   extends State
/*    */ {
/*    */   InternalFrameTitlePaneIconifyButtonWindowNotFocusedState() {
/* 33 */     super("WindowNotFocused");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isInState(JComponent paramJComponent) {
/* 38 */     Container container = paramJComponent;
/* 39 */     while (container.getParent() != null && 
/* 40 */       !(container instanceof JInternalFrame))
/*    */     {
/*    */       
/* 43 */       container = container.getParent();
/*    */     }
/* 45 */     if (container instanceof JInternalFrame) {
/* 46 */       return !((JInternalFrame)container).isSelected();
/*    */     }
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\InternalFrameTitlePaneIconifyButtonWindowNotFocusedState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */