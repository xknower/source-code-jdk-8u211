/*    */ package com.sun.xml.internal.stream;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*    */ import javax.xml.stream.XMLEventReader;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ 
/*    */ public class StaxXMLInputSource
/*    */ {
/*    */   XMLStreamReader fStreamReader;
/*    */   XMLEventReader fEventReader;
/*    */   XMLInputSource fInputSource;
/*    */   boolean fHasResolver = false;
/*    */   
/*    */   public StaxXMLInputSource(XMLStreamReader streamReader) {
/* 51 */     this.fStreamReader = streamReader;
/*    */   }
/*    */ 
/*    */   
/*    */   public StaxXMLInputSource(XMLEventReader eventReader) {
/* 56 */     this.fEventReader = eventReader;
/*    */   }
/*    */   
/*    */   public StaxXMLInputSource(XMLInputSource inputSource) {
/* 60 */     this.fInputSource = inputSource;
/*    */   }
/*    */ 
/*    */   
/*    */   public StaxXMLInputSource(XMLInputSource inputSource, boolean hasResolver) {
/* 65 */     this.fInputSource = inputSource;
/* 66 */     this.fHasResolver = hasResolver;
/*    */   }
/*    */   
/*    */   public XMLStreamReader getXMLStreamReader() {
/* 70 */     return this.fStreamReader;
/*    */   }
/*    */   
/*    */   public XMLEventReader getXMLEventReader() {
/* 74 */     return this.fEventReader;
/*    */   }
/*    */   
/*    */   public XMLInputSource getXMLInputSource() {
/* 78 */     return this.fInputSource;
/*    */   }
/*    */   
/*    */   public boolean hasXMLStreamOrXMLEventReader() {
/* 82 */     return !(this.fStreamReader == null && this.fEventReader == null);
/*    */   }
/*    */   
/*    */   public boolean hasResolver() {
/* 86 */     return this.fHasResolver;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\stream\StaxXMLInputSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */