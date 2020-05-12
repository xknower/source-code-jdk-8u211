/*     */ package java.util.function;
/*     */ 
/*     */ import java.util.Objects;
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
/*     */ @FunctionalInterface
/*     */ public interface DoublePredicate
/*     */ {
/*     */   default DoublePredicate and(DoublePredicate paramDoublePredicate) {
/*  69 */     Objects.requireNonNull(paramDoublePredicate);
/*  70 */     return paramDouble -> (test(paramDouble) && paramDoublePredicate.test(paramDouble));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default DoublePredicate negate() {
/*  81 */     return paramDouble -> !test(paramDouble);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default DoublePredicate or(DoublePredicate paramDoublePredicate) {
/* 101 */     Objects.requireNonNull(paramDoublePredicate);
/* 102 */     return paramDouble -> (test(paramDouble) || paramDoublePredicate.test(paramDouble));
/*     */   }
/*     */   
/*     */   boolean test(double paramDouble);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\function\DoublePredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */