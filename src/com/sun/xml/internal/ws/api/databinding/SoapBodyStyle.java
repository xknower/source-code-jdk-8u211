/*    */ package com.sun.xml.internal.ws.api.databinding;
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
/*    */ public enum SoapBodyStyle
/*    */ {
/* 40 */   DocumentBare,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   DocumentWrapper,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   RpcLiteral,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   RpcEncoded,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   Unspecificed;
/*    */   
/*    */   public boolean isDocument() {
/* 65 */     return (equals(DocumentBare) || equals(DocumentWrapper));
/*    */   }
/*    */   
/*    */   public boolean isRpc() {
/* 69 */     return (equals(RpcLiteral) || equals(RpcEncoded));
/*    */   }
/*    */   
/*    */   public boolean isLiteral() {
/* 73 */     return (equals(RpcLiteral) || isDocument());
/*    */   }
/*    */   
/*    */   public boolean isBare() {
/* 77 */     return equals(DocumentBare);
/*    */   }
/*    */   
/*    */   public boolean isDocumentWrapper() {
/* 81 */     return equals(DocumentWrapper);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\databinding\SoapBodyStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */