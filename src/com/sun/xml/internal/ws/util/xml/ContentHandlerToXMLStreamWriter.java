/*     */ package com.sun.xml.internal.ws.util.xml;
/*     */ 
/*     */ import java.util.Stack;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContentHandlerToXMLStreamWriter
/*     */   extends DefaultHandler
/*     */ {
/*     */   private final XMLStreamWriter staxWriter;
/*     */   private final Stack prefixBindings;
/*     */   
/*     */   public ContentHandlerToXMLStreamWriter(XMLStreamWriter staxCore) {
/*  57 */     this.staxWriter = staxCore;
/*  58 */     this.prefixBindings = new Stack();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/*     */     try {
/*  68 */       this.staxWriter.writeEndDocument();
/*  69 */       this.staxWriter.flush();
/*  70 */     } catch (XMLStreamException e) {
/*  71 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/*     */     try {
/*  82 */       this.staxWriter.writeStartDocument();
/*  83 */     } catch (XMLStreamException e) {
/*  84 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/*  97 */       this.staxWriter.writeCharacters(ch, start, length);
/*  98 */     } catch (XMLStreamException e) {
/*  99 */       throw new SAXException(e);
/*     */     } 
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
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 112 */     characters(ch, start, length);
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
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {
/*     */     try {
/* 134 */       this.staxWriter.writeEntityRef(name);
/* 135 */     } catch (XMLStreamException e) {
/* 136 */       throw new SAXException(e);
/*     */     } 
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
/*     */   public void setDocumentLocator(Locator locator) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/*     */     try {
/* 162 */       this.staxWriter.writeProcessingInstruction(target, data);
/* 163 */     } catch (XMLStreamException e) {
/* 164 */       throw new SAXException(e);
/*     */     } 
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
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 179 */     if (prefix == null) {
/* 180 */       prefix = "";
/*     */     }
/*     */     
/* 183 */     if (prefix.equals("xml")) {
/*     */       return;
/*     */     }
/*     */     
/* 187 */     this.prefixBindings.add(prefix);
/* 188 */     this.prefixBindings.add(uri);
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
/*     */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/*     */     try {
/* 202 */       this.staxWriter.writeEndElement();
/* 203 */     } catch (XMLStreamException e) {
/* 204 */       throw new SAXException(e);
/*     */     } 
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
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/*     */     try {
/* 222 */       this.staxWriter.writeStartElement(
/* 223 */           getPrefix(qName), localName, namespaceURI);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 228 */       while (this.prefixBindings.size() != 0) {
/* 229 */         String uri = this.prefixBindings.pop();
/* 230 */         String prefix = this.prefixBindings.pop();
/* 231 */         if (prefix.length() == 0) {
/* 232 */           this.staxWriter.setDefaultNamespace(uri);
/*     */         } else {
/* 234 */           this.staxWriter.setPrefix(prefix, uri);
/*     */         } 
/*     */ 
/*     */         
/* 238 */         this.staxWriter.writeNamespace(prefix, uri);
/*     */       } 
/*     */       
/* 241 */       writeAttributes(atts);
/* 242 */     } catch (XMLStreamException e) {
/* 243 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeAttributes(Attributes atts) throws XMLStreamException {
/* 255 */     for (int i = 0; i < atts.getLength(); i++) {
/* 256 */       String prefix = getPrefix(atts.getQName(i));
/* 257 */       if (!prefix.equals("xmlns")) {
/* 258 */         this.staxWriter.writeAttribute(prefix, atts
/*     */             
/* 260 */             .getURI(i), atts
/* 261 */             .getLocalName(i), atts
/* 262 */             .getValue(i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getPrefix(String qName) {
/* 275 */     int idx = qName.indexOf(':');
/* 276 */     if (idx == -1) {
/* 277 */       return "";
/*     */     }
/* 279 */     return qName.substring(0, idx);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\w\\util\xml\ContentHandlerToXMLStreamWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */