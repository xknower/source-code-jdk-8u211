/*     */ package com.sun.xml.internal.messaging.saaj.soap.ver1_2;
/*     */ 
/*     */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.internal.messaging.saaj.soap.impl.HeaderElementImpl;
/*     */ import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HeaderElement1_2Impl
/*     */   extends HeaderElementImpl
/*     */ {
/*  46 */   private static final Logger log = Logger.getLogger(HeaderElement1_2Impl.class.getName(), "com.sun.xml.internal.messaging.saaj.soap.ver1_2.LocalStrings");
/*     */ 
/*     */   
/*     */   public HeaderElement1_2Impl(SOAPDocumentImpl ownerDoc, Name qname) {
/*  50 */     super(ownerDoc, qname);
/*     */   }
/*     */   public HeaderElement1_2Impl(SOAPDocumentImpl ownerDoc, QName qname) {
/*  53 */     super(ownerDoc, qname);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/*  58 */     HeaderElementImpl copy = new HeaderElement1_2Impl((SOAPDocumentImpl)getOwnerDocument(), newName);
/*  59 */     return replaceElementWithSOAPElement(this, copy);
/*     */   }
/*     */   
/*     */   protected NameImpl getRoleAttributeName() {
/*  63 */     return NameImpl.create("role", null, "http://www.w3.org/2003/05/soap-envelope");
/*     */   }
/*     */ 
/*     */   
/*     */   protected NameImpl getActorAttributeName() {
/*  68 */     return getRoleAttributeName();
/*     */   }
/*     */   
/*     */   protected NameImpl getMustunderstandAttributeName() {
/*  72 */     return NameImpl.create("mustUnderstand", null, "http://www.w3.org/2003/05/soap-envelope");
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getMustunderstandLiteralValue(boolean mustUnderstand) {
/*  77 */     return (mustUnderstand == true) ? "true" : "false";
/*     */   }
/*     */   
/*     */   protected boolean getMustunderstandAttributeValue(String mu) {
/*  81 */     if (mu.equals("true") || mu.equals("1"))
/*  82 */       return true; 
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   protected NameImpl getRelayAttributeName() {
/*  87 */     return NameImpl.create("relay", null, "http://www.w3.org/2003/05/soap-envelope");
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getRelayLiteralValue(boolean relay) {
/*  92 */     return (relay == true) ? "true" : "false";
/*     */   }
/*     */   
/*     */   protected boolean getRelayAttributeValue(String relay) {
/*  96 */     if (relay.equals("true") || relay.equals("1"))
/*  97 */       return true; 
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   protected String getActorOrRole() {
/* 102 */     return getRole();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saaj\soap\ver1_2\HeaderElement1_2Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */