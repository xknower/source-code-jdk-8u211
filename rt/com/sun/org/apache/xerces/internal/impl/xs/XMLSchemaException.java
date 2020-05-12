/*    */ package com.sun.org.apache.xerces.internal.impl.xs;
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
/*    */ public class XMLSchemaException
/*    */   extends Exception
/*    */ {
/*    */   static final long serialVersionUID = -9096984648537046218L;
/*    */   String key;
/*    */   Object[] args;
/*    */   
/*    */   public XMLSchemaException(String key, Object[] args) {
/* 41 */     this.key = key;
/* 42 */     this.args = args;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 46 */     return this.key;
/*    */   }
/*    */   
/*    */   public Object[] getArgs() {
/* 50 */     return this.args;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\xs\XMLSchemaException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */