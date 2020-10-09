/*    */ package com.sun.istack.internal.localization;
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
/*    */ public class LocalizableMessageFactory
/*    */ {
/*    */   private final String _bundlename;
/*    */   
/*    */   public LocalizableMessageFactory(String bundlename) {
/* 36 */     this._bundlename = bundlename;
/*    */   }
/*    */   
/*    */   public Localizable getMessage(String key, Object... args) {
/* 40 */     return new LocalizableMessage(this._bundlename, key, args);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\istack\internal\localization\LocalizableMessageFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */