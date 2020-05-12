/*     */ package com.sun.jmx.snmp;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnmpPduFactoryBER
/*     */   implements SnmpPduFactory, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3525318344000547635L;
/*     */   
/*     */   public SnmpPdu decodeSnmpPdu(SnmpMsg paramSnmpMsg) throws SnmpStatusException {
/*  93 */     return paramSnmpMsg.decodeSnmpPdu();
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
/*     */   public SnmpMsg encodeSnmpPdu(SnmpPdu paramSnmpPdu, int paramInt) throws SnmpStatusException, SnmpTooBigException {
/*     */     SnmpMessage snmpMessage;
/*     */     SnmpV3Message snmpV3Message;
/* 115 */     switch (paramSnmpPdu.version) {
/*     */       case 0:
/*     */       case 1:
/* 118 */         snmpMessage = new SnmpMessage();
/* 119 */         snmpMessage.encodeSnmpPdu(paramSnmpPdu, paramInt);
/* 120 */         return snmpMessage;
/*     */       
/*     */       case 3:
/* 123 */         snmpV3Message = new SnmpV3Message();
/* 124 */         snmpV3Message.encodeSnmpPdu(paramSnmpPdu, paramInt);
/* 125 */         return snmpV3Message;
/*     */     } 
/*     */     
/* 128 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpPduFactoryBER.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */