/*    */ package com.sun.xml.internal.ws.server.sei;
/*    */ 
/*    */ import com.sun.xml.internal.ws.model.ParameterImpl;
/*    */ import javax.jws.WebParam;
/*    */ import javax.xml.ws.Holder;
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
/*    */ 
/*    */ public enum ValueGetter
/*    */ {
/* 54 */   PLAIN {
/*    */     public Object get(Object parameter) {
/* 56 */       return parameter;
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   HOLDER {
/*    */     public Object get(Object parameter) {
/* 69 */       if (parameter == null)
/*    */       {
/* 71 */         return null; } 
/* 72 */       return ((Holder)parameter).value;
/*    */     }
/*    */   };
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
/*    */   public static ValueGetter get(ParameterImpl p) {
/* 86 */     if (p.getMode() == WebParam.Mode.IN || p.getIndex() == -1) {
/* 87 */       return PLAIN;
/*    */     }
/* 89 */     return HOLDER;
/*    */   }
/*    */   
/*    */   public abstract Object get(Object paramObject);
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\server\sei\ValueGetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */