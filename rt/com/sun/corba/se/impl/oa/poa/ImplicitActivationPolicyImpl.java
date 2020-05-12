/*    */ package com.sun.corba.se.impl.oa.poa;
/*    */ 
/*    */ import org.omg.CORBA.LocalObject;
/*    */ import org.omg.CORBA.Policy;
/*    */ import org.omg.PortableServer.ImplicitActivationPolicy;
/*    */ import org.omg.PortableServer.ImplicitActivationPolicyValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ImplicitActivationPolicyImpl
/*    */   extends LocalObject
/*    */   implements ImplicitActivationPolicy
/*    */ {
/*    */   private ImplicitActivationPolicyValue value;
/*    */   
/*    */   public ImplicitActivationPolicyImpl(ImplicitActivationPolicyValue paramImplicitActivationPolicyValue) {
/* 37 */     this.value = paramImplicitActivationPolicyValue;
/*    */   }
/*    */   
/*    */   public ImplicitActivationPolicyValue value() {
/* 41 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int policy_type() {
/* 46 */     return 20;
/*    */   }
/*    */   
/*    */   public Policy copy() {
/* 50 */     return new ImplicitActivationPolicyImpl(this.value);
/*    */   }
/*    */   
/*    */   public void destroy() {
/* 54 */     this.value = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 61 */     return "ImplicitActivationPolicy[" + (
/* 62 */       (this.value.value() == 0) ? "IMPLICIT_ACTIVATION" : "NO_IMPLICIT_ACTIVATION]");
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\oa\poa\ImplicitActivationPolicyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */