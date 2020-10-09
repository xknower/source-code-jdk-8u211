/*    */ package com.sun.xml.internal.ws.server;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.server.AbstractInstanceResolver;
/*    */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*    */ import com.sun.xml.internal.ws.api.server.WSWebServiceContext;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.PreDestroy;
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
/*    */ public final class SingletonResolver<T>
/*    */   extends AbstractInstanceResolver<T>
/*    */ {
/*    */   @NotNull
/*    */   private final T singleton;
/*    */   
/*    */   public SingletonResolver(@NotNull T singleton) {
/* 47 */     this.singleton = singleton;
/*    */   }
/*    */   @NotNull
/*    */   public T resolve(Packet request) {
/* 51 */     return this.singleton;
/*    */   }
/*    */   
/*    */   public void start(WSWebServiceContext wsc, WSEndpoint endpoint) {
/* 55 */     getResourceInjector(endpoint).inject(wsc, this.singleton);
/*    */     
/* 57 */     invokeMethod(findAnnotatedMethod(this.singleton.getClass(), (Class)PostConstruct.class), this.singleton, new Object[0]);
/*    */   }
/*    */   
/*    */   public void dispose() {
/* 61 */     invokeMethod(findAnnotatedMethod(this.singleton.getClass(), (Class)PreDestroy.class), this.singleton, new Object[0]);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\server\SingletonResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */