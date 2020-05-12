/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*     */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentFilter;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLDocumentFilterImpl
/*     */   implements XMLDocumentFilter
/*     */ {
/*     */   private XMLDocumentHandler next;
/*     */   private XMLDocumentSource source;
/*     */   
/*     */   public void setDocumentHandler(XMLDocumentHandler handler) {
/*  92 */     this.next = handler;
/*     */   }
/*     */   
/*     */   public XMLDocumentHandler getDocumentHandler() {
/*  96 */     return this.next;
/*     */   }
/*     */   
/*     */   public void setDocumentSource(XMLDocumentSource source) {
/* 100 */     this.source = source;
/*     */   }
/*     */   
/*     */   public XMLDocumentSource getDocumentSource() {
/* 104 */     return this.source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(XMLString text, Augmentations augs) throws XNIException {
/* 113 */     this.next.characters(text, augs);
/*     */   }
/*     */   
/*     */   public void comment(XMLString text, Augmentations augs) throws XNIException {
/* 117 */     this.next.comment(text, augs);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doctypeDecl(String rootElement, String publicId, String systemId, Augmentations augs) throws XNIException {
/* 122 */     this.next.doctypeDecl(rootElement, publicId, systemId, augs);
/*     */   }
/*     */   
/*     */   public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
/* 126 */     this.next.emptyElement(element, attributes, augs);
/*     */   }
/*     */   
/*     */   public void endCDATA(Augmentations augs) throws XNIException {
/* 130 */     this.next.endCDATA(augs);
/*     */   }
/*     */   
/*     */   public void endDocument(Augmentations augs) throws XNIException {
/* 134 */     this.next.endDocument(augs);
/*     */   }
/*     */   
/*     */   public void endElement(QName element, Augmentations augs) throws XNIException {
/* 138 */     this.next.endElement(element, augs);
/*     */   }
/*     */   
/*     */   public void endGeneralEntity(String name, Augmentations augs) throws XNIException {
/* 142 */     this.next.endGeneralEntity(name, augs);
/*     */   }
/*     */   
/*     */   public void ignorableWhitespace(XMLString text, Augmentations augs) throws XNIException {
/* 146 */     this.next.ignorableWhitespace(text, augs);
/*     */   }
/*     */   
/*     */   public void processingInstruction(String target, XMLString data, Augmentations augs) throws XNIException {
/* 150 */     this.next.processingInstruction(target, data, augs);
/*     */   }
/*     */   
/*     */   public void startCDATA(Augmentations augs) throws XNIException {
/* 154 */     this.next.startCDATA(augs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument(XMLLocator locator, String encoding, NamespaceContext namespaceContext, Augmentations augs) throws XNIException {
/* 163 */     this.next.startDocument(locator, encoding, namespaceContext, augs);
/*     */   }
/*     */   
/*     */   public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
/* 167 */     this.next.startElement(element, attributes, augs);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startGeneralEntity(String name, XMLResourceIdentifier identifier, String encoding, Augmentations augs) throws XNIException {
/* 172 */     this.next.startGeneralEntity(name, identifier, encoding, augs);
/*     */   }
/*     */   
/*     */   public void textDecl(String version, String encoding, Augmentations augs) throws XNIException {
/* 176 */     this.next.textDecl(version, encoding, augs);
/*     */   }
/*     */   
/*     */   public void xmlDecl(String version, String encoding, String standalone, Augmentations augs) throws XNIException {
/* 180 */     this.next.xmlDecl(version, encoding, standalone, augs);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\interna\\util\XMLDocumentFilterImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */