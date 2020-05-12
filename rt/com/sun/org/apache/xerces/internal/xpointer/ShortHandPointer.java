/*     */ package com.sun.org.apache.xerces.internal.xpointer;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xs.AttributePSVI;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ShortHandPointer
/*     */   implements XPointerPart
/*     */ {
/*     */   private String fShortHandPointer;
/*     */   private boolean fIsFragmentResolved = false;
/*     */   private SymbolTable fSymbolTable;
/*     */   int fMatchingChildCount;
/*     */   
/*     */   public void parseXPointer(String part) throws XNIException {
/*  69 */     this.fShortHandPointer = part;
/*     */     
/*  71 */     this.fIsFragmentResolved = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHandPointer() {
/*  81 */     this.fMatchingChildCount = 0; } public ShortHandPointer(SymbolTable symbolTable) { this.fMatchingChildCount = 0;
/*     */     this.fSymbolTable = symbolTable; }
/*     */ 
/*     */   
/*     */   public boolean resolveXPointer(QName element, XMLAttributes attributes, Augmentations augs, int event) throws XNIException {
/*  86 */     if (this.fMatchingChildCount == 0) {
/*  87 */       this.fIsFragmentResolved = false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  92 */     if (event == 0) {
/*  93 */       if (this.fMatchingChildCount == 0) {
/*  94 */         this.fIsFragmentResolved = hasMatchingIdentifier(element, attributes, augs, event);
/*     */       }
/*     */       
/*  97 */       if (this.fIsFragmentResolved) {
/*  98 */         this.fMatchingChildCount++;
/*     */       }
/* 100 */     } else if (event == 2) {
/* 101 */       if (this.fMatchingChildCount == 0) {
/* 102 */         this.fIsFragmentResolved = hasMatchingIdentifier(element, attributes, augs, event);
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 109 */     else if (this.fIsFragmentResolved) {
/* 110 */       this.fMatchingChildCount--;
/*     */     } 
/*     */ 
/*     */     
/* 114 */     return this.fIsFragmentResolved;
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
/*     */   private boolean hasMatchingIdentifier(QName element, XMLAttributes attributes, Augmentations augs, int event) throws XNIException {
/* 129 */     String normalizedValue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     if (attributes != null) {
/* 135 */       for (int i = 0; i < attributes.getLength(); i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 141 */         normalizedValue = getSchemaDeterminedID(attributes, i);
/* 142 */         if (normalizedValue != null) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 150 */         normalizedValue = getChildrenSchemaDeterminedID(attributes, i);
/* 151 */         if (normalizedValue != null) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 160 */         normalizedValue = getDTDDeterminedID(attributes, i);
/* 161 */         if (normalizedValue != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 168 */     if (normalizedValue != null && normalizedValue
/* 169 */       .equals(this.fShortHandPointer)) {
/* 170 */       return true;
/*     */     }
/*     */     
/* 173 */     return false;
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
/*     */   public String getDTDDeterminedID(XMLAttributes attributes, int index) throws XNIException {
/* 187 */     if (attributes.getType(index).equals("ID")) {
/* 188 */       return attributes.getValue(index);
/*     */     }
/* 190 */     return null;
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
/*     */   public String getSchemaDeterminedID(XMLAttributes attributes, int index) throws XNIException {
/* 204 */     Augmentations augs = attributes.getAugmentations(index);
/*     */     
/* 206 */     AttributePSVI attrPSVI = (AttributePSVI)augs.getItem("ATTRIBUTE_PSVI");
/*     */     
/* 208 */     if (attrPSVI != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 222 */       XSTypeDefinition typeDef = attrPSVI.getMemberTypeDefinition();
/* 223 */       if (typeDef != null) {
/* 224 */         typeDef = attrPSVI.getTypeDefinition();
/*     */       }
/*     */ 
/*     */       
/* 228 */       if (typeDef != null && ((XSSimpleType)typeDef).isIDType()) {
/* 229 */         return attrPSVI.getSchemaNormalizedValue();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 235 */     return null;
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
/*     */   public String getChildrenSchemaDeterminedID(XMLAttributes attributes, int index) throws XNIException {
/* 248 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFragmentResolved() {
/* 256 */     return this.fIsFragmentResolved;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChildFragmentResolved() {
/* 264 */     return this.fIsFragmentResolved & ((this.fMatchingChildCount > 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSchemeName() {
/* 273 */     return this.fShortHandPointer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSchemeData() {
/* 280 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSchemeName(String schemeName) {
/* 287 */     this.fShortHandPointer = schemeName;
/*     */   }
/*     */   
/*     */   public void setSchemeData(String schemeData) {}
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xpointer\ShortHandPointer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */