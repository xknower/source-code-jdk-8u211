/*     */ package com.sun.xml.internal.bind.v2.runtime;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AssociationMap<XmlNode>
/*     */ {
/*     */   static final class Entry<XmlNode>
/*     */   {
/*     */     private XmlNode element;
/*     */     private Object inner;
/*     */     private Object outer;
/*     */     
/*     */     public XmlNode element() {
/*  55 */       return this.element;
/*     */     }
/*     */     public Object inner() {
/*  58 */       return this.inner;
/*     */     }
/*     */     public Object outer() {
/*  61 */       return this.outer;
/*     */     }
/*     */   }
/*     */   
/*  65 */   private final Map<XmlNode, Entry<XmlNode>> byElement = new IdentityHashMap<>();
/*  66 */   private final Map<Object, Entry<XmlNode>> byPeer = new IdentityHashMap<>();
/*  67 */   private final Set<XmlNode> usedNodes = new HashSet<>();
/*     */ 
/*     */   
/*     */   public void addInner(XmlNode element, Object inner) {
/*  71 */     Entry<XmlNode> e = this.byElement.get(element);
/*  72 */     if (e != null) {
/*  73 */       if (e.inner != null)
/*  74 */         this.byPeer.remove(e.inner); 
/*  75 */       e.inner = inner;
/*     */     } else {
/*  77 */       e = new Entry<>();
/*  78 */       e.element = element;
/*  79 */       e.inner = inner;
/*     */     } 
/*     */     
/*  82 */     this.byElement.put(element, e);
/*     */     
/*  84 */     Entry<XmlNode> old = this.byPeer.put(inner, e);
/*  85 */     if (old != null) {
/*  86 */       if (old.outer != null)
/*  87 */         this.byPeer.remove(old.outer); 
/*  88 */       if (old.element != null) {
/*  89 */         this.byElement.remove(old.element);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addOuter(XmlNode element, Object outer) {
/*  95 */     Entry<XmlNode> e = this.byElement.get(element);
/*  96 */     if (e != null) {
/*  97 */       if (e.outer != null)
/*  98 */         this.byPeer.remove(e.outer); 
/*  99 */       e.outer = outer;
/*     */     } else {
/* 101 */       e = new Entry<>();
/* 102 */       e.element = element;
/* 103 */       e.outer = outer;
/*     */     } 
/*     */     
/* 106 */     this.byElement.put(element, e);
/*     */     
/* 108 */     Entry<XmlNode> old = this.byPeer.put(outer, e);
/* 109 */     if (old != null) {
/* 110 */       old.outer = null;
/*     */       
/* 112 */       if (old.inner == null)
/*     */       {
/* 114 */         this.byElement.remove(old.element); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addUsed(XmlNode n) {
/* 119 */     this.usedNodes.add(n);
/*     */   }
/*     */   
/*     */   public Entry<XmlNode> byElement(Object e) {
/* 123 */     return this.byElement.get(e);
/*     */   }
/*     */   
/*     */   public Entry<XmlNode> byPeer(Object o) {
/* 127 */     return this.byPeer.get(o);
/*     */   }
/*     */   
/*     */   public Object getInnerPeer(XmlNode element) {
/* 131 */     Entry<XmlNode> e = byElement(element);
/* 132 */     if (e == null) return null; 
/* 133 */     return e.inner;
/*     */   }
/*     */   
/*     */   public Object getOuterPeer(XmlNode element) {
/* 137 */     Entry<XmlNode> e = byElement(element);
/* 138 */     if (e == null) return null; 
/* 139 */     return e.outer;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtime\AssociationMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */