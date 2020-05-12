/*    */ package com.sun.xml.internal.ws.server;
/*    */ 
/*    */ import com.sun.xml.internal.ws.developer.ValidationErrorHandler;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.SAXParseException;
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
/*    */ public class DraconianValidationErrorHandler
/*    */   extends ValidationErrorHandler
/*    */ {
/*    */   public void warning(SAXParseException e) throws SAXException {}
/*    */   
/*    */   public void error(SAXParseException e) throws SAXException {
/* 45 */     throw e;
/*    */   }
/*    */   
/*    */   public void fatalError(SAXParseException e) throws SAXException {
/* 49 */     throw e;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\server\DraconianValidationErrorHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */