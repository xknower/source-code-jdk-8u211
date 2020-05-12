/*     */ package com.sun.security.sasl.util;
/*     */ 
/*     */ import java.util.Map;
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
/*     */ public final class PolicyUtils
/*     */ {
/*     */   public static final int NOPLAINTEXT = 1;
/*     */   public static final int NOACTIVE = 2;
/*     */   public static final int NODICTIONARY = 4;
/*     */   public static final int FORWARD_SECRECY = 8;
/*     */   public static final int NOANONYMOUS = 16;
/*     */   public static final int PASS_CREDENTIALS = 512;
/*     */   
/*     */   public static boolean checkPolicy(int paramInt, Map<String, ?> paramMap) {
/*  57 */     if (paramMap == null) {
/*  58 */       return true;
/*     */     }
/*     */     
/*  61 */     if ("true".equalsIgnoreCase((String)paramMap.get("javax.security.sasl.policy.noplaintext")) && (paramInt & 0x1) == 0)
/*     */     {
/*  63 */       return false;
/*     */     }
/*  65 */     if ("true".equalsIgnoreCase((String)paramMap.get("javax.security.sasl.policy.noactive")) && (paramInt & 0x2) == 0)
/*     */     {
/*  67 */       return false;
/*     */     }
/*  69 */     if ("true".equalsIgnoreCase((String)paramMap.get("javax.security.sasl.policy.nodictionary")) && (paramInt & 0x4) == 0)
/*     */     {
/*  71 */       return false;
/*     */     }
/*  73 */     if ("true".equalsIgnoreCase((String)paramMap.get("javax.security.sasl.policy.noanonymous")) && (paramInt & 0x10) == 0)
/*     */     {
/*  75 */       return false;
/*     */     }
/*  77 */     if ("true".equalsIgnoreCase((String)paramMap.get("javax.security.sasl.policy.forward")) && (paramInt & 0x8) == 0)
/*     */     {
/*  79 */       return false;
/*     */     }
/*  81 */     if ("true".equalsIgnoreCase((String)paramMap.get("javax.security.sasl.policy.credentials")) && (paramInt & 0x200) == 0)
/*     */     {
/*  83 */       return false;
/*     */     }
/*     */     
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] filterMechs(String[] paramArrayOfString, int[] paramArrayOfint, Map<String, ?> paramMap) {
/*  97 */     if (paramMap == null) {
/*  98 */       return (String[])paramArrayOfString.clone();
/*     */     }
/*     */     
/* 101 */     boolean[] arrayOfBoolean = new boolean[paramArrayOfString.length];
/* 102 */     byte b1 = 0;
/* 103 */     for (byte b2 = 0; b2 < paramArrayOfString.length; b2++) {
/* 104 */       arrayOfBoolean[b2] = checkPolicy(paramArrayOfint[b2], paramMap); if (checkPolicy(paramArrayOfint[b2], paramMap)) {
/* 105 */         b1++;
/*     */       }
/*     */     } 
/* 108 */     String[] arrayOfString = new String[b1];
/* 109 */     for (byte b3 = 0, b4 = 0; b3 < paramArrayOfString.length; b3++) {
/* 110 */       if (arrayOfBoolean[b3]) {
/* 111 */         arrayOfString[b4++] = paramArrayOfString[b3];
/*     */       }
/*     */     } 
/*     */     
/* 115 */     return arrayOfString;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\security\sas\\util\PolicyUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */