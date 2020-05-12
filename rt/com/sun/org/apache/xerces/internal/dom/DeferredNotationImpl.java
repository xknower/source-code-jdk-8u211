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
/*     */ public class DeferredNotationImpl
/*     */   extends NotationImpl
/*     */   implements DeferredNode
/*     */ {
/*     */   static final long serialVersionUID = 5705337172887990848L;
/*     */   protected transient int fNodeIndex;
/*     */   
/*     */   DeferredNotationImpl(DeferredDocumentImpl ownerDocument, int nodeIndex) {
/*  72 */     super(ownerDocument, (String)null);
/*     */     
/*  74 */     this.fNodeIndex = nodeIndex;
/*  75 */     needsSyncData(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNodeIndex() {
/*  85 */     return this.fNodeIndex;
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
/*     */ 
/*     */   
/*     */   protected void synchronizeData() {
/*  99 */     needsSyncData(false);
/*     */ 
/*     */ 
/*     */     
/* 103 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)ownerDocument();
/* 104 */     this.name = ownerDocument.getNodeName(this.fNodeIndex);
/*     */     
/* 106 */     ownerDocument.getNodeType(this.fNodeIndex);
/*     */     
/* 108 */     this.publicId = ownerDocument.getNodeValue(this.fNodeIndex);
/* 109 */     this.systemId = ownerDocument.getNodeURI(this.fNodeIndex);
/* 110 */     int extraDataIndex = ownerDocument.getNodeExtra(this.fNodeIndex);
/* 111 */     ownerDocument.getNodeType(extraDataIndex);
/* 112 */     this.baseURI = ownerDocument.getNodeName(extraDataIndex);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\dom\DeferredNotationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */