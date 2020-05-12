/*    */ package javax.swing.plaf.nimbus;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JSplitPane;
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
/*    */ class SplitPaneDividerVerticalState
/*    */   extends State
/*    */ {
/*    */   SplitPaneDividerVerticalState() {
/* 33 */     super("Vertical");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isInState(JComponent paramJComponent) {
/* 38 */     return (paramJComponent instanceof JSplitPane && ((JSplitPane)paramJComponent).getOrientation() == 1);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\SplitPaneDividerVerticalState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */