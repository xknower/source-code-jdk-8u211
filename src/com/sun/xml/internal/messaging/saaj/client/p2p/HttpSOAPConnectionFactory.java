/*    */ package com.sun.xml.internal.messaging.saaj.client.p2p;
/*    */ 
/*    */ import javax.xml.soap.SOAPConnection;
/*    */ import javax.xml.soap.SOAPConnectionFactory;
/*    */ import javax.xml.soap.SOAPException;
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
/*    */ public class HttpSOAPConnectionFactory
/*    */   extends SOAPConnectionFactory
/*    */ {
/*    */   public SOAPConnection createConnection() throws SOAPException {
/* 40 */     return new HttpSOAPConnection();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saaj\client\p2p\HttpSOAPConnectionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */