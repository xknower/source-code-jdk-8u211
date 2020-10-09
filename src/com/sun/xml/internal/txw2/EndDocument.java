/*    */ package com.sun.xml.internal.txw2;
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
/*    */ final class EndDocument
/*    */   extends Content
/*    */ {
/*    */   boolean concludesPendingStartTag() {
/* 33 */     return true;
/*    */   }
/*    */   
/*    */   void accept(ContentVisitor visitor) {
/* 37 */     visitor.onEndDocument();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\txw2\EndDocument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */