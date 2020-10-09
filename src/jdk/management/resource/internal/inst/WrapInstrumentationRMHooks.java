/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import jdk.internal.instrumentation.InstrumentationMethod;
/*    */ import jdk.internal.instrumentation.InstrumentationTarget;
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
/*    */ @InstrumentationTarget("jdk.management.resource.internal.WrapInstrumentation")
/*    */ public class WrapInstrumentationRMHooks
/*    */ {
/*    */   @InstrumentationMethod
/*    */   public boolean wrapComplete() {
/* 24 */     wrapComplete();
/* 25 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\WrapInstrumentationRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */