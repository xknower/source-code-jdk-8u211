/*    */ package javax.swing.plaf.nimbus;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JToolBar;
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
/*    */ class ToolBarNorthState
/*    */   extends State
/*    */ {
/*    */   ToolBarNorthState() {
/* 33 */     super("North");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isInState(JComponent paramJComponent) {
/* 38 */     return (paramJComponent instanceof JToolBar && 
/* 39 */       NimbusLookAndFeel.resolveToolbarConstraint((JToolBar)paramJComponent) == "North");
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\ToolBarNorthState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */