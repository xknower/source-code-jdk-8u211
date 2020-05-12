/*    */ package javax.swing.plaf.basic;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.LookAndFeel;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.ViewportUI;
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
/*    */ public class BasicViewportUI
/*    */   extends ViewportUI
/*    */ {
/*    */   private static ViewportUI viewportUI;
/*    */   
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 47 */     if (viewportUI == null) {
/* 48 */       viewportUI = new BasicViewportUI();
/*    */     }
/* 50 */     return viewportUI;
/*    */   }
/*    */   
/*    */   public void installUI(JComponent paramJComponent) {
/* 54 */     super.installUI(paramJComponent);
/* 55 */     installDefaults(paramJComponent);
/*    */   }
/*    */   
/*    */   public void uninstallUI(JComponent paramJComponent) {
/* 59 */     uninstallDefaults(paramJComponent);
/* 60 */     super.uninstallUI(paramJComponent);
/*    */   }
/*    */   
/*    */   protected void installDefaults(JComponent paramJComponent) {
/* 64 */     LookAndFeel.installColorsAndFont(paramJComponent, "Viewport.background", "Viewport.foreground", "Viewport.font");
/*    */ 
/*    */ 
/*    */     
/* 68 */     LookAndFeel.installProperty(paramJComponent, "opaque", Boolean.TRUE);
/*    */   }
/*    */   
/*    */   protected void uninstallDefaults(JComponent paramJComponent) {}
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\basic\BasicViewportUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */