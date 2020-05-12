/*    */ package com.sun.corba.se.impl.protocol;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequestCanceledException
/*    */   extends RuntimeException
/*    */ {
/* 33 */   private int requestId = 0;
/*    */   
/*    */   public RequestCanceledException(int paramInt) {
/* 36 */     this.requestId = paramInt;
/*    */   }
/*    */   
/*    */   public int getRequestId() {
/* 40 */     return this.requestId;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\protocol\RequestCanceledException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */