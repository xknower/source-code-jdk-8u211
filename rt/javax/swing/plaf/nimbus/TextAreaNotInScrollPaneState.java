/*    */ package javax.swing.plaf.nimbus;
/*    */ 
/*    */ import javax.swing.JComponent;
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
/*    */ class TextAreaNotInScrollPaneState
/*    */   extends State
/*    */ {
/*    */   TextAreaNotInScrollPaneState() {
/* 33 */     super("NotInScrollPane");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isInState(JComponent paramJComponent) {
/* 38 */     return !(paramJComponent.getParent() instanceof javax.swing.JViewport);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\TextAreaNotInScrollPaneState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */