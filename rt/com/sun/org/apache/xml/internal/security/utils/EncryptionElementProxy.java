/*    */ package com.sun.org.apache.xml.internal.security.utils;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
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
/*    */ public abstract class EncryptionElementProxy
/*    */   extends ElementProxy
/*    */ {
/*    */   public EncryptionElementProxy(Document paramDocument) {
/* 44 */     super(paramDocument);
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
/*    */   public EncryptionElementProxy(Element paramElement, String paramString) throws XMLSecurityException {
/* 56 */     super(paramElement, paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getBaseNamespace() {
/* 61 */     return "http://www.w3.org/2001/04/xmlenc#";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\securit\\utils\EncryptionElementProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */