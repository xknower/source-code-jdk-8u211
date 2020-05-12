/*    */ package com.sun.org.apache.xerces.internal.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
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
/* 45 */     super(msg);
/* 46 */     this.exception = x;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Exception getException() {
/* 55 */     return this.exception;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\interna\\utils\ConfigurationError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */