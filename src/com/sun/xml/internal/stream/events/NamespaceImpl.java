/*    */ package com.sun.xml.internal.stream.events;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.events.Namespace;
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
/*    */ public class NamespaceImpl
/*    */   extends AttributeImpl
/*    */   implements Namespace
/*    */ {
/*    */   public NamespaceImpl() {
/* 40 */     init();
/*    */   }
/*    */ 
/*    */   
/*    */   public NamespaceImpl(String namespaceURI) {
/* 45 */     super("xmlns", "http://www.w3.org/2000/xmlns/", "", namespaceURI, (String)null);
/* 46 */     init();
/*    */   }
/*    */   
/*    */   public NamespaceImpl(String prefix, String namespaceURI) {
/* 50 */     super("xmlns", "http://www.w3.org/2000/xmlns/", prefix, namespaceURI, (String)null);
/* 51 */     init();
/*    */   }
/*    */   
/*    */   public boolean isDefaultNamespaceDeclaration() {
/* 55 */     QName name = getName();
/*    */     
/* 57 */     if (name != null && name.getLocalPart().equals(""))
/* 58 */       return true; 
/* 59 */     return false;
/*    */   }
/*    */   
/*    */   void setPrefix(String prefix) {
/* 63 */     if (prefix == null) {
/* 64 */       setName(new QName("http://www.w3.org/2000/xmlns/", "", "xmlns"));
/*    */     } else {
/* 66 */       setName(new QName("http://www.w3.org/2000/xmlns/", prefix, "xmlns"));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 72 */     QName name = getName();
/* 73 */     if (name != null)
/* 74 */       return name.getLocalPart(); 
/* 75 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getNamespaceURI() {
/* 81 */     return getValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   void setNamespaceURI(String uri) {
/* 87 */     setValue(uri);
/*    */   }
/*    */   
/*    */   protected void init() {
/* 91 */     setEventType(13);
/*    */   }
/*    */   
/*    */   public int getEventType() {
/* 95 */     return 13;
/*    */   }
/*    */   
/*    */   public boolean isNamespace() {
/* 99 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\stream\events\NamespaceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */