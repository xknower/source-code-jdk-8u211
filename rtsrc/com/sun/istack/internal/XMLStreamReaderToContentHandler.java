/*     */ package com.sun.istack.internal;
/*     */ 
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
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
/*     */ public class XMLStreamReaderToContentHandler
/*     */ {
/*     */   private final XMLStreamReader staxStreamReader;
/*     */   private final ContentHandler saxHandler;
/*     */   private final boolean eagerQuit;
/*     */   private final boolean fragment;
/*     */   private final String[] inscopeNamespaces;
/*     */   
/*     */   public XMLStreamReaderToContentHandler(XMLStreamReader staxCore, ContentHandler saxCore, boolean eagerQuit, boolean fragment) {
/*  72 */     this(staxCore, saxCore, eagerQuit, fragment, new String[0]);
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
/*     */   public XMLStreamReaderToContentHandler(XMLStreamReader staxCore, ContentHandler saxCore, boolean eagerQuit, boolean fragment, String[] inscopeNamespaces) {
/*  90 */     this.staxStreamReader = staxCore;
/*  91 */     this.saxHandler = saxCore;
/*  92 */     this.eagerQuit = eagerQuit;
/*  93 */     this.fragment = fragment;
/*  94 */     this.inscopeNamespaces = inscopeNamespaces;
/*  95 */     assert inscopeNamespaces.length % 2 == 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bridge() throws XMLStreamException {
/*     */     try {
/* 106 */       int depth = 0;
/*     */ 
/*     */       
/* 109 */       int event = this.staxStreamReader.getEventType();
/* 110 */       if (event == 7)
/*     */       {
/* 112 */         while (!this.staxStreamReader.isStartElement()) {
/* 113 */           event = this.staxStreamReader.next();
/*     */         }
/*     */       }
/*     */       
/* 117 */       if (event != 1) {
/* 118 */         throw new IllegalStateException("The current event is not START_ELEMENT\n but " + event);
/*     */       }
/* 120 */       handleStartDocument();
/*     */       int i;
/* 122 */       for (i = 0; i < this.inscopeNamespaces.length; i += 2) {
/* 123 */         this.saxHandler.startPrefixMapping(this.inscopeNamespaces[i], this.inscopeNamespaces[i + 1]);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 131 */         switch (event) {
/*     */           case 1:
/* 133 */             depth++;
/* 134 */             handleStartElement();
/*     */             break;
/*     */           case 2:
/* 137 */             handleEndElement();
/* 138 */             depth--;
/* 139 */             if (depth == 0 && this.eagerQuit)
/*     */               break; 
/*     */             break;
/*     */           case 4:
/* 143 */             handleCharacters();
/*     */             break;
/*     */           case 9:
/* 146 */             handleEntityReference();
/*     */             break;
/*     */           case 3:
/* 149 */             handlePI();
/*     */             break;
/*     */           case 5:
/* 152 */             handleComment();
/*     */             break;
/*     */           case 11:
/* 155 */             handleDTD();
/*     */             break;
/*     */           case 10:
/* 158 */             handleAttribute();
/*     */             break;
/*     */           case 13:
/* 161 */             handleNamespace();
/*     */             break;
/*     */           case 12:
/* 164 */             handleCDATA();
/*     */             break;
/*     */           case 15:
/* 167 */             handleEntityDecl();
/*     */             break;
/*     */           case 14:
/* 170 */             handleNotationDecl();
/*     */             break;
/*     */           case 6:
/* 173 */             handleSpace();
/*     */             break;
/*     */           default:
/* 176 */             throw new InternalError("processing event: " + event);
/*     */         } 
/*     */         
/* 179 */         event = this.staxStreamReader.next();
/* 180 */       } while (depth != 0);
/*     */       
/* 182 */       for (i = 0; i < this.inscopeNamespaces.length; i += 2) {
/* 183 */         this.saxHandler.endPrefixMapping(this.inscopeNamespaces[i]);
/*     */       }
/*     */       
/* 186 */       handleEndDocument();
/* 187 */     } catch (SAXException e) {
/* 188 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleEndDocument() throws SAXException {
/* 193 */     if (this.fragment) {
/*     */       return;
/*     */     }
/* 196 */     this.saxHandler.endDocument();
/*     */   }
/*     */   
/*     */   private void handleStartDocument() throws SAXException {
/* 200 */     if (this.fragment) {
/*     */       return;
/*     */     }
/* 203 */     this.saxHandler.setDocumentLocator(new Locator() {
/*     */           public int getColumnNumber() {
/* 205 */             return XMLStreamReaderToContentHandler.this.staxStreamReader.getLocation().getColumnNumber();
/*     */           }
/*     */           public int getLineNumber() {
/* 208 */             return XMLStreamReaderToContentHandler.this.staxStreamReader.getLocation().getLineNumber();
/*     */           }
/*     */           public String getPublicId() {
/* 211 */             return XMLStreamReaderToContentHandler.this.staxStreamReader.getLocation().getPublicId();
/*     */           }
/*     */           public String getSystemId() {
/* 214 */             return XMLStreamReaderToContentHandler.this.staxStreamReader.getLocation().getSystemId();
/*     */           }
/*     */         });
/* 217 */     this.saxHandler.startDocument();
/*     */   }
/*     */   
/*     */   private void handlePI() throws XMLStreamException {
/*     */     try {
/* 222 */       this.saxHandler.processingInstruction(this.staxStreamReader
/* 223 */           .getPITarget(), this.staxStreamReader
/* 224 */           .getPIData());
/* 225 */     } catch (SAXException e) {
/* 226 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleCharacters() throws XMLStreamException {
/*     */     try {
/* 232 */       this.saxHandler.characters(this.staxStreamReader
/* 233 */           .getTextCharacters(), this.staxStreamReader
/* 234 */           .getTextStart(), this.staxStreamReader
/* 235 */           .getTextLength());
/* 236 */     } catch (SAXException e) {
/* 237 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleEndElement() throws XMLStreamException {
/* 242 */     QName qName = this.staxStreamReader.getName();
/*     */     
/*     */     try {
/* 245 */       String pfix = qName.getPrefix();
/*     */ 
/*     */       
/* 248 */       String rawname = (pfix == null || pfix.length() == 0) ? qName.getLocalPart() : (pfix + ':' + qName.getLocalPart());
/*     */       
/* 250 */       this.saxHandler.endElement(qName
/* 251 */           .getNamespaceURI(), qName
/* 252 */           .getLocalPart(), rawname);
/*     */ 
/*     */ 
/*     */       
/* 256 */       int nsCount = this.staxStreamReader.getNamespaceCount();
/* 257 */       for (int i = nsCount - 1; i >= 0; i--) {
/* 258 */         String prefix = this.staxStreamReader.getNamespacePrefix(i);
/* 259 */         if (prefix == null) {
/* 260 */           prefix = "";
/*     */         }
/* 262 */         this.saxHandler.endPrefixMapping(prefix);
/*     */       } 
/* 264 */     } catch (SAXException e) {
/* 265 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleStartElement() throws XMLStreamException {
/*     */     try {
/*     */       String rawname;
/* 273 */       int nsCount = this.staxStreamReader.getNamespaceCount();
/* 274 */       for (int i = 0; i < nsCount; i++) {
/* 275 */         this.saxHandler.startPrefixMapping(
/* 276 */             fixNull(this.staxStreamReader.getNamespacePrefix(i)), 
/* 277 */             fixNull(this.staxStreamReader.getNamespaceURI(i)));
/*     */       }
/*     */ 
/*     */       
/* 281 */       QName qName = this.staxStreamReader.getName();
/* 282 */       String prefix = qName.getPrefix();
/*     */       
/* 284 */       if (prefix == null || prefix.length() == 0) {
/* 285 */         rawname = qName.getLocalPart();
/*     */       } else {
/* 287 */         rawname = prefix + ':' + qName.getLocalPart();
/* 288 */       }  Attributes attrs = getAttributes();
/* 289 */       this.saxHandler.startElement(qName
/* 290 */           .getNamespaceURI(), qName
/* 291 */           .getLocalPart(), rawname, attrs);
/*     */     
/*     */     }
/* 294 */     catch (SAXException e) {
/* 295 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String fixNull(String s) {
/* 300 */     if (s == null) return ""; 
/* 301 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Attributes getAttributes() {
/* 311 */     AttributesImpl attrs = new AttributesImpl();
/*     */     
/* 313 */     int eventType = this.staxStreamReader.getEventType();
/* 314 */     if (eventType != 10 && eventType != 1)
/*     */     {
/* 316 */       throw new InternalError("getAttributes() attempting to process: " + eventType);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     for (int i = 0; i < this.staxStreamReader.getAttributeCount(); i++) {
/* 326 */       String qName, uri = this.staxStreamReader.getAttributeNamespace(i);
/* 327 */       if (uri == null) uri = ""; 
/* 328 */       String localName = this.staxStreamReader.getAttributeLocalName(i);
/* 329 */       String prefix = this.staxStreamReader.getAttributePrefix(i);
/*     */       
/* 331 */       if (prefix == null || prefix.length() == 0) {
/* 332 */         qName = localName;
/*     */       } else {
/* 334 */         qName = prefix + ':' + localName;
/* 335 */       }  String type = this.staxStreamReader.getAttributeType(i);
/* 336 */       String value = this.staxStreamReader.getAttributeValue(i);
/*     */       
/* 338 */       attrs.addAttribute(uri, localName, qName, type, value);
/*     */     } 
/*     */     
/* 341 */     return attrs;
/*     */   }
/*     */   
/*     */   private void handleNamespace() {}
/*     */   
/*     */   private void handleAttribute() {}
/*     */   
/*     */   private void handleDTD() {}
/*     */   
/*     */   private void handleComment() {}
/*     */   
/*     */   private void handleEntityReference() {}
/*     */   
/*     */   private void handleSpace() {}
/*     */   
/*     */   private void handleNotationDecl() {}
/*     */   
/*     */   private void handleEntityDecl() {}
/*     */   
/*     */   private void handleCDATA() {}
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\istack\internal\XMLStreamReaderToContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */