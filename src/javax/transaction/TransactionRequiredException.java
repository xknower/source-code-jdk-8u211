/*    */ package javax.transaction;
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
/*    */ public class TransactionRequiredException
/*    */   extends RemoteException
/*    */ {
/*    */   public TransactionRequiredException() {}
/*    */   
/*    */   public TransactionRequiredException(String paramString) {
/* 47 */     super(paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\transaction\TransactionRequiredException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */