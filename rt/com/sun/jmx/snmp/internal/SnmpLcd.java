/*     */ package com.sun.jmx.snmp.internal;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpEngineId;
/*     */ import com.sun.jmx.snmp.SnmpUnknownModelLcdException;
/*     */ import com.sun.jmx.snmp.SnmpUnknownSubSystemException;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SnmpLcd
/*     */ {
/*     */   class SubSysLcdManager
/*     */   {
/*  41 */     private Hashtable<Integer, SnmpModelLcd> models = new Hashtable<>();
/*     */ 
/*     */ 
/*     */     
/*     */     public void addModelLcd(int param1Int, SnmpModelLcd param1SnmpModelLcd) {
/*  46 */       this.models.put(new Integer(param1Int), param1SnmpModelLcd);
/*     */     }
/*     */     
/*     */     public SnmpModelLcd getModelLcd(int param1Int) {
/*  50 */       return this.models.get(new Integer(param1Int));
/*     */     }
/*     */     
/*     */     public SnmpModelLcd removeModelLcd(int param1Int) {
/*  54 */       return this.models.remove(new Integer(param1Int));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  59 */   private Hashtable<SnmpSubSystem, SubSysLcdManager> subs = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getEngineBoots();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getEngineId();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void storeEngineBoots(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void storeEngineId(SnmpEngineId paramSnmpEngineId);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addModelLcd(SnmpSubSystem paramSnmpSubSystem, int paramInt, SnmpModelLcd paramSnmpModelLcd) {
/*  94 */     SubSysLcdManager subSysLcdManager = this.subs.get(paramSnmpSubSystem);
/*  95 */     if (subSysLcdManager == null) {
/*  96 */       subSysLcdManager = new SubSysLcdManager();
/*  97 */       this.subs.put(paramSnmpSubSystem, subSysLcdManager);
/*     */     } 
/*     */     
/* 100 */     subSysLcdManager.addModelLcd(paramInt, paramSnmpModelLcd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeModelLcd(SnmpSubSystem paramSnmpSubSystem, int paramInt) throws SnmpUnknownModelLcdException, SnmpUnknownSubSystemException {
/* 111 */     SubSysLcdManager subSysLcdManager = this.subs.get(paramSnmpSubSystem);
/* 112 */     if (subSysLcdManager != null) {
/* 113 */       SnmpModelLcd snmpModelLcd = subSysLcdManager.removeModelLcd(paramInt);
/* 114 */       if (snmpModelLcd == null) {
/* 115 */         throw new SnmpUnknownModelLcdException("Model : " + paramInt);
/*     */       }
/*     */     } else {
/*     */       
/* 119 */       throw new SnmpUnknownSubSystemException(paramSnmpSubSystem.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpModelLcd getModelLcd(SnmpSubSystem paramSnmpSubSystem, int paramInt) {
/* 130 */     SubSysLcdManager subSysLcdManager = this.subs.get(paramSnmpSubSystem);
/*     */     
/* 132 */     if (subSysLcdManager == null) return null;
/*     */     
/* 134 */     return subSysLcdManager.getModelLcd(paramInt);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\internal\SnmpLcd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */