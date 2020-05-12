/*    */ package com.sun.xml.internal.ws.policy.subject;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*    */ import com.sun.xml.internal.ws.policy.PolicyMapKey;
/*    */ import com.sun.xml.internal.ws.policy.privateutil.PolicyLogger;
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
/*    */ public class PolicyMapKeyConverter
/*    */ {
/* 41 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyMapKeyConverter.class);
/*    */   
/*    */   private final QName serviceName;
/*    */   private final QName portName;
/*    */   
/*    */   public PolicyMapKeyConverter(QName serviceName, QName portName) {
/* 47 */     this.serviceName = serviceName;
/* 48 */     this.portName = portName;
/*    */   }
/*    */   
/*    */   public PolicyMapKey getPolicyMapKey(WsdlBindingSubject subject) {
/* 52 */     LOGGER.entering(new Object[] { subject });
/*    */     
/* 54 */     PolicyMapKey key = null;
/* 55 */     if (subject.isBindingSubject()) {
/* 56 */       key = PolicyMap.createWsdlEndpointScopeKey(this.serviceName, this.portName);
/*    */     }
/* 58 */     else if (subject.isBindingOperationSubject()) {
/* 59 */       key = PolicyMap.createWsdlOperationScopeKey(this.serviceName, this.portName, subject.getName());
/*    */     }
/* 61 */     else if (subject.isBindingMessageSubject()) {
/* 62 */       if (subject.getMessageType() == WsdlBindingSubject.WsdlMessageType.FAULT) {
/* 63 */         key = PolicyMap.createWsdlFaultMessageScopeKey(this.serviceName, this.portName, subject
/* 64 */             .getParent().getName(), subject.getName());
/*    */       } else {
/*    */         
/* 67 */         key = PolicyMap.createWsdlMessageScopeKey(this.serviceName, this.portName, subject.getParent().getName());
/*    */       } 
/*    */     } 
/*    */     
/* 71 */     LOGGER.exiting(key);
/* 72 */     return key;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\policy\subject\PolicyMapKeyConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */