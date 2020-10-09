/*    */ package com.sun.xml.internal.stream.buffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLStreamBufferException
/*    */   extends Exception
/*    */ {
/*    */   public XMLStreamBufferException(String message) {
/* 31 */     super(message);
/*    */   }
/*    */   
/*    */   public XMLStreamBufferException(String message, Exception e) {
/* 35 */     super(message, e);
/*    */   }
/*    */   
/*    */   public XMLStreamBufferException(Exception e) {
/* 39 */     super(e);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\stream\buffer\XMLStreamBufferException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */