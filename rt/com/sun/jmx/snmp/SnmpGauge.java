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
/*    */ public class SnmpGauge
/*    */   extends SnmpUnsignedInt
/*    */ {
/*    */   private static final long serialVersionUID = -8366622742122792945L;
/*    */   static final String name = "Gauge32";
/*    */   
/*    */   public SnmpGauge(int paramInt) throws IllegalArgumentException {
/* 50 */     super(paramInt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SnmpGauge(Integer paramInteger) throws IllegalArgumentException {
/* 60 */     super(paramInteger);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SnmpGauge(long paramLong) throws IllegalArgumentException {
/* 70 */     super(paramLong);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SnmpGauge(Long paramLong) throws IllegalArgumentException {
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
/* 90 */     return "Gauge32";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpGauge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */