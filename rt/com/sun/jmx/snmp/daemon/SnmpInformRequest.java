/*      */ package com.sun.jmx.snmp.daemon;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import com.sun.jmx.snmp.SnmpDefinitions;
/*      */ import com.sun.jmx.snmp.SnmpMessage;
/*      */ import com.sun.jmx.snmp.SnmpPdu;
/*      */ import com.sun.jmx.snmp.SnmpPduFactory;
/*      */ import com.sun.jmx.snmp.SnmpPduRequest;
/*      */ import com.sun.jmx.snmp.SnmpPduRequestType;
/*      */ import com.sun.jmx.snmp.SnmpStatusException;
/*      */ import com.sun.jmx.snmp.SnmpTooBigException;
/*      */ import com.sun.jmx.snmp.SnmpVarBind;
/*      */ import com.sun.jmx.snmp.SnmpVarBindList;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.util.Date;
/*      */ import java.util.logging.Level;
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
/*      */ public class SnmpInformRequest
/*      */   implements SnmpDefinitions
/*      */ {
/*   80 */   private static SnmpRequestCounter requestCounter = new SnmpRequestCounter();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   85 */   private SnmpVarBindList varBindList = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   90 */   int errorStatus = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   95 */   int errorIndex = 0;
/*      */ 
/*      */   
/*   98 */   SnmpVarBind[] internalVarBind = null;
/*      */ 
/*      */   
/*  101 */   String reason = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient SnmpAdaptorServer adaptor;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient SnmpSession informSession;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  116 */   private SnmpInformHandler callback = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SnmpPdu requestPdu;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SnmpPduRequestType responsePdu;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int stBase = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int stInProgress = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int stWaitingToSend = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int stWaitingForReply = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int stReceivedReply = 9;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int stAborted = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int stTimeout = 32;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int stInternalError = 64;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int stResultsAvailable = 128;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int stNeverUsed = 256;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  183 */   private int numTries = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  189 */   private int timeout = 3000;
/*      */ 
/*      */ 
/*      */   
/*  193 */   private int reqState = 256;
/*      */ 
/*      */   
/*  196 */   private long prevPollTime = 0L;
/*  197 */   private long nextPollTime = 0L;
/*      */   private long waitTimeForResponse;
/*  199 */   private Date debugDate = new Date();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  204 */   private int requestId = 0;
/*      */   
/*  206 */   private int port = 0;
/*      */   
/*  208 */   private InetAddress address = null;
/*  209 */   private String communityString = null;
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
/*      */   SnmpInformRequest(SnmpSession paramSnmpSession, SnmpAdaptorServer paramSnmpAdaptorServer, InetAddress paramInetAddress, String paramString, int paramInt, SnmpInformHandler paramSnmpInformHandler) throws SnmpStatusException {
/*  232 */     this.informSession = paramSnmpSession;
/*  233 */     this.adaptor = paramSnmpAdaptorServer;
/*  234 */     this.address = paramInetAddress;
/*  235 */     this.communityString = paramString;
/*  236 */     this.port = paramInt;
/*  237 */     this.callback = paramSnmpInformHandler;
/*  238 */     this.informSession.addInformRequest(this);
/*  239 */     setTimeout(this.adaptor.getTimeout());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized int getRequestId() {
/*  250 */     return this.requestId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized InetAddress getAddress() {
/*  258 */     return this.address;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized int getRequestStatus() {
/*  266 */     return this.reqState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized boolean isAborted() {
/*  274 */     return ((this.reqState & 0x10) == 16);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized boolean inProgress() {
/*  282 */     return ((this.reqState & 0x1) == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized boolean isResultAvailable() {
/*  290 */     return (this.reqState == 128);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized int getErrorStatus() {
/*  298 */     return this.errorStatus;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized int getErrorIndex() {
/*  307 */     return this.errorIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getMaxTries() {
/*  315 */     return this.adaptor.getMaxTries();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized int getNumTries() {
/*  323 */     return this.numTries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final synchronized void setTimeout(int paramInt) {
/*  330 */     this.timeout = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized long getAbsNextPollTime() {
/*  339 */     return this.nextPollTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized long getAbsMaxTimeToWait() {
/*  348 */     if (this.prevPollTime == 0L) {
/*  349 */       return System.currentTimeMillis();
/*      */     }
/*  351 */     return this.waitTimeForResponse;
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
/*      */   public final synchronized SnmpVarBindList getResponseVarBindList() {
/*  365 */     if (inProgress())
/*  366 */       return null; 
/*  367 */     return this.varBindList;
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
/*      */   public final boolean waitForCompletion(long paramLong) {
/*  384 */     if (!inProgress()) {
/*  385 */       return true;
/*      */     }
/*  387 */     if (this.informSession.thisSessionContext()) {
/*      */ 
/*      */       
/*  390 */       SnmpInformHandler snmpInformHandler = this.callback;
/*  391 */       this.callback = null;
/*  392 */       this.informSession.waitForResponse(this, paramLong);
/*  393 */       this.callback = snmpInformHandler;
/*      */     }
/*      */     else {
/*      */       
/*  397 */       synchronized (this) {
/*  398 */         SnmpInformHandler snmpInformHandler = this.callback;
/*      */         try {
/*  400 */           this.callback = null;
/*  401 */           wait(paramLong);
/*  402 */         } catch (InterruptedException interruptedException) {}
/*      */         
/*  404 */         this.callback = snmpInformHandler;
/*      */       } 
/*      */     } 
/*      */     
/*  408 */     return !inProgress();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void cancelRequest() {
/*  415 */     this.errorStatus = 225;
/*  416 */     stopRequest();
/*  417 */     deleteRequest();
/*  418 */     notifyClient();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void notifyClient() {
/*  425 */     notifyAll();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalize() {
/*  436 */     this.callback = null;
/*  437 */     this.varBindList = null;
/*  438 */     this.internalVarBind = null;
/*  439 */     this.adaptor = null;
/*  440 */     this.informSession = null;
/*  441 */     this.requestPdu = null;
/*  442 */     this.responsePdu = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String snmpErrorToString(int paramInt) {
/*  451 */     switch (paramInt) {
/*      */       case 0:
/*  453 */         return "noError";
/*      */       case 1:
/*  455 */         return "tooBig";
/*      */       case 2:
/*  457 */         return "noSuchName";
/*      */       case 3:
/*  459 */         return "badValue";
/*      */       case 4:
/*  461 */         return "readOnly";
/*      */       case 5:
/*  463 */         return "genErr";
/*      */       case 6:
/*  465 */         return "noAccess";
/*      */       case 7:
/*  467 */         return "wrongType";
/*      */       case 8:
/*  469 */         return "wrongLength";
/*      */       case 9:
/*  471 */         return "wrongEncoding";
/*      */       case 10:
/*  473 */         return "wrongValue";
/*      */       case 11:
/*  475 */         return "noCreation";
/*      */       case 12:
/*  477 */         return "inconsistentValue";
/*      */       case 13:
/*  479 */         return "resourceUnavailable";
/*      */       case 14:
/*  481 */         return "commitFailed";
/*      */       case 15:
/*  483 */         return "undoFailed";
/*      */       case 16:
/*  485 */         return "authorizationError";
/*      */       case 17:
/*  487 */         return "notWritable";
/*      */       case 18:
/*  489 */         return "inconsistentName";
/*      */       case 224:
/*  491 */         return "reqTimeout";
/*      */       case 225:
/*  493 */         return "reqAborted";
/*      */       case 226:
/*  495 */         return "rspDecodingError";
/*      */       case 227:
/*  497 */         return "reqEncodingError";
/*      */       case 228:
/*  499 */         return "reqPacketOverflow";
/*      */       case 229:
/*  501 */         return "rspEndOfTable";
/*      */       case 230:
/*  503 */         return "reqRefireAfterVbFix";
/*      */       case 231:
/*  505 */         return "reqHandleTooBig";
/*      */       case 232:
/*  507 */         return "reqTooBigImpossible";
/*      */       case 240:
/*  509 */         return "reqInternalError";
/*      */       case 241:
/*  511 */         return "reqSocketIOError";
/*      */       case 242:
/*  513 */         return "reqUnknownError";
/*      */       case 243:
/*  515 */         return "wrongSnmpVersion";
/*      */       case 244:
/*  517 */         return "snmpUnknownPrincipal";
/*      */       case 245:
/*  519 */         return "snmpAuthNotSupported";
/*      */       case 246:
/*  521 */         return "snmpPrivNotSupported";
/*      */       case 249:
/*  523 */         return "snmpBadSecurityLevel";
/*      */       case 247:
/*  525 */         return "snmpUsmBadEngineId";
/*      */       case 248:
/*  527 */         return "snmpUsmInvalidTimeliness";
/*      */     } 
/*  529 */     return "Unknown Error = " + paramInt;
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
/*      */   synchronized void start(SnmpVarBindList paramSnmpVarBindList) throws SnmpStatusException {
/*  543 */     if (inProgress())
/*  544 */       throw new SnmpStatusException("Inform request already in progress."); 
/*  545 */     setVarBindList(paramSnmpVarBindList);
/*  546 */     initializeAndFire();
/*      */   }
/*      */   
/*      */   private synchronized void initializeAndFire() {
/*  550 */     this.requestPdu = null;
/*  551 */     this.responsePdu = null;
/*  552 */     this.reason = null;
/*  553 */     startRequest(System.currentTimeMillis());
/*  554 */     setErrorStatusAndIndex(0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void startRequest(long paramLong) {
/*  564 */     this.nextPollTime = paramLong;
/*  565 */     this.prevPollTime = 0L;
/*  566 */     schedulePoll();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void schedulePoll() {
/*  573 */     this.numTries = 0;
/*  574 */     initNewRequest();
/*  575 */     setRequestStatus(3);
/*  576 */     this.informSession.getSnmpQManager().addRequest(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void action() {
/*  585 */     if (!inProgress())
/*      */       return; 
/*      */     while (true) {
/*      */       try {
/*  589 */         if (this.numTries == 0) {
/*  590 */           invokeOnReady();
/*  591 */         } else if (this.numTries < getMaxTries()) {
/*  592 */           invokeOnRetry();
/*      */         } else {
/*  594 */           invokeOnTimeout();
/*      */         } 
/*      */         return;
/*  597 */       } catch (OutOfMemoryError outOfMemoryError) {
/*      */ 
/*      */         
/*  600 */         this.numTries++;
/*  601 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  602 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "action", "Inform request hit out of memory situation...");
/*      */         }
/*      */         
/*  605 */         Thread.yield();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void invokeOnReady() {
/*  611 */     if (this.requestPdu == null) {
/*  612 */       this.requestPdu = constructPduPacket();
/*      */     }
/*  614 */     if (this.requestPdu != null && 
/*  615 */       !sendPdu()) {
/*  616 */       queueResponse();
/*      */     }
/*      */   }
/*      */   
/*      */   private void invokeOnRetry() {
/*  621 */     invokeOnReady();
/*      */   }
/*      */   
/*      */   private void invokeOnTimeout() {
/*  625 */     this.errorStatus = 224;
/*  626 */     queueResponse();
/*      */   }
/*      */   
/*      */   private void queueResponse() {
/*  630 */     this.informSession.addResponse(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized SnmpPdu constructPduPacket() {
/*  637 */     SnmpPduRequest snmpPduRequest = null;
/*  638 */     Exception exception = null;
/*      */     try {
/*  640 */       snmpPduRequest = new SnmpPduRequest();
/*  641 */       snmpPduRequest.port = this.port;
/*  642 */       snmpPduRequest.type = 166;
/*  643 */       snmpPduRequest.version = 1;
/*  644 */       snmpPduRequest.community = this.communityString.getBytes("8859_1");
/*  645 */       snmpPduRequest.requestId = getRequestId();
/*  646 */       snmpPduRequest.varBindList = this.internalVarBind;
/*      */       
/*  648 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  649 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpInformRequest.class.getName(), "constructPduPacket", "Packet built");
/*      */       
/*      */       }
/*      */     }
/*  653 */     catch (Exception exception1) {
/*  654 */       exception = exception1;
/*  655 */       this.errorStatus = 242;
/*  656 */       this.reason = exception1.getMessage();
/*      */     } 
/*  658 */     if (exception != null) {
/*  659 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  660 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "constructPduPacket", "Got unexpected exception", exception);
/*      */       }
/*      */       
/*  663 */       snmpPduRequest = null;
/*  664 */       queueResponse();
/*      */     } 
/*  666 */     return snmpPduRequest;
/*      */   }
/*      */   
/*      */   boolean sendPdu() {
/*      */     try {
/*  671 */       this.responsePdu = null;
/*      */       
/*  673 */       SnmpPduFactory snmpPduFactory = this.adaptor.getPduFactory();
/*  674 */       SnmpMessage snmpMessage = (SnmpMessage)snmpPduFactory.encodeSnmpPdu(this.requestPdu, this.adaptor.getBufferSize().intValue());
/*      */       
/*  676 */       if (snmpMessage == null) {
/*  677 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  678 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "sendPdu", "pdu factory returned a null value");
/*      */         }
/*      */         
/*  681 */         throw new SnmpStatusException(242);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  686 */       int i = this.adaptor.getBufferSize().intValue();
/*  687 */       byte[] arrayOfByte = new byte[i];
/*  688 */       int j = snmpMessage.encodeMessage(arrayOfByte);
/*      */       
/*  690 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  691 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpInformRequest.class.getName(), "sendPdu", "Dump : \n" + snmpMessage
/*  692 */             .printMessage());
/*      */       }
/*      */       
/*  695 */       sendPduPacket(arrayOfByte, j);
/*  696 */       return true;
/*  697 */     } catch (SnmpTooBigException snmpTooBigException) {
/*      */       
/*  699 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  700 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "sendPdu", "Got unexpected exception", snmpTooBigException);
/*      */       }
/*      */ 
/*      */       
/*  704 */       setErrorStatusAndIndex(228, snmpTooBigException.getVarBindCount());
/*  705 */       this.requestPdu = null;
/*  706 */       this.reason = snmpTooBigException.getMessage();
/*  707 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  708 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "sendPdu", "Packet Overflow while building inform request");
/*      */       }
/*      */     }
/*  711 */     catch (IOException iOException) {
/*  712 */       setErrorStatusAndIndex(241, 0);
/*  713 */       this.reason = iOException.getMessage();
/*  714 */     } catch (Exception exception) {
/*  715 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  716 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "sendPdu", "Got unexpected exception", exception);
/*      */       }
/*      */       
/*  719 */       setErrorStatusAndIndex(242, 0);
/*  720 */       this.reason = exception.getMessage();
/*      */     } 
/*  722 */     return false;
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
/*      */   final void sendPduPacket(byte[] paramArrayOfbyte, int paramInt) throws IOException {
/*  734 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  735 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpInformRequest.class.getName(), "sendPduPacket", "Send to peer. Peer/Port : " + this.address
/*  736 */           .getHostName() + "/" + this.port + ". Length = " + paramInt + "\nDump : \n" + 
/*      */           
/*  738 */           SnmpMessage.dumpHexBuffer(paramArrayOfbyte, 0, paramInt));
/*      */     }
/*  740 */     SnmpSocket snmpSocket = this.informSession.getSocket();
/*  741 */     synchronized (snmpSocket) {
/*  742 */       snmpSocket.sendPacket(paramArrayOfbyte, paramInt, this.address, this.port);
/*  743 */       setRequestSentTime(System.currentTimeMillis());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void processResponse() {
/*  752 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  753 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpInformRequest.class.getName(), "processResponse", "errstatus = " + this.errorStatus);
/*      */     }
/*      */ 
/*      */     
/*  757 */     if (!inProgress()) {
/*  758 */       this.responsePdu = null;
/*      */       
/*      */       return;
/*      */     } 
/*  762 */     if (this.errorStatus >= 240) {
/*  763 */       handleInternalError("Internal Error...");
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/*  768 */       parsePduPacket(this.responsePdu);
/*      */ 
/*      */ 
/*      */       
/*  772 */       switch (this.errorStatus) {
/*      */         case 0:
/*  774 */           handleSuccess();
/*      */           return;
/*      */         case 224:
/*  777 */           handleTimeout();
/*      */           return;
/*      */         case 240:
/*  780 */           handleInternalError("Unknown internal error.  deal with it later!");
/*      */           return;
/*      */         case 231:
/*  783 */           setErrorStatusAndIndex(1, 0);
/*  784 */           handleError("Cannot handle too-big situation...");
/*      */           return;
/*      */         
/*      */         case 230:
/*  788 */           initializeAndFire();
/*      */           return;
/*      */       } 
/*  791 */       handleError("Error status set in packet...!!");
/*      */       
/*      */       return;
/*  794 */     } catch (Exception exception) {
/*  795 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  796 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "processResponse", "Got unexpected exception", exception);
/*      */       }
/*      */       
/*  799 */       this.reason = exception.getMessage();
/*      */       
/*  801 */       handleInternalError(this.reason);
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void parsePduPacket(SnmpPduRequestType paramSnmpPduRequestType) {
/*  810 */     if (paramSnmpPduRequestType == null) {
/*      */       return;
/*      */     }
/*  813 */     this.errorStatus = paramSnmpPduRequestType.getErrorStatus();
/*  814 */     this.errorIndex = paramSnmpPduRequestType.getErrorIndex();
/*      */     
/*  816 */     if (this.errorStatus == 0) {
/*  817 */       updateInternalVarBindWithResult(((SnmpPdu)paramSnmpPduRequestType).varBindList);
/*      */       
/*      */       return;
/*      */     } 
/*  821 */     if (this.errorStatus != 0) {
/*  822 */       this.errorIndex--;
/*      */     }
/*  824 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  825 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpInformRequest.class.getName(), "parsePduPacket", "received inform response. ErrorStatus/ErrorIndex = " + this.errorStatus + "/" + this.errorIndex);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleSuccess() {
/*  836 */     setRequestStatus(128);
/*      */     
/*  838 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  839 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpInformRequest.class.getName(), "handleSuccess", "Invoking user defined callback...");
/*      */     }
/*      */ 
/*      */     
/*  843 */     deleteRequest();
/*  844 */     notifyClient();
/*      */     
/*  846 */     this.requestPdu = null;
/*      */     
/*  848 */     this.internalVarBind = null;
/*      */     
/*      */     try {
/*  851 */       if (this.callback != null)
/*  852 */         this.callback.processSnmpPollData(this, this.errorStatus, this.errorIndex, getVarBindList()); 
/*  853 */     } catch (Exception exception) {
/*  854 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  855 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "handleSuccess", "Exception generated by user callback", exception);
/*      */       }
/*      */     }
/*  858 */     catch (OutOfMemoryError outOfMemoryError) {
/*  859 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  860 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "handleSuccess", "OutOfMemory Error generated by user callback", outOfMemoryError);
/*      */       }
/*      */       
/*  863 */       Thread.yield();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleTimeout() {
/*  872 */     setRequestStatus(32);
/*      */     
/*  874 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  875 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "handleTimeout", "Snmp error/index = " + 
/*  876 */           snmpErrorToString(this.errorStatus) + "/" + this.errorIndex + ". Invoking timeout user defined callback...");
/*      */     }
/*      */     
/*  879 */     deleteRequest();
/*  880 */     notifyClient();
/*      */     
/*  882 */     this.requestPdu = null;
/*  883 */     this.responsePdu = null;
/*  884 */     this.internalVarBind = null;
/*      */     
/*      */     try {
/*  887 */       if (this.callback != null)
/*  888 */         this.callback.processSnmpPollTimeout(this); 
/*  889 */     } catch (Exception exception) {
/*  890 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  891 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "handleTimeout", "Exception generated by user callback", exception);
/*      */       }
/*      */     }
/*  894 */     catch (OutOfMemoryError outOfMemoryError) {
/*  895 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  896 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "handleTimeout", "OutOfMemory Error generated by user callback", outOfMemoryError);
/*      */       }
/*      */       
/*  899 */       Thread.yield();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleError(String paramString) {
/*  908 */     setRequestStatus(128);
/*      */     
/*  910 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  911 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "handleError", "Snmp error/index = " + 
/*  912 */           snmpErrorToString(this.errorStatus) + "/" + this.errorIndex + ". Invoking error user defined callback...\n" + 
/*  913 */           getVarBindList());
/*      */     }
/*  915 */     deleteRequest();
/*  916 */     notifyClient();
/*      */     
/*  918 */     this.requestPdu = null;
/*  919 */     this.responsePdu = null;
/*  920 */     this.internalVarBind = null;
/*      */     
/*      */     try {
/*  923 */       if (this.callback != null)
/*  924 */         this.callback.processSnmpPollData(this, getErrorStatus(), getErrorIndex(), getVarBindList()); 
/*  925 */     } catch (Exception exception) {
/*  926 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  927 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "handleError", "Exception generated by user callback", exception);
/*      */       }
/*      */     }
/*  930 */     catch (OutOfMemoryError outOfMemoryError) {
/*  931 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  932 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "handleError", "OutOfMemory Error generated by user callback", outOfMemoryError);
/*      */       }
/*      */       
/*  935 */       Thread.yield();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleInternalError(String paramString) {
/*  944 */     setRequestStatus(64);
/*  945 */     if (this.reason == null) {
/*  946 */       this.reason = paramString;
/*      */     }
/*  948 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  949 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "handleInternalError", "Snmp error/index = " + 
/*  950 */           snmpErrorToString(this.errorStatus) + "/" + this.errorIndex + ". Invoking internal error user defined callback...\n" + 
/*      */           
/*  952 */           getVarBindList());
/*      */     }
/*      */     
/*  955 */     deleteRequest();
/*  956 */     notifyClient();
/*      */     
/*  958 */     this.requestPdu = null;
/*  959 */     this.responsePdu = null;
/*  960 */     this.internalVarBind = null;
/*      */     
/*      */     try {
/*  963 */       if (this.callback != null)
/*  964 */         this.callback.processSnmpInternalError(this, this.reason); 
/*  965 */     } catch (Exception exception) {
/*  966 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  967 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "handleInternalError", "Exception generated by user callback", exception);
/*      */       }
/*      */     }
/*  970 */     catch (OutOfMemoryError outOfMemoryError) {
/*  971 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  972 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpInformRequest.class.getName(), "handleInternalError", "OutOfMemory Error generated by user callback", outOfMemoryError);
/*      */       }
/*      */       
/*  975 */       Thread.yield();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void updateInternalVarBindWithResult(SnmpVarBind[] paramArrayOfSnmpVarBind) {
/*  981 */     if (paramArrayOfSnmpVarBind == null || paramArrayOfSnmpVarBind.length == 0) {
/*      */       return;
/*      */     }
/*  984 */     byte b1 = 0;
/*      */     
/*  986 */     for (byte b2 = 0; b2 < this.internalVarBind.length && b1 < paramArrayOfSnmpVarBind.length; b2++) {
/*  987 */       SnmpVarBind snmpVarBind = this.internalVarBind[b2];
/*  988 */       if (snmpVarBind != null) {
/*      */ 
/*      */         
/*  991 */         SnmpVarBind snmpVarBind1 = paramArrayOfSnmpVarBind[b1];
/*  992 */         snmpVarBind.setSnmpValue(snmpVarBind1.getSnmpValue());
/*  993 */         b1++;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final void invokeOnResponse(Object paramObject) {
/* 1001 */     if (paramObject != null)
/* 1002 */       if (paramObject instanceof SnmpPduRequestType) {
/* 1003 */         this.responsePdu = (SnmpPduRequestType)paramObject;
/*      */       } else {
/*      */         return;
/*      */       }  
/* 1007 */     setRequestStatus(9);
/* 1008 */     queueResponse();
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
/*      */   private void stopRequest() {
/* 1021 */     synchronized (this) {
/* 1022 */       setRequestStatus(16);
/*      */     } 
/* 1024 */     this.informSession.getSnmpQManager().removeRequest(this);
/* 1025 */     synchronized (this) {
/* 1026 */       this.requestId = 0;
/*      */     } 
/*      */   }
/*      */   
/*      */   final synchronized void deleteRequest() {
/* 1031 */     this.informSession.removeInformRequest(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final synchronized SnmpVarBindList getVarBindList() {
/* 1041 */     return this.varBindList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final synchronized void setVarBindList(SnmpVarBindList paramSnmpVarBindList) {
/* 1050 */     this.varBindList = paramSnmpVarBindList;
/* 1051 */     if (this.internalVarBind == null || this.internalVarBind.length != this.varBindList.size()) {
/* 1052 */       this.internalVarBind = new SnmpVarBind[this.varBindList.size()];
/*      */     }
/* 1054 */     this.varBindList.copyInto((Object[])this.internalVarBind);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final synchronized void setErrorStatusAndIndex(int paramInt1, int paramInt2) {
/* 1061 */     this.errorStatus = paramInt1;
/* 1062 */     this.errorIndex = paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final synchronized void setPrevPollTime(long paramLong) {
/* 1069 */     this.prevPollTime = paramLong;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void setRequestSentTime(long paramLong) {
/* 1076 */     this.numTries++;
/* 1077 */     setPrevPollTime(paramLong);
/* 1078 */     this.waitTimeForResponse = this.prevPollTime + (this.timeout * this.numTries);
/* 1079 */     setRequestStatus(5);
/*      */     
/* 1081 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1082 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpInformRequest.class.getName(), "setRequestSentTime", "Inform request Successfully sent");
/*      */     }
/*      */ 
/*      */     
/* 1086 */     this.informSession.getSnmpQManager().addWaiting(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final synchronized void initNewRequest() {
/* 1093 */     this.requestId = requestCounter.getNewId();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   long timeRemainingForAction(long paramLong) {
/* 1100 */     switch (this.reqState) {
/*      */       case 3:
/* 1102 */         return this.nextPollTime - paramLong;
/*      */       case 5:
/* 1104 */         return this.waitTimeForResponse - paramLong;
/*      */     } 
/* 1106 */     return -1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String statusDescription(int paramInt) {
/* 1116 */     switch (paramInt) {
/*      */       case 3:
/* 1118 */         return "Waiting to send.";
/*      */       case 5:
/* 1120 */         return "Waiting for reply.";
/*      */       case 9:
/* 1122 */         return "Response arrived.";
/*      */       case 16:
/* 1124 */         return "Aborted by user.";
/*      */       case 32:
/* 1126 */         return "Timeout Occured.";
/*      */       case 64:
/* 1128 */         return "Internal error.";
/*      */       case 128:
/* 1130 */         return "Results available";
/*      */       case 256:
/* 1132 */         return "Inform request in createAndWait state";
/*      */     } 
/* 1134 */     return "Unknown inform request state.";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final synchronized void setRequestStatus(int paramInt) {
/* 1142 */     this.reqState = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized String toString() {
/* 1151 */     StringBuffer stringBuffer = new StringBuffer(300);
/* 1152 */     stringBuffer.append(tostring());
/* 1153 */     stringBuffer.append("\nPeer/Port : " + this.address.getHostName() + "/" + this.port);
/*      */     
/* 1155 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   private synchronized String tostring() {
/* 1159 */     StringBuffer stringBuffer = new StringBuffer("InformRequestId = " + this.requestId);
/* 1160 */     stringBuffer.append("   Status = " + statusDescription(this.reqState));
/* 1161 */     stringBuffer.append("  Timeout/MaxTries/NumTries = " + (this.timeout * this.numTries) + "/" + 
/* 1162 */         getMaxTries() + "/" + this.numTries);
/*      */     
/* 1164 */     if (this.prevPollTime > 0L) {
/* 1165 */       this.debugDate.setTime(this.prevPollTime);
/* 1166 */       stringBuffer.append("\nPrevPolled = " + this.debugDate.toString());
/*      */     } else {
/* 1168 */       stringBuffer.append("\nNeverPolled");
/* 1169 */     }  stringBuffer.append(" / RemainingTime(millis) = " + 
/* 1170 */         timeRemainingForAction(System.currentTimeMillis()));
/*      */     
/* 1172 */     return stringBuffer.toString();
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpInformRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */