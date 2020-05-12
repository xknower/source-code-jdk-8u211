/*    */ package com.sun.org.apache.xml.internal.security.utils;
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
/*    */ public class XalanXPathFactory
/*    */   extends XPathFactory
/*    */ {
/*    */   public XPathAPI newXPathAPI() {
/* 35 */     return new XalanXPathAPI();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\securit\\utils\XalanXPathFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */