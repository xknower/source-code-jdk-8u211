/*    */ package javax.swing.plaf.nimbus;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JProgressBar;
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
/*    */ class ProgressBarIndeterminateState
/*    */   extends State
/*    */ {
/*    */   ProgressBarIndeterminateState() {
/* 33 */     super("Indeterminate");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isInState(JComponent paramJComponent) {
/* 38 */     return (paramJComponent instanceof JProgressBar && ((JProgressBar)paramJComponent)
/* 39 */       .isIndeterminate());
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\ProgressBarIndeterminateState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */