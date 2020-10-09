/*    */ package com.sun.xml.internal.messaging.saaj.soap.ver1_1;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.impl.BodyElementImpl;
/*    */ import javax.xml.namespace.QName;
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
/*    */ 
/*    */ public class BodyElement1_1Impl
/*    */   extends BodyElementImpl
/*    */ {
/*    */   public BodyElement1_1Impl(SOAPDocumentImpl ownerDoc, Name qname) {
/* 43 */     super(ownerDoc, qname);
/*    */   }
/*    */   public BodyElement1_1Impl(SOAPDocumentImpl ownerDoc, QName qname) {
/* 46 */     super(ownerDoc, qname);
/*    */   }
/*    */   
/*    */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/* 50 */     BodyElementImpl copy = new BodyElement1_1Impl((SOAPDocumentImpl)getOwnerDocument(), newName);
/* 51 */     return replaceElementWithSOAPElement(this, copy);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saaj\soap\ver1_1\BodyElement1_1Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */