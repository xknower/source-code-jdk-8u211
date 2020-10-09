/*    */ package com.sun.xml.internal.ws.developer;
/*    */ 
/*    */ import com.sun.org.glassfish.gmbal.ManagedAttribute;
/*    */ import com.sun.org.glassfish.gmbal.ManagedData;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ManagedData
/*    */ public final class BindingTypeFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   public static final String ID = "http://jax-ws.dev.java.net/features/binding";
/*    */   private final String bindingId;
/*    */   
/*    */   public BindingTypeFeature(String bindingId) {
/* 49 */     this.bindingId = bindingId;
/*    */   }
/*    */   
/*    */   @ManagedAttribute
/*    */   public String getID() {
/* 54 */     return "http://jax-ws.dev.java.net/features/binding";
/*    */   }
/*    */   
/*    */   @ManagedAttribute
/*    */   public String getBindingId() {
/* 59 */     return this.bindingId;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\developer\BindingTypeFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */