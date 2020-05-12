/*    */ package javax.swing.plaf.metal;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import java.io.Serializable;
/*    */ import javax.swing.Icon;
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
/*    */ public class MetalComboBoxIcon
/*    */   implements Icon, Serializable
/*    */ {
/*    */   public void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2) {
/* 49 */     JComponent jComponent = (JComponent)paramComponent;
/* 50 */     int i = getIconWidth();
/*    */     
/* 52 */     paramGraphics.translate(paramInt1, paramInt2);
/*    */     
/* 54 */     paramGraphics.setColor(jComponent.isEnabled() ? MetalLookAndFeel.getControlInfo() : MetalLookAndFeel.getControlShadow());
/* 55 */     paramGraphics.drawLine(0, 0, i - 1, 0);
/* 56 */     paramGraphics.drawLine(1, 1, 1 + i - 3, 1);
/* 57 */     paramGraphics.drawLine(2, 2, 2 + i - 5, 2);
/* 58 */     paramGraphics.drawLine(3, 3, 3 + i - 7, 3);
/* 59 */     paramGraphics.drawLine(4, 4, 4 + i - 9, 4);
/*    */     
/* 61 */     paramGraphics.translate(-paramInt1, -paramInt2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIconWidth() {
/* 67 */     return 10;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIconHeight() {
/* 72 */     return 5;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\metal\MetalComboBoxIcon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */