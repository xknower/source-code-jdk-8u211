/*    */ package com.sun.xml.internal.ws.client.sei;
/*    */ 
/*    */ import com.sun.xml.internal.ws.model.ParameterImpl;
/*    */ import javax.jws.WebParam;
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
/*    */ abstract class ValueGetterFactory
/*    */ {
/* 41 */   static final ValueGetterFactory SYNC = new ValueGetterFactory() {
/*    */       ValueGetter get(ParameterImpl p) {
/* 43 */         return (p.getMode() == WebParam.Mode.IN || p.getIndex() == -1) ? ValueGetter.PLAIN : ValueGetter.HOLDER;
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   static final ValueGetterFactory ASYNC = new ValueGetterFactory() {
/*    */       ValueGetter get(ParameterImpl p) {
/* 54 */         return ValueGetter.PLAIN;
/*    */       }
/*    */     };
/*    */   
/*    */   abstract ValueGetter get(ParameterImpl paramParameterImpl);
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\client\sei\ValueGetterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */