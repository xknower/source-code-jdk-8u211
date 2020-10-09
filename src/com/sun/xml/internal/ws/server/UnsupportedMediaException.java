/*    */ package com.sun.xml.internal.ws.server;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*    */ import com.sun.xml.internal.ws.util.exception.JAXWSExceptionBase;
/*    */ import java.util.List;
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
/*    */ public final class UnsupportedMediaException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public UnsupportedMediaException(@NotNull String contentType, List<String> expectedContentTypes) {
/* 43 */     super(ServerMessages.localizableUNSUPPORTED_CONTENT_TYPE(contentType, expectedContentTypes));
/*    */   }
/*    */   
/*    */   public UnsupportedMediaException() {
/* 47 */     super(ServerMessages.localizableNO_CONTENT_TYPE());
/*    */   }
/*    */   
/*    */   public UnsupportedMediaException(String charset) {
/* 51 */     super(ServerMessages.localizableUNSUPPORTED_CHARSET(charset));
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 55 */     return "com.sun.xml.internal.ws.resources.server";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\server\UnsupportedMediaException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */