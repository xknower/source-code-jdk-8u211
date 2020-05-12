/*     */ package com.sun.jmx.snmp.daemon;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.snmp.SnmpDefinitions;
/*     */ import com.sun.jmx.snmp.SnmpEngine;
/*     */ import com.sun.jmx.snmp.SnmpPdu;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpVarBind;
/*     */ import com.sun.jmx.snmp.ThreadContext;
/*     */ import com.sun.jmx.snmp.agent.SnmpMibAgent;
/*     */ import com.sun.jmx.snmp.agent.SnmpMibRequest;
/*     */ import com.sun.jmx.snmp.internal.SnmpIncomingRequest;
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
/*     */ class SnmpSubRequestHandler
/*     */   implements SnmpDefinitions, Runnable
/*     */ {
/*  54 */   protected SnmpIncomingRequest incRequest = null;
/*  55 */   protected SnmpEngine engine = null;
/*     */   protected int version;
/*     */   protected int type;
/*     */   protected SnmpMibAgent agent;
/*     */   protected int errorStatus;
/*     */   protected int errorIndex;
/*     */   
/*     */   protected SnmpSubRequestHandler(SnmpEngine paramSnmpEngine, SnmpIncomingRequest paramSnmpIncomingRequest, SnmpMibAgent paramSnmpMibAgent, SnmpPdu paramSnmpPdu) {
/*  63 */     this(paramSnmpMibAgent, paramSnmpPdu);
/*  64 */     init(paramSnmpEngine, paramSnmpIncomingRequest);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector<SnmpVarBind> varBind;
/*     */   protected int[] translation;
/*     */   protected Object data;
/*     */   private SnmpMibRequest mibRequest;
/*     */   private SnmpPdu reqPdu;
/*     */   
/*     */   protected SnmpSubRequestHandler(SnmpEngine paramSnmpEngine, SnmpIncomingRequest paramSnmpIncomingRequest, SnmpMibAgent paramSnmpMibAgent, SnmpPdu paramSnmpPdu, boolean paramBoolean) {
/*  75 */     this(paramSnmpMibAgent, paramSnmpPdu, paramBoolean);
/*  76 */     init(paramSnmpEngine, paramSnmpIncomingRequest);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpSubRequestHandler(SnmpMibAgent paramSnmpMibAgent, SnmpPdu paramSnmpPdu, boolean paramBoolean) {
/* 109 */     this(paramSnmpMibAgent, paramSnmpPdu);
/*     */ 
/*     */ 
/*     */     
/* 113 */     int i = this.translation.length;
/* 114 */     SnmpVarBind[] arrayOfSnmpVarBind = paramSnmpPdu.varBindList;
/* 115 */     for (byte b = 0; b < i; b++) {
/* 116 */       this.translation[b] = b;
/* 117 */       ((NonSyncVector<SnmpVarBind>)this.varBind).addNonSyncElement(arrayOfSnmpVarBind[b]);
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
/*     */   SnmpMibRequest createMibRequest(Vector<SnmpVarBind> paramVector, int paramInt, Object paramObject) {
/* 129 */     if (this.type == 163 && this.mibRequest != null) {
/* 130 */       return this.mibRequest;
/*     */     }
/*     */ 
/*     */     
/* 134 */     SnmpMibRequest snmpMibRequest = null;
/* 135 */     if (this.incRequest != null) {
/* 136 */       snmpMibRequest = SnmpMibAgent.newMibRequest(this.engine, this.reqPdu, paramVector, paramInt, paramObject, this.incRequest
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 141 */           .getPrincipal(), this.incRequest
/* 142 */           .getSecurityLevel(), this.incRequest
/* 143 */           .getSecurityModel(), this.incRequest
/* 144 */           .getContextName(), this.incRequest
/* 145 */           .getAccessContext());
/*     */     } else {
/* 147 */       snmpMibRequest = SnmpMibAgent.newMibRequest(this.reqPdu, paramVector, paramInt, paramObject);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     if (this.type == 253) {
/* 156 */       this.mibRequest = snmpMibRequest;
/*     */     }
/* 158 */     return snmpMibRequest;
/*     */   }
/*     */   
/*     */   void setUserData(Object paramObject) {
/* 162 */     this.data = paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 169 */       ThreadContext threadContext = ThreadContext.push("SnmpUserData", this.data);
/*     */       try {
/* 171 */         switch (this.type) {
/*     */ 
/*     */           
/*     */           case 160:
/* 175 */             if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 176 */               JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(), "run", "[" + 
/* 177 */                   Thread.currentThread() + "]:get operation on " + this.agent
/* 178 */                   .getMibName());
/*     */             }
/*     */             
/* 181 */             this.agent.get(createMibRequest(this.varBind, this.version, this.data));
/*     */             break;
/*     */           
/*     */           case 161:
/* 185 */             if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 186 */               JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(), "run", "[" + 
/* 187 */                   Thread.currentThread() + "]:getNext operation on " + this.agent
/* 188 */                   .getMibName());
/*     */             }
/*     */             
/* 191 */             this.agent.getNext(createMibRequest(this.varBind, this.version, this.data));
/*     */             break;
/*     */           
/*     */           case 163:
/* 195 */             if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 196 */               JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(), "run", "[" + 
/* 197 */                   Thread.currentThread() + "]:set operation on " + this.agent
/* 198 */                   .getMibName());
/*     */             }
/* 200 */             this.agent.set(createMibRequest(this.varBind, this.version, this.data));
/*     */             break;
/*     */           
/*     */           case 253:
/* 204 */             if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 205 */               JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(), "run", "[" + 
/* 206 */                   Thread.currentThread() + "]:check operation on " + this.agent
/* 207 */                   .getMibName());
/*     */             }
/* 209 */             this.agent.check(createMibRequest(this.varBind, this.version, this.data));
/*     */             break;
/*     */           
/*     */           default:
/* 213 */             if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 214 */               JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubRequestHandler.class.getName(), "run", "[" + 
/* 215 */                   Thread.currentThread() + "]:unknown operation (" + this.type + ") on " + this.agent
/*     */                   
/* 217 */                   .getMibName());
/*     */             }
/* 219 */             this.errorStatus = 5;
/* 220 */             this.errorIndex = 1;
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } finally {
/* 226 */         ThreadContext.restore(threadContext);
/*     */       } 
/* 228 */     } catch (SnmpStatusException snmpStatusException) {
/* 229 */       this.errorStatus = snmpStatusException.getStatus();
/* 230 */       this.errorIndex = snmpStatusException.getErrorIndex();
/* 231 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 232 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubRequestHandler.class.getName(), "run", "[" + 
/* 233 */             Thread.currentThread() + "]:an Snmp error occurred during the operation", snmpStatusException);
/*     */       
/*     */       }
/*     */     }
/* 237 */     catch (Exception exception) {
/* 238 */       this.errorStatus = 5;
/* 239 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 240 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubRequestHandler.class.getName(), "run", "[" + 
/* 241 */             Thread.currentThread() + "]:a generic error occurred during the operation", exception);
/*     */       }
/*     */     } 
/*     */     
/* 245 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 246 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(), "run", "[" + 
/* 247 */           Thread.currentThread() + "]:operation completed");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int mapErrorStatusToV1(int paramInt1, int paramInt2) {
/* 285 */     if (paramInt1 == 0) {
/* 286 */       return 0;
/*     */     }
/* 288 */     if (paramInt1 == 5) {
/* 289 */       return 5;
/*     */     }
/* 291 */     if (paramInt1 == 2) {
/* 292 */       return 2;
/*     */     }
/* 294 */     if (paramInt1 == 224 || paramInt1 == 225 || paramInt1 == 6 || paramInt1 == 18 || paramInt1 == 16)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 300 */       return 2;
/*     */     }
/* 302 */     if (paramInt1 == 16 || paramInt1 == 17) {
/*     */ 
/*     */ 
/*     */       
/* 306 */       if (paramInt2 == 253) {
/* 307 */         return 4;
/*     */       }
/* 309 */       return 2;
/*     */     } 
/* 311 */     if (paramInt1 == 11)
/*     */     {
/* 313 */       return 2;
/*     */     }
/* 315 */     if (paramInt1 == 7 || paramInt1 == 8 || paramInt1 == 9 || paramInt1 == 10 || paramInt1 == 8 || paramInt1 == 12) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 323 */       if (paramInt2 == 163 || paramInt2 == 253)
/*     */       {
/* 325 */         return 3;
/*     */       }
/* 327 */       return 2;
/*     */     } 
/* 329 */     if (paramInt1 == 13 || paramInt1 == 14 || paramInt1 == 15)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 335 */       return 5;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 341 */     if (paramInt1 == 1) {
/* 342 */       return 1;
/*     */     }
/* 344 */     if (paramInt1 == 3 || paramInt1 == 4) {
/*     */       
/* 346 */       if (paramInt2 == 163 || paramInt2 == 253)
/*     */       {
/* 348 */         return paramInt1;
/*     */       }
/* 350 */       return 2;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 356 */     return 5;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int mapErrorStatusToV2(int paramInt1, int paramInt2) {
/* 399 */     if (paramInt1 == 0) {
/* 400 */       return 0;
/*     */     }
/* 402 */     if (paramInt1 == 5) {
/* 403 */       return 5;
/*     */     }
/* 405 */     if (paramInt1 == 1) {
/* 406 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 411 */     if (paramInt2 != 163 && paramInt2 != 253) {
/*     */       
/* 413 */       if (paramInt1 == 16) {
/* 414 */         return paramInt1;
/*     */       }
/* 416 */       return 5;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 428 */     if (paramInt1 == 2) {
/* 429 */       return 6;
/*     */     }
/*     */     
/* 432 */     if (paramInt1 == 4) {
/* 433 */       return 17;
/*     */     }
/*     */     
/* 436 */     if (paramInt1 == 3) {
/* 437 */       return 10;
/*     */     }
/*     */     
/* 440 */     if (paramInt1 == 6 || paramInt1 == 18 || paramInt1 == 16 || paramInt1 == 17 || paramInt1 == 11 || paramInt1 == 7 || paramInt1 == 8 || paramInt1 == 9 || paramInt1 == 10 || paramInt1 == 8 || paramInt1 == 12 || paramInt1 == 13 || paramInt1 == 14 || paramInt1 == 15)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 454 */       return paramInt1;
/*     */     }
/*     */     
/* 457 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final int mapErrorStatus(int paramInt1, int paramInt2, int paramInt3) {
/* 463 */     if (paramInt1 == 0) {
/* 464 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 468 */     if (paramInt2 == 0)
/* 469 */       return mapErrorStatusToV1(paramInt1, paramInt3); 
/* 470 */     if (paramInt2 == 1 || paramInt2 == 3)
/*     */     {
/* 472 */       return mapErrorStatusToV2(paramInt1, paramInt3);
/*     */     }
/* 474 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getErrorStatus() {
/* 482 */     if (this.errorStatus == 0) {
/* 483 */       return 0;
/*     */     }
/* 485 */     return mapErrorStatus(this.errorStatus, this.version, this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getErrorIndex() {
/* 494 */     if (this.errorStatus == 0) {
/* 495 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 501 */     if (this.errorIndex == 0 || this.errorIndex == -1) {
/* 502 */       this.errorIndex = 1;
/*     */     }
/* 504 */     return this.translation[this.errorIndex - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateRequest(SnmpVarBind paramSnmpVarBind, int paramInt) {
/* 511 */     int i = this.varBind.size();
/* 512 */     this.translation[i] = paramInt;
/* 513 */     this.varBind.addElement(paramSnmpVarBind);
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
/*     */   protected void updateResult(SnmpVarBind[] paramArrayOfSnmpVarBind) {
/* 525 */     if (paramArrayOfSnmpVarBind == null)
/* 526 */       return;  int i = this.varBind.size();
/* 527 */     int j = paramArrayOfSnmpVarBind.length;
/* 528 */     for (byte b = 0; b < i; b++) {
/*     */ 
/*     */       
/* 531 */       int k = this.translation[b];
/* 532 */       if (k < j) {
/* 533 */         paramArrayOfSnmpVarBind[k] = ((NonSyncVector<SnmpVarBind>)this.varBind)
/* 534 */           .elementAtNonSync(b);
/*     */       }
/* 536 */       else if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 537 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubRequestHandler.class.getName(), "updateResult", "Position `" + k + "' is out of bound...");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(SnmpEngine paramSnmpEngine, SnmpIncomingRequest paramSnmpIncomingRequest) {
/* 546 */     this.incRequest = paramSnmpIncomingRequest;
/* 547 */     this.engine = paramSnmpEngine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpSubRequestHandler(SnmpMibAgent paramSnmpMibAgent, SnmpPdu paramSnmpPdu)
/*     */   {
/* 556 */     this.version = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 562 */     this.type = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 572 */     this.errorStatus = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 578 */     this.errorIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 601 */     this.mibRequest = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 607 */     this.reqPdu = null;
/*     */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER))
/*     */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(), "constructor", "creating instance for request " + String.valueOf(paramSnmpPdu.requestId)); 
/*     */     this.version = paramSnmpPdu.version;
/*     */     this.type = paramSnmpPdu.type;
/*     */     this.agent = paramSnmpMibAgent;
/*     */     this.reqPdu = paramSnmpPdu;
/*     */     int i = paramSnmpPdu.varBindList.length;
/*     */     this.translation = new int[i];
/*     */     this.varBind = new NonSyncVector<>(i); } class NonSyncVector<E> extends Vector<E> { public NonSyncVector(int param1Int) {
/* 617 */       super(param1Int);
/*     */     }
/*     */     
/*     */     final void addNonSyncElement(E param1E) {
/* 621 */       ensureCapacity(this.elementCount + 1);
/* 622 */       this.elementData[this.elementCount++] = param1E;
/*     */     }
/*     */ 
/*     */     
/*     */     final E elementAtNonSync(int param1Int) {
/* 627 */       return (E)this.elementData[param1Int];
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpSubRequestHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */