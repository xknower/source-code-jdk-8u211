/*    */ package com.sun.jmx.snmp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SnmpScopedPduRequest
/*    */   extends SnmpScopedPduPacket
/*    */   implements SnmpPduRequestType
/*    */ {
/*    */   private static final long serialVersionUID = 6463060973056773680L;
/* 37 */   int errorStatus = 0;
/*    */   
/* 39 */   int errorIndex = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setErrorIndex(int paramInt) {
/* 48 */     this.errorIndex = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setErrorStatus(int paramInt) {
/* 56 */     this.errorStatus = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getErrorIndex() {
/* 65 */     return this.errorIndex;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getErrorStatus() {
/* 71 */     return this.errorStatus;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SnmpPdu getResponsePdu() {
/* 78 */     SnmpScopedPduRequest snmpScopedPduRequest = new SnmpScopedPduRequest();
/* 79 */     snmpScopedPduRequest.address = this.address;
/* 80 */     snmpScopedPduRequest.port = this.port;
/* 81 */     snmpScopedPduRequest.version = this.version;
/* 82 */     snmpScopedPduRequest.requestId = this.requestId;
/* 83 */     snmpScopedPduRequest.msgId = this.msgId;
/* 84 */     snmpScopedPduRequest.msgMaxSize = this.msgMaxSize;
/* 85 */     snmpScopedPduRequest.msgFlags = this.msgFlags;
/* 86 */     snmpScopedPduRequest.msgSecurityModel = this.msgSecurityModel;
/* 87 */     snmpScopedPduRequest.contextEngineId = this.contextEngineId;
/* 88 */     snmpScopedPduRequest.contextName = this.contextName;
/* 89 */     snmpScopedPduRequest.securityParameters = this.securityParameters;
/* 90 */     snmpScopedPduRequest.type = 162;
/* 91 */     snmpScopedPduRequest.errorStatus = 0;
/* 92 */     snmpScopedPduRequest.errorIndex = 0;
/* 93 */     return snmpScopedPduRequest;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpScopedPduRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */