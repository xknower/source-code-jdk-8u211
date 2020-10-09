/*    */ package com.sun.xml.internal.ws.policy.jaxws;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.PolicyException;
/*    */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*    */ import com.sun.xml.internal.ws.policy.PolicyMapExtender;
/*    */ import com.sun.xml.internal.ws.policy.PolicyMapKey;
/*    */ import com.sun.xml.internal.ws.policy.PolicySubject;
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.PolicySourceModel;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import javax.xml.namespace.QName;
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
/*    */ final class BuilderHandlerServiceScope
/*    */   extends BuilderHandler
/*    */ {
/*    */   private final QName service;
/*    */   
/*    */   BuilderHandlerServiceScope(Collection<String> policyURIs, Map<String, PolicySourceModel> policyStore, Object policySubject, QName service) {
/* 52 */     super(policyURIs, policyStore, policySubject);
/* 53 */     this.service = service;
/*    */   }
/*    */   
/*    */   protected void doPopulate(PolicyMapExtender policyMapExtender) throws PolicyException {
/* 57 */     PolicyMapKey mapKey = PolicyMap.createWsdlServiceScopeKey(this.service);
/* 58 */     for (PolicySubject subject : getPolicySubjects()) {
/* 59 */       policyMapExtender.putServiceSubject(mapKey, subject);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return this.service.toString();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\policy\jaxws\BuilderHandlerServiceScope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */