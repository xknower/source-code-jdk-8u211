/*     */ package com.sun.jmx.snmp;
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
/*     */ public abstract class SnmpUnsignedInt
/*     */   extends SnmpInt
/*     */ {
/*     */   public static final long MAX_VALUE = 4294967295L;
/*     */   static final String name = "Unsigned32";
/*     */   
/*     */   public SnmpUnsignedInt(int paramInt) throws IllegalArgumentException {
/*  54 */     super(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpUnsignedInt(Integer paramInteger) throws IllegalArgumentException {
/*  64 */     super(paramInteger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpUnsignedInt(long paramLong) throws IllegalArgumentException {
/*  74 */     super(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpUnsignedInt(Long paramLong) throws IllegalArgumentException {
/*  84 */     super(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTypeName() {
/*  94 */     return "Unsigned32";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isInitValueValid(int paramInt) {
/* 102 */     if (paramInt < 0 || paramInt > 4294967295L) {
/* 103 */       return false;
/*     */     }
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isInitValueValid(long paramLong) {
/* 113 */     if (paramLong < 0L || paramLong > 4294967295L) {
/* 114 */       return false;
/*     */     }
/* 116 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpUnsignedInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */