/*     */ package com.sun.jmx.snmp;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
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
/*     */ public class SnmpOidDatabaseSupport
/*     */   implements SnmpOidDatabase
/*     */ {
/*     */   private Vector<SnmpOidTable> tables;
/*     */   
/*     */   public SnmpOidDatabaseSupport() {
/*  36 */     this.tables = new Vector<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpOidDatabaseSupport(SnmpOidTable paramSnmpOidTable) {
/*  44 */     this.tables = new Vector<>();
/*  45 */     this.tables.addElement(paramSnmpOidTable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(SnmpOidTable paramSnmpOidTable) {
/*  53 */     if (!this.tables.contains(paramSnmpOidTable)) {
/*  54 */       this.tables.addElement(paramSnmpOidTable);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(SnmpOidTable paramSnmpOidTable) throws SnmpStatusException {
/*  64 */     if (!this.tables.contains(paramSnmpOidTable)) {
/*  65 */       throw new SnmpStatusException("The specified SnmpOidTable does not exist in this SnmpOidDatabase");
/*     */     }
/*  67 */     this.tables.removeElement(paramSnmpOidTable);
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
/*     */   public SnmpOidRecord resolveVarName(String paramString) throws SnmpStatusException {
/*  79 */     for (byte b = 0; b < this.tables.size(); b++) {
/*     */       try {
/*  81 */         return ((SnmpOidTable)this.tables.elementAt(b)).resolveVarName(paramString);
/*     */       }
/*  83 */       catch (SnmpStatusException snmpStatusException) {
/*  84 */         if (b == this.tables.size() - 1) {
/*  85 */           throw new SnmpStatusException(snmpStatusException.getMessage());
/*     */         }
/*     */       } 
/*     */     } 
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpOidRecord resolveVarOid(String paramString) throws SnmpStatusException {
/* 100 */     for (byte b = 0; b < this.tables.size(); b++) {
/*     */       try {
/* 102 */         return ((SnmpOidTable)this.tables.elementAt(b)).resolveVarOid(paramString);
/*     */       }
/* 104 */       catch (SnmpStatusException snmpStatusException) {
/* 105 */         if (b == this.tables.size() - 1) {
/* 106 */           throw new SnmpStatusException(snmpStatusException.getMessage());
/*     */         }
/*     */       } 
/*     */     } 
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<?> getAllEntries() {
/* 119 */     Vector<?> vector = new Vector();
/* 120 */     for (byte b = 0; b < this.tables.size(); b++) {
/* 121 */       Vector vector1 = Util.<Vector>cast(((SnmpOidTable)this.tables.elementAt(b)).getAllEntries());
/* 122 */       if (vector1 != null) {
/* 123 */         for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 124 */           vector.addElement(vector1.elementAt(b1));
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 129 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAll() {
/* 136 */     this.tables.removeAllElements();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpOidDatabaseSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */