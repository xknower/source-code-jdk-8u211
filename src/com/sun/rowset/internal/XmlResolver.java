/*    */ package com.sun.rowset.internal;
/*    */ 
/*    */ import org.xml.sax.EntityResolver;
/*    */ import org.xml.sax.InputSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XmlResolver
/*    */   implements EntityResolver
/*    */ {
/*    */   public InputSource resolveEntity(String paramString1, String paramString2) {
/* 42 */     String str = paramString2.substring(paramString2.lastIndexOf("/"));
/*    */     
/* 44 */     if (paramString2.startsWith("http://java.sun.com/xml/ns/jdbc")) {
/* 45 */       return new InputSource(getClass().getResourceAsStream(str));
/*    */     }
/*    */ 
/*    */     
/* 49 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\rowset\internal\XmlResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */