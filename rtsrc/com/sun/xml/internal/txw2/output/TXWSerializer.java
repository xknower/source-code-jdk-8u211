/*    */ package com.sun.xml.internal.txw2.output;
/*    */ 
/*    */ import com.sun.xml.internal.txw2.TypedXmlWriter;
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
/*    */ public final class TXWSerializer
/*    */   implements XmlSerializer
/*    */ {
/*    */   public final TypedXmlWriter txw;
/*    */   
/*    */   public TXWSerializer(TypedXmlWriter txw) {
/* 41 */     this.txw = txw;
/*    */   }
/*    */   
/*    */   public void startDocument() {
/* 45 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void endDocument() {
/* 49 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void beginStartTag(String uri, String localName, String prefix) {
/* 53 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void writeAttribute(String uri, String localName, String prefix, StringBuilder value) {
/* 57 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void writeXmlns(String prefix, String uri) {
/* 61 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void endStartTag(String uri, String localName, String prefix) {
/* 65 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void endTag() {
/* 69 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void text(StringBuilder text) {
/* 73 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void cdata(StringBuilder text) {
/* 77 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void comment(StringBuilder comment) {
/* 81 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void flush() {
/* 85 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\txw2\output\TXWSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */