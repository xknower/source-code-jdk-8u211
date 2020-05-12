/*     */ package com.sun.xml.internal.ws.developer;
/*     */ 
/*     */ import com.sun.org.glassfish.gmbal.ManagedAttribute;
/*     */ import com.sun.org.glassfish.gmbal.ManagedData;
/*     */ import com.sun.xml.internal.ws.api.FeatureConstructor;
/*     */ import javax.xml.ws.WebServiceFeature;
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
/*     */ @ManagedData
/*     */ public class MemberSubmissionAddressingFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "http://java.sun.com/xml/ns/jaxws/2004/08/addressing";
/*     */   public static final String IS_REQUIRED = "ADDRESSING_IS_REQUIRED";
/*     */   private boolean required;
/*     */   
/*     */   public MemberSubmissionAddressingFeature() {
/*  61 */     this.enabled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemberSubmissionAddressingFeature(boolean enabled) {
/*  71 */     this.enabled = enabled;
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
/*     */   public MemberSubmissionAddressingFeature(boolean enabled, boolean required) {
/*  84 */     this.enabled = enabled;
/*  85 */     this.required = required;
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
/*     */   
/*     */   @FeatureConstructor({"enabled", "required", "validation"})
/*     */   public MemberSubmissionAddressingFeature(boolean enabled, boolean required, MemberSubmissionAddressing.Validation validation) {
/* 101 */     this.enabled = enabled;
/* 102 */     this.required = required;
/* 103 */     this.validation = validation;
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/* 109 */     return "http://java.sun.com/xml/ns/jaxws/2004/08/addressing";
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public boolean isRequired() {
/* 114 */     return this.required;
/*     */   }
/*     */   
/*     */   public void setRequired(boolean required) {
/* 118 */     this.required = required;
/*     */   }
/*     */   
/* 121 */   private MemberSubmissionAddressing.Validation validation = MemberSubmissionAddressing.Validation.LAX;
/*     */   public void setValidation(MemberSubmissionAddressing.Validation validation) {
/* 123 */     this.validation = validation;
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public MemberSubmissionAddressing.Validation getValidation() {
/* 129 */     return this.validation;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\developer\MemberSubmissionAddressingFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */