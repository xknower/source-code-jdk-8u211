/*     */ package com.sun.xml.internal.ws.policy.jaxws;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.policy.AlternativeSelector;
/*     */ import com.sun.xml.internal.ws.api.policy.PolicyResolver;
/*     */ import com.sun.xml.internal.ws.api.policy.ValidationProcessor;
/*     */ import com.sun.xml.internal.ws.policy.AssertionSet;
/*     */ import com.sun.xml.internal.ws.policy.EffectivePolicyModifier;
/*     */ import com.sun.xml.internal.ws.policy.Policy;
/*     */ import com.sun.xml.internal.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.internal.ws.policy.PolicyException;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import com.sun.xml.internal.ws.policy.spi.PolicyAssertionValidator;
/*     */ import com.sun.xml.internal.ws.resources.PolicyMessages;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultPolicyResolver
/*     */   implements PolicyResolver
/*     */ {
/*     */   public PolicyMap resolve(PolicyResolver.ServerContext context) {
/*  52 */     PolicyMap map = context.getPolicyMap();
/*  53 */     if (map != null)
/*  54 */       validateServerPolicyMap(map); 
/*  55 */     return map;
/*     */   }
/*     */   
/*     */   public PolicyMap resolve(PolicyResolver.ClientContext context) {
/*  59 */     PolicyMap map = context.getPolicyMap();
/*  60 */     if (map != null)
/*  61 */       map = doAlternativeSelection(map); 
/*  62 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateServerPolicyMap(PolicyMap policyMap) {
/*     */     try {
/*  73 */       ValidationProcessor validationProcessor = ValidationProcessor.getInstance();
/*     */       
/*  75 */       for (Policy policy : policyMap) {
/*     */ 
/*     */ 
/*     */         
/*  79 */         for (AssertionSet assertionSet : policy) {
/*  80 */           for (PolicyAssertion assertion : assertionSet) {
/*  81 */             PolicyAssertionValidator.Fitness validationResult = validationProcessor.validateServerSide(assertion);
/*  82 */             if (validationResult != PolicyAssertionValidator.Fitness.SUPPORTED) {
/*  83 */               throw new PolicyException(PolicyMessages.WSP_1015_SERVER_SIDE_ASSERTION_VALIDATION_FAILED(assertion
/*  84 */                     .getName(), validationResult));
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*  90 */     } catch (PolicyException e) {
/*  91 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PolicyMap doAlternativeSelection(PolicyMap policyMap) {
/* 102 */     EffectivePolicyModifier modifier = EffectivePolicyModifier.createEffectivePolicyModifier();
/* 103 */     modifier.connect(policyMap);
/*     */     try {
/* 105 */       AlternativeSelector.doSelection(modifier);
/* 106 */     } catch (PolicyException e) {
/* 107 */       throw new WebServiceException(e);
/*     */     } 
/* 109 */     return policyMap;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\policy\jaxws\DefaultPolicyResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */