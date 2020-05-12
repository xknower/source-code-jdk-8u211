/*    */ package com.sun.xml.internal.ws.spi.db;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DatabindingException
/*    */   extends RuntimeException
/*    */ {
/*    */   public DatabindingException() {}
/*    */   
/*    */   public DatabindingException(String message) {
/* 35 */     super(message);
/* 36 */   } public DatabindingException(Throwable cause) { super(cause); } public DatabindingException(String message, Throwable cause) {
/* 37 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\spi\db\DatabindingException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */