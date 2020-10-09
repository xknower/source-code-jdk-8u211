/*    */ package com.sun.xml.internal.ws.encoding.soap;
/*    */ 
/*    */ import com.sun.istack.internal.localization.Localizable;
/*    */ import com.sun.xml.internal.ws.util.exception.JAXWSExceptionBase;
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
/*    */ public class SerializationException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public SerializationException(String key, Object... args) {
/* 42 */     super(key, args);
/*    */   }
/*    */   
/*    */   public SerializationException(Localizable arg) {
/* 46 */     super("nestedSerializationError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public SerializationException(Throwable throwable) {
/* 50 */     super(throwable);
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 54 */     return "com.sun.xml.internal.ws.resources.encoding";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\encoding\soap\SerializationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */