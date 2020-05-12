/*     */ package com.sun.jmx.snmp.daemon;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class ClientHandler
/*     */   implements Runnable
/*     */ {
/*     */   protected CommunicatorServer adaptorServer;
/*     */   protected int requestId;
/*     */   protected MBeanServer mbs;
/*     */   protected ObjectName objectName;
/*     */   protected Thread thread;
/*     */   protected boolean interruptCalled;
/*     */   protected String dbgTag;
/*     */   
/*     */   public ClientHandler(CommunicatorServer paramCommunicatorServer, int paramInt, MBeanServer paramMBeanServer, ObjectName paramObjectName) {
/* 122 */     this.adaptorServer = null;
/* 123 */     this.requestId = -1;
/* 124 */     this.mbs = null;
/* 125 */     this.objectName = null;
/* 126 */     this.thread = null;
/* 127 */     this.interruptCalled = false;
/* 128 */     this.dbgTag = null; this.adaptorServer = paramCommunicatorServer; this.requestId = paramInt; this.mbs = paramMBeanServer; this.objectName = paramObjectName;
/*     */     this.interruptCalled = false;
/*     */     this.dbgTag = makeDebugTag();
/* 131 */     this.thread = createThread(this); } protected String makeDebugTag() { return "ClientHandler[" + this.adaptorServer.getProtocol() + ":" + this.adaptorServer.getPort() + "][" + this.requestId + "]"; }
/*     */ 
/*     */   
/*     */   Thread createThread(Runnable paramRunnable) {
/*     */     return new Thread(this);
/*     */   }
/*     */   
/*     */   public void interrupt() {
/*     */     JmxProperties.SNMP_ADAPTOR_LOGGER.entering(this.dbgTag, "interrupt");
/*     */     this.interruptCalled = true;
/*     */     if (this.thread != null)
/*     */       this.thread.interrupt(); 
/*     */     JmxProperties.SNMP_ADAPTOR_LOGGER.exiting(this.dbgTag, "interrupt");
/*     */   }
/*     */   
/*     */   public void join() {
/*     */     if (this.thread != null)
/*     */       try {
/*     */         this.thread.join();
/*     */       } catch (InterruptedException interruptedException) {} 
/*     */   }
/*     */   
/*     */   public void run() {
/*     */     try {
/*     */       this.adaptorServer.notifyClientHandlerCreated(this);
/*     */       doRun();
/*     */     } finally {
/*     */       this.adaptorServer.notifyClientHandlerDeleted(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void doRun();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\ClientHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */