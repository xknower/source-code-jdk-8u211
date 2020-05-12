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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnmpStatusException
/*     */   extends Exception
/*     */   implements SnmpDefinitions
/*     */ {
/*     */   private static final long serialVersionUID = 5809485694133115675L;
/*     */   public static final int noSuchName = 2;
/*     */   public static final int badValue = 3;
/*     */   public static final int readOnly = 4;
/*     */   public static final int noAccess = 6;
/*     */   public static final int noSuchInstance = 224;
/*     */   public static final int noSuchObject = 225;
/*     */   private int errorStatus;
/*     */   private int errorIndex;
/*     */   
/*     */   public SnmpStatusException(int paramInt) {
/* 137 */     this.errorStatus = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     this.errorIndex = -1; this.errorStatus = paramInt; } public SnmpStatusException(int paramInt1, int paramInt2) { this.errorStatus = 0; this.errorIndex = -1; this.errorStatus = paramInt1; this.errorIndex = paramInt2; } public SnmpStatusException(String paramString) { super(paramString); this.errorStatus = 0; this.errorIndex = -1; } public SnmpStatusException(SnmpStatusException paramSnmpStatusException, int paramInt) { super(paramSnmpStatusException.getMessage()); this.errorStatus = 0; this.errorIndex = -1;
/*     */     this.errorStatus = paramSnmpStatusException.errorStatus;
/*     */     this.errorIndex = paramInt; }
/*     */ 
/*     */   
/*     */   public int getStatus() {
/*     */     return this.errorStatus;
/*     */   }
/*     */   
/*     */   public int getErrorIndex() {
/*     */     return this.errorIndex;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpStatusException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */