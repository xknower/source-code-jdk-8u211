/*    */ package javax.swing.plaf.nimbus;
/*    */ 
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
/*    */ 
/*    */ class InternalFrameTitlePaneWindowFocusedState
/*    */   extends State
/*    */ {
/*    */   InternalFrameTitlePaneWindowFocusedState() {
/* 33 */     super("WindowFocused");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isInState(JComponent paramJComponent) {
/* 38 */     return (paramJComponent instanceof JInternalFrame && ((JInternalFrame)paramJComponent).isSelected());
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\InternalFrameTitlePaneWindowFocusedState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */