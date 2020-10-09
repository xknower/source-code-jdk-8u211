/*    */ package com.sun.xml.internal.ws.client.sei;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MethodHandler
/*    */ {
/*    */   protected final SEIStub owner;
/*    */   protected Method method;
/*    */   
/*    */   protected MethodHandler(SEIStub owner, Method m) {
/* 50 */     this.owner = owner;
/* 51 */     this.method = m;
/*    */   }
/*    */   
/*    */   abstract Object invoke(Object paramObject, Object[] paramArrayOfObject) throws WebServiceException, Throwable;
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\client\sei\MethodHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */