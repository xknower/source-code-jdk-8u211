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
/*    */ 
/*    */ final class BuilderHandlerOperationScope
/*    */   extends BuilderHandler
/*    */ {
/*    */   private final QName service;
/*    */   private final QName port;
/*    */   private final QName operation;
/*    */   
/*    */   BuilderHandlerOperationScope(Collection<String> policyURIs, Map<String, PolicySourceModel> policyStore, Object policySubject, QName service, QName port, QName operation) {
/* 55 */     super(policyURIs, policyStore, policySubject);
/* 56 */     this.service = service;
/* 57 */     this.port = port;
/* 58 */     this.operation = operation;
/*    */   }
/*    */   
/*    */   protected void doPopulate(PolicyMapExtender policyMapExtender) throws PolicyException {
/* 62 */     PolicyMapKey mapKey = PolicyMap.createWsdlOperationScopeKey(this.service, this.port, this.operation);
/* 63 */     for (PolicySubject subject : getPolicySubjects())
/* 64 */       policyMapExtender.putOperationSubject(mapKey, subject); 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\policy\jaxws\BuilderHandlerOperationScope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */