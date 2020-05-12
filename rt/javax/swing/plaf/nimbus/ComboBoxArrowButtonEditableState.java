/*    */ package javax.swing.plaf.nimbus;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import javax.swing.JComboBox;
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
/*    */ class ComboBoxArrowButtonEditableState
/*    */   extends State
/*    */ {
/*    */   ComboBoxArrowButtonEditableState() {
/* 33 */     super("Editable");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isInState(JComponent paramJComponent) {
/* 38 */     Container container = paramJComponent.getParent();
/* 39 */     return (container instanceof JComboBox && ((JComboBox)container).isEditable());
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\ComboBoxArrowButtonEditableState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */