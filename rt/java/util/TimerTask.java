/*     */ package java.util;
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
/*     */ public abstract class TimerTask
/*     */   implements Runnable
/*     */ {
/*  40 */   final Object lock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   int state = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int VIRGIN = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int SCHEDULED = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int EXECUTED = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int CANCELLED = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long nextExecutionTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   long period = 0L;
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
/*     */   public abstract void run();
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
/*     */   public boolean cancel() {
/* 117 */     synchronized (this.lock) {
/* 118 */       boolean bool = (this.state == 1) ? true : false;
/* 119 */       this.state = 3;
/* 120 */       return bool;
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
/*     */   public long scheduledExecutionTime() {
/* 153 */     synchronized (this.lock) {
/* 154 */       return (this.period < 0L) ? (this.nextExecutionTime + this.period) : (this.nextExecutionTime - this.period);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\TimerTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */