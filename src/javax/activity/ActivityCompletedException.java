/*    */ package javax.activity;
/*    */ 
/*    */ import java.rmi.RemoteException;
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
/*    */ public class ActivityCompletedException
/*    */   extends RemoteException
/*    */ {
/*    */   public ActivityCompletedException() {}
/*    */   
/*    */   public ActivityCompletedException(String paramString) {
/* 50 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ActivityCompletedException(Throwable paramThrowable) {
/* 60 */     this("", paramThrowable);
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
/*    */   public ActivityCompletedException(String paramString, Throwable paramThrowable) {
/* 72 */     super(paramString, paramThrowable);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\activity\ActivityCompletedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */