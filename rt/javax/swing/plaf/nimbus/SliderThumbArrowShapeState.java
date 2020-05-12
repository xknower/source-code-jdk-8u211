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
/*    */ class SliderThumbArrowShapeState
/*    */   extends State
/*    */ {
/*    */   SliderThumbArrowShapeState() {
/* 33 */     super("ArrowShape");
/*    */   }
/*    */   
/*    */   protected boolean isInState(JComponent paramJComponent) {
/* 37 */     return (paramJComponent.getClientProperty("Slider.paintThumbArrowShape") == Boolean.TRUE);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\nimbus\SliderThumbArrowShapeState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */