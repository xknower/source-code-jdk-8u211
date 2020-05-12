/*     */ package com.sun.xml.internal.ws.policy;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
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
/*     */ public final class NestedPolicy
/*     */   extends Policy
/*     */ {
/*     */   private static final String NESTED_POLICY_TOSTRING_NAME = "nested policy";
/*     */   
/*     */   private NestedPolicy(AssertionSet set) {
/*  40 */     super("nested policy", Arrays.asList(new AssertionSet[] { set }));
/*     */   }
/*     */   
/*     */   private NestedPolicy(String name, String policyId, AssertionSet set) {
/*  44 */     super("nested policy", name, policyId, Arrays.asList(new AssertionSet[] { set }));
/*     */   }
/*     */   
/*     */   static NestedPolicy createNestedPolicy(AssertionSet set) {
/*  48 */     return new NestedPolicy(set);
/*     */   }
/*     */   
/*     */   static NestedPolicy createNestedPolicy(String name, String policyId, AssertionSet set) {
/*  52 */     return new NestedPolicy(name, policyId, set);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssertionSet getAssertionSet() {
/*  63 */     Iterator<AssertionSet> iterator = iterator();
/*  64 */     if (iterator.hasNext()) {
/*  65 */       return iterator.next();
/*     */     }
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  76 */     if (this == obj) {
/*  77 */       return true;
/*     */     }
/*     */     
/*  80 */     if (!(obj instanceof NestedPolicy)) {
/*  81 */       return false;
/*     */     }
/*     */     
/*  84 */     NestedPolicy that = (NestedPolicy)obj;
/*     */     
/*  86 */     return super.equals(that);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  91 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  99 */     return toString(0, new StringBuffer()).toString();
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
/*     */   StringBuffer toString(int indentLevel, StringBuffer buffer) {
/* 111 */     return super.toString(indentLevel, buffer);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\policy\NestedPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */