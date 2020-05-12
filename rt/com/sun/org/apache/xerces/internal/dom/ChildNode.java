/*     */ package com.sun.org.apache.xerces.internal.dom;
/*     */ 
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ChildNode
/*     */   extends NodeImpl
/*     */ {
/*     */   static final long serialVersionUID = -6112455738802414002L;
/*  42 */   transient StringBuffer fBufferStr = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChildNode previousSibling;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChildNode nextSibling;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChildNode(CoreDocumentImpl ownerDocument) {
/*  65 */     super(ownerDocument);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChildNode() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node cloneNode(boolean deep) {
/* 100 */     ChildNode newnode = (ChildNode)super.cloneNode(deep);
/*     */ 
/*     */     
/* 103 */     newnode.previousSibling = null;
/* 104 */     newnode.nextSibling = null;
/* 105 */     newnode.isFirstChild(false);
/*     */     
/* 107 */     return newnode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getParentNode() {
/* 117 */     return isOwned() ? this.ownerNode : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final NodeImpl parentNode() {
/* 126 */     return isOwned() ? this.ownerNode : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Node getNextSibling() {
/* 131 */     return this.nextSibling;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getPreviousSibling() {
/* 138 */     return isFirstChild() ? null : this.previousSibling;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final ChildNode previousSibling() {
/* 147 */     return isFirstChild() ? null : this.previousSibling;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\dom\ChildNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */