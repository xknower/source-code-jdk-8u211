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
/*     */ public class SnmpInt
/*     */   extends SnmpValue
/*     */ {
/*     */   private static final long serialVersionUID = -7163624758070343373L;
/*     */   static final String name = "Integer32";
/*     */   
/*     */   public SnmpInt(int paramInt) throws IllegalArgumentException {
/*  52 */     if (!isInitValueValid(paramInt)) {
/*  53 */       throw new IllegalArgumentException();
/*     */     }
/*  55 */     this.value = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpInt(Integer paramInteger) throws IllegalArgumentException {
/*  65 */     this(paramInteger.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpInt(long paramLong) throws IllegalArgumentException {
/*  75 */     if (!isInitValueValid(paramLong)) {
/*  76 */       throw new IllegalArgumentException();
/*     */     }
/*  78 */     this.value = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpInt(Long paramLong) throws IllegalArgumentException {
/*  88 */     this(paramLong.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpInt(Enumerated paramEnumerated) throws IllegalArgumentException {
/*  99 */     this(paramEnumerated.intValue());
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
/*     */   public SnmpInt(boolean paramBoolean) {
/* 115 */     this.value = paramBoolean ? 1L : 2L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/* 125 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long toLong() {
/* 133 */     return new Long(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/* 141 */     return (int)this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer toInteger() {
/* 149 */     return new Integer((int)this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 157 */     return String.valueOf(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpOid toOid() {
/* 165 */     return new SnmpOid(this.value);
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
/*     */   public static SnmpOid toOid(long[] paramArrayOflong, int paramInt) throws SnmpStatusException {
/*     */     try {
/* 179 */       return new SnmpOid(paramArrayOflong[paramInt]);
/*     */     }
/* 181 */     catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 182 */       throw new SnmpStatusException(2);
/*     */     } 
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
/*     */   public static int nextOid(long[] paramArrayOflong, int paramInt) throws SnmpStatusException {
/* 196 */     if (paramInt >= paramArrayOflong.length) {
/* 197 */       throw new SnmpStatusException(2);
/*     */     }
/*     */     
/* 200 */     return paramInt + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void appendToOid(SnmpOid paramSnmpOid1, SnmpOid paramSnmpOid2) {
/* 210 */     if (paramSnmpOid1.getLength() != 1) {
/* 211 */       throw new IllegalArgumentException();
/*     */     }
/* 213 */     paramSnmpOid2.append(paramSnmpOid1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized SnmpValue duplicate() {
/* 222 */     return (SnmpValue)clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized Object clone() {
/* 230 */     SnmpInt snmpInt = null;
/*     */     try {
/* 232 */       snmpInt = (SnmpInt)super.clone();
/* 233 */       snmpInt.value = this.value;
/* 234 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 235 */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/* 237 */     return snmpInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTypeName() {
/* 245 */     return "Integer32";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isInitValueValid(int paramInt) {
/* 253 */     if (paramInt < Integer.MIN_VALUE || paramInt > Integer.MAX_VALUE) {
/* 254 */       return false;
/*     */     }
/* 256 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isInitValueValid(long paramLong) {
/* 264 */     if (paramLong < -2147483648L || paramLong > 2147483647L) {
/* 265 */       return false;
/*     */     }
/* 267 */     return true;
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
/* 281 */   protected long value = 0L;
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */