/*     */ package com.sun.jmx.snmp.agent;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpVarBind;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.ObjectName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnmpErrorHandlerAgent
/*     */   extends SnmpMibAgent
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7751082923508885650L;
/*     */   
/*     */   public void init() throws IllegalAccessException {}
/*     */   
/*     */   public ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/*  81 */     return paramObjectName;
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
/*     */   public long[] getRootOid() {
/*  94 */     return null;
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
/*     */   public void get(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException {
/* 108 */     JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpErrorHandlerAgent.class
/* 109 */         .getName(), "get", "Get in Exception");
/*     */ 
/*     */     
/* 112 */     if (paramSnmpMibRequest.getVersion() == 0) {
/* 113 */       throw new SnmpStatusException(2);
/*     */     }
/* 115 */     Enumeration<SnmpVarBind> enumeration = paramSnmpMibRequest.getElements();
/* 116 */     while (enumeration.hasMoreElements()) {
/* 117 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/* 118 */       snmpVarBind.setNoSuchObject();
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
/*     */   public void check(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException {
/* 138 */     JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpErrorHandlerAgent.class
/* 139 */         .getName(), "check", "Check in Exception");
/*     */ 
/*     */     
/* 142 */     throw new SnmpStatusException(17);
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
/*     */   public void set(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException {
/* 156 */     JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpErrorHandlerAgent.class
/* 157 */         .getName(), "set", "Set in Exception, CANNOT be called");
/*     */ 
/*     */     
/* 160 */     throw new SnmpStatusException(17);
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
/*     */   public void getNext(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException {
/* 174 */     JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpErrorHandlerAgent.class
/* 175 */         .getName(), "getNext", "GetNext in Exception");
/*     */ 
/*     */     
/* 178 */     if (paramSnmpMibRequest.getVersion() == 0) {
/* 179 */       throw new SnmpStatusException(2);
/*     */     }
/* 181 */     Enumeration<SnmpVarBind> enumeration = paramSnmpMibRequest.getElements();
/* 182 */     while (enumeration.hasMoreElements()) {
/* 183 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/* 184 */       snmpVarBind.setEndOfMibView();
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
/*     */   public void getBulk(SnmpMibRequest paramSnmpMibRequest, int paramInt1, int paramInt2) throws SnmpStatusException {
/* 200 */     JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpErrorHandlerAgent.class
/* 201 */         .getName(), "getBulk", "GetBulk in Exception");
/*     */ 
/*     */     
/* 204 */     if (paramSnmpMibRequest.getVersion() == 0) {
/* 205 */       throw new SnmpStatusException(5, 0);
/*     */     }
/* 207 */     Enumeration<SnmpVarBind> enumeration = paramSnmpMibRequest.getElements();
/* 208 */     while (enumeration.hasMoreElements()) {
/* 209 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/* 210 */       snmpVarBind.setEndOfMibView();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpErrorHandlerAgent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */