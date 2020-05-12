/*    */ package com.sun.xml.internal.ws.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.AssertionData;
/*    */ import java.util.Collection;
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
/*    */ public abstract class ComplexAssertion
/*    */   extends PolicyAssertion
/*    */ {
/*    */   private final NestedPolicy nestedPolicy;
/*    */   
/*    */   protected ComplexAssertion() {
/* 44 */     this.nestedPolicy = NestedPolicy.createNestedPolicy(AssertionSet.emptyAssertionSet());
/*    */   }
/*    */   
/*    */   protected ComplexAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/* 48 */     super(data, assertionParameters);
/*    */     
/* 50 */     AssertionSet nestedSet = (nestedAlternative != null) ? nestedAlternative : AssertionSet.emptyAssertionSet();
/* 51 */     this.nestedPolicy = NestedPolicy.createNestedPolicy(nestedSet);
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean hasNestedPolicy() {
/* 56 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public final NestedPolicy getNestedPolicy() {
/* 61 */     return this.nestedPolicy;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\policy\ComplexAssertion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */