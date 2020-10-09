/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.peer.ButtonPeer;
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
/*     */ final class WButtonPeer
/*     */   extends WComponentPeer
/*     */   implements ButtonPeer
/*     */ {
/*     */   static {
/*  35 */     initIDs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize() {
/*  42 */     FontMetrics fontMetrics = getFontMetrics(((Button)this.target).getFont());
/*  43 */     String str = ((Button)this.target).getLabel();
/*  44 */     if (str == null) {
/*  45 */       str = "";
/*     */     }
/*  47 */     return new Dimension(fontMetrics.stringWidth(str) + 14, fontMetrics
/*  48 */         .getHeight() + 8);
/*     */   }
/*     */   
/*     */   public boolean isFocusable() {
/*  52 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WButtonPeer(Button paramButton) {
/*  63 */     super(paramButton);
/*     */   }
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
/*     */   public void handleAction(final long when, final int modifiers) {
/*  76 */     WToolkit.executeOnEventHandlerThread(this.target, new Runnable()
/*     */         {
/*     */           public void run() {
/*  79 */             WButtonPeer.this.postEvent(new ActionEvent(WButtonPeer.this.target, 1001, ((Button)WButtonPeer.this.target)
/*  80 */                   .getActionCommand(), when, modifiers));
/*     */           }
/*     */         }when);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldClearRectBeforePaint() {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleJavaKeyEvent(KeyEvent paramKeyEvent) {
/*  99 */     switch (paramKeyEvent.getID()) {
/*     */       case 402:
/* 101 */         if (paramKeyEvent.getKeyCode() == 32) {
/* 102 */           handleAction(paramKeyEvent.getWhen(), paramKeyEvent.getModifiers());
/*     */         }
/*     */         break;
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   public native void setLabel(String paramString);
/*     */   
/*     */   native void create(WComponentPeer paramWComponentPeer);
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WButtonPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */