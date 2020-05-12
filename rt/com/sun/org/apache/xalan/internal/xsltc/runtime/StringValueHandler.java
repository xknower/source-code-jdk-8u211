/*     */ package com.sun.org.apache.xalan.internal.xsltc.runtime;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.serializer.EmptySerializer;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StringValueHandler
/*     */   extends EmptySerializer
/*     */ {
/*  37 */   private StringBuilder _buffer = new StringBuilder();
/*  38 */   private String _str = null;
/*     */   private static final String EMPTY_STR = "";
/*     */   private boolean m_escaping = false;
/*  41 */   private int _nestedLevel = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int off, int len) throws SAXException {
/*  46 */     if (this._nestedLevel > 0) {
/*     */       return;
/*     */     }
/*  49 */     if (this._str != null) {
/*  50 */       this._buffer.append(this._str);
/*  51 */       this._str = null;
/*     */     } 
/*  53 */     this._buffer.append(ch, off, len);
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  57 */     if (this._buffer.length() != 0) {
/*  58 */       String str = this._buffer.toString();
/*  59 */       this._buffer.setLength(0);
/*  60 */       return str;
/*     */     } 
/*     */     
/*  63 */     String result = this._str;
/*  64 */     this._str = null;
/*  65 */     return (result != null) ? result : "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void characters(String characters) throws SAXException {
/*  70 */     if (this._nestedLevel > 0) {
/*     */       return;
/*     */     }
/*  73 */     if (this._str == null && this._buffer.length() == 0) {
/*  74 */       this._str = characters;
/*     */     } else {
/*     */       
/*  77 */       if (this._str != null) {
/*  78 */         this._buffer.append(this._str);
/*  79 */         this._str = null;
/*     */       } 
/*     */       
/*  82 */       this._buffer.append(characters);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startElement(String qname) throws SAXException {
/*  87 */     this._nestedLevel++;
/*     */   }
/*     */   
/*     */   public void endElement(String qname) throws SAXException {
/*  91 */     this._nestedLevel--;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setEscaping(boolean bool) {
/*  97 */     boolean oldEscaping = this.m_escaping;
/*  98 */     this.m_escaping = bool;
/*     */     
/* 100 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueOfPI() {
/* 108 */     String value = getValue();
/*     */     
/* 110 */     if (value.indexOf("?>") > 0) {
/* 111 */       int n = value.length();
/* 112 */       StringBuilder valueOfPI = new StringBuilder();
/*     */       
/* 114 */       for (int i = 0; i < n; ) {
/* 115 */         char ch = value.charAt(i++);
/* 116 */         if (ch == '?' && value.charAt(i) == '>') {
/* 117 */           valueOfPI.append("? >"); i++;
/*     */           continue;
/*     */         } 
/* 120 */         valueOfPI.append(ch);
/*     */       } 
/*     */       
/* 123 */       return valueOfPI.toString();
/*     */     } 
/* 125 */     return value;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\runtime\StringValueHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */