/*    */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*    */ import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;
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
/*    */ public final class ClonedNodeListIterator
/*    */   extends DTMAxisIteratorBase
/*    */ {
/*    */   private CachedNodeListIterator _source;
/* 40 */   private int _index = 0;
/*    */   
/*    */   public ClonedNodeListIterator(CachedNodeListIterator source) {
/* 43 */     this._source = source;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRestartable(boolean isRestartable) {}
/*    */ 
/*    */   
/*    */   public DTMAxisIterator setStartNode(int node) {
/* 52 */     return this;
/*    */   }
/*    */   
/*    */   public int next() {
/* 56 */     return this._source.getNode(this._index++);
/*    */   }
/*    */   
/*    */   public int getPosition() {
/* 60 */     return (this._index == 0) ? 1 : this._index;
/*    */   }
/*    */   
/*    */   public int getNodeByPosition(int pos) {
/* 64 */     return this._source.getNode(pos);
/*    */   }
/*    */   
/*    */   public DTMAxisIterator cloneIterator() {
/* 68 */     return this._source.cloneIterator();
/*    */   }
/*    */   
/*    */   public DTMAxisIterator reset() {
/* 72 */     this._index = 0;
/* 73 */     return this;
/*    */   }
/*    */   
/*    */   public void setMark() {
/* 77 */     this._source.setMark();
/*    */   }
/*    */   
/*    */   public void gotoMark() {
/* 81 */     this._source.gotoMark();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\dom\ClonedNodeListIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */