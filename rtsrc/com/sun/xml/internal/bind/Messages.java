/*    */ package com.sun.xml.internal.bind;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import java.util.ResourceBundle;
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
/*    */ enum Messages
/*    */ {
/* 35 */   FAILED_TO_INITIALE_DATATYPE_FACTORY;
/*    */   
/*    */   static {
/* 38 */     rb = ResourceBundle.getBundle(Messages.class.getName());
/*    */   }
/*    */   private static final ResourceBundle rb;
/*    */   public String toString() {
/* 42 */     return format(new Object[0]);
/*    */   }
/*    */   
/*    */   public String format(Object... args) {
/* 46 */     return MessageFormat.format(rb.getString(name()), args);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\Messages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */