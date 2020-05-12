/*    */ package javax.swing.plaf.basic;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
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
/*    */ 
/*    */ public class BasicFormattedTextFieldUI
/*    */   extends BasicTextFieldUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 44 */     return new BasicFormattedTextFieldUI();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getPropertyPrefix() {
/* 55 */     return "FormattedTextField";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\basic\BasicFormattedTextFieldUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */