/*    */ package java.util.function;
/*    */ 
/*    */ import java.util.Objects;
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
/*    */ @FunctionalInterface
/*    */ public interface DoubleUnaryOperator
/*    */ {
/*    */   default DoubleUnaryOperator compose(DoubleUnaryOperator paramDoubleUnaryOperator) {
/* 65 */     Objects.requireNonNull(paramDoubleUnaryOperator);
/* 66 */     return paramDouble -> applyAsDouble(paramDoubleUnaryOperator.applyAsDouble(paramDouble));
/*    */   }
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
/*    */   default DoubleUnaryOperator andThen(DoubleUnaryOperator paramDoubleUnaryOperator) {
/* 83 */     Objects.requireNonNull(paramDoubleUnaryOperator);
/* 84 */     return paramDouble -> paramDoubleUnaryOperator.applyAsDouble(applyAsDouble(paramDouble));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static DoubleUnaryOperator identity() {
/* 93 */     return paramDouble -> paramDouble;
/*    */   }
/*    */   
/*    */   double applyAsDouble(double paramDouble);
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\function\DoubleUnaryOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */