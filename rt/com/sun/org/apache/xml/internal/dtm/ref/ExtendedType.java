/*     */ package com.sun.org.apache.xml.internal.dtm.ref;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ExtendedType
/*     */ {
/*     */   private int nodetype;
/*     */   private String namespace;
/*     */   private String localName;
/*     */   private int hash;
/*     */   
/*     */   public ExtendedType(int nodetype, String namespace, String localName) {
/*  46 */     this.nodetype = nodetype;
/*  47 */     this.namespace = namespace;
/*  48 */     this.localName = localName;
/*  49 */     this.hash = nodetype + namespace.hashCode() + localName.hashCode();
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
/*     */   public ExtendedType(int nodetype, String namespace, String localName, int hash) {
/*  63 */     this.nodetype = nodetype;
/*  64 */     this.namespace = namespace;
/*  65 */     this.localName = localName;
/*  66 */     this.hash = hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void redefine(int nodetype, String namespace, String localName) {
/*  76 */     this.nodetype = nodetype;
/*  77 */     this.namespace = namespace;
/*  78 */     this.localName = localName;
/*  79 */     this.hash = nodetype + namespace.hashCode() + localName.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void redefine(int nodetype, String namespace, String localName, int hash) {
/*  89 */     this.nodetype = nodetype;
/*  90 */     this.namespace = namespace;
/*  91 */     this.localName = localName;
/*  92 */     this.hash = hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 100 */     return this.hash;
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
/*     */   public boolean equals(ExtendedType other) {
/*     */     try {
/* 113 */       return (other.nodetype == this.nodetype && other.localName
/* 114 */         .equals(this.localName) && other.namespace
/* 115 */         .equals(this.namespace));
/*     */     }
/* 117 */     catch (NullPointerException e) {
/*     */       
/* 119 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNodeType() {
/* 128 */     return this.nodetype;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalName() {
/* 136 */     return this.localName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespace() {
/* 144 */     return this.namespace;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\dtm\ref\ExtendedType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */