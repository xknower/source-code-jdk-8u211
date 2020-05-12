/*    */ package com.sun.xml.internal.ws.model;
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
/*    */ public class RuntimeModelerException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public RuntimeModelerException(String key, Object... args) {
/* 41 */     super(key, args);
/*    */   }
/*    */   
/*    */   public RuntimeModelerException(Throwable throwable) {
/* 45 */     super(throwable);
/*    */   }
/*    */   
/*    */   public RuntimeModelerException(Localizable arg) {
/* 49 */     super("nestedModelerError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 53 */     return "com.sun.xml.internal.ws.resources.modeler";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\model\RuntimeModelerException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */