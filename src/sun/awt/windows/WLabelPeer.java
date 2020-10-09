/*    */ package sun.awt.windows;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.FontMetrics;
/*    */ import java.awt.Label;
/*    */ import java.awt.peer.LabelPeer;
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
/*    */ final class WLabelPeer
/*    */   extends WComponentPeer
/*    */   implements LabelPeer
/*    */ {
/*    */   public Dimension getMinimumSize() {
/* 35 */     FontMetrics fontMetrics = getFontMetrics(((Label)this.target).getFont());
/* 36 */     String str = ((Label)this.target).getText();
/* 37 */     if (str == null)
/* 38 */       str = ""; 
/* 39 */     return new Dimension(fontMetrics.stringWidth(str) + 14, fontMetrics.getHeight() + 8);
/*    */   }
/*    */ 
/*    */   
/*    */   synchronized void start() {
/* 44 */     super.start();
/*    */     
/* 46 */     lazyPaint();
/*    */   }
/*    */   native void lazyPaint();
/*    */   
/*    */   public boolean shouldClearRectBeforePaint() {
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public native void setText(String paramString);
/*    */   
/*    */   public native void setAlignment(int paramInt);
/*    */   
/*    */   WLabelPeer(Label paramLabel) {
/* 60 */     super(paramLabel);
/*    */   }
/*    */   
/*    */   native void create(WComponentPeer paramWComponentPeer);
/*    */   
/*    */   void initialize() {
/* 66 */     Label label = (Label)this.target;
/*    */     
/* 68 */     String str = label.getText();
/* 69 */     if (str != null) {
/* 70 */       setText(str);
/*    */     }
/*    */     
/* 73 */     int i = label.getAlignment();
/* 74 */     if (i != 0) {
/* 75 */       setAlignment(i);
/*    */     }
/*    */     
/* 78 */     Color color = ((Component)this.target).getBackground();
/* 79 */     if (color != null) {
/* 80 */       setBackground(color);
/*    */     }
/*    */     
/* 83 */     super.initialize();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WLabelPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */