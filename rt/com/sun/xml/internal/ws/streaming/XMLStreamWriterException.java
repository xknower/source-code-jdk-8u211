/*    */ package com.sun.xml.internal.ws.streaming;
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
/*    */ public class XMLStreamWriterException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public XMLStreamWriterException(String key, Object... args) {
/* 42 */     super(key, args);
/*    */   }
/*    */   
/*    */   public XMLStreamWriterException(Throwable throwable) {
/* 46 */     super(throwable);
/*    */   }
/*    */   
/*    */   public XMLStreamWriterException(Localizable arg) {
/* 50 */     super("xmlwriter.nestedError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 54 */     return "com.sun.xml.internal.ws.resources.streaming";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\streaming\XMLStreamWriterException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */