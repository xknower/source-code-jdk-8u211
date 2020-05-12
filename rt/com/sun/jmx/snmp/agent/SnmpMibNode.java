/*     */ package com.sun.jmx.snmp.agent;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpVarBind;
/*     */ import java.io.Serializable;
/*     */ import java.util.Vector;
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
/*     */ public abstract class SnmpMibNode
/*     */   implements Serializable
/*     */ {
/*     */   protected int[] varList;
/*     */   
/*     */   public long getNextVarId(long paramLong, Object paramObject) throws SnmpStatusException {
/*  79 */     return getNextIdentifier(this.varList, paramLong);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getNextVarId(long paramLong, Object paramObject, int paramInt) throws SnmpStatusException {
/* 104 */     long l = paramLong;
/*     */     do {
/* 106 */       l = getNextVarId(l, paramObject);
/* 107 */     } while (skipVariable(l, paramObject, paramInt));
/*     */     
/* 109 */     return l;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean skipVariable(long paramLong, Object paramObject, int paramInt) {
/* 133 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void findHandlingNode(SnmpVarBind paramSnmpVarBind, long[] paramArrayOflong, int paramInt, SnmpRequestTree paramSnmpRequestTree) throws SnmpStatusException {
/* 158 */     throw new SnmpStatusException(225);
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
/*     */   long[] findNextHandlingNode(SnmpVarBind paramSnmpVarBind, long[] paramArrayOflong, int paramInt1, int paramInt2, SnmpRequestTree paramSnmpRequestTree, AcmChecker paramAcmChecker) throws SnmpStatusException {
/* 186 */     throw new SnmpStatusException(225);
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
/*     */   public abstract void get(SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException;
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
/*     */   public abstract void set(SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException;
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
/*     */   public abstract void check(SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException;
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
/*     */   public static void sort(int[] paramArrayOfint) {
/* 248 */     QuickSort(paramArrayOfint, 0, paramArrayOfint.length - 1);
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
/*     */   public void getRootOid(Vector<Integer> paramVector) {}
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
/*     */   static void QuickSort(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 278 */     int i = paramInt1;
/* 279 */     int j = paramInt2;
/*     */ 
/*     */     
/* 282 */     if (paramInt2 > paramInt1) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 287 */       int k = paramArrayOfint[(paramInt1 + paramInt2) / 2];
/*     */ 
/*     */       
/* 290 */       while (i <= j) {
/*     */ 
/*     */ 
/*     */         
/* 294 */         while (i < paramInt2 && paramArrayOfint[i] < k) {
/* 295 */           i++;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 300 */         while (j > paramInt1 && paramArrayOfint[j] > k) {
/* 301 */           j--;
/*     */         }
/*     */         
/* 304 */         if (i <= j) {
/* 305 */           swap(paramArrayOfint, i, j);
/* 306 */           i++;
/* 307 */           j--;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 314 */       if (paramInt1 < j) {
/* 315 */         QuickSort(paramArrayOfint, paramInt1, j);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 320 */       if (i < paramInt2) {
/* 321 */         QuickSort(paramArrayOfint, i, paramInt2);
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int getNextIdentifier(int[] paramArrayOfint, long paramLong) throws SnmpStatusException {
/* 346 */     int[] arrayOfInt = paramArrayOfint;
/* 347 */     int i = (int)paramLong;
/*     */     
/* 349 */     if (arrayOfInt == null) {
/* 350 */       throw new SnmpStatusException(225);
/*     */     }
/*     */     
/* 353 */     int j = 0;
/* 354 */     int k = arrayOfInt.length;
/* 355 */     int m = j + (k - j) / 2;
/* 356 */     int n = 0;
/*     */ 
/*     */ 
/*     */     
/* 360 */     if (k < 1) {
/* 361 */       throw new SnmpStatusException(225);
/*     */     }
/*     */     
/* 364 */     if (arrayOfInt[k - 1] <= i) {
/* 365 */       throw new SnmpStatusException(225);
/*     */     }
/*     */     
/* 368 */     while (j <= k) {
/* 369 */       n = arrayOfInt[m];
/* 370 */       if (i == n) {
/*     */ 
/*     */         
/* 373 */         m++;
/* 374 */         return arrayOfInt[m];
/*     */       } 
/* 376 */       if (n < i) {
/* 377 */         j = m + 1;
/*     */       } else {
/* 379 */         k = m - 1;
/*     */       } 
/* 381 */       m = j + (k - j) / 2;
/*     */     } 
/* 383 */     return arrayOfInt[m];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final void swap(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 393 */     int i = paramArrayOfint[paramInt1];
/* 394 */     paramArrayOfint[paramInt1] = paramArrayOfint[paramInt2];
/* 395 */     paramArrayOfint[paramInt2] = i;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpMibNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */