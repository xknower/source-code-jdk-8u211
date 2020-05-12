/*     */ package com.sun.org.apache.xpath.internal.jaxp;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*     */ import javax.xml.namespace.NamespaceContext;
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
/*     */ public class JAXPPrefixResolver
/*     */   implements PrefixResolver
/*     */ {
/*     */   private NamespaceContext namespaceContext;
/*     */   public static final String S_XMLNAMESPACEURI = "http://www.w3.org/XML/1998/namespace";
/*     */   
/*     */   public JAXPPrefixResolver(NamespaceContext nsContext) {
/*  44 */     this.namespaceContext = nsContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespaceForPrefix(String prefix) {
/*  49 */     return this.namespaceContext.getNamespaceURI(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseIdentifier() {
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handlesNullPrefixes() {
/*  65 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceForPrefix(String prefix, Node namespaceContext) {
/*  90 */     Node parent = namespaceContext;
/*  91 */     String namespace = null;
/*     */     
/*  93 */     if (prefix.equals("xml")) {
/*  94 */       namespace = "http://www.w3.org/XML/1998/namespace";
/*     */     } else {
/*     */       int type;
/*     */       
/*  98 */       while (null != parent && null == namespace && ((
/*  99 */         type = parent.getNodeType()) == 1 || type == 5)) {
/*     */ 
/*     */         
/* 102 */         if (type == 1) {
/* 103 */           NamedNodeMap nnm = parent.getAttributes();
/*     */           
/* 105 */           for (int i = 0; i < nnm.getLength(); i++) {
/* 106 */             Node attr = nnm.item(i);
/* 107 */             String aname = attr.getNodeName();
/* 108 */             boolean isPrefix = aname.startsWith("xmlns:");
/*     */             
/* 110 */             if (isPrefix || aname.equals("xmlns")) {
/* 111 */               int index = aname.indexOf(':');
/* 112 */               String p = isPrefix ? aname.substring(index + 1) : "";
/*     */               
/* 114 */               if (p.equals(prefix)) {
/* 115 */                 namespace = attr.getNodeValue();
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 122 */         parent = parent.getParentNode();
/*     */       } 
/*     */     } 
/* 125 */     return namespace;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\jaxp\JAXPPrefixResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */