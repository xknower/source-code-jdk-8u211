/*     */ package com.sun.org.apache.xerces.internal.dom;
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
/*     */ public final class DeferredAttrImpl
/*     */   extends AttrImpl
/*     */   implements DeferredNode
/*     */ {
/*     */   static final long serialVersionUID = 6903232312469148636L;
/*     */   protected transient int fNodeIndex;
/*     */   
/*     */   DeferredAttrImpl(DeferredDocumentImpl ownerDocument, int nodeIndex) {
/*  94 */     super(ownerDocument, (String)null);
/*     */     
/*  96 */     this.fNodeIndex = nodeIndex;
/*  97 */     needsSyncData(true);
/*  98 */     needsSyncChildren(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNodeIndex() {
/* 108 */     return this.fNodeIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void synchronizeData() {
/* 119 */     needsSyncData(false);
/*     */ 
/*     */ 
/*     */     
/* 123 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)ownerDocument();
/* 124 */     this.name = ownerDocument.getNodeName(this.fNodeIndex);
/* 125 */     int extra = ownerDocument.getNodeExtra(this.fNodeIndex);
/* 126 */     isSpecified(((extra & 0x20) != 0));
/* 127 */     isIdAttribute(((extra & 0x200) != 0));
/*     */     
/* 129 */     int extraNode = ownerDocument.getLastChild(this.fNodeIndex);
/* 130 */     this.type = ownerDocument.getTypeInfo(extraNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void synchronizeChildren() {
/* 141 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)ownerDocument();
/* 142 */     ownerDocument.synchronizeChildren(this, this.fNodeIndex);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\dom\DeferredAttrImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */