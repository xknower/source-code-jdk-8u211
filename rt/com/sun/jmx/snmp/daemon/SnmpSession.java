/*     */ package com.sun.jmx.snmp.daemon;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.snmp.SnmpDefinitions;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpVarBindList;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Stack;
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
/*     */ class SnmpSession
/*     */   implements SnmpDefinitions, Runnable
/*     */ {
/*     */   protected transient SnmpAdaptorServer adaptor;
/*  53 */   protected transient SnmpSocket informSocket = null;
/*     */ 
/*     */ 
/*     */   
/*  57 */   private transient Hashtable<SnmpInformRequest, SnmpInformRequest> informRequestList = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private transient Stack<SnmpInformRequest> informRespq = new Stack<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private transient Thread myThread = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient SnmpInformRequest syncInformReq;
/*     */ 
/*     */   
/*  78 */   SnmpQManager snmpQman = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBeingCancelled = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpSession(SnmpAdaptorServer paramSnmpAdaptorServer) throws SocketException {
/*  91 */     this.adaptor = paramSnmpAdaptorServer;
/*  92 */     this.snmpQman = new SnmpQManager();
/*  93 */     SnmpResponseHandler snmpResponseHandler = new SnmpResponseHandler(paramSnmpAdaptorServer, this.snmpQman);
/*  94 */     initialize(paramSnmpAdaptorServer, snmpResponseHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpSession() throws SocketException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void initialize(SnmpAdaptorServer paramSnmpAdaptorServer, SnmpResponseHandler paramSnmpResponseHandler) throws SocketException {
/* 111 */     this.informSocket = new SnmpSocket(paramSnmpResponseHandler, paramSnmpAdaptorServer.getAddress(), paramSnmpAdaptorServer.getBufferSize().intValue());
/*     */     
/* 113 */     this.myThread = new Thread(this, "SnmpSession");
/* 114 */     this.myThread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized boolean isSessionActive() {
/* 123 */     return (this.adaptor.isActive() && this.myThread != null && this.myThread.isAlive());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SnmpSocket getSocket() {
/* 131 */     return this.informSocket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SnmpQManager getSnmpQManager() {
/* 139 */     return this.snmpQman;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized boolean syncInProgress() {
/* 147 */     return (this.syncInformReq != null);
/*     */   }
/*     */   
/*     */   private synchronized void setSyncMode(SnmpInformRequest paramSnmpInformRequest) {
/* 151 */     this.syncInformReq = paramSnmpInformRequest;
/*     */   }
/*     */   
/*     */   private synchronized void resetSyncMode() {
/* 155 */     if (this.syncInformReq == null)
/*     */       return; 
/* 157 */     this.syncInformReq = null;
/* 158 */     if (thisSessionContext())
/*     */       return; 
/* 160 */     notifyAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean thisSessionContext() {
/* 171 */     return (Thread.currentThread() == this.myThread);
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
/*     */   SnmpInformRequest makeAsyncRequest(InetAddress paramInetAddress, String paramString, SnmpInformHandler paramSnmpInformHandler, SnmpVarBindList paramSnmpVarBindList, int paramInt) throws SnmpStatusException {
/* 188 */     if (!isSessionActive()) {
/* 189 */       throw new SnmpStatusException("SNMP adaptor server not ONLINE");
/*     */     }
/* 191 */     SnmpInformRequest snmpInformRequest = new SnmpInformRequest(this, this.adaptor, paramInetAddress, paramString, paramInt, paramSnmpInformHandler);
/* 192 */     snmpInformRequest.start(paramSnmpVarBindList);
/* 193 */     return snmpInformRequest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void waitForResponse(SnmpInformRequest paramSnmpInformRequest, long paramLong) {
/*     */     long l;
/* 203 */     if (!paramSnmpInformRequest.inProgress())
/*     */       return; 
/* 205 */     setSyncMode(paramSnmpInformRequest);
/* 206 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 207 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSession.class.getName(), "waitForResponse", "Session switching to sync mode for inform request " + paramSnmpInformRequest
/* 208 */           .getRequestId());
/*     */     }
/*     */     
/* 211 */     if (paramLong <= 0L) {
/* 212 */       l = System.currentTimeMillis() + 6000000L;
/*     */     } else {
/* 214 */       l = System.currentTimeMillis() + paramLong;
/*     */     } 
/* 216 */     while (paramSnmpInformRequest.inProgress() || syncInProgress()) {
/* 217 */       paramLong = l - System.currentTimeMillis();
/* 218 */       if (paramLong <= 0L)
/*     */         break; 
/* 220 */       synchronized (this) {
/* 221 */         if (!this.informRespq.removeElement(paramSnmpInformRequest)) {
/*     */           try {
/* 223 */             wait(paramLong);
/* 224 */           } catch (InterruptedException interruptedException) {}
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */       try {
/* 230 */         processResponse(paramSnmpInformRequest);
/* 231 */       } catch (Exception exception) {
/* 232 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 233 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSession.class.getName(), "waitForResponse", "Got unexpected exception", exception);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 238 */     resetSyncMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 248 */     this.myThread = Thread.currentThread();
/* 249 */     this.myThread.setPriority(5);
/*     */     
/* 251 */     SnmpInformRequest snmpInformRequest = null;
/* 252 */     while (this.myThread != null) {
/*     */       try {
/* 254 */         snmpInformRequest = nextResponse();
/* 255 */         if (snmpInformRequest != null) {
/* 256 */           processResponse(snmpInformRequest);
/*     */         }
/* 258 */       } catch (ThreadDeath threadDeath) {
/* 259 */         this.myThread = null;
/* 260 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 261 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSession.class.getName(), "run", "ThreadDeath, session thread unexpectedly shutting down");
/*     */         }
/*     */         
/* 264 */         throw threadDeath;
/*     */       } 
/*     */     } 
/* 267 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 268 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSession.class.getName(), "run", "Session thread shutting down");
/*     */     }
/*     */     
/* 271 */     this.myThread = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void processResponse(SnmpInformRequest paramSnmpInformRequest) {
/* 276 */     while (paramSnmpInformRequest != null && this.myThread != null) {
/*     */       try {
/* 278 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 279 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSession.class.getName(), "processResponse", "Processing response to req = " + paramSnmpInformRequest
/* 280 */               .getRequestId());
/*     */         }
/* 282 */         paramSnmpInformRequest.processResponse();
/* 283 */         paramSnmpInformRequest = null;
/* 284 */       } catch (Exception exception) {
/* 285 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 286 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSession.class.getName(), "processResponse", "Got unexpected exception", exception);
/*     */         }
/*     */         
/* 289 */         paramSnmpInformRequest = null;
/* 290 */       } catch (OutOfMemoryError outOfMemoryError) {
/* 291 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 292 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSession.class.getName(), "processResponse", "Out of memory error in session thread", outOfMemoryError);
/*     */         }
/*     */         
/* 295 */         Thread.yield();
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
/*     */   synchronized void addInformRequest(SnmpInformRequest paramSnmpInformRequest) throws SnmpStatusException {
/* 313 */     if (!isSessionActive()) {
/* 314 */       throw new SnmpStatusException("SNMP adaptor is not ONLINE or session is dead...");
/*     */     }
/* 316 */     this.informRequestList.put(paramSnmpInformRequest, paramSnmpInformRequest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void removeInformRequest(SnmpInformRequest paramSnmpInformRequest) {
/* 326 */     if (!this.isBeingCancelled) {
/* 327 */       this.informRequestList.remove(paramSnmpInformRequest);
/*     */     }
/* 329 */     if (this.syncInformReq != null && this.syncInformReq == paramSnmpInformRequest) {
/* 330 */       resetSyncMode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cancelAllRequests() {
/*     */     SnmpInformRequest[] arrayOfSnmpInformRequest;
/* 340 */     synchronized (this) {
/*     */       
/* 342 */       if (this.informRequestList.isEmpty()) {
/*     */         return;
/*     */       }
/*     */       
/* 346 */       this.isBeingCancelled = true;
/*     */       
/* 348 */       arrayOfSnmpInformRequest = new SnmpInformRequest[this.informRequestList.size()];
/* 349 */       Iterator<SnmpInformRequest> iterator = this.informRequestList.values().iterator();
/* 350 */       byte b1 = 0;
/* 351 */       while (iterator.hasNext()) {
/* 352 */         SnmpInformRequest snmpInformRequest = iterator.next();
/* 353 */         arrayOfSnmpInformRequest[b1++] = snmpInformRequest;
/* 354 */         iterator.remove();
/*     */       } 
/* 356 */       this.informRequestList.clear();
/*     */     } 
/*     */     
/* 359 */     for (byte b = 0; b < arrayOfSnmpInformRequest.length; b++) {
/* 360 */       arrayOfSnmpInformRequest[b].cancelRequest();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addResponse(SnmpInformRequest paramSnmpInformRequest) {
/* 371 */     SnmpInformRequest snmpInformRequest = paramSnmpInformRequest;
/* 372 */     if (isSessionActive()) {
/* 373 */       synchronized (this) {
/* 374 */         this.informRespq.push(paramSnmpInformRequest);
/* 375 */         notifyAll();
/*     */       }
/*     */     
/* 378 */     } else if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 379 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSession.class.getName(), "addResponse", "Adaptor not ONLINE or session thread dead, so inform response is dropped..." + paramSnmpInformRequest
/* 380 */           .getRequestId());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized SnmpInformRequest nextResponse() {
/* 387 */     if (this.informRespq.isEmpty()) {
/*     */       try {
/* 389 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 390 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSession.class.getName(), "nextResponse", "Blocking for response");
/*     */         }
/*     */         
/* 393 */         wait();
/* 394 */       } catch (InterruptedException interruptedException) {}
/*     */     }
/*     */     
/* 397 */     if (this.informRespq.isEmpty())
/* 398 */       return null; 
/* 399 */     SnmpInformRequest snmpInformRequest = this.informRespq.firstElement();
/* 400 */     this.informRespq.removeElementAt(0);
/* 401 */     return snmpInformRequest;
/*     */   }
/*     */   
/*     */   private synchronized void cancelAllResponses() {
/* 405 */     if (this.informRespq != null) {
/* 406 */       this.syncInformReq = null;
/* 407 */       this.informRespq.removeAllElements();
/* 408 */       notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void destroySession() {
/* 418 */     cancelAllRequests();
/* 419 */     cancelAllResponses();
/* 420 */     synchronized (this) {
/* 421 */       this.informSocket.close();
/* 422 */       this.informSocket = null;
/*     */     } 
/* 424 */     this.snmpQman.stopQThreads();
/* 425 */     this.snmpQman = null;
/* 426 */     killSessionThread();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void killSessionThread() {
/* 435 */     if (this.myThread != null && this.myThread.isAlive()) {
/* 436 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 437 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSession.class.getName(), "killSessionThread", "Destroying session");
/*     */       }
/*     */       
/* 440 */       if (!thisSessionContext()) {
/* 441 */         this.myThread = null;
/* 442 */         notifyAll();
/*     */       } else {
/* 444 */         this.myThread = null;
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
/*     */   protected void finalize() {
/* 458 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 459 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSession.class.getName(), "finalize", "Shutting all servers");
/*     */     }
/*     */ 
/*     */     
/* 463 */     if (this.informRespq != null)
/* 464 */       this.informRespq.removeAllElements(); 
/* 465 */     this.informRespq = null;
/* 466 */     if (this.informSocket != null)
/* 467 */       this.informSocket.close(); 
/* 468 */     this.informSocket = null;
/*     */     
/* 470 */     this.snmpQman = null;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpSession.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */