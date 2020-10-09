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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class Text
/*    */   extends Content
/*    */ {
/* 37 */   protected final StringBuilder buffer = new StringBuilder();
/*    */   
/*    */   protected Text(Document document, NamespaceResolver nsResolver, Object obj) {
/* 40 */     document.writeValue(obj, nsResolver, this.buffer);
/*    */   }
/*    */   
/*    */   boolean concludesPendingStartTag() {
/* 44 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\txw2\Text.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */