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
/*    */ public interface IntUnaryOperator
/*    */ {
/*    */   default IntUnaryOperator compose(IntUnaryOperator paramIntUnaryOperator) {
/* 65 */     Objects.requireNonNull(paramIntUnaryOperator);
/* 66 */     return paramInt -> applyAsInt(paramIntUnaryOperator.applyAsInt(paramInt));
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
/*    */   default IntUnaryOperator andThen(IntUnaryOperator paramIntUnaryOperator) {
/* 83 */     Objects.requireNonNull(paramIntUnaryOperator);
/* 84 */     return paramInt -> paramIntUnaryOperator.applyAsInt(applyAsInt(paramInt));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static IntUnaryOperator identity() {
/* 93 */     return paramInt -> paramInt;
/*    */   }
/*    */   
/*    */   int applyAsInt(int paramInt);
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\function\IntUnaryOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */