/*     */ package com.sun.jmx.snmp.daemon;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.snmp.SnmpMessage;
/*     */ import com.sun.jmx.snmp.SnmpPduFactory;
/*     */ import com.sun.jmx.snmp.SnmpPduPacket;
/*     */ import com.sun.jmx.snmp.SnmpPduRequest;
/*     */ import java.net.DatagramPacket;
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
/*     */ class SnmpResponseHandler
/*     */ {
/*  33 */   SnmpAdaptorServer adaptor = null;
/*  34 */   SnmpQManager snmpq = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpResponseHandler(SnmpAdaptorServer paramSnmpAdaptorServer, SnmpQManager paramSnmpQManager) {
/*  40 */     this.adaptor = paramSnmpAdaptorServer;
/*  41 */     this.snmpq = paramSnmpQManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void processDatagram(DatagramPacket paramDatagramPacket) {
/*  49 */     byte[] arrayOfByte = paramDatagramPacket.getData();
/*  50 */     int i = paramDatagramPacket.getLength();
/*     */     
/*  52 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  53 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpResponseHandler.class.getName(), "action", "processDatagram", "Received from " + paramDatagramPacket
/*  54 */           .getAddress().toString() + " Length = " + i + "\nDump : \n" + 
/*  55 */           SnmpMessage.dumpHexBuffer(arrayOfByte, 0, i));
/*     */     }
/*     */     
/*     */     try {
/*  59 */       SnmpMessage snmpMessage = new SnmpMessage();
/*  60 */       snmpMessage.decodeMessage(arrayOfByte, i);
/*  61 */       snmpMessage.address = paramDatagramPacket.getAddress();
/*  62 */       snmpMessage.port = paramDatagramPacket.getPort();
/*     */ 
/*     */ 
/*     */       
/*  66 */       SnmpPduFactory snmpPduFactory = this.adaptor.getPduFactory();
/*  67 */       if (snmpPduFactory == null) {
/*  68 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  69 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpResponseHandler.class.getName(), "processDatagram", "Dropping packet. Unable to find the pdu factory of the SNMP adaptor server");
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/*  74 */         SnmpPduPacket snmpPduPacket = (SnmpPduPacket)snmpPduFactory.decodeSnmpPdu(snmpMessage);
/*     */         
/*  76 */         if (snmpPduPacket == null) {
/*  77 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  78 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpResponseHandler.class.getName(), "processDatagram", "Dropping packet. Pdu factory returned a null value");
/*     */           
/*     */           }
/*     */         }
/*  82 */         else if (snmpPduPacket instanceof SnmpPduRequest) {
/*     */           
/*  84 */           SnmpPduRequest snmpPduRequest = (SnmpPduRequest)snmpPduPacket;
/*  85 */           SnmpInformRequest snmpInformRequest = this.snmpq.removeRequest(snmpPduRequest.requestId);
/*  86 */           if (snmpInformRequest != null) {
/*  87 */             snmpInformRequest.invokeOnResponse(snmpPduRequest);
/*     */           }
/*  89 */           else if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  90 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpResponseHandler.class.getName(), "processDatagram", "Dropping packet. Unable to find corresponding for InformRequestId = " + snmpPduRequest.requestId);
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*  96 */         else if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  97 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpResponseHandler.class.getName(), "processDatagram", "Dropping packet. The packet does not contain an inform response");
/*     */         } 
/*     */ 
/*     */         
/* 101 */         snmpPduPacket = null;
/*     */       } 
/* 103 */     } catch (Exception exception) {
/* 104 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST))
/* 105 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpResponseHandler.class.getName(), "processDatagram", "Exception while processsing", exception); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpResponseHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */