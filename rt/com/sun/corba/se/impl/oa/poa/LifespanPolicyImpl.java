/*    */ package com.sun.corba.se.impl.oa.poa;
/*    */ 
/*    */ import org.omg.CORBA.LocalObject;
/*    */ import org.omg.CORBA.Policy;
/*    */ import org.omg.PortableServer.LifespanPolicy;
/*    */ import org.omg.PortableServer.LifespanPolicyValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class LifespanPolicyImpl
/*    */   extends LocalObject
/*    */   implements LifespanPolicy
/*    */ {
/*    */   private LifespanPolicyValue value;
/*    */   
/*    */   public LifespanPolicyImpl(LifespanPolicyValue paramLifespanPolicyValue) {
/* 35 */     this.value = paramLifespanPolicyValue;
/*    */   }
/*    */   
/*    */   public LifespanPolicyValue value() {
/* 39 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int policy_type() {
/* 44 */     return 17;
/*    */   }
/*    */   
/*    */   public Policy copy() {
/* 48 */     return new LifespanPolicyImpl(this.value);
/*    */   }
/*    */   
/*    */   public void destroy() {
/* 52 */     this.value = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return "LifespanPolicy[" + (
/* 60 */       (this.value.value() == 0) ? "TRANSIENT" : "PERSISTENT]");
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\oa\poa\LifespanPolicyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */