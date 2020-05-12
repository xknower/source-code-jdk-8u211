/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.PaintEvent;
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
/*     */ public class PaintEventDispatcher
/*     */ {
/*     */   private static PaintEventDispatcher dispatcher;
/*     */   
/*     */   public static void setPaintEventDispatcher(PaintEventDispatcher paramPaintEventDispatcher) {
/*  49 */     synchronized (PaintEventDispatcher.class) {
/*  50 */       dispatcher = paramPaintEventDispatcher;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PaintEventDispatcher getPaintEventDispatcher() {
/*  61 */     synchronized (PaintEventDispatcher.class) {
/*  62 */       if (dispatcher == null) {
/*  63 */         dispatcher = new PaintEventDispatcher();
/*     */       }
/*  65 */       return dispatcher;
/*     */     } 
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
/*     */   
/*     */   public PaintEvent createPaintEvent(Component paramComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  80 */     return new PaintEvent(paramComponent, 800, new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldDoNativeBackgroundErase(Component paramComponent) {
/*  89 */     return true;
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
/*     */   public boolean queueSurfaceDataReplacing(Component paramComponent, Runnable paramRunnable) {
/* 102 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\PaintEventDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */