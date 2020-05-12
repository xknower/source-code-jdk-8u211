/*     */ package com.sun.org.apache.xml.internal.dtm.ref;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*     */ import com.sun.org.apache.xml.internal.utils.XMLString;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
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
/*     */ public class DTMTreeWalker
/*     */ {
/*  45 */   private ContentHandler m_contentHandler = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected DTM m_dtm;
/*     */ 
/*     */   
/*     */   boolean nextIsRaw;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDTM(DTM dtm) {
/*  57 */     this.m_dtm = dtm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentHandler getcontentHandler() {
/*  67 */     return this.m_contentHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setcontentHandler(ContentHandler ch) {
/*  77 */     this.m_contentHandler = ch;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void traverse(int pos) throws SAXException {
/* 112 */     int top = pos;
/*     */     
/* 114 */     while (-1 != pos) {
/*     */       
/* 116 */       startNode(pos);
/* 117 */       int nextNode = this.m_dtm.getFirstChild(pos);
/* 118 */       while (-1 == nextNode) {
/*     */         
/* 120 */         endNode(pos);
/*     */         
/* 122 */         if (top == pos) {
/*     */           break;
/*     */         }
/* 125 */         nextNode = this.m_dtm.getNextSibling(pos);
/*     */         
/* 127 */         if (-1 == nextNode) {
/*     */           
/* 129 */           pos = this.m_dtm.getParent(pos);
/*     */           
/* 131 */           if (-1 == pos || top == pos) {
/*     */ 
/*     */ 
/*     */             
/* 135 */             if (-1 != pos) {
/* 136 */               endNode(pos);
/*     */             }
/* 138 */             nextNode = -1;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 145 */       pos = nextNode;
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
/*     */   public void traverse(int pos, int top) throws SAXException {
/* 167 */     while (-1 != pos) {
/*     */       
/* 169 */       startNode(pos);
/* 170 */       int nextNode = this.m_dtm.getFirstChild(pos);
/* 171 */       while (-1 == nextNode) {
/*     */         
/* 173 */         endNode(pos);
/*     */         
/* 175 */         if (-1 != top && top == pos) {
/*     */           break;
/*     */         }
/* 178 */         nextNode = this.m_dtm.getNextSibling(pos);
/*     */         
/* 180 */         if (-1 == nextNode) {
/*     */           
/* 182 */           pos = this.m_dtm.getParent(pos);
/*     */           
/* 184 */           if (-1 == pos || (-1 != top && top == pos)) {
/*     */             
/* 186 */             nextNode = -1;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 193 */       pos = nextNode;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DTMTreeWalker() {
/* 198 */     this.nextIsRaw = false; } public DTMTreeWalker(ContentHandler contentHandler, DTM dtm) { this.nextIsRaw = false;
/*     */     this.m_contentHandler = contentHandler;
/*     */     this.m_dtm = dtm; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void dispatachChars(int node) throws SAXException {
/* 206 */     this.m_dtm.dispatchCharactersEvents(node, this.m_contentHandler, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void startNode(int node) throws SAXException {
/*     */     XMLString data;
/*     */     DTM dtm;
/*     */     int nsn;
/*     */     String ns;
/*     */     AttributesImpl attrs;
/*     */     int i;
/*     */     String name;
/*     */     boolean isLexH;
/*     */     LexicalHandler lh;
/* 220 */     if (this.m_contentHandler instanceof com.sun.org.apache.xml.internal.utils.NodeConsumer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 226 */     switch (this.m_dtm.getNodeType(node)) {
/*     */ 
/*     */       
/*     */       case 8:
/* 230 */         data = this.m_dtm.getStringValue(node);
/*     */         
/* 232 */         if (this.m_contentHandler instanceof LexicalHandler) {
/*     */           
/* 234 */           LexicalHandler lexicalHandler = (LexicalHandler)this.m_contentHandler;
/* 235 */           data.dispatchAsComment(lexicalHandler);
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 9:
/* 244 */         this.m_contentHandler.startDocument();
/*     */         break;
/*     */       case 1:
/* 247 */         dtm = this.m_dtm;
/*     */         
/* 249 */         for (nsn = dtm.getFirstNamespaceNode(node, true); -1 != nsn; 
/* 250 */           nsn = dtm.getNextNamespaceNode(node, nsn, true)) {
/*     */ 
/*     */           
/* 253 */           String prefix = dtm.getNodeNameX(nsn);
/*     */           
/* 255 */           this.m_contentHandler.startPrefixMapping(prefix, dtm.getNodeValue(nsn));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 261 */         ns = dtm.getNamespaceURI(node);
/* 262 */         if (null == ns) {
/* 263 */           ns = "";
/*     */         }
/*     */         
/* 266 */         attrs = new AttributesImpl();
/*     */ 
/*     */         
/* 269 */         i = dtm.getFirstAttribute(node);
/* 270 */         for (; i != -1; 
/* 271 */           i = dtm.getNextAttribute(i))
/*     */         {
/* 273 */           attrs.addAttribute(dtm.getNamespaceURI(i), dtm
/* 274 */               .getLocalName(i), dtm
/* 275 */               .getNodeName(i), "CDATA", dtm
/*     */               
/* 277 */               .getNodeValue(i));
/*     */         }
/*     */ 
/*     */         
/* 281 */         this.m_contentHandler.startElement(ns, this.m_dtm
/* 282 */             .getLocalName(node), this.m_dtm
/* 283 */             .getNodeName(node), attrs);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 288 */         name = this.m_dtm.getNodeName(node);
/*     */ 
/*     */         
/* 291 */         if (name.equals("xslt-next-is-raw")) {
/*     */           
/* 293 */           this.nextIsRaw = true;
/*     */           
/*     */           break;
/*     */         } 
/* 297 */         this.m_contentHandler.processingInstruction(name, this.m_dtm
/* 298 */             .getNodeValue(node));
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 304 */         isLexH = this.m_contentHandler instanceof LexicalHandler;
/* 305 */         lh = isLexH ? (LexicalHandler)this.m_contentHandler : null;
/*     */ 
/*     */         
/* 308 */         if (isLexH)
/*     */         {
/* 310 */           lh.startCDATA();
/*     */         }
/*     */         
/* 313 */         dispatachChars(node);
/*     */ 
/*     */         
/* 316 */         if (isLexH)
/*     */         {
/* 318 */           lh.endCDATA();
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 325 */         if (this.nextIsRaw) {
/*     */           
/* 327 */           this.nextIsRaw = false;
/*     */           
/* 329 */           this.m_contentHandler.processingInstruction("javax.xml.transform.disable-output-escaping", "");
/* 330 */           dispatachChars(node);
/* 331 */           this.m_contentHandler.processingInstruction("javax.xml.transform.enable-output-escaping", "");
/*     */           
/*     */           break;
/*     */         } 
/* 335 */         dispatachChars(node);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 341 */         if (this.m_contentHandler instanceof LexicalHandler)
/*     */         {
/* 343 */           ((LexicalHandler)this.m_contentHandler).startEntity(this.m_dtm
/* 344 */               .getNodeName(node));
/*     */         }
/*     */         break;
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
/*     */   protected void endNode(int node) throws SAXException {
/*     */     String ns;
/*     */     int nsn;
/* 368 */     switch (this.m_dtm.getNodeType(node)) {
/*     */       
/*     */       case 9:
/* 371 */         this.m_contentHandler.endDocument();
/*     */         break;
/*     */       case 1:
/* 374 */         ns = this.m_dtm.getNamespaceURI(node);
/* 375 */         if (null == ns)
/* 376 */           ns = ""; 
/* 377 */         this.m_contentHandler.endElement(ns, this.m_dtm
/* 378 */             .getLocalName(node), this.m_dtm
/* 379 */             .getNodeName(node));
/*     */         
/* 381 */         for (nsn = this.m_dtm.getFirstNamespaceNode(node, true); -1 != nsn; 
/* 382 */           nsn = this.m_dtm.getNextNamespaceNode(node, nsn, true)) {
/*     */ 
/*     */           
/* 385 */           String prefix = this.m_dtm.getNodeNameX(nsn);
/*     */           
/* 387 */           this.m_contentHandler.endPrefixMapping(prefix);
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 394 */         if (this.m_contentHandler instanceof LexicalHandler) {
/*     */           
/* 396 */           LexicalHandler lh = (LexicalHandler)this.m_contentHandler;
/*     */           
/* 398 */           lh.endEntity(this.m_dtm.getNodeName(node));
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\dtm\ref\DTMTreeWalker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */