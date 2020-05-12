/*    */ package com.sun.xml.internal.messaging.saaj.soap;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.SOAPExceptionImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SOAPVersionMismatchException
/*    */   extends SOAPExceptionImpl
/*    */ {
/*    */   public SOAPVersionMismatchException() {}
/*    */   
/*    */   public SOAPVersionMismatchException(String reason) {
/* 46 */     super(reason);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPVersionMismatchException(String reason, Throwable cause) {
/* 60 */     super(reason, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPVersionMismatchException(Throwable cause) {
/* 68 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saaj\soap\SOAPVersionMismatchException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */