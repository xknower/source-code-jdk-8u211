/*     */ package com.sun.org.apache.xml.internal.serializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ElemContext
/*     */ {
/*     */   final int m_currentElemDepth;
/*  65 */   ElemDesc m_elementDesc = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   String m_elementLocalName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   String m_elementName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   String m_elementURI = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean m_isCdataSection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean m_isRaw = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ElemContext m_next;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final ElemContext m_prev;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean m_startTagOpen = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ElemContext() {
/* 123 */     this.m_prev = this;
/*     */     
/* 125 */     this.m_currentElemDepth = 0;
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
/*     */   private ElemContext(ElemContext previous) {
/* 140 */     this.m_prev = previous;
/* 141 */     previous.m_currentElemDepth++;
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
/*     */   final ElemContext pop() {
/* 154 */     return this.m_prev;
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
/*     */   final ElemContext push() {
/* 166 */     ElemContext frame = this.m_next;
/* 167 */     if (frame == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 172 */       frame = new ElemContext(this);
/* 173 */       this.m_next = frame;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     frame.m_startTagOpen = true;
/* 182 */     return frame;
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
/*     */   final ElemContext push(String uri, String localName, String qName) {
/* 200 */     ElemContext frame = this.m_next;
/* 201 */     if (frame == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 206 */       frame = new ElemContext(this);
/* 207 */       this.m_next = frame;
/*     */     } 
/*     */ 
/*     */     
/* 211 */     frame.m_elementName = qName;
/* 212 */     frame.m_elementLocalName = localName;
/* 213 */     frame.m_elementURI = uri;
/* 214 */     frame.m_isCdataSection = false;
/* 215 */     frame.m_startTagOpen = true;
/*     */ 
/*     */ 
/*     */     
/* 219 */     return frame;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\serializer\ElemContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */