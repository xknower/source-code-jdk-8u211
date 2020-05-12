/*     */ package com.sun.xml.internal.txw2.output;
/*     */ 
/*     */ import com.sun.xml.internal.txw2.TxwException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StaxSerializer
/*     */   implements XmlSerializer
/*     */ {
/*     */   private final XMLStreamWriter out;
/*     */   
/*     */   public StaxSerializer(XMLStreamWriter writer) {
/*  45 */     this(writer, true);
/*     */   }
/*     */   
/*     */   public StaxSerializer(XMLStreamWriter writer, boolean indenting) {
/*  49 */     if (indenting)
/*  50 */       writer = new IndentingXMLStreamWriter(writer); 
/*  51 */     this.out = writer;
/*     */   }
/*     */   
/*     */   public void startDocument() {
/*     */     try {
/*  56 */       this.out.writeStartDocument();
/*  57 */     } catch (XMLStreamException e) {
/*  58 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void beginStartTag(String uri, String localName, String prefix) {
/*     */     try {
/*  64 */       this.out.writeStartElement(prefix, localName, uri);
/*  65 */     } catch (XMLStreamException e) {
/*  66 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeAttribute(String uri, String localName, String prefix, StringBuilder value) {
/*     */     try {
/*  72 */       this.out.writeAttribute(prefix, uri, localName, value.toString());
/*  73 */     } catch (XMLStreamException e) {
/*  74 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeXmlns(String prefix, String uri) {
/*     */     try {
/*  80 */       if (prefix.length() == 0) {
/*  81 */         this.out.setDefaultNamespace(uri);
/*     */       } else {
/*  83 */         this.out.setPrefix(prefix, uri);
/*     */       } 
/*     */ 
/*     */       
/*  87 */       this.out.writeNamespace(prefix, uri);
/*  88 */     } catch (XMLStreamException e) {
/*  89 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void endStartTag(String uri, String localName, String prefix) {}
/*     */ 
/*     */   
/*     */   public void endTag() {
/*     */     try {
/*  99 */       this.out.writeEndElement();
/* 100 */     } catch (XMLStreamException e) {
/* 101 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void text(StringBuilder text) {
/*     */     try {
/* 107 */       this.out.writeCharacters(text.toString());
/* 108 */     } catch (XMLStreamException e) {
/* 109 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cdata(StringBuilder text) {
/*     */     try {
/* 115 */       this.out.writeCData(text.toString());
/* 116 */     } catch (XMLStreamException e) {
/* 117 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void comment(StringBuilder comment) {
/*     */     try {
/* 123 */       this.out.writeComment(comment.toString());
/* 124 */     } catch (XMLStreamException e) {
/* 125 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endDocument() {
/*     */     try {
/* 131 */       this.out.writeEndDocument();
/* 132 */       this.out.flush();
/* 133 */     } catch (XMLStreamException e) {
/* 134 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void flush() {
/*     */     try {
/* 140 */       this.out.flush();
/* 141 */     } catch (XMLStreamException e) {
/* 142 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\txw2\output\StaxSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */