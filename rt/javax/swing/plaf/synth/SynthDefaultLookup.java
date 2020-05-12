/*    */ package javax.swing.plaf.synth;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import sun.swing.DefaultLookup;
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
/*    */ class SynthDefaultLookup
/*    */   extends DefaultLookup
/*    */ {
/*    */   public Object getDefault(JComponent paramJComponent, ComponentUI paramComponentUI, String paramString) {
/* 38 */     if (!(paramComponentUI instanceof SynthUI)) {
/* 39 */       return super.getDefault(paramJComponent, paramComponentUI, paramString);
/*    */     }
/*    */     
/* 42 */     SynthContext synthContext = ((SynthUI)paramComponentUI).getContext(paramJComponent);
/* 43 */     Object object = synthContext.getStyle().get(synthContext, paramString);
/* 44 */     synthContext.dispose();
/* 45 */     return object;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\synth\SynthDefaultLookup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */