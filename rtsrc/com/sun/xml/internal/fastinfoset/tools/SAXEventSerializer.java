/*     */ package com.sun.xml.internal.fastinfoset.tools;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
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
/*     */ public class SAXEventSerializer
/*     */   extends DefaultHandler
/*     */   implements LexicalHandler
/*     */ {
/*     */   private Writer _writer;
/*     */   private boolean _charactersAreCDATA;
/*     */   private StringBuffer _characters;
/*  51 */   private Stack _namespaceStack = new Stack();
/*     */   protected List _namespaceAttributes;
/*     */   
/*     */   public SAXEventSerializer(OutputStream s) throws IOException {
/*  55 */     this._writer = new OutputStreamWriter(s);
/*  56 */     this._charactersAreCDATA = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/*     */     try {
/*  63 */       this._writer.write("<sax xmlns=\"http://www.sun.com/xml/sax-events\">\n");
/*  64 */       this._writer.write("<startDocument/>\n");
/*  65 */       this._writer.flush();
/*     */     }
/*  67 */     catch (IOException e) {
/*  68 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endDocument() throws SAXException {
/*     */     try {
/*  74 */       this._writer.write("<endDocument/>\n");
/*  75 */       this._writer.write("</sax>");
/*  76 */       this._writer.flush();
/*  77 */       this._writer.close();
/*     */     }
/*  79 */     catch (IOException e) {
/*  80 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/*  88 */     if (this._namespaceAttributes == null) {
/*  89 */       this._namespaceAttributes = new ArrayList();
/*     */     }
/*     */     
/*  92 */     String qName = (prefix.length() == 0) ? "xmlns" : ("xmlns" + prefix);
/*  93 */     AttributeValueHolder attribute = new AttributeValueHolder(qName, prefix, uri, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     this._namespaceAttributes.add(attribute);
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
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/*     */     try {
/* 124 */       outputCharacters();
/*     */       
/* 126 */       if (this._namespaceAttributes != null) {
/*     */         
/* 128 */         AttributeValueHolder[] arrayOfAttributeValueHolder = new AttributeValueHolder[0];
/* 129 */         arrayOfAttributeValueHolder = (AttributeValueHolder[])this._namespaceAttributes.toArray((Object[])arrayOfAttributeValueHolder);
/*     */ 
/*     */         
/* 132 */         quicksort(arrayOfAttributeValueHolder, 0, arrayOfAttributeValueHolder.length - 1);
/*     */         
/* 134 */         for (int k = 0; k < arrayOfAttributeValueHolder.length; k++) {
/* 135 */           this._writer.write("<startPrefixMapping prefix=\"" + (arrayOfAttributeValueHolder[k]).localName + "\" uri=\"" + (arrayOfAttributeValueHolder[k]).uri + "\"/>\n");
/*     */           
/* 137 */           this._writer.flush();
/*     */         } 
/*     */         
/* 140 */         this._namespaceStack.push(arrayOfAttributeValueHolder);
/* 141 */         this._namespaceAttributes = null;
/*     */       } else {
/* 143 */         this._namespaceStack.push(null);
/*     */       } 
/*     */ 
/*     */       
/* 147 */       AttributeValueHolder[] attrsHolder = new AttributeValueHolder[attributes.getLength()];
/* 148 */       for (int i = 0; i < attributes.getLength(); i++) {
/* 149 */         attrsHolder[i] = new AttributeValueHolder(attributes
/* 150 */             .getQName(i), attributes
/* 151 */             .getLocalName(i), attributes
/* 152 */             .getURI(i), attributes
/* 153 */             .getType(i), attributes
/* 154 */             .getValue(i));
/*     */       }
/*     */ 
/*     */       
/* 158 */       quicksort(attrsHolder, 0, attrsHolder.length - 1);
/*     */       
/* 160 */       int attributeCount = 0; int j;
/* 161 */       for (j = 0; j < attrsHolder.length; j++) {
/* 162 */         if (!(attrsHolder[j]).uri.equals("http://www.w3.org/2000/xmlns/"))
/*     */         {
/*     */ 
/*     */           
/* 166 */           attributeCount++;
/*     */         }
/*     */       } 
/* 169 */       if (attributeCount == 0) {
/* 170 */         this._writer.write("<startElement uri=\"" + uri + "\" localName=\"" + localName + "\" qName=\"" + qName + "\"/>\n");
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 176 */       this._writer.write("<startElement uri=\"" + uri + "\" localName=\"" + localName + "\" qName=\"" + qName + "\">\n");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 181 */       for (j = 0; j < attrsHolder.length; j++) {
/* 182 */         if (!(attrsHolder[j]).uri.equals("http://www.w3.org/2000/xmlns/"))
/*     */         {
/*     */ 
/*     */           
/* 186 */           this._writer.write("  <attribute qName=\"" + (attrsHolder[j]).qName + "\" localName=\"" + (attrsHolder[j]).localName + "\" uri=\"" + (attrsHolder[j]).uri + "\" value=\"" + (attrsHolder[j]).value + "\"/>\n");
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 195 */       this._writer.write("</startElement>\n");
/* 196 */       this._writer.flush();
/*     */     }
/* 198 */     catch (IOException e) {
/* 199 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws SAXException {
/*     */     try {
/* 207 */       outputCharacters();
/*     */       
/* 209 */       this._writer.write("<endElement uri=\"" + uri + "\" localName=\"" + localName + "\" qName=\"" + qName + "\"/>\n");
/*     */ 
/*     */       
/* 212 */       this._writer.flush();
/*     */ 
/*     */ 
/*     */       
/* 216 */       AttributeValueHolder[] attrsHolder = this._namespaceStack.pop();
/* 217 */       if (attrsHolder != null) {
/* 218 */         for (int i = 0; i < attrsHolder.length; i++) {
/* 219 */           this._writer.write("<endPrefixMapping prefix=\"" + (attrsHolder[i]).localName + "\"/>\n");
/*     */           
/* 221 */           this._writer.flush();
/*     */         }
/*     */       
/*     */       }
/*     */     }
/* 226 */     catch (IOException e) {
/* 227 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 234 */     if (length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 238 */     if (this._characters == null) {
/* 239 */       this._characters = new StringBuffer();
/*     */     }
/*     */ 
/*     */     
/* 243 */     this._characters.append(ch, start, length);
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
/*     */   private void outputCharacters() throws SAXException {
/* 261 */     if (this._characters == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 266 */       this._writer.write("<characters>" + (this._charactersAreCDATA ? "<![CDATA[" : "") + this._characters + (this._charactersAreCDATA ? "]]>" : "") + "</characters>\n");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 271 */       this._writer.flush();
/*     */       
/* 273 */       this._characters = null;
/* 274 */     } catch (IOException e) {
/* 275 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 283 */     characters(ch, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/*     */     try {
/* 290 */       outputCharacters();
/*     */       
/* 292 */       this._writer.write("<processingInstruction target=\"" + target + "\" data=\"" + data + "\"/>\n");
/*     */       
/* 294 */       this._writer.flush();
/*     */     }
/* 296 */     catch (IOException e) {
/* 297 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDTD() throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startCDATA() throws SAXException {
/* 325 */     this._charactersAreCDATA = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void endCDATA() throws SAXException {
/* 330 */     this._charactersAreCDATA = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 337 */       outputCharacters();
/*     */       
/* 339 */       this._writer.write("<comment>" + new String(ch, start, length) + "</comment>\n");
/*     */ 
/*     */       
/* 342 */       this._writer.flush();
/*     */     }
/* 344 */     catch (IOException e) {
/* 345 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void quicksort(AttributeValueHolder[] attrs, int p, int r) {
/* 352 */     while (p < r) {
/* 353 */       int q = partition(attrs, p, r);
/* 354 */       quicksort(attrs, p, q);
/* 355 */       p = q + 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int partition(AttributeValueHolder[] attrs, int p, int r) {
/* 360 */     AttributeValueHolder x = attrs[p + r >>> 1];
/* 361 */     int i = p - 1;
/* 362 */     int j = r + 1;
/*     */     while (true) {
/* 364 */       if (x.compareTo(attrs[--j]) < 0)
/* 365 */         continue;  while (x.compareTo(attrs[++i]) > 0);
/* 366 */       if (i < j) {
/* 367 */         AttributeValueHolder t = attrs[i];
/* 368 */         attrs[i] = attrs[j];
/* 369 */         attrs[j] = t; continue;
/*     */       }  break;
/*     */     } 
/* 372 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class AttributeValueHolder
/*     */     implements Comparable
/*     */   {
/*     */     public final String qName;
/*     */     
/*     */     public final String localName;
/*     */     
/*     */     public final String uri;
/*     */     
/*     */     public final String type;
/*     */     
/*     */     public final String value;
/*     */     
/*     */     public AttributeValueHolder(String qName, String localName, String uri, String type, String value) {
/* 390 */       this.qName = qName;
/* 391 */       this.localName = localName;
/* 392 */       this.uri = uri;
/* 393 */       this.type = type;
/* 394 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int compareTo(Object o) {
/*     */       try {
/* 399 */         return this.qName.compareTo(((AttributeValueHolder)o).qName);
/* 400 */       } catch (Exception e) {
/* 401 */         throw new RuntimeException(CommonResourceBundle.getInstance().getString("message.AttributeValueHolderExpected"));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*     */       try {
/* 408 */         return (o instanceof AttributeValueHolder && this.qName
/* 409 */           .equals(((AttributeValueHolder)o).qName));
/* 410 */       } catch (Exception e) {
/* 411 */         throw new RuntimeException(CommonResourceBundle.getInstance().getString("message.AttributeValueHolderExpected"));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 417 */       int hash = 7;
/* 418 */       hash = 97 * hash + ((this.qName != null) ? this.qName.hashCode() : 0);
/* 419 */       return hash;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\fastinfoset\tools\SAXEventSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */