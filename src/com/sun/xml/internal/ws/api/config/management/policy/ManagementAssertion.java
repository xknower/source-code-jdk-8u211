/*     */ package com.sun.xml.internal.ws.api.config.management.policy;
/*     */ 
/*     */ import com.sun.istack.internal.logging.Logger;
/*     */ import com.sun.xml.internal.ws.policy.AssertionSet;
/*     */ import com.sun.xml.internal.ws.policy.Policy;
/*     */ import com.sun.xml.internal.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.internal.ws.policy.PolicyException;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.internal.ws.policy.SimpleAssertion;
/*     */ import com.sun.xml.internal.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.internal.ws.policy.spi.AssertionCreationException;
/*     */ import com.sun.xml.internal.ws.resources.ManagementMessages;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public abstract class ManagementAssertion
/*     */   extends SimpleAssertion
/*     */ {
/*     */   public enum Setting
/*     */   {
/*  56 */     NOT_SET, OFF, ON;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  61 */   protected static final QName MANAGEMENT_ATTRIBUTE_QNAME = new QName("management");
/*     */ 
/*     */ 
/*     */   
/*  65 */   protected static final QName MONITORING_ATTRIBUTE_QNAME = new QName("monitoring");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final QName ID_ATTRIBUTE_QNAME = new QName("id");
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final QName START_ATTRIBUTE_QNAME = new QName("start");
/*     */   
/*  76 */   private static final Logger LOGGER = Logger.getLogger(ManagementAssertion.class);
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
/*     */   protected static <T extends ManagementAssertion> T getAssertion(QName name, PolicyMap policyMap, QName serviceName, QName portName, Class<T> type) throws WebServiceException {
/*     */     try {
/*  95 */       PolicyAssertion assertion = null;
/*  96 */       if (policyMap != null) {
/*  97 */         PolicyMapKey key = PolicyMap.createWsdlEndpointScopeKey(serviceName, portName);
/*  98 */         Policy policy = policyMap.getEndpointEffectivePolicy(key);
/*  99 */         if (policy != null) {
/* 100 */           Iterator<AssertionSet> assertionSets = policy.iterator();
/* 101 */           if (assertionSets.hasNext()) {
/* 102 */             AssertionSet assertionSet = assertionSets.next();
/* 103 */             Iterator<PolicyAssertion> assertions = assertionSet.get(name).iterator();
/* 104 */             if (assertions.hasNext()) {
/* 105 */               assertion = assertions.next();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 110 */       return (assertion == null) ? null : (T)assertion.<ManagementAssertion>getImplementation(type);
/* 111 */     } catch (PolicyException ex) {
/* 112 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(
/* 113 */             ManagementMessages.WSM_1001_FAILED_ASSERTION(name), ex));
/*     */     } 
/*     */   }
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
/*     */   protected ManagementAssertion(QName name, AssertionData data, Collection<PolicyAssertion> assertionParameters) throws AssertionCreationException {
/* 128 */     super(data, assertionParameters);
/* 129 */     if (!name.equals(data.getName())) {
/* 130 */       throw (AssertionCreationException)LOGGER.logSevereException(new AssertionCreationException(data, 
/* 131 */             ManagementMessages.WSM_1002_EXPECTED_MANAGEMENT_ASSERTION(name)));
/*     */     }
/* 133 */     if (isManagementEnabled() && !data.containsAttribute(ID_ATTRIBUTE_QNAME)) {
/* 134 */       throw (AssertionCreationException)LOGGER.logSevereException(new AssertionCreationException(data, 
/* 135 */             ManagementMessages.WSM_1003_MANAGEMENT_ASSERTION_MISSING_ID(name)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 145 */     return getAttributeValue(ID_ATTRIBUTE_QNAME);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStart() {
/* 154 */     return getAttributeValue(START_ATTRIBUTE_QNAME);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isManagementEnabled();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Setting monitoringAttribute() {
/* 171 */     String monitoring = getAttributeValue(MONITORING_ATTRIBUTE_QNAME);
/* 172 */     Setting result = Setting.NOT_SET;
/* 173 */     if (monitoring != null) {
/* 174 */       if (monitoring.trim().toLowerCase().equals("on") || 
/* 175 */         Boolean.parseBoolean(monitoring)) {
/* 176 */         result = Setting.ON;
/*     */       } else {
/*     */         
/* 179 */         result = Setting.OFF;
/*     */       } 
/*     */     }
/* 182 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\config\management\policy\ManagementAssertion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */