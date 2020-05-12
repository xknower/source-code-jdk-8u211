/*    */ package com.sun.org.apache.xerces.internal.impl.xs.opti;
/*    */ 
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.DOMImplementation;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.DocumentType;
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
/*    */ final class SchemaDOMImplementation
/*    */   implements DOMImplementation
/*    */ {
/* 35 */   private static final SchemaDOMImplementation singleton = new SchemaDOMImplementation();
/*    */ 
/*    */   
/*    */   public static DOMImplementation getDOMImplementation() {
/* 39 */     return singleton;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype) throws DOMException {
/* 46 */     throw new DOMException((short)9, "Method not supported");
/*    */   }
/*    */ 
/*    */   
/*    */   public DocumentType createDocumentType(String qualifiedName, String publicId, String systemId) throws DOMException {
/* 51 */     throw new DOMException((short)9, "Method not supported");
/*    */   }
/*    */   
/*    */   public Object getFeature(String feature, String version) {
/* 55 */     if (singleton.hasFeature(feature, version)) {
/* 56 */       return singleton;
/*    */     }
/* 58 */     return null;
/*    */   }
/*    */   
/*    */   public boolean hasFeature(String feature, String version) {
/* 62 */     boolean anyVersion = (version == null || version.length() == 0);
/* 63 */     return ((feature.equalsIgnoreCase("Core") || feature.equalsIgnoreCase("XML")) && (anyVersion || version
/* 64 */       .equals("1.0") || version.equals("2.0") || version.equals("3.0")));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\xs\opti\SchemaDOMImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */