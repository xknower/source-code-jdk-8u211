/*     */ package com.sun.org.apache.xml.internal.dtm.ref;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMDOMException;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMIterator;
/*     */ import com.sun.org.apache.xml.internal.utils.WrappedRuntimeException;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.traversal.NodeFilter;
/*     */ import org.w3c.dom.traversal.NodeIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DTMNodeIterator
/*     */   implements NodeIterator
/*     */ {
/*     */   private DTMIterator dtm_iter;
/*     */   private boolean valid = true;
/*     */   
/*     */   public DTMNodeIterator(DTMIterator dtmIterator) {
/*     */     try {
/*  76 */       this.dtm_iter = (DTMIterator)dtmIterator.clone();
/*     */     }
/*  78 */     catch (CloneNotSupportedException cnse) {
/*     */       
/*  80 */       throw new WrappedRuntimeException(cnse);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTMIterator getDTMIterator() {
/*  89 */     return this.dtm_iter;
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
/*     */   public void detach() {
/* 105 */     this.valid = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getExpandEntityReferences() {
/* 115 */     return false;
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
/*     */   public NodeFilter getFilter() {
/* 132 */     throw new DTMDOMException((short)9);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getRoot() {
/* 141 */     int handle = this.dtm_iter.getRoot();
/* 142 */     return this.dtm_iter.getDTM(handle).getNode(handle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWhatToShow() {
/* 151 */     return this.dtm_iter.getWhatToShow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node nextNode() throws DOMException {
/* 162 */     if (!this.valid) {
/* 163 */       throw new DTMDOMException((short)11);
/*     */     }
/* 165 */     int handle = this.dtm_iter.nextNode();
/* 166 */     if (handle == -1)
/* 167 */       return null; 
/* 168 */     return this.dtm_iter.getDTM(handle).getNode(handle);
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
/*     */   public Node previousNode() {
/* 180 */     if (!this.valid) {
/* 181 */       throw new DTMDOMException((short)11);
/*     */     }
/* 183 */     int handle = this.dtm_iter.previousNode();
/* 184 */     if (handle == -1)
/* 185 */       return null; 
/* 186 */     return this.dtm_iter.getDTM(handle).getNode(handle);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\dtm\ref\DTMNodeIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */