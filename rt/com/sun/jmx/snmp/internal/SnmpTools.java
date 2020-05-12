/*     */ package com.sun.jmx.snmp.internal;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpDefinitions;
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
/*     */ public class SnmpTools
/*     */   implements SnmpDefinitions
/*     */ {
/*     */   public static String binary2ascii(byte[] paramArrayOfbyte, int paramInt) {
/*  43 */     if (paramArrayOfbyte == null) return null; 
/*  44 */     int i = paramInt * 2 + 2;
/*  45 */     byte[] arrayOfByte = new byte[i];
/*  46 */     arrayOfByte[0] = 48;
/*  47 */     arrayOfByte[1] = 120;
/*  48 */     for (byte b = 0; b < paramInt; b++) {
/*  49 */       int j = b * 2;
/*  50 */       int k = paramArrayOfbyte[b] & 0xF0;
/*  51 */       k >>= 4;
/*  52 */       if (k < 10) {
/*  53 */         arrayOfByte[j + 2] = (byte)(48 + k);
/*     */       } else {
/*  55 */         arrayOfByte[j + 2] = (byte)(65 + k - 10);
/*  56 */       }  k = paramArrayOfbyte[b] & 0xF;
/*  57 */       if (k < 10) {
/*  58 */         arrayOfByte[j + 1 + 2] = (byte)(48 + k);
/*     */       } else {
/*  60 */         arrayOfByte[j + 1 + 2] = (byte)(65 + k - 10);
/*     */       } 
/*  62 */     }  return new String(arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String binary2ascii(byte[] paramArrayOfbyte) {
/*  72 */     return binary2ascii(paramArrayOfbyte, paramArrayOfbyte.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] ascii2binary(String paramString) {
/*  80 */     if (paramString == null) return null; 
/*  81 */     String str = paramString.substring(2);
/*     */     
/*  83 */     int i = str.length();
/*  84 */     byte[] arrayOfByte1 = new byte[i / 2];
/*  85 */     byte[] arrayOfByte2 = str.getBytes();
/*     */     
/*  87 */     for (byte b = 0; b < i / 2; b++) {
/*     */       
/*  89 */       int j = b * 2;
/*  90 */       byte b1 = 0;
/*  91 */       if (arrayOfByte2[j] >= 48 && arrayOfByte2[j] <= 57) {
/*  92 */         b1 = (byte)(arrayOfByte2[j] - 48 << 4);
/*     */       }
/*  94 */       else if (arrayOfByte2[j] >= 97 && arrayOfByte2[j] <= 102) {
/*  95 */         b1 = (byte)(arrayOfByte2[j] - 97 + 10 << 4);
/*     */       }
/*  97 */       else if (arrayOfByte2[j] >= 65 && arrayOfByte2[j] <= 70) {
/*  98 */         b1 = (byte)(arrayOfByte2[j] - 65 + 10 << 4);
/*     */       } else {
/*     */         
/* 101 */         throw new Error("BAD format :" + paramString);
/*     */       } 
/* 103 */       if (arrayOfByte2[j + 1] >= 48 && arrayOfByte2[j + 1] <= 57) {
/*     */         
/* 105 */         b1 = (byte)(b1 + arrayOfByte2[j + 1] - 48);
/*     */       
/*     */       }
/* 108 */       else if (arrayOfByte2[j + 1] >= 97 && arrayOfByte2[j + 1] <= 102) {
/*     */         
/* 110 */         b1 = (byte)(b1 + arrayOfByte2[j + 1] - 97 + 10);
/*     */       
/*     */       }
/* 113 */       else if (arrayOfByte2[j + 1] >= 65 && arrayOfByte2[j + 1] <= 70) {
/*     */         
/* 115 */         b1 = (byte)(b1 + arrayOfByte2[j + 1] - 65 + 10);
/*     */       }
/*     */       else {
/*     */         
/* 119 */         throw new Error("BAD format :" + paramString);
/*     */       } 
/* 121 */       arrayOfByte1[b] = b1;
/*     */     } 
/* 123 */     return arrayOfByte1;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\internal\SnmpTools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */