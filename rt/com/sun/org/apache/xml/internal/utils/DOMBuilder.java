/*     */ package com.sun.org.apache.xml.internal.utils;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.res.XMLMessages;
/*     */ import java.io.Writer;
/*     */ import java.util.Stack;
/*     */ import org.w3c.dom.CDATASection;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentFragment;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMBuilder
/*     */   implements ContentHandler, LexicalHandler
/*     */ {
/*     */   public Document m_doc;
/*  55 */   protected Node m_currentNode = null;
/*     */ 
/*     */   
/*  58 */   protected Node m_root = null;
/*     */ 
/*     */   
/*  61 */   protected Node m_nextSibling = null;
/*     */ 
/*     */   
/*  64 */   public DocumentFragment m_docFrag = null;
/*     */ 
/*     */   
/*  67 */   protected Stack m_elemStack = new Stack();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean m_inCData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getRootDocument() {
/* 116 */     return (null != this.m_docFrag) ? this.m_docFrag : this.m_doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getRootNode() {
/* 124 */     return this.m_root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getCurrentNode() {
/* 134 */     return this.m_currentNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNextSibling(Node nextSibling) {
/* 145 */     this.m_nextSibling = nextSibling;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getNextSibling() {
/* 155 */     return this.m_nextSibling;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Writer getWriter() {
/* 165 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void append(Node newNode) throws SAXException {
/* 176 */     Node currentNode = this.m_currentNode;
/*     */     
/* 178 */     if (null != currentNode) {
/*     */       
/* 180 */       if (currentNode == this.m_root && this.m_nextSibling != null) {
/* 181 */         currentNode.insertBefore(newNode, this.m_nextSibling);
/*     */       } else {
/* 183 */         currentNode.appendChild(newNode);
/*     */       }
/*     */     
/*     */     }
/* 187 */     else if (null != this.m_docFrag) {
/*     */       
/* 189 */       if (this.m_nextSibling != null) {
/* 190 */         this.m_docFrag.insertBefore(newNode, this.m_nextSibling);
/*     */       } else {
/* 192 */         this.m_docFrag.appendChild(newNode);
/*     */       } 
/*     */     } else {
/*     */       
/* 196 */       boolean ok = true;
/* 197 */       short type = newNode.getNodeType();
/*     */       
/* 199 */       if (type == 3) {
/*     */         
/* 201 */         String data = newNode.getNodeValue();
/*     */         
/* 203 */         if (null != data && data.trim().length() > 0)
/*     */         {
/* 205 */           throw new SAXException(
/* 206 */               XMLMessages.createXMLMessage("ER_CANT_OUTPUT_TEXT_BEFORE_DOC", null));
/*     */         }
/*     */ 
/*     */         
/* 210 */         ok = false;
/*     */       }
/* 212 */       else if (type == 1) {
/*     */         
/* 214 */         if (this.m_doc.getDocumentElement() != null) {
/*     */           
/* 216 */           ok = false;
/*     */           
/* 218 */           throw new SAXException(
/* 219 */               XMLMessages.createXMLMessage("ER_CANT_HAVE_MORE_THAN_ONE_ROOT", null));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 224 */       if (ok)
/*     */       {
/* 226 */         if (this.m_nextSibling != null) {
/* 227 */           this.m_doc.insertBefore(newNode, this.m_nextSibling);
/*     */         } else {
/* 229 */           this.m_doc.appendChild(newNode);
/*     */         } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String ns, String localName, String name, Attributes atts) throws SAXException {
/*     */     Element elem;
/* 325 */     if (null == ns || ns.length() == 0) {
/* 326 */       elem = this.m_doc.createElementNS((String)null, name);
/*     */     } else {
/* 328 */       elem = this.m_doc.createElementNS(ns, name);
/*     */     } 
/* 330 */     append(elem);
/*     */ 
/*     */     
/*     */     try {
/* 334 */       int nAtts = atts.getLength();
/*     */       
/* 336 */       if (0 != nAtts)
/*     */       {
/* 338 */         for (int i = 0; i < nAtts; i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 343 */           if (atts.getType(i).equalsIgnoreCase("ID")) {
/* 344 */             setIDAttribute(atts.getValue(i), elem);
/*     */           }
/* 346 */           String attrNS = atts.getURI(i);
/*     */           
/* 348 */           if ("".equals(attrNS)) {
/* 349 */             attrNS = null;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 354 */           String attrQName = atts.getQName(i);
/*     */ 
/*     */ 
/*     */           
/* 358 */           if (attrQName.startsWith("xmlns:") || attrQName.equals("xmlns")) {
/* 359 */             attrNS = "http://www.w3.org/2000/xmlns/";
/*     */           }
/*     */ 
/*     */           
/* 363 */           elem.setAttributeNS(attrNS, attrQName, atts.getValue(i));
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 369 */       this.m_elemStack.push(elem);
/*     */       
/* 371 */       this.m_currentNode = elem;
/*     */ 
/*     */     
/*     */     }
/* 375 */     catch (Exception de) {
/*     */ 
/*     */       
/* 378 */       throw new SAXException(de);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String ns, String localName, String name) throws SAXException {
/* 405 */     this.m_elemStack.pop();
/* 406 */     this.m_currentNode = this.m_elemStack.isEmpty() ? null : this.m_elemStack.peek();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIDAttribute(String id, Element elem) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 446 */     if (isOutsideDocElem() && 
/* 447 */       XMLCharacterRecognizer.isWhiteSpace(ch, start, length)) {
/*     */       return;
/*     */     }
/* 450 */     if (this.m_inCData) {
/*     */       
/* 452 */       cdata(ch, start, length);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 457 */     String s = new String(ch, start, length);
/*     */     
/* 459 */     Node childNode = (this.m_currentNode != null) ? this.m_currentNode.getLastChild() : null;
/* 460 */     if (childNode != null && childNode.getNodeType() == 3) {
/* 461 */       ((Text)childNode).appendData(s);
/*     */     } else {
/*     */       
/* 464 */       Text text = this.m_doc.createTextNode(s);
/* 465 */       append(text);
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
/*     */   public void charactersRaw(char[] ch, int start, int length) throws SAXException {
/* 482 */     if (isOutsideDocElem() && 
/* 483 */       XMLCharacterRecognizer.isWhiteSpace(ch, start, length)) {
/*     */       return;
/*     */     }
/*     */     
/* 487 */     String s = new String(ch, start, length);
/*     */     
/* 489 */     append(this.m_doc.createProcessingInstruction("xslt-next-is-raw", "formatter-to-dom"));
/*     */     
/* 491 */     append(this.m_doc.createTextNode(s));
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
/*     */   public void startEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void entityReference(String name) throws SAXException {
/* 530 */     append(this.m_doc.createEntityReference(name));
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
/*     */ 
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
/* 558 */     if (isOutsideDocElem()) {
/*     */       return;
/*     */     }
/* 561 */     String s = new String(ch, start, length);
/*     */     
/* 563 */     append(this.m_doc.createTextNode(s));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isOutsideDocElem() {
/* 573 */     return (null == this.m_docFrag && this.m_elemStack.size() == 0 && (null == this.m_currentNode || this.m_currentNode.getNodeType() == 9));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/* 594 */     append(this.m_doc.createProcessingInstruction(target, data));
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
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/* 610 */     append(this.m_doc.createComment(new String(ch, start, length)));
/*     */   }
/*     */   
/*     */   public DOMBuilder(Document doc, Node node) {
/* 614 */     this.m_inCData = false; this.m_doc = doc; this.m_currentNode = this.m_root = node; if (node instanceof Element) this.m_elemStack.push(node);  } public DOMBuilder(Document doc, DocumentFragment docFrag) { this.m_inCData = false; this.m_doc = doc; this.m_docFrag = docFrag; } public DOMBuilder(Document doc) { this.m_inCData = false;
/*     */     this.m_doc = doc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startCDATA() throws SAXException {
/* 623 */     this.m_inCData = true;
/* 624 */     append(this.m_doc.createCDATASection(""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endCDATA() throws SAXException {
/* 634 */     this.m_inCData = false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cdata(char[] ch, int start, int length) throws SAXException {
/* 662 */     if (isOutsideDocElem() && 
/* 663 */       XMLCharacterRecognizer.isWhiteSpace(ch, start, length)) {
/*     */       return;
/*     */     }
/* 666 */     String s = new String(ch, start, length);
/*     */     
/* 668 */     CDATASection section = (CDATASection)this.m_currentNode.getLastChild();
/* 669 */     section.appendData(s);
/*     */   }
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {}
/*     */   
/*     */   public void endDTD() throws SAXException {}
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {}
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {}
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\interna\\utils\DOMBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */