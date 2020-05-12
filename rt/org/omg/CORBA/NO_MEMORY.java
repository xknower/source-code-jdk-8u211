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
/*    */ 
/*    */ public final class NO_MEMORY
/*    */   extends SystemException
/*    */ {
/*    */   public NO_MEMORY() {
/* 47 */     this("");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NO_MEMORY(String paramString) {
/* 56 */     this(paramString, 0, CompletionStatus.COMPLETED_NO);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NO_MEMORY(int paramInt, CompletionStatus paramCompletionStatus) {
/* 66 */     this("", paramInt, paramCompletionStatus);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NO_MEMORY(String paramString, int paramInt, CompletionStatus paramCompletionStatus) {
/* 77 */     super(paramString, paramInt, paramCompletionStatus);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\NO_MEMORY.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */