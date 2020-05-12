/*    */ package com.sun.xml.internal.ws.handler;
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
/*    */ public class HandlerException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public HandlerException(String key, Object... args) {
/* 42 */     super(key, args);
/*    */   }
/*    */   
/*    */   public HandlerException(Throwable throwable) {
/* 46 */     super(throwable);
/*    */   }
/*    */   
/*    */   public HandlerException(Localizable arg) {
/* 50 */     super("handler.nestedError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 54 */     return "com.sun.xml.internal.ws.resources.handler";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\handler\HandlerException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */