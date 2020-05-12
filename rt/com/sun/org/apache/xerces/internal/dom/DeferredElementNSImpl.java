/*     */ package com.sun.org.apache.xerces.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
/*     */ import org.w3c.dom.NamedNodeMap;
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
/*     */ public class DeferredElementNSImpl
/*     */   extends ElementNSImpl
/*     */   implements DeferredNode
/*     */ {
/*     */   static final long serialVersionUID = -5001885145370927385L;
/*     */   protected transient int fNodeIndex;
/*     */   
/*     */   DeferredElementNSImpl(DeferredDocumentImpl ownerDoc, int nodeIndex) {
/*  71 */     super(ownerDoc, (String)null);
/*     */     
/*  73 */     this.fNodeIndex = nodeIndex;
/*  74 */     needsSyncChildren(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getNodeIndex() {
/*  84 */     return this.fNodeIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void synchronizeData() {
/*  95 */     needsSyncData(false);
/*     */ 
/*     */     
/*  98 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)this.ownerDocument;
/*     */ 
/*     */ 
/*     */     
/* 102 */     boolean orig = ownerDocument.mutationEvents;
/* 103 */     ownerDocument.mutationEvents = false;
/*     */     
/* 105 */     this.name = ownerDocument.getNodeName(this.fNodeIndex);
/*     */ 
/*     */     
/* 108 */     int index = this.name.indexOf(':');
/* 109 */     if (index < 0) {
/* 110 */       this.localName = this.name;
/*     */     } else {
/*     */       
/* 113 */       this.localName = this.name.substring(index + 1);
/*     */     } 
/*     */     
/* 116 */     this.namespaceURI = ownerDocument.getNodeURI(this.fNodeIndex);
/* 117 */     this.type = (XSTypeDefinition)ownerDocument.getTypeInfo(this.fNodeIndex);
/*     */ 
/*     */     
/* 120 */     setupDefaultAttributes();
/* 121 */     int attrIndex = ownerDocument.getNodeExtra(this.fNodeIndex);
/* 122 */     if (attrIndex != -1) {
/* 123 */       NamedNodeMap attrs = getAttributes();
/* 124 */       boolean seenSchemaDefault = false;
/*     */       do {
/* 126 */         AttrImpl attr = (AttrImpl)ownerDocument.getNodeObject(attrIndex);
/*     */ 
/*     */ 
/*     */         
/* 130 */         if (!attr.getSpecified() && (seenSchemaDefault || (attr
/* 131 */           .getNamespaceURI() != null && attr
/* 132 */           .getNamespaceURI() != NamespaceContext.XMLNS_URI && attr
/* 133 */           .getName().indexOf(':') < 0))) {
/* 134 */           seenSchemaDefault = true;
/* 135 */           attrs.setNamedItemNS(attr);
/*     */         } else {
/*     */           
/* 138 */           attrs.setNamedItem(attr);
/*     */         } 
/* 140 */         attrIndex = ownerDocument.getPrevSibling(attrIndex);
/* 141 */       } while (attrIndex != -1);
/*     */     } 
/*     */ 
/*     */     
/* 145 */     ownerDocument.mutationEvents = orig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void synchronizeChildren() {
/* 157 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)ownerDocument();
/* 158 */     ownerDocument.synchronizeChildren(this, this.fNodeIndex);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\dom\DeferredElementNSImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */