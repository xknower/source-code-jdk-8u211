/*    */ package javax.swing;
/*    */ 
/*    */ import java.awt.Image;
/*    */ import java.awt.image.ImageObserver;
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
/*    */ class DebugGraphicsObserver
/*    */   implements ImageObserver
/*    */ {
/*    */   int lastInfo;
/*    */   
/*    */   synchronized boolean allBitsPresent() {
/* 39 */     return ((this.lastInfo & 0x20) != 0);
/*    */   }
/*    */   
/*    */   synchronized boolean imageHasProblem() {
/* 43 */     return ((this.lastInfo & 0x40) != 0 || (this.lastInfo & 0x80) != 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized boolean imageUpdate(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 50 */     this.lastInfo = paramInt1;
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\DebugGraphicsObserver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */