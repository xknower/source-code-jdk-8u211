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
/*     */ public class SnmpPduBulk
/*     */   extends SnmpPduPacket
/*     */   implements SnmpPduBulkType
/*     */ {
/*     */   private static final long serialVersionUID = -7431306775883371046L;
/*     */   public int nonRepeaters;
/*     */   public int maxRepetitions;
/*     */   
/*     */   public void setMaxRepetitions(int paramInt) {
/*  79 */     this.maxRepetitions = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNonRepeaters(int paramInt) {
/*  87 */     this.nonRepeaters = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxRepetitions() {
/*  94 */     return this.maxRepetitions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNonRepeaters() {
/* 100 */     return this.nonRepeaters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpPdu getResponsePdu() {
/* 107 */     SnmpPduRequest snmpPduRequest = new SnmpPduRequest();
/* 108 */     snmpPduRequest.address = this.address;
/* 109 */     snmpPduRequest.port = this.port;
/* 110 */     snmpPduRequest.version = this.version;
/* 111 */     snmpPduRequest.community = this.community;
/* 112 */     snmpPduRequest.type = 162;
/* 113 */     snmpPduRequest.requestId = this.requestId;
/* 114 */     snmpPduRequest.errorStatus = 0;
/* 115 */     snmpPduRequest.errorIndex = 0;
/*     */     
/* 117 */     return snmpPduRequest;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpPduBulk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */