/*     */ package com.sun.jmx.snmp;
/*     */ 
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
/*     */ public class SnmpVarBindList
/*     */   extends Vector<SnmpVarBind>
/*     */ {
/*     */   private static final long serialVersionUID = -7203997794636430321L;
/*  29 */   public String identity = "VarBindList ";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Timestamp timestamp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpVarBindList() {
/*  48 */     super(5, 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpVarBindList(int paramInt) {
/*  56 */     super(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpVarBindList(String paramString) {
/*  64 */     super(5, 5);
/*  65 */     this.identity = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpVarBindList(SnmpVarBindList paramSnmpVarBindList) {
/*  74 */     super(paramSnmpVarBindList.size(), 5);
/*  75 */     paramSnmpVarBindList.copyInto(this.elementData);
/*  76 */     this.elementCount = paramSnmpVarBindList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpVarBindList(Vector<SnmpVarBind> paramVector) {
/*  85 */     super(paramVector.size(), 5);
/*  86 */     for (Enumeration<SnmpVarBind> enumeration = paramVector.elements(); enumeration.hasMoreElements(); ) {
/*  87 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/*  88 */       addElement(snmpVarBind.clone());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpVarBindList(String paramString, Vector<SnmpVarBind> paramVector) {
/*  99 */     this(paramVector);
/* 100 */     this.identity = paramString;
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
/*     */   public Timestamp getTimestamp() {
/* 112 */     return this.timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimestamp(Timestamp paramTimestamp) {
/* 122 */     this.timestamp = paramTimestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized SnmpVarBind getVarBindAt(int paramInt) {
/* 132 */     return elementAt(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getVarBindCount() {
/* 140 */     return size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Enumeration<SnmpVarBind> getVarBindList() {
/* 149 */     return elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized void setVarBindList(Vector<SnmpVarBind> paramVector) {
/* 160 */     setVarBindList(paramVector, false);
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
/*     */   public final synchronized void setVarBindList(Vector<SnmpVarBind> paramVector, boolean paramBoolean) {
/* 172 */     synchronized (paramVector) {
/* 173 */       int i = paramVector.size();
/* 174 */       setSize(i);
/* 175 */       paramVector.copyInto(this.elementData);
/* 176 */       if (paramBoolean) {
/* 177 */         for (byte b = 0; b < i; b++) {
/* 178 */           SnmpVarBind snmpVarBind = (SnmpVarBind)this.elementData[b];
/* 179 */           this.elementData[b] = snmpVarBind.clone();
/*     */         } 
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
/*     */   public synchronized void addVarBindList(SnmpVarBindList paramSnmpVarBindList) {
/* 194 */     ensureCapacity(paramSnmpVarBindList.size() + size());
/* 195 */     for (byte b = 0; b < paramSnmpVarBindList.size(); b++) {
/* 196 */       addElement(paramSnmpVarBindList.getVarBindAt(b));
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
/*     */   public synchronized boolean removeVarBindList(SnmpVarBindList paramSnmpVarBindList) {
/* 208 */     boolean bool = true;
/* 209 */     for (byte b = 0; b < paramSnmpVarBindList.size(); b++) {
/* 210 */       bool = removeElement(paramSnmpVarBindList.getVarBindAt(b));
/*     */     }
/* 212 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized void replaceVarBind(SnmpVarBind paramSnmpVarBind, int paramInt) {
/* 222 */     setElementAt(paramSnmpVarBind, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized void addVarBind(String[] paramArrayOfString, String paramString) throws SnmpStatusException {
/* 232 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 233 */       SnmpVarBind snmpVarBind = new SnmpVarBind(paramArrayOfString[b]);
/* 234 */       snmpVarBind.addInstance(paramString);
/* 235 */       addElement(snmpVarBind);
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
/*     */   public synchronized boolean removeVarBind(String[] paramArrayOfString, String paramString) throws SnmpStatusException {
/* 248 */     boolean bool = true;
/* 249 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 250 */       SnmpVarBind snmpVarBind = new SnmpVarBind(paramArrayOfString[b]);
/* 251 */       snmpVarBind.addInstance(paramString);
/* 252 */       int i = indexOfOid(snmpVarBind);
/*     */       try {
/* 254 */         removeElementAt(i);
/* 255 */       } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 256 */         bool = false;
/*     */       } 
/*     */     } 
/* 259 */     return bool;
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
/*     */   public synchronized void addVarBind(String[] paramArrayOfString) throws SnmpStatusException {
/* 275 */     addVarBind(paramArrayOfString, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean removeVarBind(String[] paramArrayOfString) throws SnmpStatusException {
/* 286 */     return removeVarBind(paramArrayOfString, (String)null);
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
/*     */   public synchronized void addVarBind(String paramString) throws SnmpStatusException {
/* 298 */     SnmpVarBind snmpVarBind = new SnmpVarBind(paramString);
/* 299 */     addVarBind(snmpVarBind);
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
/*     */   public synchronized boolean removeVarBind(String paramString) throws SnmpStatusException {
/* 313 */     SnmpVarBind snmpVarBind = new SnmpVarBind(paramString);
/* 314 */     int i = indexOfOid(snmpVarBind);
/*     */     try {
/* 316 */       removeElementAt(i);
/* 317 */       return true;
/* 318 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 319 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addVarBind(SnmpVarBind paramSnmpVarBind) {
/* 329 */     addElement(paramSnmpVarBind);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean removeVarBind(SnmpVarBind paramSnmpVarBind) {
/* 339 */     return removeElement(paramSnmpVarBind);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addInstance(String paramString) throws SnmpStatusException {
/* 349 */     int i = size();
/* 350 */     for (byte b = 0; b < i; b++) {
/* 351 */       ((SnmpVarBind)this.elementData[b]).addInstance(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized void concat(Vector<SnmpVarBind> paramVector) {
/* 361 */     ensureCapacity(size() + paramVector.size());
/* 362 */     for (Enumeration<SnmpVarBind> enumeration = paramVector.elements(); enumeration.hasMoreElements();) {
/* 363 */       addElement(enumeration.nextElement());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean checkForValidValues() {
/* 373 */     int i = size();
/* 374 */     for (byte b = 0; b < i; b++) {
/* 375 */       SnmpVarBind snmpVarBind = (SnmpVarBind)this.elementData[b];
/* 376 */       if (!snmpVarBind.isValidValue())
/* 377 */         return false; 
/*     */     } 
/* 379 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean checkForUnspecifiedValue() {
/* 387 */     int i = size();
/* 388 */     for (byte b = 0; b < i; b++) {
/* 389 */       SnmpVarBind snmpVarBind = (SnmpVarBind)this.elementData[b];
/* 390 */       if (snmpVarBind.isUnspecifiedValue())
/* 391 */         return true; 
/*     */     } 
/* 393 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized SnmpVarBindList splitAt(int paramInt) {
/* 402 */     SnmpVarBindList snmpVarBindList = null;
/* 403 */     if (paramInt > this.elementCount)
/* 404 */       return snmpVarBindList; 
/* 405 */     snmpVarBindList = new SnmpVarBindList();
/* 406 */     int i = size();
/* 407 */     for (int j = paramInt; j < i; j++)
/* 408 */       snmpVarBindList.addElement((SnmpVarBind)this.elementData[j]); 
/* 409 */     this.elementCount = paramInt;
/* 410 */     trimToSize();
/* 411 */     return snmpVarBindList;
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
/*     */   public synchronized int indexOfOid(SnmpVarBind paramSnmpVarBind, int paramInt1, int paramInt2) {
/* 424 */     SnmpOid snmpOid = paramSnmpVarBind.getOid();
/* 425 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 426 */       SnmpVarBind snmpVarBind = (SnmpVarBind)this.elementData[i];
/* 427 */       if (snmpOid.equals(snmpVarBind.getOid()))
/* 428 */         return i; 
/*     */     } 
/* 430 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int indexOfOid(SnmpVarBind paramSnmpVarBind) {
/* 439 */     return indexOfOid(paramSnmpVarBind, 0, size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int indexOfOid(SnmpOid paramSnmpOid) {
/* 448 */     int i = size();
/* 449 */     for (byte b = 0; b < i; b++) {
/* 450 */       SnmpVarBind snmpVarBind = (SnmpVarBind)this.elementData[b];
/* 451 */       if (paramSnmpOid.equals(snmpVarBind.getOid()))
/* 452 */         return b; 
/*     */     } 
/* 454 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized SnmpVarBindList cloneWithValue() {
/* 463 */     SnmpVarBindList snmpVarBindList = new SnmpVarBindList();
/* 464 */     snmpVarBindList.setTimestamp(getTimestamp());
/* 465 */     snmpVarBindList.ensureCapacity(size());
/* 466 */     for (byte b = 0; b < size(); b++) {
/* 467 */       SnmpVarBind snmpVarBind = (SnmpVarBind)this.elementData[b];
/* 468 */       snmpVarBindList.addElement(snmpVarBind.clone());
/*     */     } 
/* 470 */     return snmpVarBindList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized SnmpVarBindList cloneWithoutValue() {
/* 479 */     SnmpVarBindList snmpVarBindList = new SnmpVarBindList();
/* 480 */     int i = size();
/* 481 */     snmpVarBindList.ensureCapacity(i);
/* 482 */     for (byte b = 0; b < i; b++) {
/* 483 */       SnmpVarBind snmpVarBind = (SnmpVarBind)this.elementData[b];
/* 484 */       snmpVarBindList.addElement((SnmpVarBind)snmpVarBind.cloneWithoutValue());
/*     */     } 
/* 486 */     return snmpVarBindList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized SnmpVarBindList clone() {
/* 496 */     return cloneWithValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Vector<SnmpVarBind> toVector(boolean paramBoolean) {
/* 507 */     int i = this.elementCount;
/* 508 */     if (!paramBoolean) return new Vector<>(this); 
/* 509 */     Vector<SnmpVarBind> vector = new Vector(i, 5);
/* 510 */     for (byte b = 0; b < i; b++) {
/* 511 */       SnmpVarBind snmpVarBind = (SnmpVarBind)this.elementData[b];
/* 512 */       vector.addElement(snmpVarBind.clone());
/*     */     } 
/* 514 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String oidListToString() {
/* 522 */     StringBuilder stringBuilder = new StringBuilder(300);
/* 523 */     for (byte b = 0; b < this.elementCount; b++) {
/* 524 */       SnmpVarBind snmpVarBind = (SnmpVarBind)this.elementData[b];
/* 525 */       stringBuilder.append(snmpVarBind.getOid().toString()).append("\n");
/*     */     } 
/* 527 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String varBindListToString() {
/* 536 */     StringBuilder stringBuilder = new StringBuilder(300);
/* 537 */     for (byte b = 0; b < this.elementCount; b++) {
/* 538 */       stringBuilder.append(this.elementData[b].toString()).append("\n");
/*     */     }
/* 540 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() {
/* 551 */     removeAllElements();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpVarBindList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */