/*    */ package com.sun.corba.se.spi.orbutil.closure;
/*    */ 
/*    */ import com.sun.corba.se.impl.orbutil.closure.Constant;
/*    */ import com.sun.corba.se.impl.orbutil.closure.Future;
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
/*    */ public abstract class ClosureFactory
/*    */ {
/*    */   public static Closure makeConstant(Object paramObject) {
/* 36 */     return new Constant(paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Closure makeFuture(Closure paramClosure) {
/* 41 */     return new Future(paramClosure);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\orbutil\closure\ClosureFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */