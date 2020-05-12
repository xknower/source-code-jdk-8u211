/*     */ package com.sun.xml.internal.stream.buffer.stax;
/*     */ 
/*     */ import com.sun.xml.internal.stream.buffer.AbstractCreator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class StreamBufferCreator
/*     */   extends AbstractCreator
/*     */ {
/*     */   private boolean checkAttributeValue = false;
/*  43 */   protected List<String> attributeValuePrefixes = new ArrayList<>();
/*     */   
/*     */   protected void storeQualifiedName(int item, String prefix, String uri, String localName) {
/*  46 */     if (uri != null && uri.length() > 0) {
/*  47 */       if (prefix != null && prefix.length() > 0) {
/*  48 */         item |= 0x1;
/*  49 */         storeStructureString(prefix);
/*     */       } 
/*     */       
/*  52 */       item |= 0x2;
/*  53 */       storeStructureString(uri);
/*     */     } 
/*     */     
/*  56 */     storeStructureString(localName);
/*     */     
/*  58 */     storeStructure(item);
/*     */   }
/*     */   
/*     */   protected final void storeNamespaceAttribute(String prefix, String uri) {
/*  62 */     int item = 64;
/*     */     
/*  64 */     if (prefix != null && prefix.length() > 0) {
/*  65 */       item |= 0x1;
/*  66 */       storeStructureString(prefix);
/*     */     } 
/*     */     
/*  69 */     if (uri != null && uri.length() > 0) {
/*  70 */       item |= 0x2;
/*  71 */       storeStructureString(uri);
/*     */     } 
/*     */     
/*  74 */     storeStructure(item);
/*     */   }
/*     */   
/*     */   protected final void storeAttribute(String prefix, String uri, String localName, String type, String value) {
/*  78 */     storeQualifiedName(48, prefix, uri, localName);
/*     */     
/*  80 */     storeStructureString(type);
/*  81 */     storeContentString(value);
/*  82 */     if (this.checkAttributeValue && value.indexOf("://") == -1) {
/*  83 */       int firstIndex = value.indexOf(":");
/*  84 */       int lastIndex = value.lastIndexOf(":");
/*  85 */       if (firstIndex != -1 && lastIndex == firstIndex) {
/*  86 */         String valuePrefix = value.substring(0, firstIndex);
/*  87 */         if (!this.attributeValuePrefixes.contains(valuePrefix)) {
/*  88 */           this.attributeValuePrefixes.add(valuePrefix);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public final List getAttributeValuePrefixes() {
/*  95 */     return this.attributeValuePrefixes;
/*     */   }
/*     */   
/*     */   protected final void storeProcessingInstruction(String target, String data) {
/*  99 */     storeStructure(112);
/* 100 */     storeStructureString(target);
/* 101 */     storeStructureString(data);
/*     */   }
/*     */   
/*     */   public final boolean isCheckAttributeValue() {
/* 105 */     return this.checkAttributeValue;
/*     */   }
/*     */   
/*     */   public final void setCheckAttributeValue(boolean value) {
/* 109 */     this.checkAttributeValue = value;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\stream\buffer\stax\StreamBufferCreator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */