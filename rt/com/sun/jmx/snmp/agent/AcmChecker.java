/*     */ package com.sun.jmx.snmp.agent;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpUnknownModelException;
/*     */ import com.sun.jmx.snmp.internal.SnmpAccessControlModel;
/*     */ import com.sun.jmx.snmp.internal.SnmpEngineImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AcmChecker
/*     */ {
/*  56 */   SnmpAccessControlModel model = null;
/*  57 */   String principal = null;
/*  58 */   int securityLevel = -1;
/*  59 */   int version = -1;
/*  60 */   int pduType = -1;
/*  61 */   int securityModel = -1;
/*  62 */   byte[] contextName = null;
/*  63 */   SnmpEngineImpl engine = null;
/*  64 */   LongList l = null;
/*     */   AcmChecker(SnmpMibRequest paramSnmpMibRequest) {
/*  66 */     this.engine = (SnmpEngineImpl)paramSnmpMibRequest.getEngine();
/*     */     
/*  68 */     if (this.engine != null && 
/*  69 */       this.engine.isCheckOidActivated()) {
/*     */       try {
/*  71 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  72 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/*  73 */               .getName(), "AcmChecker(SnmpMibRequest)", "SNMP V3 Access Control to be done");
/*     */         }
/*     */ 
/*     */         
/*  77 */         this
/*     */           
/*  79 */           .model = (SnmpAccessControlModel)this.engine.getAccessControlSubSystem().getModel(3);
/*  80 */         this.principal = paramSnmpMibRequest.getPrincipal();
/*  81 */         this.securityLevel = paramSnmpMibRequest.getSecurityLevel();
/*  82 */         this.pduType = (paramSnmpMibRequest.getPdu()).type;
/*  83 */         this.version = paramSnmpMibRequest.getRequestPduVersion();
/*  84 */         this.securityModel = paramSnmpMibRequest.getSecurityModel();
/*  85 */         this.contextName = paramSnmpMibRequest.getAccessContextName();
/*  86 */         this.l = new LongList();
/*  87 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  95 */           StringBuilder stringBuilder = (new StringBuilder()).append("Will check oid for : principal : ").append(this.principal).append("; securityLevel : ").append(this.securityLevel).append("; pduType : ").append(this.pduType).append("; version : ").append(this.version).append("; securityModel : ").append(this.securityModel).append("; contextName : ").append(this.contextName);
/*  96 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/*  97 */               .getName(), "AcmChecker(SnmpMibRequest)", stringBuilder
/*  98 */               .toString());
/*     */         }
/*     */       
/* 101 */       } catch (SnmpUnknownModelException snmpUnknownModelException) {
/* 102 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 103 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/* 104 */               .getName(), "AcmChecker(SnmpMibRequest)", "Unknown Model, no ACM check.");
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void add(int paramInt, long paramLong) {
/* 114 */     if (this.model != null)
/* 115 */       this.l.add(paramInt, paramLong); 
/*     */   }
/*     */   
/*     */   void remove(int paramInt) {
/* 119 */     if (this.model != null) {
/* 120 */       this.l.remove(paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */   void add(int paramInt1, long[] paramArrayOflong, int paramInt2, int paramInt3) {
/* 125 */     if (this.model != null)
/* 126 */       this.l.add(paramInt1, paramArrayOflong, paramInt2, paramInt3); 
/*     */   }
/*     */   
/*     */   void remove(int paramInt1, int paramInt2) {
/* 130 */     if (this.model != null)
/* 131 */       this.l.remove(paramInt1, paramInt2); 
/*     */   }
/*     */   
/*     */   void checkCurrentOid() throws SnmpStatusException {
/* 135 */     if (this.model != null) {
/* 136 */       SnmpOid snmpOid = new SnmpOid(this.l.toArray());
/* 137 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 138 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class.getName(), "checkCurrentOid", "Checking access for : " + snmpOid);
/*     */       }
/*     */       
/* 141 */       this.model.checkAccess(this.version, this.principal, this.securityLevel, this.pduType, this.securityModel, this.contextName, snmpOid);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\AcmChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */