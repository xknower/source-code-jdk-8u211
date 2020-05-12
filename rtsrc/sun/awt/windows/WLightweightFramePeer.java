/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.MenuBar;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.dnd.DropTarget;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import sun.awt.LightweightFrame;
/*     */ import sun.awt.OverrideNativeWindowHandle;
/*     */ import sun.swing.JLightweightFrame;
/*     */ import sun.swing.SwingAccessor;
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
/*     */ public class WLightweightFramePeer
/*     */   extends WFramePeer
/*     */   implements OverrideNativeWindowHandle
/*     */ {
/*     */   public WLightweightFramePeer(LightweightFrame paramLightweightFrame) {
/*  42 */     super(paramLightweightFrame);
/*     */   }
/*     */   
/*     */   private LightweightFrame getLwTarget() {
/*  46 */     return (LightweightFrame)this.target;
/*     */   }
/*     */ 
/*     */   
/*     */   public Graphics getGraphics() {
/*  51 */     return getLwTarget().getGraphics();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void overrideWindowHandle(long paramLong) {
/*  58 */     overrideNativeHandle(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public void show() {
/*  63 */     super.show();
/*  64 */     postEvent(new ComponentEvent((Component)getTarget(), 102));
/*     */   }
/*     */ 
/*     */   
/*     */   public void hide() {
/*  69 */     super.hide();
/*  70 */     postEvent(new ComponentEvent((Component)getTarget(), 103));
/*     */   }
/*     */ 
/*     */   
/*     */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  75 */     super.reshape(paramInt1, paramInt2, paramInt3, paramInt4);
/*  76 */     postEvent(new ComponentEvent((Component)getTarget(), 100));
/*  77 */     postEvent(new ComponentEvent((Component)getTarget(), 101));
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEvent(AWTEvent paramAWTEvent) {
/*  82 */     if (paramAWTEvent.getID() == 501) {
/*  83 */       emulateActivation(true);
/*     */     }
/*  85 */     super.handleEvent(paramAWTEvent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void grab() {
/*  90 */     getLwTarget().grabFocus();
/*     */   }
/*     */ 
/*     */   
/*     */   public void ungrab() {
/*  95 */     getLwTarget().ungrabFocus();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateCursorImmediately() {
/* 100 */     SwingAccessor.getJLightweightFrameAccessor().updateCursor((JLightweightFrame)getLwTarget());
/*     */   }
/*     */   
/*     */   public boolean isLightweightFramePeer() {
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDropTarget(DropTarget paramDropTarget) {
/* 109 */     getLwTarget().addDropTarget(paramDropTarget);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeDropTarget(DropTarget paramDropTarget) {
/* 114 */     getLwTarget().removeDropTarget(paramDropTarget);
/*     */   }
/*     */   
/*     */   private native void overrideNativeHandle(long paramLong);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WLightweightFramePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */