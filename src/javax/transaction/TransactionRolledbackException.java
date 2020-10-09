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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransactionRolledbackException
/*    */   extends RemoteException
/*    */ {
/*    */   public TransactionRolledbackException() {}
/*    */   
/*    */   public TransactionRolledbackException(String paramString) {
/* 50 */     super(paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\transaction\TransactionRolledbackException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */