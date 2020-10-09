/*    */ package com.sun.xml.internal.ws.client;
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
/*    */ public enum ContentNegotiation
/*    */ {
/* 49 */   none,
/* 50 */   pessimistic,
/* 51 */   optimistic;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final String PROPERTY = "com.sun.xml.internal.ws.client.ContentNegotiation";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ContentNegotiation obtainFromSystemProperty() {
/*    */     try {
/* 67 */       String value = System.getProperty("com.sun.xml.internal.ws.client.ContentNegotiation");
/*    */       
/* 69 */       if (value == null) {
/* 70 */         return none;
/*    */       }
/*    */       
/* 73 */       return valueOf(value);
/* 74 */     } catch (Exception e) {
/*    */ 
/*    */       
/* 77 */       return none;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\client\ContentNegotiation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */