/*    */ package com.sun.corba.se.spi.protocol;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum RetryType
/*    */ {
/* 38 */   NONE(false),
/* 39 */   BEFORE_RESPONSE(true),
/* 40 */   AFTER_RESPONSE(true);
/*    */   
/*    */   private final boolean isRetry;
/*    */   
/*    */   RetryType(boolean paramBoolean) {
/* 45 */     this.isRetry = paramBoolean;
/*    */   }
/*    */   
/*    */   public boolean isRetry() {
/* 49 */     return this.isRetry;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\protocol\RetryType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */