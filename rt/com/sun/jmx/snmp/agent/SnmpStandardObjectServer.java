/*     */ package com.sun.jmx.snmp.agent;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpVarBind;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnmpStandardObjectServer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4641068116505308488L;
/*     */   
/*     */   public void get(SnmpStandardMetaServer paramSnmpStandardMetaServer, SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException {
/* 115 */     Object object = paramSnmpMibSubRequest.getUserData();
/*     */     
/* 117 */     for (Enumeration<SnmpVarBind> enumeration = paramSnmpMibSubRequest.getElements(); enumeration.hasMoreElements(); ) {
/* 118 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/*     */       try {
/* 120 */         long l = snmpVarBind.oid.getOidArc(paramInt);
/* 121 */         snmpVarBind.value = paramSnmpStandardMetaServer.get(l, object);
/* 122 */       } catch (SnmpStatusException snmpStatusException) {
/* 123 */         paramSnmpMibSubRequest.registerGetException(snmpVarBind, snmpStatusException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(SnmpStandardMetaServer paramSnmpStandardMetaServer, SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException {
/* 176 */     Object object = paramSnmpMibSubRequest.getUserData();
/*     */     
/* 178 */     for (Enumeration<SnmpVarBind> enumeration = paramSnmpMibSubRequest.getElements(); enumeration.hasMoreElements(); ) {
/* 179 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 184 */         long l = snmpVarBind.oid.getOidArc(paramInt);
/* 185 */         snmpVarBind.value = paramSnmpStandardMetaServer.set(snmpVarBind.value, l, object);
/* 186 */       } catch (SnmpStatusException snmpStatusException) {
/* 187 */         paramSnmpMibSubRequest.registerSetException(snmpVarBind, snmpStatusException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void check(SnmpStandardMetaServer paramSnmpStandardMetaServer, SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException {
/* 241 */     Object object = paramSnmpMibSubRequest.getUserData();
/*     */     
/* 243 */     for (Enumeration<SnmpVarBind> enumeration = paramSnmpMibSubRequest.getElements(); enumeration.hasMoreElements(); ) {
/* 244 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 249 */         long l = snmpVarBind.oid.getOidArc(paramInt);
/* 250 */         paramSnmpStandardMetaServer.check(snmpVarBind.value, l, object);
/* 251 */       } catch (SnmpStatusException snmpStatusException) {
/* 252 */         paramSnmpMibSubRequest.registerCheckException(snmpVarBind, snmpStatusException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpStandardObjectServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */