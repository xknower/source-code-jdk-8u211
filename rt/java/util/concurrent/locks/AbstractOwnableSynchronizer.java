/*    */ package java.util.concurrent.locks;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public abstract class AbstractOwnableSynchronizer
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 3737899427754241961L;
/*    */   private transient Thread exclusiveOwnerThread;
/*    */   
/*    */   protected final void setExclusiveOwnerThread(Thread paramThread) {
/* 74 */     this.exclusiveOwnerThread = paramThread;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final Thread getExclusiveOwnerThread() {
/* 84 */     return this.exclusiveOwnerThread;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\concurrent\locks\AbstractOwnableSynchronizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */