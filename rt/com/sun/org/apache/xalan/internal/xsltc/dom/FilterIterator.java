/*     */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMFilter;
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FilterIterator
/*     */   extends DTMAxisIteratorBase
/*     */ {
/*     */   private DTMAxisIterator _source;
/*     */   private final DTMFilter _filter;
/*     */   private final boolean _isReverse;
/*     */   
/*     */   public FilterIterator(DTMAxisIterator source, DTMFilter filter) {
/*  58 */     this._source = source;
/*     */     
/*  60 */     this._filter = filter;
/*  61 */     this._isReverse = source.isReverse();
/*     */   }
/*     */   
/*     */   public boolean isReverse() {
/*  65 */     return this._isReverse;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRestartable(boolean isRestartable) {
/*  70 */     this._isRestartable = isRestartable;
/*  71 */     this._source.setRestartable(isRestartable);
/*     */   }
/*     */ 
/*     */   
/*     */   public DTMAxisIterator cloneIterator() {
/*     */     try {
/*  77 */       FilterIterator clone = (FilterIterator)clone();
/*  78 */       clone._source = this._source.cloneIterator();
/*  79 */       clone._isRestartable = false;
/*  80 */       return clone.reset();
/*     */     }
/*  82 */     catch (CloneNotSupportedException e) {
/*  83 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e
/*  84 */           .toString());
/*  85 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DTMAxisIterator reset() {
/*  90 */     this._source.reset();
/*  91 */     return resetPosition();
/*     */   }
/*     */   
/*     */   public int next() {
/*     */     int node;
/*  96 */     while ((node = this._source.next()) != -1) {
/*  97 */       if (this._filter.acceptNode(node, -1) == 1) {
/*  98 */         return returnNode(node);
/*     */       }
/*     */     } 
/* 101 */     return -1;
/*     */   }
/*     */   
/*     */   public DTMAxisIterator setStartNode(int node) {
/* 105 */     if (this._isRestartable) {
/* 106 */       this._source.setStartNode(this._startNode = node);
/* 107 */       return resetPosition();
/*     */     } 
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   public void setMark() {
/* 113 */     this._source.setMark();
/*     */   }
/*     */   
/*     */   public void gotoMark() {
/* 117 */     this._source.gotoMark();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\dom\FilterIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */