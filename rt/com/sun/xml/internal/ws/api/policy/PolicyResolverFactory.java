/*    */ package com.sun.xml.internal.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.jaxws.DefaultPolicyResolver;
/*    */ import com.sun.xml.internal.ws.util.ServiceFinder;
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
/*    */ public abstract class PolicyResolverFactory
/*    */ {
/*    */   public abstract PolicyResolver doCreate();
/*    */   
/*    */   public static PolicyResolver create() {
/* 46 */     for (PolicyResolverFactory factory : ServiceFinder.<PolicyResolverFactory>find(PolicyResolverFactory.class)) {
/* 47 */       PolicyResolver policyResolver = factory.doCreate();
/* 48 */       if (policyResolver != null) {
/* 49 */         return policyResolver;
/*    */       }
/*    */     } 
/*    */     
/* 53 */     return DEFAULT_POLICY_RESOLVER;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 61 */   public static final PolicyResolver DEFAULT_POLICY_RESOLVER = new DefaultPolicyResolver();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\policy\PolicyResolverFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */