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
/*     */ public class SnmpStringFixed
/*     */   extends SnmpString
/*     */ {
/*     */   private static final long serialVersionUID = -9120939046874646063L;
/*     */   
/*     */   public SnmpStringFixed(byte[] paramArrayOfbyte) {
/*  54 */     super(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpStringFixed(Byte[] paramArrayOfByte) {
/*  62 */     super(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpStringFixed(String paramString) {
/*  70 */     super(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpStringFixed(int paramInt, byte[] paramArrayOfbyte) throws IllegalArgumentException {
/*  81 */     if (paramInt <= 0 || paramArrayOfbyte == null) {
/*  82 */       throw new IllegalArgumentException();
/*     */     }
/*  84 */     int i = Math.min(paramInt, paramArrayOfbyte.length);
/*  85 */     this.value = new byte[paramInt]; int j;
/*  86 */     for (j = 0; j < i; j++) {
/*  87 */       this.value[j] = paramArrayOfbyte[j];
/*     */     }
/*  89 */     for (j = i; j < paramInt; j++) {
/*  90 */       this.value[j] = 0;
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
/*     */   public SnmpStringFixed(int paramInt, Byte[] paramArrayOfByte) throws IllegalArgumentException {
/* 102 */     if (paramInt <= 0 || paramArrayOfByte == null) {
/* 103 */       throw new IllegalArgumentException();
/*     */     }
/* 105 */     int i = Math.min(paramInt, paramArrayOfByte.length);
/* 106 */     this.value = new byte[paramInt]; int j;
/* 107 */     for (j = 0; j < i; j++) {
/* 108 */       this.value[j] = paramArrayOfByte[j].byteValue();
/*     */     }
/* 110 */     for (j = i; j < paramInt; j++) {
/* 111 */       this.value[j] = 0;
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
/*     */   public SnmpStringFixed(int paramInt, String paramString) throws IllegalArgumentException {
/* 123 */     if (paramInt <= 0 || paramString == null) {
/* 124 */       throw new IllegalArgumentException();
/*     */     }
/* 126 */     byte[] arrayOfByte = paramString.getBytes();
/* 127 */     int i = Math.min(paramInt, arrayOfByte.length);
/* 128 */     this.value = new byte[paramInt]; int j;
/* 129 */     for (j = 0; j < i; j++) {
/* 130 */       this.value[j] = arrayOfByte[j];
/*     */     }
/* 132 */     for (j = i; j < paramInt; j++) {
/* 133 */       this.value[j] = 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SnmpOid toOid(int paramInt1, long[] paramArrayOflong, int paramInt2) throws SnmpStatusException {
/*     */     try {
/* 153 */       long[] arrayOfLong = new long[paramInt1];
/* 154 */       for (byte b = 0; b < paramInt1; b++) {
/* 155 */         arrayOfLong[b] = paramArrayOflong[paramInt2 + b];
/*     */       }
/* 157 */       return new SnmpOid(arrayOfLong);
/*     */     }
/* 159 */     catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 160 */       throw new SnmpStatusException(2);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static int nextOid(int paramInt1, long[] paramArrayOflong, int paramInt2) throws SnmpStatusException {
/* 177 */     int i = paramInt2 + paramInt1;
/* 178 */     if (i > paramArrayOflong.length) {
/* 179 */       throw new SnmpStatusException(2);
/*     */     }
/* 181 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void appendToOid(int paramInt, SnmpOid paramSnmpOid1, SnmpOid paramSnmpOid2) {
/* 191 */     paramSnmpOid2.append(paramSnmpOid1);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpStringFixed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */