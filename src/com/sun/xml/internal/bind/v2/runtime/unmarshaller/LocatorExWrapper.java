/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import javax.xml.bind.ValidationEventLocator;
/*    */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
/*    */ import org.xml.sax.Locator;
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
/*    */ class LocatorExWrapper
/*    */   implements LocatorEx
/*    */ {
/*    */   private final Locator locator;
/*    */   
/*    */   public LocatorExWrapper(Locator locator) {
/* 42 */     this.locator = locator;
/*    */   }
/*    */   
/*    */   public ValidationEventLocator getLocation() {
/* 46 */     return new ValidationEventLocatorImpl(this.locator);
/*    */   }
/*    */   
/*    */   public String getPublicId() {
/* 50 */     return this.locator.getPublicId();
/*    */   }
/*    */   
/*    */   public String getSystemId() {
/* 54 */     return this.locator.getSystemId();
/*    */   }
/*    */   
/*    */   public int getLineNumber() {
/* 58 */     return this.locator.getLineNumber();
/*    */   }
/*    */   
/*    */   public int getColumnNumber() {
/* 62 */     return this.locator.getColumnNumber();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtim\\unmarshaller\LocatorExWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */