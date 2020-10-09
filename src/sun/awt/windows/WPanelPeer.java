/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.peer.PanelPeer;
/*     */ import sun.awt.SunGraphicsCallback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WPanelPeer
/*     */   extends WCanvasPeer
/*     */   implements PanelPeer
/*     */ {
/*     */   Insets insets_;
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/*  38 */     super.paint(paramGraphics);
/*  39 */     SunGraphicsCallback.PaintHeavyweightComponentsCallback.getInstance()
/*  40 */       .runComponents(((Container)this.target).getComponents(), paramGraphics, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(Graphics paramGraphics) {
/*  46 */     super.print(paramGraphics);
/*  47 */     SunGraphicsCallback.PrintHeavyweightComponentsCallback.getInstance()
/*  48 */       .runComponents(((Container)this.target).getComponents(), paramGraphics, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets getInsets() {
/*  57 */     return this.insets_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  65 */     initIDs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WPanelPeer(Component paramComponent) {
/*  74 */     super(paramComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   void initialize() {
/*  79 */     super.initialize();
/*  80 */     this.insets_ = new Insets(0, 0, 0, 0);
/*     */     
/*  82 */     Color color = ((Component)this.target).getBackground();
/*  83 */     if (color == null) {
/*  84 */       color = WColor.getDefaultColor(1);
/*  85 */       ((Component)this.target).setBackground(color);
/*  86 */       setBackground(color);
/*     */     } 
/*  88 */     color = ((Component)this.target).getForeground();
/*  89 */     if (color == null) {
/*  90 */       color = WColor.getDefaultColor(2);
/*  91 */       ((Component)this.target).setForeground(color);
/*  92 */       setForeground(color);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets insets() {
/* 100 */     return getInsets();
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WPanelPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */