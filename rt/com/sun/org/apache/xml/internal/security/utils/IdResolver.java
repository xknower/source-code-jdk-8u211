/*    */ package com.sun.org.apache.xml.internal.security.utils;
/*    */ 
/*    */ import org.w3c.dom.Attr;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Element;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class IdResolver
/*    */ {
/*    */   public static void registerElementById(Element paramElement, Attr paramAttr) {
/* 50 */     paramElement.setIdAttributeNode(paramAttr, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Element getElementById(Document paramDocument, String paramString) {
/* 61 */     return paramDocument.getElementById(paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\securit\\utils\IdResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */