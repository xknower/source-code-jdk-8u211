/*     */ package com.sun.jmx.snmp.agent;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpVarBind;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
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
/*     */ 
/*     */ 
/*     */ public class SnmpMibOid
/*     */   extends SnmpMibNode
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5012254771107446812L;
/*     */   
/*     */   public void get(SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException {
/*  84 */     for (Enumeration<SnmpVarBind> enumeration = paramSnmpMibSubRequest.getElements(); enumeration.hasMoreElements(); ) {
/*  85 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/*  86 */       SnmpStatusException snmpStatusException = new SnmpStatusException(225);
/*     */       
/*  88 */       paramSnmpMibSubRequest.registerGetException(snmpVarBind, snmpStatusException);
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
/*     */   public void set(SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException {
/* 108 */     for (Enumeration<SnmpVarBind> enumeration = paramSnmpMibSubRequest.getElements(); enumeration.hasMoreElements(); ) {
/* 109 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/* 110 */       SnmpStatusException snmpStatusException = new SnmpStatusException(6);
/*     */       
/* 112 */       paramSnmpMibSubRequest.registerSetException(snmpVarBind, snmpStatusException);
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
/*     */   public void check(SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException {
/* 132 */     for (Enumeration<SnmpVarBind> enumeration = paramSnmpMibSubRequest.getElements(); enumeration.hasMoreElements(); ) {
/* 133 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/* 134 */       SnmpStatusException snmpStatusException = new SnmpStatusException(6);
/*     */       
/* 136 */       paramSnmpMibSubRequest.registerCheckException(snmpVarBind, snmpStatusException);
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
/*     */   void findHandlingNode(SnmpVarBind paramSnmpVarBind, long[] paramArrayOflong, int paramInt, SnmpRequestTree paramSnmpRequestTree) throws SnmpStatusException {
/* 155 */     int i = paramArrayOflong.length;
/* 156 */     Object object = null;
/*     */     
/* 158 */     if (paramSnmpRequestTree == null) {
/* 159 */       throw new SnmpStatusException(5);
/*     */     }
/* 161 */     if (paramInt > i)
/*     */     {
/* 163 */       throw new SnmpStatusException(225); } 
/* 164 */     if (paramInt == i)
/*     */     {
/* 166 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 171 */     SnmpMibNode snmpMibNode = getChild(paramArrayOflong[paramInt]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     if (snmpMibNode == null) {
/* 183 */       paramSnmpRequestTree.add(this, paramInt, paramSnmpVarBind);
/*     */     } else {
/* 185 */       snmpMibNode.findHandlingNode(paramSnmpVarBind, paramArrayOflong, paramInt + 1, paramSnmpRequestTree);
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
/*     */   long[] findNextHandlingNode(SnmpVarBind paramSnmpVarBind, long[] paramArrayOflong, int paramInt1, int paramInt2, SnmpRequestTree paramSnmpRequestTree, AcmChecker paramAcmChecker) throws SnmpStatusException {
/* 203 */     int i = paramArrayOflong.length;
/* 204 */     Object object = null;
/* 205 */     long[] arrayOfLong1 = null;
/* 206 */     if (paramSnmpRequestTree == null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 211 */       throw new SnmpStatusException(225);
/*     */     }
/*     */     
/* 214 */     Object object1 = paramSnmpRequestTree.getUserData();
/* 215 */     int j = paramSnmpRequestTree.getRequestPduVersion();
/*     */     
/* 217 */     if (paramInt1 >= i) {
/* 218 */       long[] arrayOfLong = new long[1];
/* 219 */       arrayOfLong[0] = getNextVarId(-1L, object1, j);
/* 220 */       arrayOfLong1 = findNextHandlingNode(paramSnmpVarBind, arrayOfLong, 0, paramInt2, paramSnmpRequestTree, paramAcmChecker);
/*     */       
/* 222 */       return arrayOfLong1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 227 */     long[] arrayOfLong2 = new long[1];
/* 228 */     long l = paramArrayOflong[paramInt1];
/*     */ 
/*     */     
/*     */     while (true) {
/*     */       try {
/* 233 */         SnmpMibNode snmpMibNode = getChild(l);
/*     */         
/* 235 */         if (snmpMibNode == null)
/*     */         {
/* 237 */           throw new SnmpStatusException(225);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 242 */         paramAcmChecker.add(paramInt2, l);
/*     */         try {
/* 244 */           arrayOfLong1 = snmpMibNode.findNextHandlingNode(paramSnmpVarBind, paramArrayOflong, paramInt1 + 1, paramInt2 + 1, paramSnmpRequestTree, paramAcmChecker);
/*     */         }
/*     */         finally {
/*     */           
/* 248 */           paramAcmChecker.remove(paramInt2);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 253 */         arrayOfLong1[paramInt2] = l;
/* 254 */         return arrayOfLong1;
/*     */       }
/* 256 */       catch (SnmpStatusException snmpStatusException) {
/*     */ 
/*     */         
/* 259 */         l = getNextVarId(l, object1, j);
/*     */ 
/*     */         
/* 262 */         arrayOfLong2[0] = l;
/* 263 */         paramInt1 = 1;
/* 264 */         paramArrayOflong = arrayOfLong2;
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
/*     */   public void getRootOid(Vector<Integer> paramVector) {
/* 279 */     if (this.nbChildren != 1) {
/*     */       return;
/*     */     }
/* 282 */     paramVector.addElement(Integer.valueOf(this.varList[0]));
/*     */ 
/*     */ 
/*     */     
/* 286 */     ((SnmpMibNode)this.children.firstElement()).getRootOid(paramVector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerNode(String paramString, SnmpMibNode paramSnmpMibNode) throws IllegalAccessException {
/* 295 */     SnmpOid snmpOid = new SnmpOid(paramString);
/* 296 */     registerNode(snmpOid.longValue(), 0, paramSnmpMibNode);
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
/*     */   void registerNode(long[] paramArrayOflong, int paramInt, SnmpMibNode paramSnmpMibNode) throws IllegalAccessException {
/* 308 */     if (paramInt >= paramArrayOflong.length) {
/* 309 */       throw new IllegalAccessException();
/*     */     }
/*     */ 
/*     */     
/* 313 */     long l = paramArrayOflong[paramInt];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 318 */     int i = retrieveIndex(l);
/* 319 */     if (i == this.nbChildren) {
/* 320 */       this.nbChildren++;
/* 321 */       this.varList = new int[this.nbChildren];
/* 322 */       this.varList[0] = (int)l;
/* 323 */       i = 0;
/* 324 */       if (paramInt + 1 == paramArrayOflong.length) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 330 */         this.children.insertElementAt(paramSnmpMibNode, i);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 336 */       SnmpMibOid snmpMibOid = new SnmpMibOid();
/* 337 */       this.children.insertElementAt(snmpMibOid, i);
/* 338 */       snmpMibOid.registerNode(paramArrayOflong, paramInt + 1, paramSnmpMibNode);
/*     */       return;
/*     */     } 
/* 341 */     if (i == -1) {
/*     */ 
/*     */       
/* 344 */       int[] arrayOfInt = new int[this.nbChildren + 1];
/* 345 */       arrayOfInt[this.nbChildren] = (int)l;
/* 346 */       System.arraycopy(this.varList, 0, arrayOfInt, 0, this.nbChildren);
/* 347 */       this.varList = arrayOfInt;
/* 348 */       this.nbChildren++;
/* 349 */       SnmpMibNode.sort(this.varList);
/* 350 */       int j = retrieveIndex(l);
/* 351 */       this.varList[j] = (int)l;
/* 352 */       if (paramInt + 1 == paramArrayOflong.length) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 358 */         this.children.insertElementAt(paramSnmpMibNode, j);
/*     */         return;
/*     */       } 
/* 361 */       SnmpMibOid snmpMibOid = new SnmpMibOid();
/*     */ 
/*     */       
/* 364 */       this.children.insertElementAt(snmpMibOid, j);
/* 365 */       snmpMibOid.registerNode(paramArrayOflong, paramInt + 1, paramSnmpMibNode);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 370 */       SnmpMibNode snmpMibNode = this.children.elementAt(i);
/* 371 */       if (paramInt + 1 == paramArrayOflong.length) {
/*     */ 
/*     */         
/* 374 */         if (snmpMibNode == paramSnmpMibNode)
/* 375 */           return;  if (snmpMibNode != null && paramSnmpMibNode != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 383 */           if (paramSnmpMibNode instanceof SnmpMibGroup) {
/*     */ 
/*     */ 
/*     */             
/* 387 */             ((SnmpMibOid)snmpMibNode).exportChildren((SnmpMibOid)paramSnmpMibNode);
/* 388 */             this.children.setElementAt(paramSnmpMibNode, i);
/*     */             return;
/*     */           } 
/* 391 */           if (paramSnmpMibNode instanceof SnmpMibOid && snmpMibNode instanceof SnmpMibGroup) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 397 */             ((SnmpMibOid)paramSnmpMibNode).exportChildren((SnmpMibOid)snmpMibNode); return;
/*     */           } 
/* 399 */           if (paramSnmpMibNode instanceof SnmpMibOid) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 404 */             ((SnmpMibOid)snmpMibNode).exportChildren((SnmpMibOid)paramSnmpMibNode);
/* 405 */             this.children.setElementAt(paramSnmpMibNode, i);
/*     */             return;
/*     */           } 
/*     */         } 
/* 409 */         this.children.setElementAt(paramSnmpMibNode, i);
/*     */       } else {
/* 411 */         if (snmpMibNode == null)
/* 412 */           throw new IllegalAccessException(); 
/* 413 */         ((SnmpMibOid)snmpMibNode).registerNode(paramArrayOflong, paramInt + 1, paramSnmpMibNode);
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
/*     */   void exportChildren(SnmpMibOid paramSnmpMibOid) throws IllegalAccessException {
/* 428 */     if (paramSnmpMibOid == null)
/* 429 */       return;  long[] arrayOfLong = new long[1];
/* 430 */     for (byte b = 0; b < this.nbChildren; b++) {
/* 431 */       SnmpMibNode snmpMibNode = this.children.elementAt(b);
/* 432 */       if (snmpMibNode != null) {
/* 433 */         arrayOfLong[0] = this.varList[b];
/* 434 */         paramSnmpMibOid.registerNode(arrayOfLong, 0, snmpMibNode);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SnmpMibNode getChild(long paramLong) throws SnmpStatusException {
/* 445 */     int i = getInsertAt(paramLong);
/* 446 */     if (i >= this.nbChildren) {
/* 447 */       throw new SnmpStatusException(225);
/*     */     }
/*     */     
/* 450 */     if (this.varList[i] != (int)paramLong) {
/* 451 */       throw new SnmpStatusException(225);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 456 */     SnmpMibNode snmpMibNode = null;
/*     */     try {
/* 458 */       snmpMibNode = this.children.elementAtNonSync(i);
/* 459 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 460 */       throw new SnmpStatusException(225);
/*     */     } 
/* 462 */     if (snmpMibNode == null) {
/* 463 */       throw new SnmpStatusException(224);
/*     */     }
/* 465 */     return snmpMibNode;
/*     */   }
/*     */ 
/*     */   
/*     */   private int retrieveIndex(long paramLong) {
/* 470 */     int i = 0;
/* 471 */     int j = (int)paramLong;
/* 472 */     if (this.varList == null || this.varList.length < 1) {
/* 473 */       return this.nbChildren;
/*     */     }
/* 475 */     int k = this.varList.length - 1;
/* 476 */     int m = i + (k - i) / 2;
/*     */     
/* 478 */     while (i <= k) {
/* 479 */       int n = this.varList[m];
/* 480 */       if (j == n)
/*     */       {
/*     */         
/* 483 */         return m;
/*     */       }
/* 485 */       if (n < j) {
/* 486 */         i = m + 1;
/*     */       } else {
/* 488 */         k = m - 1;
/*     */       } 
/* 490 */       m = i + (k - i) / 2;
/*     */     } 
/* 492 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getInsertAt(long paramLong) {
/* 497 */     int i = 0;
/* 498 */     int j = (int)paramLong;
/* 499 */     if (this.varList == null)
/* 500 */       return -1; 
/* 501 */     int k = this.varList.length - 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 509 */     int m = i + (k - i) / 2;
/* 510 */     while (i <= k) {
/*     */       
/* 512 */       int n = this.varList[m];
/*     */ 
/*     */ 
/*     */       
/* 516 */       if (j == n) {
/* 517 */         return m;
/*     */       }
/* 519 */       if (n < j) {
/* 520 */         i = m + 1;
/*     */       } else {
/* 522 */         k = m - 1;
/*     */       } 
/* 524 */       m = i + (k - i) / 2;
/*     */     } 
/*     */     
/* 527 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 536 */   private NonSyncVector<SnmpMibNode> children = new NonSyncVector<>(1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 541 */   private int nbChildren = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class NonSyncVector<E>
/*     */     extends Vector<E>
/*     */   {
/*     */     public NonSyncVector(int param1Int) {
/* 552 */       super(param1Int);
/*     */     }
/*     */     
/*     */     final void addNonSyncElement(E param1E) {
/* 556 */       ensureCapacity(this.elementCount + 1);
/* 557 */       this.elementData[this.elementCount++] = param1E;
/*     */     }
/*     */ 
/*     */     
/*     */     final E elementAtNonSync(int param1Int) {
/* 562 */       return (E)this.elementData[param1Int];
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpMibOid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */