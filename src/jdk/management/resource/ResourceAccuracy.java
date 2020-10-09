/*    */ package jdk.management.resource;
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
/*    */ public enum ResourceAccuracy
/*    */ {
/* 20 */   LOW,
/*    */ 
/*    */ 
/*    */   
/* 24 */   MEDIUM,
/*    */ 
/*    */ 
/*    */   
/* 28 */   HIGH,
/*    */ 
/*    */ 
/*    */   
/* 32 */   HIGHEST;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceAccuracy improve() {
/* 41 */     if (equals(LOW))
/* 42 */       return MEDIUM; 
/* 43 */     if (equals(MEDIUM)) {
/* 44 */       return HIGH;
/*    */     }
/* 46 */     return HIGHEST;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\ResourceAccuracy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */