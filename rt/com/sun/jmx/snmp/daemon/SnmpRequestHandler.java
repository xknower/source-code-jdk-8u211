/*      */ package com.sun.jmx.snmp.daemon;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import com.sun.jmx.snmp.InetAddressAcl;
/*      */ import com.sun.jmx.snmp.SnmpDefinitions;
/*      */ import com.sun.jmx.snmp.SnmpMessage;
/*      */ import com.sun.jmx.snmp.SnmpPduBulk;
/*      */ import com.sun.jmx.snmp.SnmpPduFactory;
/*      */ import com.sun.jmx.snmp.SnmpPduPacket;
/*      */ import com.sun.jmx.snmp.SnmpPduRequest;
/*      */ import com.sun.jmx.snmp.SnmpStatusException;
/*      */ import com.sun.jmx.snmp.SnmpTooBigException;
/*      */ import com.sun.jmx.snmp.SnmpValue;
/*      */ import com.sun.jmx.snmp.SnmpVarBind;
/*      */ import com.sun.jmx.snmp.SnmpVarBindList;
/*      */ import com.sun.jmx.snmp.agent.SnmpMibAgent;
/*      */ import com.sun.jmx.snmp.agent.SnmpUserDataFactory;
/*      */ import java.io.InterruptedIOException;
/*      */ import java.net.DatagramPacket;
/*      */ import java.net.DatagramSocket;
/*      */ import java.net.SocketException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import java.util.logging.Level;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.ObjectName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class SnmpRequestHandler
/*      */   extends ClientHandler
/*      */   implements SnmpDefinitions
/*      */ {
/*   74 */   private transient DatagramSocket socket = null;
/*   75 */   private transient DatagramPacket packet = null;
/*   76 */   private transient Vector<SnmpMibAgent> mibs = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   81 */   private transient Hashtable<SnmpMibAgent, SnmpSubRequestHandler> subs = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient SnmpMibTree root;
/*      */ 
/*      */   
/*   88 */   private transient InetAddressAcl ipacl = null;
/*   89 */   private transient SnmpPduFactory pduFactory = null;
/*   90 */   private transient SnmpUserDataFactory userDataFactory = null;
/*   91 */   private transient SnmpAdaptorServer adaptor = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String InterruptSysCallMsg = "Interrupted system call";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpRequestHandler(SnmpAdaptorServer paramSnmpAdaptorServer, int paramInt, DatagramSocket paramDatagramSocket, DatagramPacket paramDatagramPacket, SnmpMibTree paramSnmpMibTree, Vector<SnmpMibAgent> paramVector, InetAddressAcl paramInetAddressAcl, SnmpPduFactory paramSnmpPduFactory, SnmpUserDataFactory paramSnmpUserDataFactory, MBeanServer paramMBeanServer, ObjectName paramObjectName) {
/*  103 */     super(paramSnmpAdaptorServer, paramInt, paramMBeanServer, paramObjectName);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  108 */     this.adaptor = paramSnmpAdaptorServer;
/*  109 */     this.socket = paramDatagramSocket;
/*  110 */     this.packet = paramDatagramPacket;
/*  111 */     this.root = paramSnmpMibTree;
/*  112 */     this.mibs = new Vector<>(paramVector);
/*  113 */     this.subs = new Hashtable<>(this.mibs.size());
/*  114 */     this.ipacl = paramInetAddressAcl;
/*  115 */     this.pduFactory = paramSnmpPduFactory;
/*  116 */     this.userDataFactory = paramSnmpUserDataFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doRun() {
/*  130 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  131 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "doRun", "Packet received:\n" + 
/*      */           
/*  133 */           SnmpMessage.dumpHexBuffer(this.packet.getData(), 0, this.packet.getLength()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  138 */     DatagramPacket datagramPacket = makeResponsePacket(this.packet);
/*      */ 
/*      */ 
/*      */     
/*  142 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER) && datagramPacket != null) {
/*  143 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "doRun", "Packet to be sent:\n" + 
/*      */           
/*  145 */           SnmpMessage.dumpHexBuffer(datagramPacket.getData(), 0, datagramPacket.getLength()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  150 */     if (datagramPacket != null) {
/*      */       try {
/*  152 */         this.socket.send(datagramPacket);
/*  153 */       } catch (SocketException socketException) {
/*  154 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  155 */           if (socketException.getMessage().equals("Interrupted system call")) {
/*  156 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "doRun", "interrupted");
/*      */           } else {
/*      */             
/*  159 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "doRun", "I/O exception", socketException);
/*      */           }
/*      */         
/*      */         }
/*  163 */       } catch (InterruptedIOException interruptedIOException) {
/*  164 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  165 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "doRun", "interrupted");
/*      */         }
/*      */       }
/*  168 */       catch (Exception exception) {
/*  169 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  170 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "doRun", "failure when sending response", exception);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DatagramPacket makeResponsePacket(DatagramPacket paramDatagramPacket) {
/*  182 */     DatagramPacket datagramPacket = null;
/*      */ 
/*      */ 
/*      */     
/*  186 */     SnmpMessage snmpMessage1 = new SnmpMessage();
/*      */     try {
/*  188 */       snmpMessage1.decodeMessage(paramDatagramPacket.getData(), paramDatagramPacket.getLength());
/*  189 */       snmpMessage1.address = paramDatagramPacket.getAddress();
/*  190 */       snmpMessage1.port = paramDatagramPacket.getPort();
/*      */     }
/*  192 */     catch (SnmpStatusException snmpStatusException) {
/*  193 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  194 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponsePacket", "packet decoding failed", snmpStatusException);
/*      */       }
/*      */       
/*  197 */       snmpMessage1 = null;
/*  198 */       ((SnmpAdaptorServer)this.adaptorServer).incSnmpInASNParseErrs(1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  203 */     SnmpMessage snmpMessage2 = null;
/*  204 */     if (snmpMessage1 != null) {
/*  205 */       snmpMessage2 = makeResponseMessage(snmpMessage1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  211 */     if (snmpMessage2 != null) {
/*      */       try {
/*  213 */         paramDatagramPacket.setLength(snmpMessage2.encodeMessage(paramDatagramPacket.getData()));
/*  214 */         datagramPacket = paramDatagramPacket;
/*      */       }
/*  216 */       catch (SnmpTooBigException snmpTooBigException) {
/*  217 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  218 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponsePacket", "response message is too big");
/*      */         }
/*      */         
/*      */         try {
/*  222 */           snmpMessage2 = newTooBigMessage(snmpMessage1);
/*  223 */           paramDatagramPacket.setLength(snmpMessage2.encodeMessage(paramDatagramPacket.getData()));
/*  224 */           datagramPacket = paramDatagramPacket;
/*      */         }
/*  226 */         catch (SnmpTooBigException snmpTooBigException1) {
/*  227 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  228 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponsePacket", "'too big' is 'too big' !!!");
/*      */           }
/*      */           
/*  231 */           this.adaptor.incSnmpSilentDrops(1);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  236 */     return datagramPacket;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpMessage makeResponseMessage(SnmpMessage paramSnmpMessage) {
/*      */     SnmpPduPacket snmpPduPacket1;
/*  244 */     SnmpMessage snmpMessage = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  249 */     Object object = null;
/*      */     try {
/*  251 */       snmpPduPacket1 = (SnmpPduPacket)this.pduFactory.decodeSnmpPdu(paramSnmpMessage);
/*  252 */       if (snmpPduPacket1 != null && this.userDataFactory != null) {
/*  253 */         object = this.userDataFactory.allocateUserData(snmpPduPacket1);
/*      */       }
/*  255 */     } catch (SnmpStatusException snmpStatusException) {
/*  256 */       snmpPduPacket1 = null;
/*  257 */       SnmpAdaptorServer snmpAdaptorServer = (SnmpAdaptorServer)this.adaptorServer;
/*  258 */       snmpAdaptorServer.incSnmpInASNParseErrs(1);
/*  259 */       if (snmpStatusException.getStatus() == 243)
/*  260 */         snmpAdaptorServer.incSnmpInBadVersions(1); 
/*  261 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  262 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponseMessage", "message decoding failed", snmpStatusException);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  269 */     SnmpPduPacket snmpPduPacket2 = null;
/*  270 */     if (snmpPduPacket1 != null) {
/*  271 */       snmpPduPacket2 = makeResponsePdu(snmpPduPacket1, object);
/*      */       try {
/*  273 */         if (this.userDataFactory != null)
/*  274 */           this.userDataFactory.releaseUserData(object, snmpPduPacket2); 
/*  275 */       } catch (SnmpStatusException snmpStatusException) {
/*  276 */         snmpPduPacket2 = null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  282 */     if (snmpPduPacket2 != null) {
/*      */       
/*      */       try {
/*  285 */         snmpMessage = (SnmpMessage)this.pduFactory.encodeSnmpPdu(snmpPduPacket2, (this.packet.getData()).length);
/*      */       }
/*  287 */       catch (SnmpStatusException snmpStatusException) {
/*  288 */         snmpMessage = null;
/*  289 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  290 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponseMessage", "failure when encoding the response message", snmpStatusException);
/*      */         
/*      */         }
/*      */       }
/*  294 */       catch (SnmpTooBigException snmpTooBigException) {
/*  295 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  296 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponseMessage", "response message is too big");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  304 */           if ((this.packet.getData()).length <= 32)
/*  305 */             throw snmpTooBigException; 
/*  306 */           int i = snmpTooBigException.getVarBindCount();
/*  307 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  308 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponseMessage", "fail on element" + i);
/*      */           }
/*      */ 
/*      */           
/*      */           while (true) {
/*      */             try {
/*  314 */               snmpPduPacket2 = reduceResponsePdu(snmpPduPacket1, snmpPduPacket2, i);
/*      */               
/*  316 */               snmpMessage = (SnmpMessage)this.pduFactory.encodeSnmpPdu(snmpPduPacket2, (this.packet
/*  317 */                   .getData()).length - 32);
/*      */               break;
/*  319 */             } catch (SnmpTooBigException snmpTooBigException1) {
/*  320 */               if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  321 */                 JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponseMessage", "response message is still too big");
/*      */               }
/*      */               
/*  324 */               int j = i;
/*  325 */               i = snmpTooBigException1.getVarBindCount();
/*  326 */               if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  327 */                 JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponseMessage", "fail on element" + i);
/*      */               }
/*      */               
/*  330 */               if (i == j)
/*      */               {
/*      */ 
/*      */                 
/*  334 */                 throw snmpTooBigException1;
/*      */               }
/*      */             } 
/*      */           } 
/*  338 */         } catch (SnmpStatusException snmpStatusException) {
/*  339 */           snmpMessage = null;
/*  340 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  341 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponseMessage", "failure when encoding the response message", snmpStatusException);
/*      */           
/*      */           }
/*      */         }
/*  345 */         catch (SnmpTooBigException snmpTooBigException1) {
/*      */           try {
/*  347 */             snmpPduPacket2 = newTooBigPdu(snmpPduPacket1);
/*      */             
/*  349 */             snmpMessage = (SnmpMessage)this.pduFactory.encodeSnmpPdu(snmpPduPacket2, (this.packet.getData()).length);
/*      */           }
/*  351 */           catch (SnmpTooBigException snmpTooBigException2) {
/*  352 */             snmpMessage = null;
/*  353 */             if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  354 */               JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponseMessage", "'too big' is 'too big' !!!");
/*      */             }
/*      */             
/*  357 */             this.adaptor.incSnmpSilentDrops(1);
/*      */           }
/*  359 */           catch (Exception exception) {
/*  360 */             if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  361 */               JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponseMessage", "Got unexpected exception", exception);
/*      */             }
/*      */             
/*  364 */             snmpMessage = null;
/*      */           }
/*      */         
/*  367 */         } catch (Exception exception) {
/*  368 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  369 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponseMessage", "Got unexpected exception", exception);
/*      */           }
/*      */           
/*  372 */           snmpMessage = null;
/*      */         } 
/*      */       } 
/*      */     }
/*  376 */     return snmpMessage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpPduPacket makeResponsePdu(SnmpPduPacket paramSnmpPduPacket, Object paramObject) {
/*  386 */     SnmpAdaptorServer snmpAdaptorServer = (SnmpAdaptorServer)this.adaptorServer;
/*  387 */     SnmpPduPacket snmpPduPacket = null;
/*      */     
/*  389 */     snmpAdaptorServer.updateRequestCounters(paramSnmpPduPacket.type);
/*  390 */     if (paramSnmpPduPacket.varBindList != null) {
/*  391 */       snmpAdaptorServer.updateVarCounters(paramSnmpPduPacket.type, paramSnmpPduPacket.varBindList.length);
/*      */     }
/*      */     
/*  394 */     if (checkPduType(paramSnmpPduPacket)) {
/*  395 */       snmpPduPacket = checkAcl(paramSnmpPduPacket);
/*  396 */       if (snmpPduPacket == null) {
/*  397 */         if (this.mibs.size() < 1) {
/*  398 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  399 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "makeResponsePdu", "Request " + paramSnmpPduPacket.requestId + " received but no MIB registered.");
/*      */           }
/*      */ 
/*      */           
/*  403 */           return makeNoMibErrorPdu((SnmpPduRequest)paramSnmpPduPacket, paramObject);
/*      */         } 
/*  405 */         switch (paramSnmpPduPacket.type) {
/*      */           case 160:
/*      */           case 161:
/*      */           case 163:
/*  409 */             snmpPduPacket = makeGetSetResponsePdu((SnmpPduRequest)paramSnmpPduPacket, paramObject);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 165:
/*  414 */             snmpPduPacket = makeGetBulkResponsePdu((SnmpPduBulk)paramSnmpPduPacket, paramObject);
/*      */             break;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       } else {
/*  422 */         if (!snmpAdaptorServer.getAuthRespEnabled()) {
/*  423 */           snmpPduPacket = null;
/*      */         }
/*  425 */         if (snmpAdaptorServer.getAuthTrapEnabled()) {
/*      */           try {
/*  427 */             snmpAdaptorServer.snmpV1Trap(4, 0, new SnmpVarBindList());
/*      */ 
/*      */           
/*      */           }
/*  431 */           catch (Exception exception) {
/*  432 */             if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  433 */               JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "makeResponsePdu", "Failure when sending authentication trap", exception);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  440 */     return snmpPduPacket;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SnmpPduPacket makeErrorVarbindPdu(SnmpPduPacket paramSnmpPduPacket, int paramInt) {
/*      */     byte b;
/*  455 */     SnmpVarBind[] arrayOfSnmpVarBind = paramSnmpPduPacket.varBindList;
/*  456 */     int i = arrayOfSnmpVarBind.length;
/*      */     
/*  458 */     switch (paramInt) {
/*      */       case 130:
/*  460 */         for (b = 0; b < i; b++) {
/*  461 */           (arrayOfSnmpVarBind[b]).value = SnmpVarBind.endOfMibView;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  474 */         return newValidResponsePdu(paramSnmpPduPacket, arrayOfSnmpVarBind);case 128: for (b = 0; b < i; b++) (arrayOfSnmpVarBind[b]).value = SnmpVarBind.noSuchObject;  return newValidResponsePdu(paramSnmpPduPacket, arrayOfSnmpVarBind);case 129: for (b = 0; b < i; b++) (arrayOfSnmpVarBind[b]).value = SnmpVarBind.noSuchInstance;  return newValidResponsePdu(paramSnmpPduPacket, arrayOfSnmpVarBind);
/*      */     } 
/*      */     return newErrorResponsePdu(paramSnmpPduPacket, 5, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SnmpPduPacket makeNoMibErrorPdu(SnmpPduRequest paramSnmpPduRequest, Object paramObject) {
/*  495 */     if (paramSnmpPduRequest.version == 0)
/*      */     {
/*  497 */       return 
/*  498 */         newErrorResponsePdu(paramSnmpPduRequest, 2, 1); } 
/*  499 */     if (paramSnmpPduRequest.version == 1)
/*      */     {
/*  501 */       switch (paramSnmpPduRequest.type) {
/*      */         
/*      */         case 163:
/*      */         case 253:
/*  505 */           return 
/*  506 */             newErrorResponsePdu(paramSnmpPduRequest, 6, 1);
/*      */         
/*      */         case 160:
/*  509 */           return 
/*  510 */             makeErrorVarbindPdu(paramSnmpPduRequest, 128);
/*      */ 
/*      */         
/*      */         case 161:
/*      */         case 165:
/*  515 */           return 
/*  516 */             makeErrorVarbindPdu(paramSnmpPduRequest, 130);
/*      */       } 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  522 */     return newErrorResponsePdu(paramSnmpPduRequest, 5, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpPduPacket makeGetSetResponsePdu(SnmpPduRequest paramSnmpPduRequest, Object paramObject) {
/*  543 */     if (paramSnmpPduRequest.varBindList == null)
/*      */     {
/*      */       
/*  546 */       return newValidResponsePdu(paramSnmpPduRequest, (SnmpVarBind[])null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  551 */     splitRequest(paramSnmpPduRequest);
/*  552 */     int i = this.subs.size();
/*  553 */     if (i == 1) {
/*  554 */       return turboProcessingGetSet(paramSnmpPduRequest, paramObject);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  560 */     SnmpPduPacket snmpPduPacket = executeSubRequest(paramSnmpPduRequest, paramObject);
/*  561 */     if (snmpPduPacket != null)
/*      */     {
/*      */ 
/*      */       
/*  565 */       return snmpPduPacket;
/*      */     }
/*      */ 
/*      */     
/*  569 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  570 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "makeGetSetResponsePdu", "Build the unified response for request " + paramSnmpPduRequest.requestId);
/*      */     }
/*      */ 
/*      */     
/*  574 */     return mergeResponses(paramSnmpPduRequest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpPduPacket executeSubRequest(SnmpPduPacket paramSnmpPduPacket, Object paramObject) {
/*  584 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  589 */     if (paramSnmpPduPacket.type == 163) {
/*      */       
/*  591 */       byte b1 = 0;
/*  592 */       for (Enumeration<SnmpSubRequestHandler> enumeration1 = this.subs.elements(); enumeration1.hasMoreElements(); b1++) {
/*      */ 
/*      */ 
/*      */         
/*  596 */         SnmpSubRequestHandler snmpSubRequestHandler = enumeration1.nextElement();
/*  597 */         snmpSubRequestHandler.setUserData(paramObject);
/*  598 */         snmpSubRequestHandler.type = 253;
/*      */         
/*  600 */         snmpSubRequestHandler.run();
/*      */         
/*  602 */         snmpSubRequestHandler.type = 163;
/*      */         
/*  604 */         if (snmpSubRequestHandler.getErrorStatus() != 0) {
/*      */ 
/*      */           
/*  607 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  608 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "executeSubRequest", "an error occurs");
/*      */           }
/*      */ 
/*      */           
/*  612 */           return newErrorResponsePdu(paramSnmpPduPacket, bool, snmpSubRequestHandler
/*  613 */               .getErrorIndex() + 1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  620 */     byte b = 0;
/*  621 */     for (Enumeration<SnmpSubRequestHandler> enumeration = this.subs.elements(); enumeration.hasMoreElements(); b++) {
/*  622 */       SnmpSubRequestHandler snmpSubRequestHandler = enumeration.nextElement();
/*      */       
/*  624 */       snmpSubRequestHandler.setUserData(paramObject);
/*      */ 
/*      */       
/*  627 */       snmpSubRequestHandler.run();
/*      */       
/*  629 */       if (snmpSubRequestHandler.getErrorStatus() != 0) {
/*      */ 
/*      */         
/*  632 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  633 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "executeSubRequest", "an error occurs");
/*      */         }
/*      */ 
/*      */         
/*  637 */         return newErrorResponsePdu(paramSnmpPduPacket, bool, snmpSubRequestHandler
/*  638 */             .getErrorIndex() + 1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  644 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpPduPacket turboProcessingGetSet(SnmpPduRequest paramSnmpPduRequest, Object paramObject) {
/*  654 */     SnmpSubRequestHandler snmpSubRequestHandler = this.subs.elements().nextElement();
/*  655 */     snmpSubRequestHandler.setUserData(paramObject);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  660 */     if (paramSnmpPduRequest.type == 163) {
/*  661 */       snmpSubRequestHandler.type = 253;
/*  662 */       snmpSubRequestHandler.run();
/*  663 */       snmpSubRequestHandler.type = 163;
/*      */ 
/*      */ 
/*      */       
/*  667 */       int j = snmpSubRequestHandler.getErrorStatus();
/*  668 */       if (j != 0)
/*      */       {
/*      */         
/*  671 */         return newErrorResponsePdu(paramSnmpPduRequest, j, snmpSubRequestHandler
/*  672 */             .getErrorIndex() + 1);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  679 */     snmpSubRequestHandler.run();
/*  680 */     int i = snmpSubRequestHandler.getErrorStatus();
/*  681 */     if (i != 0) {
/*      */ 
/*      */       
/*  684 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  685 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "turboProcessingGetSet", "an error occurs");
/*      */       }
/*      */       
/*  688 */       int j = snmpSubRequestHandler.getErrorIndex() + 1;
/*  689 */       return newErrorResponsePdu(paramSnmpPduRequest, i, j);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  695 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  696 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "turboProcessingGetSet", "build the unified response for request " + paramSnmpPduRequest.requestId);
/*      */     }
/*      */ 
/*      */     
/*  700 */     return mergeResponses(paramSnmpPduRequest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpPduPacket makeGetBulkResponsePdu(SnmpPduBulk paramSnmpPduBulk, Object paramObject) {
/*  713 */     int n, i = paramSnmpPduBulk.varBindList.length;
/*  714 */     int j = Math.max(Math.min(paramSnmpPduBulk.nonRepeaters, i), 0);
/*  715 */     int k = Math.max(paramSnmpPduBulk.maxRepetitions, 0);
/*  716 */     int m = i - j;
/*      */     
/*  718 */     if (paramSnmpPduBulk.varBindList == null)
/*      */     {
/*      */       
/*  721 */       return newValidResponsePdu(paramSnmpPduBulk, (SnmpVarBind[])null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  726 */     splitBulkRequest(paramSnmpPduBulk, j, k, m);
/*  727 */     SnmpPduPacket snmpPduPacket = executeSubRequest(paramSnmpPduBulk, paramObject);
/*  728 */     if (snmpPduPacket != null) {
/*  729 */       return snmpPduPacket;
/*      */     }
/*  731 */     SnmpVarBind[] arrayOfSnmpVarBind = mergeBulkResponses(j + k * m);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  736 */     int i1 = arrayOfSnmpVarBind.length;
/*  737 */     while (i1 > j && (arrayOfSnmpVarBind[i1 - 1]).value
/*  738 */       .equals(SnmpVarBind.endOfMibView)) {
/*  739 */       i1--;
/*      */     }
/*  741 */     if (i1 == j) {
/*  742 */       n = j + m;
/*      */     } else {
/*  744 */       n = j + ((i1 - 1 - j) / m + 2) * m;
/*  745 */     }  if (n < arrayOfSnmpVarBind.length) {
/*  746 */       SnmpVarBind[] arrayOfSnmpVarBind1 = new SnmpVarBind[n];
/*  747 */       for (byte b = 0; b < n; b++) {
/*  748 */         arrayOfSnmpVarBind1[b] = arrayOfSnmpVarBind[b];
/*      */       }
/*  750 */       arrayOfSnmpVarBind = arrayOfSnmpVarBind1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  755 */     return newValidResponsePdu(paramSnmpPduBulk, arrayOfSnmpVarBind);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkPduType(SnmpPduPacket paramSnmpPduPacket) {
/*  766 */     switch (paramSnmpPduPacket.type)
/*      */     
/*      */     { case 160:
/*      */       case 161:
/*      */       case 163:
/*      */       case 165:
/*  772 */         bool = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  784 */         return bool; }  if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "checkPduType", "cannot respond to this kind of PDU");  boolean bool = false; return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpPduPacket checkAcl(SnmpPduPacket paramSnmpPduPacket) {
/*  793 */     SnmpPduRequest snmpPduRequest = null;
/*  794 */     String str = new String(paramSnmpPduPacket.community);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  799 */     if (this.ipacl != null) {
/*  800 */       if (paramSnmpPduPacket.type == 163) {
/*  801 */         if (!this.ipacl.checkWritePermission(paramSnmpPduPacket.address, str)) {
/*  802 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  803 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "checkAcl", "sender is " + paramSnmpPduPacket.address + " with " + str + ". Sender has no write permission");
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  808 */           int i = SnmpSubRequestHandler.mapErrorStatus(16, paramSnmpPduPacket.version, paramSnmpPduPacket.type);
/*      */ 
/*      */           
/*  811 */           snmpPduRequest = newErrorResponsePdu(paramSnmpPduPacket, i, 0);
/*      */         
/*      */         }
/*  814 */         else if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  815 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "checkAcl", "sender is " + paramSnmpPduPacket.address + " with " + str + ". Sender has write permission");
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*  822 */       else if (!this.ipacl.checkReadPermission(paramSnmpPduPacket.address, str)) {
/*  823 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  824 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "checkAcl", "sender is " + paramSnmpPduPacket.address + " with " + str + ". Sender has no read permission");
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  829 */         int i = SnmpSubRequestHandler.mapErrorStatus(16, paramSnmpPduPacket.version, paramSnmpPduPacket.type);
/*      */ 
/*      */         
/*  832 */         snmpPduRequest = newErrorResponsePdu(paramSnmpPduPacket, i, 0);
/*      */ 
/*      */         
/*  835 */         SnmpAdaptorServer snmpAdaptorServer = (SnmpAdaptorServer)this.adaptorServer;
/*      */         
/*  837 */         snmpAdaptorServer.updateErrorCounters(2);
/*      */ 
/*      */       
/*      */       }
/*  841 */       else if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  842 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "checkAcl", "sender is " + paramSnmpPduPacket.address + " with " + str + ". Sender has read permission");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  853 */     if (snmpPduRequest != null) {
/*  854 */       SnmpAdaptorServer snmpAdaptorServer = (SnmpAdaptorServer)this.adaptorServer;
/*  855 */       snmpAdaptorServer.incSnmpInBadCommunityUses(1);
/*  856 */       if (!this.ipacl.checkCommunity(str)) {
/*  857 */         snmpAdaptorServer.incSnmpInBadCommunityNames(1);
/*      */       }
/*      */     } 
/*  860 */     return snmpPduRequest;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpPduRequest newValidResponsePdu(SnmpPduPacket paramSnmpPduPacket, SnmpVarBind[] paramArrayOfSnmpVarBind) {
/*  869 */     SnmpPduRequest snmpPduRequest = new SnmpPduRequest();
/*      */     
/*  871 */     snmpPduRequest.address = paramSnmpPduPacket.address;
/*  872 */     snmpPduRequest.port = paramSnmpPduPacket.port;
/*  873 */     snmpPduRequest.version = paramSnmpPduPacket.version;
/*  874 */     snmpPduRequest.community = paramSnmpPduPacket.community;
/*  875 */     snmpPduRequest.type = 162;
/*  876 */     snmpPduRequest.requestId = paramSnmpPduPacket.requestId;
/*  877 */     snmpPduRequest.errorStatus = 0;
/*  878 */     snmpPduRequest.errorIndex = 0;
/*  879 */     snmpPduRequest.varBindList = paramArrayOfSnmpVarBind;
/*      */     
/*  881 */     ((SnmpAdaptorServer)this.adaptorServer)
/*  882 */       .updateErrorCounters(snmpPduRequest.errorStatus);
/*      */     
/*  884 */     return snmpPduRequest;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpPduRequest newErrorResponsePdu(SnmpPduPacket paramSnmpPduPacket, int paramInt1, int paramInt2) {
/*  892 */     SnmpPduRequest snmpPduRequest = newValidResponsePdu(paramSnmpPduPacket, (SnmpVarBind[])null);
/*  893 */     snmpPduRequest.errorStatus = paramInt1;
/*  894 */     snmpPduRequest.errorIndex = paramInt2;
/*  895 */     snmpPduRequest.varBindList = paramSnmpPduPacket.varBindList;
/*      */     
/*  897 */     ((SnmpAdaptorServer)this.adaptorServer)
/*  898 */       .updateErrorCounters(snmpPduRequest.errorStatus);
/*      */     
/*  900 */     return snmpPduRequest;
/*      */   }
/*      */ 
/*      */   
/*      */   private SnmpMessage newTooBigMessage(SnmpMessage paramSnmpMessage) throws SnmpTooBigException {
/*  905 */     SnmpMessage snmpMessage = null;
/*      */ 
/*      */     
/*      */     try {
/*  909 */       SnmpPduPacket snmpPduPacket = (SnmpPduPacket)this.pduFactory.decodeSnmpPdu(paramSnmpMessage);
/*  910 */       if (snmpPduPacket != null) {
/*  911 */         SnmpPduPacket snmpPduPacket1 = newTooBigPdu(snmpPduPacket);
/*      */         
/*  913 */         snmpMessage = (SnmpMessage)this.pduFactory.encodeSnmpPdu(snmpPduPacket1, (this.packet.getData()).length);
/*      */       }
/*      */     
/*  916 */     } catch (SnmpStatusException snmpStatusException) {
/*      */ 
/*      */       
/*  919 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  920 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "newTooBigMessage", "Internal error", snmpStatusException);
/*      */       }
/*      */       
/*  923 */       throw new InternalError(snmpStatusException);
/*      */     } 
/*      */     
/*  926 */     return snmpMessage;
/*      */   }
/*      */ 
/*      */   
/*      */   private SnmpPduPacket newTooBigPdu(SnmpPduPacket paramSnmpPduPacket) {
/*  931 */     SnmpPduRequest snmpPduRequest = newErrorResponsePdu(paramSnmpPduPacket, 1, 0);
/*  932 */     snmpPduRequest.varBindList = null;
/*  933 */     return snmpPduRequest;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpPduPacket reduceResponsePdu(SnmpPduPacket paramSnmpPduPacket1, SnmpPduPacket paramSnmpPduPacket2, int paramInt) throws SnmpTooBigException {
/*      */     int i;
/*  943 */     if (paramSnmpPduPacket1.type != 165) {
/*  944 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  945 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "reduceResponsePdu", "cannot remove anything");
/*      */       }
/*      */       
/*  948 */       throw new SnmpTooBigException(paramInt);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  964 */     if (paramInt >= 3) {
/*  965 */       i = Math.min(paramInt - 1, paramSnmpPduPacket2.varBindList.length);
/*  966 */     } else if (paramInt == 1) {
/*  967 */       i = 1;
/*      */     } else {
/*  969 */       i = paramSnmpPduPacket2.varBindList.length / 2;
/*      */     } 
/*  971 */     if (i < 1) {
/*  972 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  973 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "reduceResponsePdu", "cannot remove anything");
/*      */       }
/*      */       
/*  976 */       throw new SnmpTooBigException(paramInt);
/*      */     } 
/*      */     
/*  979 */     SnmpVarBind[] arrayOfSnmpVarBind = new SnmpVarBind[i];
/*  980 */     for (byte b = 0; b < i; b++) {
/*  981 */       arrayOfSnmpVarBind[b] = paramSnmpPduPacket2.varBindList[b];
/*      */     }
/*  983 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  984 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "reduceResponsePdu", (paramSnmpPduPacket2.varBindList.length - arrayOfSnmpVarBind.length) + " items have been removed");
/*      */     }
/*      */ 
/*      */     
/*  988 */     paramSnmpPduPacket2.varBindList = arrayOfSnmpVarBind;
/*      */ 
/*      */     
/*  991 */     return paramSnmpPduPacket2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void splitRequest(SnmpPduRequest paramSnmpPduRequest) {
/*  999 */     int i = this.mibs.size();
/* 1000 */     SnmpMibAgent snmpMibAgent = this.mibs.firstElement();
/* 1001 */     if (i == 1) {
/*      */ 
/*      */       
/* 1004 */       this.subs.put(snmpMibAgent, new SnmpSubRequestHandler(snmpMibAgent, paramSnmpPduRequest, true));
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1011 */     if (paramSnmpPduRequest.type == 161) {
/* 1012 */       for (Enumeration<SnmpMibAgent> enumeration = this.mibs.elements(); enumeration.hasMoreElements(); ) {
/* 1013 */         SnmpMibAgent snmpMibAgent1 = enumeration.nextElement();
/* 1014 */         this.subs.put(snmpMibAgent1, new SnmpSubNextRequestHandler(this.adaptor, snmpMibAgent1, paramSnmpPduRequest));
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1019 */     int j = paramSnmpPduRequest.varBindList.length;
/* 1020 */     SnmpVarBind[] arrayOfSnmpVarBind = paramSnmpPduRequest.varBindList;
/*      */     
/* 1022 */     for (byte b = 0; b < j; b++) {
/* 1023 */       snmpMibAgent = this.root.getAgentMib((arrayOfSnmpVarBind[b]).oid);
/* 1024 */       SnmpSubRequestHandler snmpSubRequestHandler = this.subs.get(snmpMibAgent);
/* 1025 */       if (snmpSubRequestHandler == null) {
/*      */ 
/*      */ 
/*      */         
/* 1029 */         snmpSubRequestHandler = new SnmpSubRequestHandler(snmpMibAgent, paramSnmpPduRequest);
/* 1030 */         this.subs.put(snmpMibAgent, snmpSubRequestHandler);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1035 */       snmpSubRequestHandler.updateRequest(arrayOfSnmpVarBind[b], b);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void splitBulkRequest(SnmpPduBulk paramSnmpPduBulk, int paramInt1, int paramInt2, int paramInt3) {
/* 1049 */     for (Enumeration<SnmpMibAgent> enumeration = this.mibs.elements(); enumeration.hasMoreElements(); ) {
/* 1050 */       SnmpMibAgent snmpMibAgent = enumeration.nextElement();
/*      */       
/* 1052 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1053 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "splitBulkRequest", "Create a sub with : " + snmpMibAgent + " " + paramInt1 + " " + paramInt2 + " " + paramInt3);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1058 */       this.subs.put(snmpMibAgent, new SnmpSubBulkRequestHandler(this.adaptor, snmpMibAgent, paramSnmpPduBulk, paramInt1, paramInt2, paramInt3));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpPduPacket mergeResponses(SnmpPduRequest paramSnmpPduRequest) {
/* 1070 */     if (paramSnmpPduRequest.type == 161) {
/* 1071 */       return mergeNextResponses(paramSnmpPduRequest);
/*      */     }
/*      */     
/* 1074 */     SnmpVarBind[] arrayOfSnmpVarBind = paramSnmpPduRequest.varBindList;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1079 */     for (Enumeration<SnmpSubRequestHandler> enumeration = this.subs.elements(); enumeration.hasMoreElements(); ) {
/* 1080 */       SnmpSubRequestHandler snmpSubRequestHandler = enumeration.nextElement();
/* 1081 */       snmpSubRequestHandler.updateResult(arrayOfSnmpVarBind);
/*      */     } 
/* 1083 */     return newValidResponsePdu(paramSnmpPduRequest, arrayOfSnmpVarBind);
/*      */   }
/*      */   
/*      */   private SnmpPduPacket mergeNextResponses(SnmpPduRequest paramSnmpPduRequest) {
/* 1087 */     int i = paramSnmpPduRequest.varBindList.length;
/* 1088 */     SnmpVarBind[] arrayOfSnmpVarBind = new SnmpVarBind[i];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1093 */     for (Enumeration<SnmpSubRequestHandler> enumeration = this.subs.elements(); enumeration.hasMoreElements(); ) {
/* 1094 */       SnmpSubRequestHandler snmpSubRequestHandler = enumeration.nextElement();
/* 1095 */       snmpSubRequestHandler.updateResult(arrayOfSnmpVarBind);
/*      */     } 
/*      */     
/* 1098 */     if (paramSnmpPduRequest.version == 1) {
/* 1099 */       return newValidResponsePdu(paramSnmpPduRequest, arrayOfSnmpVarBind);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1104 */     for (byte b = 0; b < i; b++) {
/* 1105 */       SnmpValue snmpValue = (arrayOfSnmpVarBind[b]).value;
/* 1106 */       if (snmpValue == SnmpVarBind.endOfMibView) {
/* 1107 */         return newErrorResponsePdu(paramSnmpPduRequest, 2, b + 1);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1113 */     return newValidResponsePdu(paramSnmpPduRequest, arrayOfSnmpVarBind);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpVarBind[] mergeBulkResponses(int paramInt) {
/* 1119 */     SnmpVarBind[] arrayOfSnmpVarBind = new SnmpVarBind[paramInt];
/* 1120 */     for (int i = paramInt - 1; i >= 0; i--) {
/* 1121 */       arrayOfSnmpVarBind[i] = new SnmpVarBind();
/* 1122 */       (arrayOfSnmpVarBind[i]).value = SnmpVarBind.endOfMibView;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1128 */     for (Enumeration<SnmpSubRequestHandler> enumeration = this.subs.elements(); enumeration.hasMoreElements(); ) {
/* 1129 */       SnmpSubRequestHandler snmpSubRequestHandler = enumeration.nextElement();
/* 1130 */       snmpSubRequestHandler.updateResult(arrayOfSnmpVarBind);
/*      */     } 
/*      */     
/* 1133 */     return arrayOfSnmpVarBind;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String makeDebugTag() {
/* 1138 */     return "SnmpRequestHandler[" + this.adaptorServer.getProtocol() + ":" + this.adaptorServer
/* 1139 */       .getPort() + "]";
/*      */   }
/*      */ 
/*      */   
/*      */   Thread createThread(Runnable paramRunnable) {
/* 1144 */     return null;
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpRequestHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */