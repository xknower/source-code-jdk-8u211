/*     */ package com.sun.jmx.snmp.daemon;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
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
/*     */ final class SnmpSendServer
/*     */   extends Thread
/*     */ {
/*  29 */   private int intervalRange = 5000;
/*     */   
/*     */   private Vector<SnmpInformRequest> readyPool;
/*  32 */   SnmpQManager snmpq = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isBeingDestroyed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpSendServer(ThreadGroup paramThreadGroup, SnmpQManager paramSnmpQManager) {
/*  43 */     super(paramThreadGroup, "SnmpSendServer");
/*  44 */     this.snmpq = paramSnmpQManager;
/*  45 */     start();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void stopSendServer() {
/*  50 */     if (isAlive()) {
/*  51 */       interrupt();
/*     */ 
/*     */       
/*     */       try {
/*  55 */         join();
/*  56 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  64 */     Thread.currentThread().setPriority(5);
/*     */     
/*  66 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  67 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSendServer.class.getName(), "run", "Thread Started");
/*     */     }
/*     */     
/*     */     while (true) {
/*     */       try {
/*  72 */         prepareAndSendRequest();
/*  73 */         if (this.isBeingDestroyed == true) {
/*     */           break;
/*     */         }
/*  76 */       } catch (Exception exception) {
/*  77 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  78 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSendServer.class.getName(), "run", "Exception in send server", exception);
/*     */         }
/*     */       }
/*  81 */       catch (ThreadDeath threadDeath) {
/*     */ 
/*     */         
/*  84 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  85 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSendServer.class.getName(), "run", "Exiting... Fatal error");
/*     */         }
/*     */         
/*  88 */         throw threadDeath;
/*  89 */       } catch (OutOfMemoryError outOfMemoryError) {
/*  90 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  91 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSendServer.class.getName(), "run", "Out of memory");
/*     */         }
/*     */       }
/*  94 */       catch (Error error) {
/*  95 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  96 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSendServer.class.getName(), "run", "Got unexpected error", error);
/*     */         }
/*     */         
/*  99 */         throw error;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void prepareAndSendRequest() {
/* 106 */     if (this.readyPool == null || this.readyPool.isEmpty()) {
/*     */       
/* 108 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 109 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSendServer.class.getName(), "prepareAndSendRequest", "Blocking for inform requests");
/*     */       }
/*     */       
/* 112 */       this.readyPool = this.snmpq.getAllOutstandingRequest(this.intervalRange);
/* 113 */       if (this.isBeingDestroyed == true) {
/*     */         return;
/*     */       }
/* 116 */     } else if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 117 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSendServer.class.getName(), "prepareAndSendRequest", "Inform requests from a previous block left unprocessed. Will try again");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 122 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 123 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSendServer.class.getName(), "prepareAndSendRequest", "List of inform requests to send : " + 
/* 124 */           reqListToString(this.readyPool));
/*     */     }
/*     */     
/* 127 */     synchronized (this) {
/* 128 */       if (this.readyPool.size() < 2) {
/*     */         
/* 130 */         fireRequestList(this.readyPool);
/*     */         
/*     */         return;
/*     */       } 
/* 134 */       while (!this.readyPool.isEmpty()) {
/* 135 */         SnmpInformRequest snmpInformRequest = this.readyPool.lastElement();
/* 136 */         if (snmpInformRequest != null && snmpInformRequest.inProgress()) {
/* 137 */           fireRequest(snmpInformRequest);
/*     */         }
/* 139 */         this.readyPool.removeElementAt(this.readyPool.size() - 1);
/*     */       } 
/* 141 */       this.readyPool.removeAllElements();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireRequest(SnmpInformRequest paramSnmpInformRequest) {
/* 149 */     if (paramSnmpInformRequest != null && paramSnmpInformRequest.inProgress()) {
/* 150 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 151 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSendServer.class.getName(), "fireRequest", "Firing inform request directly. -> " + paramSnmpInformRequest
/* 152 */             .getRequestId());
/*     */       }
/* 154 */       paramSnmpInformRequest.action();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fireRequestList(Vector<SnmpInformRequest> paramVector) {
/* 160 */     while (!paramVector.isEmpty()) {
/* 161 */       SnmpInformRequest snmpInformRequest = paramVector.lastElement();
/* 162 */       if (snmpInformRequest != null && snmpInformRequest.inProgress())
/* 163 */         fireRequest(snmpInformRequest); 
/* 164 */       paramVector.removeElementAt(paramVector.size() - 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private final String reqListToString(Vector<SnmpInformRequest> paramVector) {
/* 169 */     StringBuilder stringBuilder = new StringBuilder(paramVector.size() * 100);
/*     */     
/* 171 */     Enumeration<SnmpInformRequest> enumeration = paramVector.elements();
/* 172 */     while (enumeration.hasMoreElements()) {
/* 173 */       SnmpInformRequest snmpInformRequest = enumeration.nextElement();
/* 174 */       stringBuilder.append("InformRequestId -> ");
/* 175 */       stringBuilder.append(snmpInformRequest.getRequestId());
/* 176 */       stringBuilder.append(" / Destination -> ");
/* 177 */       stringBuilder.append(snmpInformRequest.getAddress());
/* 178 */       stringBuilder.append(". ");
/*     */     } 
/* 180 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpSendServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */