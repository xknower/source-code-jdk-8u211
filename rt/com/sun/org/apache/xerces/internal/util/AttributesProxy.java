/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*     */ import org.xml.sax.AttributeList;
/*     */ import org.xml.sax.ext.Attributes2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AttributesProxy
/*     */   implements AttributeList, Attributes2
/*     */ {
/*     */   private XMLAttributes fAttributes;
/*     */   
/*     */   public AttributesProxy(XMLAttributes attributes) {
/*  51 */     this.fAttributes = attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributes(XMLAttributes attributes) {
/*  60 */     this.fAttributes = attributes;
/*     */   }
/*     */   
/*     */   public XMLAttributes getAttributes() {
/*  64 */     return this.fAttributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/*  72 */     return this.fAttributes.getLength();
/*     */   }
/*     */   
/*     */   public String getQName(int index) {
/*  76 */     return this.fAttributes.getQName(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI(int index) {
/*  82 */     String uri = this.fAttributes.getURI(index);
/*  83 */     return (uri != null) ? uri : XMLSymbols.EMPTY_STRING;
/*     */   }
/*     */   
/*     */   public String getLocalName(int index) {
/*  87 */     return this.fAttributes.getLocalName(index);
/*     */   }
/*     */   
/*     */   public String getType(int i) {
/*  91 */     return this.fAttributes.getType(i);
/*     */   }
/*     */   
/*     */   public String getType(String name) {
/*  95 */     return this.fAttributes.getType(name);
/*     */   }
/*     */   
/*     */   public String getType(String uri, String localName) {
/*  99 */     return uri.equals(XMLSymbols.EMPTY_STRING) ? this.fAttributes
/* 100 */       .getType(null, localName) : this.fAttributes
/* 101 */       .getType(uri, localName);
/*     */   }
/*     */   
/*     */   public String getValue(int i) {
/* 105 */     return this.fAttributes.getValue(i);
/*     */   }
/*     */   
/*     */   public String getValue(String name) {
/* 109 */     return this.fAttributes.getValue(name);
/*     */   }
/*     */   
/*     */   public String getValue(String uri, String localName) {
/* 113 */     return uri.equals(XMLSymbols.EMPTY_STRING) ? this.fAttributes
/* 114 */       .getValue(null, localName) : this.fAttributes
/* 115 */       .getValue(uri, localName);
/*     */   }
/*     */   
/*     */   public int getIndex(String qName) {
/* 119 */     return this.fAttributes.getIndex(qName);
/*     */   }
/*     */   
/*     */   public int getIndex(String uri, String localPart) {
/* 123 */     return uri.equals(XMLSymbols.EMPTY_STRING) ? this.fAttributes
/* 124 */       .getIndex(null, localPart) : this.fAttributes
/* 125 */       .getIndex(uri, localPart);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDeclared(int index) {
/* 133 */     if (index < 0 || index >= this.fAttributes.getLength()) {
/* 134 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 136 */     return Boolean.TRUE.equals(this.fAttributes
/* 137 */         .getAugmentations(index).getItem("ATTRIBUTE_DECLARED"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDeclared(String qName) {
/* 142 */     int index = getIndex(qName);
/* 143 */     if (index == -1) {
/* 144 */       throw new IllegalArgumentException(qName);
/*     */     }
/* 146 */     return Boolean.TRUE.equals(this.fAttributes
/* 147 */         .getAugmentations(index).getItem("ATTRIBUTE_DECLARED"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDeclared(String uri, String localName) {
/* 152 */     int index = getIndex(uri, localName);
/* 153 */     if (index == -1) {
/* 154 */       throw new IllegalArgumentException(localName);
/*     */     }
/* 156 */     return Boolean.TRUE.equals(this.fAttributes
/* 157 */         .getAugmentations(index).getItem("ATTRIBUTE_DECLARED"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSpecified(int index) {
/* 162 */     if (index < 0 || index >= this.fAttributes.getLength()) {
/* 163 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 165 */     return this.fAttributes.isSpecified(index);
/*     */   }
/*     */   
/*     */   public boolean isSpecified(String qName) {
/* 169 */     int index = getIndex(qName);
/* 170 */     if (index == -1) {
/* 171 */       throw new IllegalArgumentException(qName);
/*     */     }
/* 173 */     return this.fAttributes.isSpecified(index);
/*     */   }
/*     */   
/*     */   public boolean isSpecified(String uri, String localName) {
/* 177 */     int index = getIndex(uri, localName);
/* 178 */     if (index == -1) {
/* 179 */       throw new IllegalArgumentException(localName);
/*     */     }
/* 181 */     return this.fAttributes.isSpecified(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName(int i) {
/* 189 */     return this.fAttributes.getQName(i);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\interna\\util\AttributesProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */