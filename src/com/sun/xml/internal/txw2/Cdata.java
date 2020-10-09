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
/*    */ final class Cdata
/*    */   extends Text
/*    */ {
/*    */   Cdata(Document document, NamespaceResolver nsResolver, Object obj) {
/* 35 */     super(document, nsResolver, obj);
/*    */   }
/*    */   
/*    */   void accept(ContentVisitor visitor) {
/* 39 */     visitor.onCdata(this.buffer);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\txw2\Cdata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */