/*    */ package org.omg.CORBA;
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
/*    */ public final class WrongTransaction
/*    */   extends UserException
/*    */ {
/*    */   public WrongTransaction() {
/* 46 */     super(WrongTransactionHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WrongTransaction(String paramString) {
/* 54 */     super(WrongTransactionHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\WrongTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */