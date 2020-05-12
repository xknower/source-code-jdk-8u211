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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Attribute
/*    */ {
/*    */   final String nsUri;
/*    */   final String localName;
/*    */   Attribute next;
/* 43 */   final StringBuilder value = new StringBuilder();
/*    */   
/*    */   Attribute(String nsUri, String localName) {
/* 46 */     assert nsUri != null && localName != null;
/*    */     
/* 48 */     this.nsUri = nsUri;
/* 49 */     this.localName = localName;
/*    */   }
/*    */   
/*    */   boolean hasName(String nsUri, String localName) {
/* 53 */     return (this.localName.equals(localName) && this.nsUri.equals(nsUri));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\txw2\Attribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */