/*     */ package com.sun.xml.internal.messaging.saaj.util;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NamespaceContextIterator
/*     */   implements Iterator
/*     */ {
/*     */   Node context;
/*  39 */   NamedNodeMap attributes = null;
/*     */   int attributesLength;
/*     */   int attributeIndex;
/*  42 */   Attr next = null;
/*  43 */   Attr last = null;
/*     */   boolean traverseStack = true;
/*     */   
/*     */   public NamespaceContextIterator(Node context) {
/*  47 */     this.context = context;
/*  48 */     findContextAttributes();
/*     */   }
/*     */   
/*     */   public NamespaceContextIterator(Node context, boolean traverseStack) {
/*  52 */     this(context);
/*  53 */     this.traverseStack = traverseStack;
/*     */   }
/*     */   
/*     */   protected void findContextAttributes() {
/*  57 */     while (this.context != null) {
/*  58 */       int type = this.context.getNodeType();
/*  59 */       if (type == 1) {
/*  60 */         this.attributes = this.context.getAttributes();
/*  61 */         this.attributesLength = this.attributes.getLength();
/*  62 */         this.attributeIndex = 0;
/*     */         return;
/*     */       } 
/*  65 */       this.context = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void findNext() {
/*  71 */     while (this.next == null && this.context != null) {
/*  72 */       for (; this.attributeIndex < this.attributesLength; this.attributeIndex++) {
/*  73 */         Node currentAttribute = this.attributes.item(this.attributeIndex);
/*  74 */         String attributeName = currentAttribute.getNodeName();
/*  75 */         if (attributeName.startsWith("xmlns") && (attributeName
/*  76 */           .length() == 5 || attributeName
/*  77 */           .charAt(5) == ':')) {
/*  78 */           this.next = (Attr)currentAttribute;
/*  79 */           this.attributeIndex++;
/*     */           return;
/*     */         } 
/*     */       } 
/*  83 */       if (this.traverseStack) {
/*  84 */         this.context = this.context.getParentNode();
/*  85 */         findContextAttributes(); continue;
/*     */       } 
/*  87 */       this.context = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/*  93 */     findNext();
/*  94 */     return (this.next != null);
/*     */   }
/*     */   
/*     */   public Object next() {
/*  98 */     return getNext();
/*     */   }
/*     */   
/*     */   public Attr nextNamespaceAttr() {
/* 102 */     return getNext();
/*     */   }
/*     */   
/*     */   protected Attr getNext() {
/* 106 */     findNext();
/* 107 */     if (this.next == null) {
/* 108 */       throw new NoSuchElementException();
/*     */     }
/* 110 */     this.last = this.next;
/* 111 */     this.next = null;
/* 112 */     return this.last;
/*     */   }
/*     */   
/*     */   public void remove() {
/* 116 */     if (this.last == null) {
/* 117 */       throw new IllegalStateException();
/*     */     }
/* 119 */     ((Element)this.context).removeAttributeNode(this.last);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saa\\util\NamespaceContextIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */