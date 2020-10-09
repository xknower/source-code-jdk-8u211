/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Checkbox;
/*     */ import java.awt.CheckboxGroup;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.peer.CheckboxPeer;
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
/*     */ final class WCheckboxPeer
/*     */   extends WComponentPeer
/*     */   implements CheckboxPeer
/*     */ {
/*     */   public native void setState(boolean paramBoolean);
/*     */   
/*     */   public native void setCheckboxGroup(CheckboxGroup paramCheckboxGroup);
/*     */   
/*     */   public native void setLabel(String paramString);
/*     */   
/*     */   private static native int getCheckMarkSize();
/*     */   
/*     */   public Dimension getMinimumSize() {
/*  46 */     String str = ((Checkbox)this.target).getLabel();
/*  47 */     int i = getCheckMarkSize();
/*  48 */     if (str == null) {
/*  49 */       str = "";
/*     */     }
/*  51 */     FontMetrics fontMetrics = getFontMetrics(((Checkbox)this.target).getFont());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     return new Dimension(fontMetrics.stringWidth(str) + i / 2 + i, 
/*  58 */         Math.max(fontMetrics.getHeight() + 8, i));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFocusable() {
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   WCheckboxPeer(Checkbox paramCheckbox) {
/*  69 */     super(paramCheckbox);
/*     */   }
/*     */ 
/*     */   
/*     */   native void create(WComponentPeer paramWComponentPeer);
/*     */ 
/*     */   
/*     */   void initialize() {
/*  77 */     Checkbox checkbox = (Checkbox)this.target;
/*  78 */     setState(checkbox.getState());
/*  79 */     setCheckboxGroup(checkbox.getCheckboxGroup());
/*     */     
/*  81 */     Color color = ((Component)this.target).getBackground();
/*  82 */     if (color != null) {
/*  83 */       setBackground(color);
/*     */     }
/*     */     
/*  86 */     super.initialize();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldClearRectBeforePaint() {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void handleAction(final boolean state) {
/*  97 */     final Checkbox cb = (Checkbox)this.target;
/*  98 */     WToolkit.executeOnEventHandlerThread(checkbox, new Runnable()
/*     */         {
/*     */           public void run() {
/* 101 */             CheckboxGroup checkboxGroup = cb.getCheckboxGroup();
/* 102 */             if (checkboxGroup != null && cb == checkboxGroup.getSelectedCheckbox() && cb.getState()) {
/*     */               return;
/*     */             }
/* 105 */             cb.setState(state);
/* 106 */             WCheckboxPeer.this.postEvent(new ItemEvent(cb, 701, cb
/* 107 */                   .getLabel(), state ? 1 : 2));
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WCheckboxPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */