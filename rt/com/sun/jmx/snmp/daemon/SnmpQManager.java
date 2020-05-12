/*     */ package com.sun.jmx.snmp.daemon;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SnmpQManager
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2163709017015248264L;
/*     */   private SendQ newq;
/*     */   private WaitQ waitq;
/*  31 */   private ThreadGroup queueThreadGroup = null;
/*  32 */   private Thread requestQThread = null;
/*  33 */   private Thread timerQThread = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SnmpQManager() {
/*  39 */     this.newq = new SendQ(20, 5);
/*  40 */     this.waitq = new WaitQ(20, 5);
/*     */     
/*  42 */     this.queueThreadGroup = new ThreadGroup("Qmanager Thread Group");
/*     */ 
/*     */     
/*  45 */     startQThreads();
/*     */   }
/*     */   
/*     */   public void startQThreads() {
/*  49 */     if (this.timerQThread == null || !this.timerQThread.isAlive()) {
/*  50 */       this.timerQThread = new SnmpTimerServer(this.queueThreadGroup, this);
/*     */     }
/*  52 */     if (this.requestQThread == null || !this.requestQThread.isAlive()) {
/*  53 */       this.requestQThread = new SnmpSendServer(this.queueThreadGroup, this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopQThreads() {
/*  59 */     ((SnmpTimerServer)this.timerQThread).isBeingDestroyed = true;
/*  60 */     this.waitq.isBeingDestroyed = true;
/*  61 */     ((SnmpSendServer)this.requestQThread).isBeingDestroyed = true;
/*  62 */     this.newq.isBeingDestroyed = true;
/*     */     
/*  64 */     if (this.timerQThread != null && this.timerQThread.isAlive() == true) {
/*  65 */       ((SnmpTimerServer)this.timerQThread).stopTimerServer();
/*     */     }
/*  67 */     this.waitq = null;
/*  68 */     this.timerQThread = null;
/*     */     
/*  70 */     if (this.requestQThread != null && this.requestQThread.isAlive() == true) {
/*  71 */       ((SnmpSendServer)this.requestQThread).stopSendServer();
/*     */     }
/*  73 */     this.newq = null;
/*  74 */     this.requestQThread = null;
/*     */   }
/*     */   
/*     */   public void addRequest(SnmpInformRequest paramSnmpInformRequest) {
/*  78 */     this.newq.addRequest(paramSnmpInformRequest);
/*     */   }
/*     */   
/*     */   public void addWaiting(SnmpInformRequest paramSnmpInformRequest) {
/*  82 */     this.waitq.addWaiting(paramSnmpInformRequest);
/*     */   }
/*     */   
/*     */   public Vector<SnmpInformRequest> getAllOutstandingRequest(long paramLong) {
/*  86 */     return this.newq.getAllOutstandingRequest(paramLong);
/*     */   }
/*     */   
/*     */   public SnmpInformRequest getTimeoutRequests() {
/*  90 */     return this.waitq.getTimeoutRequests();
/*     */   }
/*     */   
/*     */   public void removeRequest(SnmpInformRequest paramSnmpInformRequest) {
/*  94 */     this.newq.removeElement(paramSnmpInformRequest);
/*  95 */     this.waitq.removeElement(paramSnmpInformRequest);
/*     */   }
/*     */ 
/*     */   
/*     */   public SnmpInformRequest removeRequest(long paramLong) {
/*     */     SnmpInformRequest snmpInformRequest;
/* 101 */     if ((snmpInformRequest = this.newq.removeRequest(paramLong)) == null) {
/* 102 */       snmpInformRequest = this.waitq.removeRequest(paramLong);
/*     */     }
/* 104 */     return snmpInformRequest;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpQManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */