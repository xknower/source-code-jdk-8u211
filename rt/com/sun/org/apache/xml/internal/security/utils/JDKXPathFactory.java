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
/*    */ public class JDKXPathFactory
/*    */   extends XPathFactory
/*    */ {
/*    */   public XPathAPI newXPathAPI() {
/* 35 */     return new JDKXPathAPI();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\securit\\utils\JDKXPathFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */