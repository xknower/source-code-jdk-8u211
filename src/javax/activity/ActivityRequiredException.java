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
/*    */ public class ActivityRequiredException
/*    */   extends RemoteException
/*    */ {
/*    */   public ActivityRequiredException() {}
/*    */   
/*    */   public ActivityRequiredException(String paramString) {
/* 48 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ActivityRequiredException(Throwable paramThrowable) {
/* 58 */     this("", paramThrowable);
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
/*    */   public ActivityRequiredException(String paramString, Throwable paramThrowable) {
/* 70 */     super(paramString, paramThrowable);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\activity\ActivityRequiredException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */