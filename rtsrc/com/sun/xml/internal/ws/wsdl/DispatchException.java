/*    */ package com.sun.xml.internal.ws.wsdl;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.message.Message;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DispatchException
/*    */   extends Exception
/*    */ {
/*    */   public final Message fault;
/*    */   
/*    */   public DispatchException(Message fault) {
/* 42 */     this.fault = fault;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\wsdl\DispatchException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */