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
/*     */ public class SnmpScopedPduBulk
/*     */   extends SnmpScopedPduPacket
/*     */   implements SnmpPduBulkType
/*     */ {
/*     */   private static final long serialVersionUID = -1648623646227038885L;
/*     */   int nonRepeaters;
/*     */   int maxRepetitions;
/*     */   
/*     */   public void setMaxRepetitions(int paramInt) {
/*  65 */     this.maxRepetitions = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNonRepeaters(int paramInt) {
/*  73 */     this.nonRepeaters = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxRepetitions() {
/*  80 */     return this.maxRepetitions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNonRepeaters() {
/*  86 */     return this.nonRepeaters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpPdu getResponsePdu() {
/*  93 */     SnmpScopedPduRequest snmpScopedPduRequest = new SnmpScopedPduRequest();
/*  94 */     snmpScopedPduRequest.address = this.address;
/*  95 */     snmpScopedPduRequest.port = this.port;
/*  96 */     snmpScopedPduRequest.version = this.version;
/*  97 */     snmpScopedPduRequest.requestId = this.requestId;
/*  98 */     snmpScopedPduRequest.msgId = this.msgId;
/*  99 */     snmpScopedPduRequest.msgMaxSize = this.msgMaxSize;
/* 100 */     snmpScopedPduRequest.msgFlags = this.msgFlags;
/* 101 */     snmpScopedPduRequest.msgSecurityModel = this.msgSecurityModel;
/* 102 */     snmpScopedPduRequest.contextEngineId = this.contextEngineId;
/* 103 */     snmpScopedPduRequest.contextName = this.contextName;
/* 104 */     snmpScopedPduRequest.securityParameters = this.securityParameters;
/* 105 */     snmpScopedPduRequest.type = 162;
/* 106 */     snmpScopedPduRequest.errorStatus = 0;
/* 107 */     snmpScopedPduRequest.errorIndex = 0;
/* 108 */     return snmpScopedPduRequest;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpScopedPduBulk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */