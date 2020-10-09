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
/*     */ class SendQ
/*     */   extends Vector<SnmpInformRequest>
/*     */ {
/*     */   boolean isBeingDestroyed;
/*     */   
/*     */   SendQ(int paramInt1, int paramInt2) {
/* 116 */     super(paramInt1, paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     this.isBeingDestroyed = false;
/*     */   }
/*     */   
/*     */   private synchronized void notifyClients() {
/*     */     notifyAll();
/*     */   }
/*     */   
/*     */   public synchronized void addRequest(SnmpInformRequest paramSnmpInformRequest) {
/*     */     long l = paramSnmpInformRequest.getAbsNextPollTime();
/*     */     int i;
/*     */     for (i = size(); i > 0 && l >= getRequestAt(i - 1).getAbsNextPollTime(); i--);
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
/*     */         l = snmpInformRequest.getAbsNextPollTime() - l1;
/*     */         if (l <= 0L)
/*     */           return true; 
/*     */       } 
/*     */       waitOnThisQueue(l);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized Vector<SnmpInformRequest> getAllOutstandingRequest(long paramLong) {
/*     */     Vector<SnmpInformRequest> vector = new Vector();
/*     */     while (waitUntilReady() == true) {
/*     */       long l = System.currentTimeMillis() + paramLong;
/*     */       for (int i = size(); i > 0; i--) {
/*     */         SnmpInformRequest snmpInformRequest = getRequestAt(i - 1);
/*     */         if (snmpInformRequest.getAbsNextPollTime() > l)
/*     */           break; 
/*     */         vector.addElement(snmpInformRequest);
/*     */       } 
/*     */       if (!vector.isEmpty()) {
/*     */         this.elementCount -= vector.size();
/*     */         return vector;
/*     */       } 
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public synchronized void waitOnThisQueue(long paramLong) {
/*     */     if (paramLong == 0L && !isEmpty() && JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST))
/*     */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpQManager.class.getName(), "waitOnThisQueue", "[" + Thread.currentThread().toString() + "]:Fatal BUG :: Blocking on newq permenantly. But size = " + size()); 
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


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SendQ.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */