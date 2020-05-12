/*    */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*    */ 
/*    */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*    */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
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
/*    */ public final class UnionIterator
/*    */   extends MultiValuedNodeHeapIterator
/*    */ {
/*    */   private final DOM _dom;
/*    */   
/*    */   private final class LookAheadIterator
/*    */     extends MultiValuedNodeHeapIterator.HeapNode
/*    */   {
/*    */     public DTMAxisIterator iterator;
/*    */     
/*    */     public LookAheadIterator(DTMAxisIterator iterator) {
/* 52 */       this.iterator = iterator;
/*    */     }
/*    */     
/*    */     public int step() {
/* 56 */       this._node = this.iterator.next();
/* 57 */       return this._node;
/*    */     }
/*    */     
/*    */     public MultiValuedNodeHeapIterator.HeapNode cloneHeapNode() {
/* 61 */       LookAheadIterator clone = (LookAheadIterator)super.cloneHeapNode();
/* 62 */       clone.iterator = this.iterator.cloneIterator();
/* 63 */       return clone;
/*    */     }
/*    */     
/*    */     public void setMark() {
/* 67 */       super.setMark();
/* 68 */       this.iterator.setMark();
/*    */     }
/*    */     
/*    */     public void gotoMark() {
/* 72 */       super.gotoMark();
/* 73 */       this.iterator.gotoMark();
/*    */     }
/*    */     
/*    */     public boolean isLessThan(MultiValuedNodeHeapIterator.HeapNode heapNode) {
/* 77 */       LookAheadIterator comparand = (LookAheadIterator)heapNode;
/* 78 */       return UnionIterator.this._dom.lessThan(this._node, heapNode._node);
/*    */     }
/*    */     
/*    */     public MultiValuedNodeHeapIterator.HeapNode setStartNode(int node) {
/* 82 */       this.iterator.setStartNode(node);
/* 83 */       return this;
/*    */     }
/*    */     
/*    */     public MultiValuedNodeHeapIterator.HeapNode reset() {
/* 87 */       this.iterator.reset();
/* 88 */       return this;
/*    */     }
/*    */   }
/*    */   
/*    */   public UnionIterator(DOM dom) {
/* 93 */     this._dom = dom;
/*    */   }
/*    */   
/*    */   public UnionIterator addIterator(DTMAxisIterator iterator) {
/* 97 */     addHeapNode(new LookAheadIterator(iterator));
/* 98 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\dom\UnionIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */