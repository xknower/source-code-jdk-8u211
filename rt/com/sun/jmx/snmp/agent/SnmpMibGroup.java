/*     */ package com.sun.jmx.snmp.agent;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpVarBind;
/*     */ import java.io.Serializable;
/*     */ import java.util.Hashtable;
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
/*     */ public abstract class SnmpMibGroup
/*     */   extends SnmpMibOid
/*     */   implements Serializable
/*     */ {
/*  60 */   protected Hashtable<Long, Long> subgroups = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isTable(long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isVariable(long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isReadable(long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract SnmpMibTable getTable(long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateVarId(long paramLong, Object paramObject) throws SnmpStatusException {
/* 111 */     if (!isVariable(paramLong)) {
/* 112 */       throw new SnmpStatusException(225);
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
/*     */ 
/*     */   
/*     */   public boolean isNestedArc(long paramLong) {
/* 138 */     if (this.subgroups == null) return false; 
/* 139 */     Object object = this.subgroups.get(new Long(paramLong));
/*     */ 
/*     */     
/* 142 */     return (object != null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerNestedArc(long paramLong) {
/* 263 */     Long long_ = new Long(paramLong);
/* 264 */     if (this.subgroups == null) this.subgroups = new Hashtable<>();
/*     */     
/* 266 */     this.subgroups.put(long_, long_);
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
/*     */   protected void registerObject(long paramLong) throws IllegalAccessException {
/* 293 */     long[] arrayOfLong = new long[1];
/* 294 */     arrayOfLong[0] = paramLong;
/* 295 */     super.registerNode(arrayOfLong, 0, (SnmpMibNode)null);
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
/*     */   void registerNode(long[] paramArrayOflong, int paramInt, SnmpMibNode paramSnmpMibNode) throws IllegalAccessException {
/* 315 */     super.registerNode(paramArrayOflong, paramInt, paramSnmpMibNode);
/* 316 */     if (paramInt < 0)
/* 317 */       return;  if (paramInt >= paramArrayOflong.length) {
/*     */       return;
/*     */     }
/* 320 */     registerNestedArc(paramArrayOflong[paramInt]);
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
/*     */   void findHandlingNode(SnmpVarBind paramSnmpVarBind, long[] paramArrayOflong, int paramInt, SnmpRequestTree paramSnmpRequestTree) throws SnmpStatusException {
/* 332 */     int i = paramArrayOflong.length;
/*     */     
/* 334 */     if (paramSnmpRequestTree == null) {
/* 335 */       throw new SnmpStatusException(5);
/*     */     }
/* 337 */     Object object = paramSnmpRequestTree.getUserData();
/*     */     
/* 339 */     if (paramInt >= i)
/*     */     {
/* 341 */       throw new SnmpStatusException(6);
/*     */     }
/*     */     
/* 344 */     long l = paramArrayOflong[paramInt];
/*     */     
/* 346 */     if (isNestedArc(l)) {
/*     */ 
/*     */       
/* 349 */       super.findHandlingNode(paramSnmpVarBind, paramArrayOflong, paramInt, paramSnmpRequestTree);
/* 350 */     } else if (isTable(l)) {
/*     */ 
/*     */ 
/*     */       
/* 354 */       SnmpMibTable snmpMibTable = getTable(l);
/*     */ 
/*     */       
/* 357 */       snmpMibTable.findHandlingNode(paramSnmpVarBind, paramArrayOflong, paramInt + 1, paramSnmpRequestTree);
/*     */     }
/*     */     else {
/*     */       
/* 361 */       validateVarId(l, object);
/*     */ 
/*     */       
/* 364 */       if (paramInt + 2 > i) {
/* 365 */         throw new SnmpStatusException(224);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 370 */       if (paramInt + 2 < i) {
/* 371 */         throw new SnmpStatusException(224);
/*     */       }
/*     */ 
/*     */       
/* 375 */       if (paramArrayOflong[paramInt + 1] != 0L) {
/* 376 */         throw new SnmpStatusException(224);
/*     */       }
/*     */ 
/*     */       
/* 380 */       paramSnmpRequestTree.add(this, paramInt, paramSnmpVarBind);
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
/*     */   long[] findNextHandlingNode(SnmpVarBind paramSnmpVarBind, long[] paramArrayOflong, int paramInt1, int paramInt2, SnmpRequestTree paramSnmpRequestTree, AcmChecker paramAcmChecker) throws SnmpStatusException {
/* 393 */     int i = paramArrayOflong.length;
/* 394 */     Object object = null;
/*     */     
/* 396 */     if (paramSnmpRequestTree == null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 401 */       throw new SnmpStatusException(225);
/*     */     }
/*     */     
/* 404 */     Object object1 = paramSnmpRequestTree.getUserData();
/* 405 */     int j = paramSnmpRequestTree.getRequestPduVersion();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 412 */     if (paramInt1 >= i) {
/* 413 */       return super.findNextHandlingNode(paramSnmpVarBind, paramArrayOflong, paramInt1, paramInt2, paramSnmpRequestTree, paramAcmChecker);
/*     */     }
/*     */ 
/*     */     
/* 417 */     long l = paramArrayOflong[paramInt1];
/*     */     
/* 419 */     long[] arrayOfLong = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 424 */       if (isTable(l)) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 429 */         SnmpMibTable snmpMibTable = getTable(l);
/*     */ 
/*     */         
/* 432 */         paramAcmChecker.add(paramInt2, l);
/*     */         try {
/* 434 */           arrayOfLong = snmpMibTable.findNextHandlingNode(paramSnmpVarBind, paramArrayOflong, paramInt1 + 1, paramInt2 + 1, paramSnmpRequestTree, paramAcmChecker);
/*     */         
/*     */         }
/* 437 */         catch (SnmpStatusException snmpStatusException) {
/* 438 */           throw new SnmpStatusException(225);
/*     */         } finally {
/* 440 */           paramAcmChecker.remove(paramInt2);
/*     */         } 
/*     */         
/* 443 */         arrayOfLong[paramInt2] = l;
/* 444 */         return arrayOfLong;
/* 445 */       }  if (isReadable(l)) {
/*     */ 
/*     */         
/* 448 */         if (paramInt1 == i - 1)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 455 */           arrayOfLong = new long[paramInt2 + 2];
/* 456 */           arrayOfLong[paramInt2 + 1] = 0L;
/* 457 */           arrayOfLong[paramInt2] = l;
/*     */           
/* 459 */           paramAcmChecker.add(paramInt2, arrayOfLong, paramInt2, 2);
/*     */           try {
/* 461 */             paramAcmChecker.checkCurrentOid();
/* 462 */           } catch (SnmpStatusException snmpStatusException) {
/* 463 */             throw new SnmpStatusException(225);
/*     */           } finally {
/* 465 */             paramAcmChecker.remove(paramInt2, 2);
/*     */           } 
/*     */ 
/*     */           
/* 469 */           paramSnmpRequestTree.add(this, paramInt2, paramSnmpVarBind);
/* 470 */           return arrayOfLong;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 482 */       else if (isNestedArc(l)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 489 */         SnmpMibNode snmpMibNode = getChild(l);
/*     */         
/* 491 */         if (snmpMibNode != null) {
/* 492 */           paramAcmChecker.add(paramInt2, l);
/*     */           try {
/* 494 */             arrayOfLong = snmpMibNode.findNextHandlingNode(paramSnmpVarBind, paramArrayOflong, paramInt1 + 1, paramInt2 + 1, paramSnmpRequestTree, paramAcmChecker);
/*     */ 
/*     */             
/* 497 */             arrayOfLong[paramInt2] = l;
/* 498 */             return arrayOfLong;
/*     */           } finally {
/* 500 */             paramAcmChecker.remove(paramInt2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 508 */       throw new SnmpStatusException(225);
/*     */     }
/* 510 */     catch (SnmpStatusException snmpStatusException) {
/*     */ 
/*     */ 
/*     */       
/* 514 */       long[] arrayOfLong1 = new long[1];
/* 515 */       arrayOfLong1[0] = getNextVarId(l, object1, j);
/* 516 */       return findNextHandlingNode(paramSnmpVarBind, arrayOfLong1, 0, paramInt2, paramSnmpRequestTree, paramAcmChecker);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpMibGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */