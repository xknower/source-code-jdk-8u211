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
/*    */ final class Pcdata
/*    */   extends Text
/*    */ {
/*    */   Pcdata(Document document, NamespaceResolver nsResolver, Object obj) {
/* 35 */     super(document, nsResolver, obj);
/*    */   }
/*    */   
/*    */   void accept(ContentVisitor visitor) {
/* 39 */     visitor.onPcdata(this.buffer);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\txw2\Pcdata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */