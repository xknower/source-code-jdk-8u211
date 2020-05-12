/*    */ package sun.misc;
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
/*    */ 
/*    */ public final class ConditionLock
/*    */   extends Lock
/*    */ {
/* 43 */   private int state = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ConditionLock() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ConditionLock(int paramInt) {
/* 55 */     this.state = paramInt;
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
/*    */   
/*    */   public synchronized void lockWhen(int paramInt) throws InterruptedException {
/* 68 */     while (this.state != paramInt) {
/* 69 */       wait();
/*    */     }
/* 71 */     lock();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void unlockWith(int paramInt) {
/* 79 */     this.state = paramInt;
/* 80 */     unlock();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\ConditionLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */