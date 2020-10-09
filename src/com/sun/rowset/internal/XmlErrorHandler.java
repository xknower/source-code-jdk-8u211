/*    */ package com.sun.rowset.internal;
/*    */ 
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.SAXParseException;
/*    */ import org.xml.sax.helpers.DefaultHandler;
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
/*    */ public class XmlErrorHandler
/*    */   extends DefaultHandler
/*    */ {
/* 44 */   public int errorCounter = 0;
/*    */   
/*    */   public void error(SAXParseException paramSAXParseException) throws SAXException {
/* 47 */     this.errorCounter++;
/*    */   }
/*    */ 
/*    */   
/*    */   public void fatalError(SAXParseException paramSAXParseException) throws SAXException {
/* 52 */     this.errorCounter++;
/*    */   }
/*    */   
/*    */   public void warning(SAXParseException paramSAXParseException) throws SAXException {}
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\rowset\internal\XmlErrorHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */