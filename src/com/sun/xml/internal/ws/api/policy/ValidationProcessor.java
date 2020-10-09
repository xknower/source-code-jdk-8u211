/*    */ package com.sun.xml.internal.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.addressing.policy.AddressingPolicyValidator;
/*    */ import com.sun.xml.internal.ws.config.management.policy.ManagementPolicyValidator;
/*    */ import com.sun.xml.internal.ws.encoding.policy.EncodingPolicyValidator;
/*    */ import com.sun.xml.internal.ws.policy.AssertionValidationProcessor;
/*    */ import com.sun.xml.internal.ws.policy.PolicyException;
/*    */ import com.sun.xml.internal.ws.policy.spi.PolicyAssertionValidator;
/*    */ import java.util.Arrays;
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
/*    */ public class ValidationProcessor
/*    */   extends AssertionValidationProcessor
/*    */ {
/* 44 */   private static final PolicyAssertionValidator[] JAXWS_ASSERTION_VALIDATORS = new PolicyAssertionValidator[] { new AddressingPolicyValidator(), new EncodingPolicyValidator(), new ManagementPolicyValidator() };
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
/*    */   private ValidationProcessor() throws PolicyException {
/* 58 */     super(Arrays.asList(JAXWS_ASSERTION_VALIDATORS));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ValidationProcessor getInstance() throws PolicyException {
/* 68 */     return new ValidationProcessor();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\policy\ValidationProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */