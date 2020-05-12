/*    */ package com.sun.org.apache.xalan.internal.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ConfigurationError
/*    */   extends Error
/*    */ {
/*    */   private Exception exception;
/*    */   
/*    */   ConfigurationError(String msg, Exception x) {
/* 48 */     super(msg);
/* 49 */     this.exception = x;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Exception getException() {
/* 58 */     return this.exception;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\interna\\utils\ConfigurationError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */