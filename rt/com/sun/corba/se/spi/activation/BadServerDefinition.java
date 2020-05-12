/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BadServerDefinition
/*    */   extends UserException
/*    */ {
/* 13 */   public String reason = null;
/*    */ 
/*    */   
/*    */   public BadServerDefinition() {
/* 17 */     super(BadServerDefinitionHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public BadServerDefinition(String paramString) {
/* 22 */     super(BadServerDefinitionHelper.id());
/* 23 */     this.reason = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BadServerDefinition(String paramString1, String paramString2) {
/* 29 */     super(BadServerDefinitionHelper.id() + "  " + paramString1);
/* 30 */     this.reason = paramString2;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\BadServerDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */