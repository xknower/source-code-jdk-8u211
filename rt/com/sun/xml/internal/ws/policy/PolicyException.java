/*    */ package com.sun.xml.internal.ws.policy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PolicyException
/*    */   extends Exception
/*    */ {
/*    */   public PolicyException(String message) {
/* 34 */     super(message);
/*    */   }
/*    */ 
/*    */   
/*    */   public PolicyException(String message, Throwable cause) {
/* 39 */     super(message, cause);
/*    */   }
/*    */ 
/*    */   
/*    */   public PolicyException(Throwable cause) {
/* 44 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\policy\PolicyException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */