/*     */ package com.sun.org.apache.xerces.internal.impl.dtd;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*     */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*     */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLNSDTDValidator
/*     */   extends XMLDTDValidator
/*     */ {
/* 108 */   private QName fAttributeQName = new QName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void startNamespaceScope(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
/* 116 */     this.fNamespaceContext.pushContext();
/*     */     
/* 118 */     if (element.prefix == XMLSymbols.PREFIX_XMLNS) {
/* 119 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementXMLNSPrefix", new Object[] { element.rawname }, (short)2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     int length = attributes.getLength();
/* 127 */     for (int i = 0; i < length; i++) {
/* 128 */       String localpart = attributes.getLocalName(i);
/* 129 */       String str1 = attributes.getPrefix(i);
/*     */ 
/*     */       
/* 132 */       if (str1 == XMLSymbols.PREFIX_XMLNS || (str1 == XMLSymbols.EMPTY_STRING && localpart == XMLSymbols.PREFIX_XMLNS)) {
/*     */ 
/*     */ 
/*     */         
/* 136 */         String uri = this.fSymbolTable.addSymbol(attributes.getValue(i));
/*     */ 
/*     */         
/* 139 */         if (str1 == XMLSymbols.PREFIX_XMLNS && localpart == XMLSymbols.PREFIX_XMLNS) {
/* 140 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[] { attributes
/*     */                 
/* 142 */                 .getQName(i) }, (short)2);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 147 */         if (uri == NamespaceContext.XMLNS_URI) {
/* 148 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[] { attributes
/*     */                 
/* 150 */                 .getQName(i) }, (short)2);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 155 */         if (localpart == XMLSymbols.PREFIX_XML) {
/* 156 */           if (uri != NamespaceContext.XML_URI) {
/* 157 */             this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[] { attributes
/*     */                   
/* 159 */                   .getQName(i) }, (short)2);
/*     */ 
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 165 */         else if (uri == NamespaceContext.XML_URI) {
/* 166 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[] { attributes
/*     */                 
/* 168 */                 .getQName(i) }, (short)2);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 173 */         str1 = (localpart != XMLSymbols.PREFIX_XMLNS) ? localpart : XMLSymbols.EMPTY_STRING;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 178 */         if (uri == XMLSymbols.EMPTY_STRING && localpart != XMLSymbols.PREFIX_XMLNS) {
/* 179 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "EmptyPrefixedAttName", new Object[] { attributes
/*     */                 
/* 181 */                 .getQName(i) }, (short)2);
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 187 */           this.fNamespaceContext.declarePrefix(str1, (uri.length() != 0) ? uri : null);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     String prefix = (element.prefix != null) ? element.prefix : XMLSymbols.EMPTY_STRING;
/*     */     
/* 194 */     element.uri = this.fNamespaceContext.getURI(prefix);
/* 195 */     if (element.prefix == null && element.uri != null) {
/* 196 */       element.prefix = XMLSymbols.EMPTY_STRING;
/*     */     }
/* 198 */     if (element.prefix != null && element.uri == null) {
/* 199 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementPrefixUnbound", new Object[] { element.prefix, element.rawname }, (short)2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     for (int j = 0; j < length; j++) {
/* 207 */       attributes.getName(j, this.fAttributeQName);
/* 208 */       String aprefix = (this.fAttributeQName.prefix != null) ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
/*     */       
/* 210 */       String arawname = this.fAttributeQName.rawname;
/* 211 */       if (arawname == XMLSymbols.PREFIX_XMLNS) {
/* 212 */         this.fAttributeQName.uri = this.fNamespaceContext.getURI(XMLSymbols.PREFIX_XMLNS);
/* 213 */         attributes.setName(j, this.fAttributeQName);
/*     */       }
/* 215 */       else if (aprefix != XMLSymbols.EMPTY_STRING) {
/* 216 */         this.fAttributeQName.uri = this.fNamespaceContext.getURI(aprefix);
/* 217 */         if (this.fAttributeQName.uri == null) {
/* 218 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributePrefixUnbound", new Object[] { element.rawname, arawname, aprefix }, (short)2);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 223 */         attributes.setName(j, this.fAttributeQName);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 229 */     int attrCount = attributes.getLength();
/* 230 */     for (int k = 0; k < attrCount - 1; k++) {
/* 231 */       String auri = attributes.getURI(k);
/* 232 */       if (auri != null && auri != NamespaceContext.XMLNS_URI) {
/*     */ 
/*     */         
/* 235 */         String alocalpart = attributes.getLocalName(k);
/* 236 */         for (int m = k + 1; m < attrCount; m++) {
/* 237 */           String blocalpart = attributes.getLocalName(m);
/* 238 */           String buri = attributes.getURI(m);
/* 239 */           if (alocalpart == blocalpart && auri == buri) {
/* 240 */             this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNSNotUnique", new Object[] { element.rawname, alocalpart, auri }, (short)2);
/*     */           }
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
/*     */   protected void endNamespaceScope(QName element, Augmentations augs, boolean isEmpty) throws XNIException {
/* 257 */     String eprefix = (element.prefix != null) ? element.prefix : XMLSymbols.EMPTY_STRING;
/* 258 */     element.uri = this.fNamespaceContext.getURI(eprefix);
/* 259 */     if (element.uri != null) {
/* 260 */       element.prefix = eprefix;
/*     */     }
/*     */ 
/*     */     
/* 264 */     if (this.fDocumentHandler != null && 
/* 265 */       !isEmpty) {
/* 266 */       this.fDocumentHandler.endElement(element, augs);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 271 */     this.fNamespaceContext.popContext();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\dtd\XMLNSDTDValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */