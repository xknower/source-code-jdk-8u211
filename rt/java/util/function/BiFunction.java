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
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface BiFunction<T, U, R>
/*    */ {
/*    */   R apply(T paramT, U paramU);
/*    */   
/*    */   default <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> paramFunction) {
/* 69 */     Objects.requireNonNull(paramFunction);
/* 70 */     return (paramObject1, paramObject2) -> paramFunction.apply(apply((T)paramObject1, (U)paramObject2));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\function\BiFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */