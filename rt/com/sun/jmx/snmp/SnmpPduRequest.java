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
/*     */ public class SnmpPduRequest
/*     */   extends SnmpPduPacket
/*     */   implements SnmpPduRequestType
/*     */ {
/*     */   private static final long serialVersionUID = 2218754017025258979L;
/*  53 */   public int errorStatus = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public int errorIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorIndex(int paramInt) {
/*  69 */     this.errorIndex = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorStatus(int paramInt) {
/*  77 */     this.errorStatus = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getErrorIndex() {
/*  84 */     return this.errorIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getErrorStatus() {
/*  90 */     return this.errorStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpPdu getResponsePdu() {
/*  97 */     SnmpPduRequest snmpPduRequest = new SnmpPduRequest();
/*  98 */     snmpPduRequest.address = this.address;
/*  99 */     snmpPduRequest.port = this.port;
/* 100 */     snmpPduRequest.version = this.version;
/* 101 */     snmpPduRequest.community = this.community;
/* 102 */     snmpPduRequest.type = 162;
/* 103 */     snmpPduRequest.requestId = this.requestId;
/* 104 */     snmpPduRequest.errorStatus = 0;
/* 105 */     snmpPduRequest.errorIndex = 0;
/*     */     
/* 107 */     return snmpPduRequest;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpPduRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */