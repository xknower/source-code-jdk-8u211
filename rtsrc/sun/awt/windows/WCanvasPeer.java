/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.peer.CanvasPeer;
/*     */ import sun.awt.PaintEventDispatcher;
/*     */ import sun.awt.SunToolkit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WCanvasPeer
/*     */   extends WComponentPeer
/*     */   implements CanvasPeer
/*     */ {
/*     */   private boolean eraseBackground;
/*     */   
/*     */   WCanvasPeer(Component paramComponent) {
/*  45 */     super(paramComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   native void create(WComponentPeer paramWComponentPeer);
/*     */ 
/*     */   
/*     */   void initialize() {
/*  53 */     this.eraseBackground = !SunToolkit.getSunAwtNoerasebackground();
/*  54 */     boolean bool = SunToolkit.getSunAwtErasebackgroundonresize();
/*     */ 
/*     */ 
/*     */     
/*  58 */     if (!PaintEventDispatcher.getPaintEventDispatcher().shouldDoNativeBackgroundErase((Component)this.target)) {
/*  59 */       this.eraseBackground = false;
/*     */     }
/*  61 */     setNativeBackgroundErase(this.eraseBackground, bool);
/*  62 */     super.initialize();
/*  63 */     Color color = ((Component)this.target).getBackground();
/*  64 */     if (color != null) {
/*  65 */       setBackground(color);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/*  71 */     Dimension dimension = ((Component)this.target).getSize();
/*  72 */     if (paramGraphics instanceof java.awt.Graphics2D || paramGraphics instanceof sun.awt.Graphics2Delegate) {
/*     */ 
/*     */       
/*  75 */       paramGraphics.clearRect(0, 0, dimension.width, dimension.height);
/*     */     } else {
/*     */       
/*  78 */       paramGraphics.setColor(((Component)this.target).getBackground());
/*  79 */       paramGraphics.fillRect(0, 0, dimension.width, dimension.height);
/*  80 */       paramGraphics.setColor(((Component)this.target).getForeground());
/*     */     } 
/*  82 */     super.paint(paramGraphics);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldClearRectBeforePaint() {
/*  87 */     return this.eraseBackground;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void disableBackgroundErase() {
/*  95 */     this.eraseBackground = false;
/*  96 */     setNativeBackgroundErase(false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native void setNativeBackgroundErase(boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsConfiguration getAppropriateGraphicsConfiguration(GraphicsConfiguration paramGraphicsConfiguration) {
/* 114 */     return paramGraphicsConfiguration;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WCanvasPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */