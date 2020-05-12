/*    */ package sun.awt.windows;
/*    */ 
/*    */ import java.awt.Dialog;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.GraphicsConfiguration;
/*    */ import java.awt.MenuBar;
/*    */ import java.awt.Rectangle;
/*    */ import sun.awt.EmbeddedFrame;
/*    */ import sun.awt.Win32GraphicsEnvironment;
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
/*    */ public class WEmbeddedFramePeer
/*    */   extends WFramePeer
/*    */ {
/*    */   public WEmbeddedFramePeer(EmbeddedFrame paramEmbeddedFrame) {
/* 38 */     super(paramEmbeddedFrame);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void print(Graphics paramGraphics) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateMinimumSize() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void modalDisable(Dialog paramDialog, long paramLong) {
/* 55 */     super.modalDisable(paramDialog, paramLong);
/* 56 */     ((EmbeddedFrame)this.target).notifyModalBlocked(paramDialog, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modalEnable(Dialog paramDialog) {
/* 61 */     super.modalEnable(paramDialog);
/* 62 */     ((EmbeddedFrame)this.target).notifyModalBlocked(paramDialog, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBoundsPrivate(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 67 */     setBounds(paramInt1, paramInt2, paramInt3, paramInt4, 16387);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAccelCapable() {
/* 79 */     return !Win32GraphicsEnvironment.isDWMCompositionEnabled();
/*    */   }
/*    */   
/*    */   native void create(WComponentPeer paramWComponentPeer);
/*    */   
/*    */   public native Rectangle getBoundsPrivate();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WEmbeddedFramePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */