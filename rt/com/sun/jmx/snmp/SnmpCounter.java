/*    */ package com.sun.jmx.snmp;
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
/*    */ public class SnmpCounter
/*    */   extends SnmpUnsignedInt
/*    */ {
/*    */   private static final long serialVersionUID = 4655264728839396879L;
/*    */   static final String name = "Counter32";
/*    */   
/*    */   public SnmpCounter(int paramInt) throws IllegalArgumentException {
/* 50 */     super(paramInt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SnmpCounter(Integer paramInteger) throws IllegalArgumentException {
/* 60 */     super(paramInteger);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SnmpCounter(long paramLong) throws IllegalArgumentException {
/* 70 */     super(paramLong);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SnmpCounter(Long paramLong) throws IllegalArgumentException {
/* 80 */     super(paramLong);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final String getTypeName() {
/* 90 */     return "Counter32";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */