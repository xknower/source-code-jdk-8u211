/*     */ package com.sun.org.apache.xml.internal.utils;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefixResolverDefault
/*     */   implements PrefixResolver
/*     */ {
/*     */   Node m_context;
/*     */   
/*     */   public PrefixResolverDefault(Node xpathExpressionContext) {
/*  52 */     this.m_context = xpathExpressionContext;
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
/*     */   public String getNamespaceForPrefix(String prefix) {
/*  65 */     return getNamespaceForPrefix(prefix, this.m_context);
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
/*     */   public String getNamespaceForPrefix(String prefix, Node namespaceContext) {
/*  82 */     Node parent = namespaceContext;
/*  83 */     String namespace = null;
/*     */     
/*  85 */     if (prefix.equals("xml")) {
/*     */       
/*  87 */       namespace = "http://www.w3.org/XML/1998/namespace";
/*     */     } else {
/*     */       int type;
/*     */ 
/*     */ 
/*     */       
/*  93 */       while (null != parent && null == namespace && ((
/*  94 */         type = parent.getNodeType()) == 1 || type == 5)) {
/*     */ 
/*     */         
/*  97 */         if (type == 1) {
/*     */           
/*  99 */           if (parent.getNodeName().indexOf(prefix + ":") == 0)
/* 100 */             return parent.getNamespaceURI(); 
/* 101 */           NamedNodeMap nnm = parent.getAttributes();
/*     */           
/* 103 */           for (int i = 0; i < nnm.getLength(); i++) {
/*     */             
/* 105 */             Node attr = nnm.item(i);
/* 106 */             String aname = attr.getNodeName();
/* 107 */             boolean isPrefix = aname.startsWith("xmlns:");
/*     */             
/* 109 */             if (isPrefix || aname.equals("xmlns")) {
/*     */               
/* 111 */               int index = aname.indexOf(':');
/* 112 */               String p = isPrefix ? aname.substring(index + 1) : "";
/*     */               
/* 114 */               if (p.equals(prefix)) {
/*     */                 
/* 116 */                 namespace = attr.getNodeValue();
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 124 */         parent = parent.getParentNode();
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     return namespace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseIdentifier() {
/* 138 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handlesNullPrefixes() {
/* 144 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\interna\\utils\PrefixResolverDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */