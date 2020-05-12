/*     */ package com.sun.jmx.snmp.daemon;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WaitQ
/*     */   extends Vector<SnmpInformRequest>
/*     */ {
/*     */   boolean isBeingDestroyed;
/*     */   
/*     */   WaitQ(int paramInt1, int paramInt2) {
/* 224 */     super(paramInt1, paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 308 */     this.isBeingDestroyed = false;
/*     */   }
/*     */   
/*     */   public synchronized void addWaiting(SnmpInformRequest paramSnmpInformRequest) {
/*     */     long l = paramSnmpInformRequest.getAbsMaxTimeToWait();
/*     */     int i;
/*     */     for (i = size(); i > 0 && l >= getRequestAt(i - 1).getAbsMaxTimeToWait(); i--);
/*     */     if (i == size()) {
/*     */       addElement(paramSnmpInformRequest);
/*     */       notifyClients();
/*     */     } else {
/*     */       insertElementAt(paramSnmpInformRequest, i);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized boolean waitUntilReady() {
/*     */     while (true) {
/*     */       if (this.isBeingDestroyed == true)
/*     */         return false; 
/*     */       long l = 0L;
/*     */       if (!isEmpty()) {
/*     */         long l1 = System.currentTimeMillis();
/*     */         SnmpInformRequest snmpInformRequest = lastElement();
/*     */         l = snmpInformRequest.getAbsMaxTimeToWait() - l1;
/*     */         if (l <= 0L)
/*     */           return true; 
/*     */       } 
/*     */       waitOnThisQueue(l);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized SnmpInformRequest getTimeoutRequests() {
/*     */     if (waitUntilReady() == true) {
/*     */       SnmpInformRequest snmpInformRequest = lastElement();
/*     */       this.elementCount--;
/*     */       return snmpInformRequest;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   private synchronized void notifyClients() {
/*     */     notifyAll();
/*     */   }
/*     */   
/*     */   public synchronized void waitOnThisQueue(long paramLong) {
/*     */     if (paramLong == 0L && !isEmpty() && JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST))
/*     */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpQManager.class.getName(), "waitOnThisQueue", "[" + Thread.currentThread().toString() + "]:Fatal BUG :: Blocking on waitq permenantly. But size = " + size()); 
/*     */     try {
/*     */       wait(paramLong);
/*     */     } catch (InterruptedException interruptedException) {}
/*     */   }
/*     */   
/*     */   public SnmpInformRequest getRequestAt(int paramInt) {
/*     */     return elementAt(paramInt);
/*     */   }
/*     */   
/*     */   public synchronized SnmpInformRequest removeRequest(long paramLong) {
/*     */     int i = size();
/*     */     for (byte b = 0; b < i; b++) {
/*     */       SnmpInformRequest snmpInformRequest = getRequestAt(b);
/*     */       if (paramLong == snmpInformRequest.getRequestId()) {
/*     */         removeElementAt(b);
/*     */         return snmpInformRequest;
/*     */       } 
/*     */     } 
/*     */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\WaitQ.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */