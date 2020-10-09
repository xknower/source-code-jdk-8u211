/*    */ package com.sun.xml.internal.ws.server;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.server.ResourceInjector;
/*    */ import com.sun.xml.internal.ws.api.server.WSWebServiceContext;
/*    */ import com.sun.xml.internal.ws.util.InjectionPlan;
/*    */ import javax.xml.ws.WebServiceContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DefaultResourceInjector
/*    */   extends ResourceInjector
/*    */ {
/*    */   public void inject(@NotNull WSWebServiceContext context, @NotNull Object instance) {
/* 43 */     InjectionPlan.<Object, WebServiceContext>buildInjectionPlan(instance
/* 44 */         .getClass(), WebServiceContext.class, false).inject(instance, context);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\server\DefaultResourceInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */