/*     */ package com.sun.jmx.snmp.daemon;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.snmp.SnmpEngine;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpPdu;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpValue;
/*     */ import com.sun.jmx.snmp.SnmpVarBind;
/*     */ import com.sun.jmx.snmp.ThreadContext;
/*     */ import com.sun.jmx.snmp.agent.SnmpMibAgent;
/*     */ import com.sun.jmx.snmp.internal.SnmpIncomingRequest;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SnmpSubNextRequestHandler
/*     */   extends SnmpSubRequestHandler
/*     */ {
/*  56 */   private SnmpAdaptorServer server = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpSubNextRequestHandler(SnmpAdaptorServer paramSnmpAdaptorServer, SnmpMibAgent paramSnmpMibAgent, SnmpPdu paramSnmpPdu) {
/*  64 */     super(paramSnmpMibAgent, paramSnmpPdu);
/*  65 */     init(paramSnmpPdu, paramSnmpAdaptorServer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpSubNextRequestHandler(SnmpEngine paramSnmpEngine, SnmpAdaptorServer paramSnmpAdaptorServer, SnmpIncomingRequest paramSnmpIncomingRequest, SnmpMibAgent paramSnmpMibAgent, SnmpPdu paramSnmpPdu) {
/*  73 */     super(paramSnmpEngine, paramSnmpIncomingRequest, paramSnmpMibAgent, paramSnmpPdu);
/*  74 */     init(paramSnmpPdu, paramSnmpAdaptorServer);
/*  75 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  76 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubNextRequestHandler.class.getName(), "SnmpSubNextRequestHandler", "Constructor : " + this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(SnmpPdu paramSnmpPdu, SnmpAdaptorServer paramSnmpAdaptorServer) {
/*  82 */     this.server = paramSnmpAdaptorServer;
/*     */ 
/*     */ 
/*     */     
/*  86 */     int i = this.translation.length;
/*  87 */     SnmpVarBind[] arrayOfSnmpVarBind = paramSnmpPdu.varBindList;
/*  88 */     SnmpSubRequestHandler.NonSyncVector<SnmpVarBind> nonSyncVector = (SnmpSubRequestHandler.NonSyncVector)this.varBind;
/*     */     
/*  90 */     for (byte b = 0; b < i; b++) {
/*  91 */       this.translation[b] = b;
/*     */ 
/*     */ 
/*     */       
/*  95 */       SnmpVarBind snmpVarBind = new SnmpVarBind((arrayOfSnmpVarBind[b]).oid, (arrayOfSnmpVarBind[b]).value);
/*     */       
/*  97 */       nonSyncVector.addNonSyncElement(snmpVarBind);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 106 */       ThreadContext threadContext = ThreadContext.push("SnmpUserData", this.data);
/*     */       try {
/* 108 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 109 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(), "run", "[" + 
/* 110 */               Thread.currentThread() + "]:getNext operation on " + this.agent
/* 111 */               .getMibName());
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 117 */         this.agent.getNext(createMibRequest(this.varBind, 1, this.data));
/*     */       } finally {
/* 119 */         ThreadContext.restore(threadContext);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 124 */     catch (SnmpStatusException snmpStatusException) {
/* 125 */       this.errorStatus = snmpStatusException.getStatus();
/* 126 */       this.errorIndex = snmpStatusException.getErrorIndex();
/* 127 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 128 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubRequestHandler.class.getName(), "run", "[" + 
/* 129 */             Thread.currentThread() + "]:an Snmp error occurred during the operation", snmpStatusException);
/*     */       
/*     */       }
/*     */     }
/* 133 */     catch (Exception exception) {
/* 134 */       this.errorStatus = 5;
/* 135 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 136 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubRequestHandler.class.getName(), "run", "[" + 
/* 137 */             Thread.currentThread() + "]:a generic error occurred during the operation", exception);
/*     */       }
/*     */     } 
/*     */     
/* 141 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 142 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(), "run", "[" + 
/* 143 */           Thread.currentThread() + "]:operation completed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateRequest(SnmpVarBind paramSnmpVarBind, int paramInt) {
/* 151 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 152 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubRequestHandler.class.getName(), "updateRequest", "Copy :" + paramSnmpVarBind);
/*     */     }
/*     */     
/* 155 */     int i = this.varBind.size();
/* 156 */     this.translation[i] = paramInt;
/* 157 */     SnmpVarBind snmpVarBind = new SnmpVarBind(paramSnmpVarBind.oid, paramSnmpVarBind.value);
/*     */     
/* 159 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 160 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubRequestHandler.class.getName(), "updateRequest", "Copied :" + snmpVarBind);
/*     */     }
/*     */ 
/*     */     
/* 164 */     this.varBind.addElement(snmpVarBind);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateResult(SnmpVarBind[] paramArrayOfSnmpVarBind) {
/* 175 */     int i = this.varBind.size();
/* 176 */     for (byte b = 0; b < i; b++) {
/*     */ 
/*     */       
/* 179 */       int j = this.translation[b];
/*     */       
/* 181 */       SnmpVarBind snmpVarBind1 = ((SnmpSubRequestHandler.NonSyncVector<SnmpVarBind>)this.varBind).elementAtNonSync(b);
/*     */       
/* 183 */       SnmpVarBind snmpVarBind2 = paramArrayOfSnmpVarBind[j];
/* 184 */       if (snmpVarBind2 == null) {
/* 185 */         paramArrayOfSnmpVarBind[j] = snmpVarBind1;
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 194 */         SnmpValue snmpValue = snmpVarBind2.value;
/* 195 */         if (snmpValue == null || snmpValue == SnmpVarBind.endOfMibView) {
/*     */           
/* 197 */           if (snmpVarBind1 != null && snmpVarBind1.value != SnmpVarBind.endOfMibView)
/*     */           {
/* 199 */             paramArrayOfSnmpVarBind[j] = snmpVarBind1;
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/* 207 */         else if (snmpVarBind1 != null) {
/*     */ 
/*     */           
/* 210 */           if (snmpVarBind1.value != SnmpVarBind.endOfMibView) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 215 */             int k = snmpVarBind1.oid.compareTo(snmpVarBind2.oid);
/* 216 */             if (k < 0) {
/*     */ 
/*     */               
/* 219 */               paramArrayOfSnmpVarBind[j] = snmpVarBind1;
/*     */             
/*     */             }
/* 222 */             else if (k == 0) {
/*     */ 
/*     */               
/* 225 */               if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 226 */                 JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(), "updateResult", " oid overlapping. Oid : " + snmpVarBind1.oid + "value :" + snmpVarBind1.value);
/*     */ 
/*     */                 
/* 229 */                 JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(), "updateResult", "Already present varBind : " + snmpVarBind2);
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 234 */               SnmpOid snmpOid = snmpVarBind2.oid;
/* 235 */               SnmpMibAgent snmpMibAgent = this.server.getAgentMib(snmpOid);
/*     */               
/* 237 */               if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 238 */                 JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(), "updateResult", "Deeper agent : " + snmpMibAgent);
/*     */               }
/*     */               
/* 241 */               if (snmpMibAgent == this.agent) {
/* 242 */                 if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 243 */                   JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(), "updateResult", "The current agent is the deeper one. Update the value with the current one");
/*     */                 }
/*     */                 
/* 246 */                 (paramArrayOfSnmpVarBind[j]).value = snmpVarBind1.value;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpSubNextRequestHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */