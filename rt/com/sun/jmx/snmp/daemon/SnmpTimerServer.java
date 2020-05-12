/*     */ package com.sun.jmx.snmp.daemon;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
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
/*     */ final class SnmpTimerServer
/*     */   extends Thread
/*     */ {
/*  23 */   private SnmpInformRequest req = null;
/*     */   
/*  25 */   SnmpQManager snmpq = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isBeingDestroyed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpTimerServer(ThreadGroup paramThreadGroup, SnmpQManager paramSnmpQManager) {
/*  36 */     super(paramThreadGroup, "SnmpTimerServer");
/*  37 */     setName("SnmpTimerServer");
/*  38 */     this.snmpq = paramSnmpQManager;
/*  39 */     start();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void stopTimerServer() {
/*  44 */     if (isAlive()) {
/*  45 */       interrupt();
/*     */ 
/*     */       
/*     */       try {
/*  49 */         join();
/*  50 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  57 */     Thread.currentThread().setPriority(5);
/*     */     
/*  59 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  60 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpTimerServer.class.getName(), "run", "Timer Thread started");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/*     */       try {
/*  67 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  68 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpTimerServer.class.getName(), "run", "Blocking for inform requests");
/*     */         }
/*     */         
/*  71 */         if (this.req == null) {
/*  72 */           this.req = this.snmpq.getTimeoutRequests();
/*     */         }
/*  74 */         if (this.req != null && this.req.inProgress()) {
/*  75 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  76 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpTimerServer.class.getName(), "run", "Handle timeout inform request " + this.req
/*  77 */                 .getRequestId());
/*     */           }
/*  79 */           this.req.action();
/*  80 */           this.req = null;
/*     */         } 
/*  82 */         if (this.isBeingDestroyed == true)
/*     */           break; 
/*  84 */       } catch (Exception exception) {
/*  85 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  86 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpTimerServer.class.getName(), "run", "Got unexpected exception", exception);
/*     */         }
/*     */       }
/*  89 */       catch (ThreadDeath threadDeath) {
/*  90 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  91 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpTimerServer.class.getName(), "run", "ThreadDeath, timer server unexpectedly shutting down", threadDeath);
/*     */         }
/*     */         
/*  94 */         throw threadDeath;
/*  95 */       } catch (OutOfMemoryError outOfMemoryError) {
/*  96 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  97 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpTimerServer.class.getName(), "run", "OutOfMemoryError", outOfMemoryError);
/*     */         }
/*     */         
/* 100 */         yield();
/* 101 */       } catch (Error error) {
/* 102 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST))
/* 103 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpTimerServer.class.getName(), "run", "Received Internal error", error); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpTimerServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */