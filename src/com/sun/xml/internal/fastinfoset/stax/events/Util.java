/*    */ package com.sun.xml.internal.fastinfoset.stax.events;
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
/*    */ public class Util
/*    */ {
/*    */   public static boolean isEmptyString(String s) {
/* 42 */     if (s != null && !s.equals("")) {
/* 43 */       return false;
/*    */     }
/* 45 */     return true;
/*    */   }
/*    */   
/*    */   public static final String getEventTypeString(int eventType) {
/* 49 */     switch (eventType) {
/*    */       case 1:
/* 51 */         return "START_ELEMENT";
/*    */       case 2:
/* 53 */         return "END_ELEMENT";
/*    */       case 3:
/* 55 */         return "PROCESSING_INSTRUCTION";
/*    */       case 4:
/* 57 */         return "CHARACTERS";
/*    */       case 5:
/* 59 */         return "COMMENT";
/*    */       case 7:
/* 61 */         return "START_DOCUMENT";
/*    */       case 8:
/* 63 */         return "END_DOCUMENT";
/*    */       case 9:
/* 65 */         return "ENTITY_REFERENCE";
/*    */       case 10:
/* 67 */         return "ATTRIBUTE";
/*    */       case 11:
/* 69 */         return "DTD";
/*    */       case 12:
/* 71 */         return "CDATA";
/*    */     } 
/* 73 */     return "UNKNOWN_EVENT_TYPE";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\fastinfoset\stax\events\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */