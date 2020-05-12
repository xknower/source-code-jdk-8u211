/*    */ package com.sun.xml.internal.messaging.saaj.soap.ver1_2;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.SOAPExceptionImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocument;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.impl.DetailImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;
/*    */ import java.util.logging.Logger;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.DetailEntry;
/*    */ import javax.xml.soap.Name;
/*    */ import javax.xml.soap.SOAPElement;
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
/*    */ public class Detail1_2Impl
/*    */   extends DetailImpl
/*    */ {
/* 46 */   protected static final Logger log = Logger.getLogger(Detail1_2Impl.class.getName(), "com.sun.xml.internal.messaging.saaj.soap.ver1_2.LocalStrings");
/*    */ 
/*    */   
/*    */   public Detail1_2Impl(SOAPDocumentImpl ownerDocument, String prefix) {
/* 50 */     super(ownerDocument, NameImpl.createSOAP12Name("Detail", prefix));
/*    */   }
/*    */   
/*    */   public Detail1_2Impl(SOAPDocumentImpl ownerDocument) {
/* 54 */     super(ownerDocument, NameImpl.createSOAP12Name("Detail"));
/*    */   }
/*    */   
/*    */   protected DetailEntry createDetailEntry(Name name) {
/* 58 */     return new DetailEntry1_2Impl(((SOAPDocument)
/* 59 */         getOwnerDocument()).getDocument(), name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected DetailEntry createDetailEntry(QName name) {
/* 64 */     return new DetailEntry1_2Impl(((SOAPDocument)
/* 65 */         getOwnerDocument()).getDocument(), name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setEncodingStyle(String encodingStyle) throws SOAPException {
/* 74 */     log.severe("SAAJ0403.ver1_2.no.encodingStyle.in.detail");
/* 75 */     throw new SOAPExceptionImpl("EncodingStyle attribute cannot appear in Detail");
/*    */   }
/*    */ 
/*    */   
/*    */   public SOAPElement addAttribute(Name name, String value) throws SOAPException {
/* 80 */     if (name.getLocalName().equals("encodingStyle") && name
/* 81 */       .getURI().equals("http://www.w3.org/2003/05/soap-envelope")) {
/* 82 */       setEncodingStyle(value);
/*    */     }
/* 84 */     return super.addAttribute(name, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public SOAPElement addAttribute(QName name, String value) throws SOAPException {
/* 89 */     if (name.getLocalPart().equals("encodingStyle") && name
/* 90 */       .getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope")) {
/* 91 */       setEncodingStyle(value);
/*    */     }
/* 93 */     return super.addAttribute(name, value);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saaj\soap\ver1_2\Detail1_2Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */