/*    */ package org.omg.CORBA.portable;
/*    */ 
/*    */ import org.omg.CORBA.CompletionStatus;
/*    */ import org.omg.CORBA.SystemException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IndirectionException
/*    */   extends SystemException
/*    */ {
/*    */   public int offset;
/*    */   
/*    */   public IndirectionException(int paramInt) {
/* 65 */     super("", 0, CompletionStatus.COMPLETED_MAYBE);
/* 66 */     this.offset = paramInt;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\portable\IndirectionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */