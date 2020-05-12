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
/*     */ 
/*     */ 
/*     */ public class SnmpCounter64
/*     */   extends SnmpValue
/*     */ {
/*     */   private static final long serialVersionUID = 8784850650494679937L;
/*     */   static final String name = "Counter64";
/*     */   
/*     */   public SnmpCounter64(long paramLong) throws IllegalArgumentException {
/*  56 */     if (paramLong < 0L || paramLong > Long.MAX_VALUE) {
/*  57 */       throw new IllegalArgumentException();
/*     */     }
/*  59 */     this.value = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpCounter64(Long paramLong) throws IllegalArgumentException {
/*  69 */     this(paramLong.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/*  79 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long toLong() {
/*  87 */     return new Long(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/*  95 */     return (int)this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer toInteger() {
/* 103 */     return new Integer((int)this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 111 */     return String.valueOf(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpOid toOid() {
/* 119 */     return new SnmpOid(this.value);
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
/* 133 */       return new SnmpOid(paramArrayOflong[paramInt]);
/*     */     }
/* 135 */     catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 136 */       throw new SnmpStatusException(2);
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
/* 150 */     if (paramInt >= paramArrayOflong.length) {
/* 151 */       throw new SnmpStatusException(2);
/*     */     }
/*     */     
/* 154 */     return paramInt + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void appendToOid(SnmpOid paramSnmpOid1, SnmpOid paramSnmpOid2) {
/* 164 */     if (paramSnmpOid1.getLength() != 1) {
/* 165 */       throw new IllegalArgumentException();
/*     */     }
/* 167 */     paramSnmpOid2.append(paramSnmpOid1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized SnmpValue duplicate() {
/* 176 */     return (SnmpValue)clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized Object clone() {
/* 184 */     SnmpCounter64 snmpCounter64 = null;
/*     */     try {
/* 186 */       snmpCounter64 = (SnmpCounter64)super.clone();
/* 187 */       snmpCounter64.value = this.value;
/* 188 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 189 */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/* 191 */     return snmpCounter64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getTypeName() {
/* 199 */     return "Counter64";
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
/* 213 */   private long value = 0L;
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpCounter64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */