/*    */ package com.sun.xml.internal.ws.api.config.management;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.server.Invoker;
/*    */ import org.xml.sax.EntityResolver;
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
/*    */ public class EndpointCreationAttributes
/*    */ {
/*    */   private final boolean processHandlerAnnotation;
/*    */   private final Invoker invoker;
/*    */   private final EntityResolver entityResolver;
/*    */   private final boolean isTransportSynchronous;
/*    */   
/*    */   public EndpointCreationAttributes(boolean processHandlerAnnotation, Invoker invoker, EntityResolver resolver, boolean isTransportSynchronous) {
/* 58 */     this.processHandlerAnnotation = processHandlerAnnotation;
/* 59 */     this.invoker = invoker;
/* 60 */     this.entityResolver = resolver;
/* 61 */     this.isTransportSynchronous = isTransportSynchronous;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isProcessHandlerAnnotation() {
/* 70 */     return this.processHandlerAnnotation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Invoker getInvoker() {
/* 79 */     return this.invoker;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityResolver getEntityResolver() {
/* 88 */     return this.entityResolver;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isTransportSynchronous() {
/* 97 */     return this.isTransportSynchronous;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\config\management\EndpointCreationAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */