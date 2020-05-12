/*     */ package com.sun.jmx.snmp;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnmpV3Message
/*     */   extends SnmpMsg
/*     */ {
/* 101 */   public int msgId = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public int msgMaxSize = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public byte msgFlags = 0;
/*     */ 
/*     */ 
/*     */   
/* 124 */   public int msgSecurityModel = 0;
/*     */ 
/*     */ 
/*     */   
/* 128 */   public byte[] msgSecurityParameters = null;
/*     */ 
/*     */ 
/*     */   
/* 132 */   public byte[] contextEngineId = null;
/*     */ 
/*     */ 
/*     */   
/* 136 */   public byte[] contextName = null;
/*     */ 
/*     */   
/* 139 */   public byte[] encryptedPdu = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int encodeMessage(byte[] paramArrayOfbyte) throws SnmpTooBigException {
/* 158 */     boolean bool = false;
/* 159 */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINER)) {
/* 160 */       JmxProperties.SNMP_LOGGER.logp(Level.FINER, SnmpV3Message.class.getName(), "encodeMessage", "Can't encode directly V3Message! Need a SecuritySubSystem");
/*     */     }
/*     */ 
/*     */     
/* 164 */     throw new IllegalArgumentException("Can't encode");
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
/*     */   public void decodeMessage(byte[] paramArrayOfbyte, int paramInt) throws SnmpStatusException {
/*     */     try {
/* 179 */       BerDecoder berDecoder = new BerDecoder(paramArrayOfbyte);
/* 180 */       berDecoder.openSequence();
/* 181 */       this.version = berDecoder.fetchInteger();
/* 182 */       berDecoder.openSequence();
/* 183 */       this.msgId = berDecoder.fetchInteger();
/* 184 */       this.msgMaxSize = berDecoder.fetchInteger();
/* 185 */       this.msgFlags = berDecoder.fetchOctetString()[0];
/* 186 */       this.msgSecurityModel = berDecoder.fetchInteger();
/* 187 */       berDecoder.closeSequence();
/* 188 */       this.msgSecurityParameters = berDecoder.fetchOctetString();
/* 189 */       if ((this.msgFlags & 0x2) == 0) {
/* 190 */         berDecoder.openSequence();
/* 191 */         this.contextEngineId = berDecoder.fetchOctetString();
/* 192 */         this.contextName = berDecoder.fetchOctetString();
/* 193 */         this.data = berDecoder.fetchAny();
/* 194 */         this.dataLength = this.data.length;
/* 195 */         berDecoder.closeSequence();
/*     */       } else {
/*     */         
/* 198 */         this.encryptedPdu = berDecoder.fetchOctetString();
/*     */       } 
/* 200 */       berDecoder.closeSequence();
/*     */     }
/* 202 */     catch (BerException berException) {
/* 203 */       berException.printStackTrace();
/* 204 */       throw new SnmpStatusException("Invalid encoding");
/*     */     } 
/*     */     
/* 207 */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINER)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 230 */       StringBuilder stringBuilder = (new StringBuilder()).append("Unmarshalled message : \n").append("version : ").append(this.version).append("\n").append("msgId : ").append(this.msgId).append("\n").append("msgMaxSize : ").append(this.msgMaxSize).append("\n").append("msgFlags : ").append(this.msgFlags).append("\n").append("msgSecurityModel : ").append(this.msgSecurityModel).append("\n").append("contextEngineId : ").append((this.contextEngineId == null) ? null : SnmpEngineId.createEngineId(this.contextEngineId)).append("\n").append("contextName : ").append(this.contextName).append("\n").append("data : ").append(this.data).append("\n").append("dat len : ").append((this.data == null) ? 0 : this.data.length).append("\n").append("encryptedPdu : ").append(this.encryptedPdu).append("\n");
/* 231 */       JmxProperties.SNMP_LOGGER.logp(Level.FINER, SnmpV3Message.class.getName(), "decodeMessage", stringBuilder
/* 232 */           .toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequestId(byte[] paramArrayOfbyte) throws SnmpStatusException {
/* 242 */     BerDecoder berDecoder = null;
/* 243 */     int i = 0;
/*     */     try {
/* 245 */       berDecoder = new BerDecoder(paramArrayOfbyte);
/* 246 */       berDecoder.openSequence();
/* 247 */       berDecoder.fetchInteger();
/* 248 */       berDecoder.openSequence();
/* 249 */       i = berDecoder.fetchInteger();
/* 250 */     } catch (BerException berException) {
/* 251 */       throw new SnmpStatusException("Invalid encoding");
/*     */     } 
/*     */     try {
/* 254 */       berDecoder.closeSequence();
/*     */     }
/* 256 */     catch (BerException berException) {}
/*     */ 
/*     */     
/* 259 */     return i;
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
/*     */   public void encodeSnmpPdu(SnmpPdu paramSnmpPdu, int paramInt) throws SnmpStatusException, SnmpTooBigException {
/* 287 */     SnmpScopedPduPacket snmpScopedPduPacket = (SnmpScopedPduPacket)paramSnmpPdu;
/*     */     
/* 289 */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINER)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 311 */       StringBuilder stringBuilder = (new StringBuilder()).append("PDU to marshall: \n").append("security parameters : ").append(snmpScopedPduPacket.securityParameters).append("\n").append("type : ").append(snmpScopedPduPacket.type).append("\n").append("version : ").append(snmpScopedPduPacket.version).append("\n").append("requestId : ").append(snmpScopedPduPacket.requestId).append("\n").append("msgId : ").append(snmpScopedPduPacket.msgId).append("\n").append("msgMaxSize : ").append(snmpScopedPduPacket.msgMaxSize).append("\n").append("msgFlags : ").append(snmpScopedPduPacket.msgFlags).append("\n").append("msgSecurityModel : ").append(snmpScopedPduPacket.msgSecurityModel).append("\n").append("contextEngineId : ").append(snmpScopedPduPacket.contextEngineId).append("\n").append("contextName : ").append(snmpScopedPduPacket.contextName).append("\n");
/* 312 */       JmxProperties.SNMP_LOGGER.logp(Level.FINER, SnmpV3Message.class.getName(), "encodeSnmpPdu", stringBuilder
/* 313 */           .toString());
/*     */     } 
/*     */     
/* 316 */     this.version = snmpScopedPduPacket.version;
/* 317 */     this.address = snmpScopedPduPacket.address;
/* 318 */     this.port = snmpScopedPduPacket.port;
/* 319 */     this.msgId = snmpScopedPduPacket.msgId;
/* 320 */     this.msgMaxSize = snmpScopedPduPacket.msgMaxSize;
/* 321 */     this.msgFlags = snmpScopedPduPacket.msgFlags;
/* 322 */     this.msgSecurityModel = snmpScopedPduPacket.msgSecurityModel;
/*     */     
/* 324 */     this.contextEngineId = snmpScopedPduPacket.contextEngineId;
/* 325 */     this.contextName = snmpScopedPduPacket.contextName;
/*     */     
/* 327 */     this.securityParameters = snmpScopedPduPacket.securityParameters;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 332 */     this.data = new byte[paramInt];
/*     */ 
/*     */     
/*     */     try {
/*     */       SnmpPduRequestType snmpPduRequestType;
/*     */       
/*     */       SnmpPduBulkType snmpPduBulkType;
/*     */       
/* 340 */       BerEncoder berEncoder = new BerEncoder(this.data);
/* 341 */       berEncoder.openSequence();
/* 342 */       encodeVarBindList(berEncoder, snmpScopedPduPacket.varBindList);
/*     */       
/* 344 */       switch (snmpScopedPduPacket.type) {
/*     */         
/*     */         case 160:
/*     */         case 161:
/*     */         case 162:
/*     */         case 163:
/*     */         case 166:
/*     */         case 167:
/*     */         case 168:
/* 353 */           snmpPduRequestType = (SnmpPduRequestType)snmpScopedPduPacket;
/* 354 */           berEncoder.putInteger(snmpPduRequestType.getErrorIndex());
/* 355 */           berEncoder.putInteger(snmpPduRequestType.getErrorStatus());
/* 356 */           berEncoder.putInteger(snmpScopedPduPacket.requestId);
/*     */           break;
/*     */         
/*     */         case 165:
/* 360 */           snmpPduBulkType = (SnmpPduBulkType)snmpScopedPduPacket;
/* 361 */           berEncoder.putInteger(snmpPduBulkType.getMaxRepetitions());
/* 362 */           berEncoder.putInteger(snmpPduBulkType.getNonRepeaters());
/* 363 */           berEncoder.putInteger(snmpScopedPduPacket.requestId);
/*     */           break;
/*     */         
/*     */         default:
/* 367 */           throw new SnmpStatusException("Invalid pdu type " + String.valueOf(snmpScopedPduPacket.type));
/*     */       } 
/* 369 */       berEncoder.closeSequence(snmpScopedPduPacket.type);
/* 370 */       this.dataLength = berEncoder.trim();
/*     */     }
/* 372 */     catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 373 */       throw new SnmpTooBigException();
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
/*     */   public SnmpPdu decodeSnmpPdu() throws SnmpStatusException {
/*     */     SnmpScopedPduBulk snmpScopedPduBulk;
/* 390 */     SnmpScopedPduRequest snmpScopedPduRequest = null;
/*     */     
/* 392 */     BerDecoder berDecoder = new BerDecoder(this.data); try {
/*     */       SnmpScopedPduRequest snmpScopedPduRequest1; SnmpScopedPduBulk snmpScopedPduBulk1;
/* 394 */       int i = berDecoder.getTag();
/* 395 */       berDecoder.openSequence(i);
/* 396 */       switch (i) {
/*     */         
/*     */         case 160:
/*     */         case 161:
/*     */         case 162:
/*     */         case 163:
/*     */         case 166:
/*     */         case 167:
/*     */         case 168:
/* 405 */           snmpScopedPduRequest1 = new SnmpScopedPduRequest();
/* 406 */           snmpScopedPduRequest1.requestId = berDecoder.fetchInteger();
/* 407 */           snmpScopedPduRequest1.setErrorStatus(berDecoder.fetchInteger());
/* 408 */           snmpScopedPduRequest1.setErrorIndex(berDecoder.fetchInteger());
/* 409 */           snmpScopedPduRequest = snmpScopedPduRequest1;
/*     */           break;
/*     */         
/*     */         case 165:
/* 413 */           snmpScopedPduBulk1 = new SnmpScopedPduBulk();
/* 414 */           snmpScopedPduBulk1.requestId = berDecoder.fetchInteger();
/* 415 */           snmpScopedPduBulk1.setNonRepeaters(berDecoder.fetchInteger());
/* 416 */           snmpScopedPduBulk1.setMaxRepetitions(berDecoder.fetchInteger());
/* 417 */           snmpScopedPduBulk = snmpScopedPduBulk1;
/*     */           break;
/*     */         default:
/* 420 */           throw new SnmpStatusException(9);
/*     */       } 
/* 422 */       snmpScopedPduBulk.type = i;
/* 423 */       snmpScopedPduBulk.varBindList = decodeVarBindList(berDecoder);
/* 424 */       berDecoder.closeSequence();
/* 425 */     } catch (BerException berException) {
/* 426 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 427 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, SnmpV3Message.class.getName(), "decodeSnmpPdu", "BerException", berException);
/*     */       }
/*     */       
/* 430 */       throw new SnmpStatusException(9);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     snmpScopedPduBulk.address = this.address;
/* 437 */     snmpScopedPduBulk.port = this.port;
/* 438 */     snmpScopedPduBulk.msgFlags = this.msgFlags;
/* 439 */     snmpScopedPduBulk.version = this.version;
/* 440 */     snmpScopedPduBulk.msgId = this.msgId;
/* 441 */     snmpScopedPduBulk.msgMaxSize = this.msgMaxSize;
/* 442 */     snmpScopedPduBulk.msgSecurityModel = this.msgSecurityModel;
/* 443 */     snmpScopedPduBulk.contextEngineId = this.contextEngineId;
/* 444 */     snmpScopedPduBulk.contextName = this.contextName;
/*     */     
/* 446 */     snmpScopedPduBulk.securityParameters = this.securityParameters;
/*     */     
/* 448 */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINER)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 468 */       StringBuilder stringBuilder = (new StringBuilder()).append("Unmarshalled PDU : \n").append("type : ").append(snmpScopedPduBulk.type).append("\n").append("version : ").append(snmpScopedPduBulk.version).append("\n").append("requestId : ").append(snmpScopedPduBulk.requestId).append("\n").append("msgId : ").append(snmpScopedPduBulk.msgId).append("\n").append("msgMaxSize : ").append(snmpScopedPduBulk.msgMaxSize).append("\n").append("msgFlags : ").append(snmpScopedPduBulk.msgFlags).append("\n").append("msgSecurityModel : ").append(snmpScopedPduBulk.msgSecurityModel).append("\n").append("contextEngineId : ").append(snmpScopedPduBulk.contextEngineId).append("\n").append("contextName : ").append(snmpScopedPduBulk.contextName).append("\n");
/* 469 */       JmxProperties.SNMP_LOGGER.logp(Level.FINER, SnmpV3Message.class.getName(), "decodeSnmpPdu", stringBuilder
/* 470 */           .toString());
/*     */     } 
/* 472 */     return snmpScopedPduBulk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String printMessage() {
/* 481 */     StringBuffer stringBuffer = new StringBuffer();
/* 482 */     stringBuffer.append("msgId : " + this.msgId + "\n");
/* 483 */     stringBuffer.append("msgMaxSize : " + this.msgMaxSize + "\n");
/* 484 */     stringBuffer.append("msgFlags : " + this.msgFlags + "\n");
/* 485 */     stringBuffer.append("msgSecurityModel : " + this.msgSecurityModel + "\n");
/*     */     
/* 487 */     if (this.contextEngineId == null) {
/* 488 */       stringBuffer.append("contextEngineId : null");
/*     */     } else {
/*     */       
/* 491 */       stringBuffer.append("contextEngineId : {\n");
/* 492 */       stringBuffer.append(dumpHexBuffer(this.contextEngineId, 0, this.contextEngineId.length));
/*     */ 
/*     */       
/* 495 */       stringBuffer.append("\n}\n");
/*     */     } 
/*     */     
/* 498 */     if (this.contextName == null) {
/* 499 */       stringBuffer.append("contextName : null");
/*     */     } else {
/*     */       
/* 502 */       stringBuffer.append("contextName : {\n");
/* 503 */       stringBuffer.append(dumpHexBuffer(this.contextName, 0, this.contextName.length));
/*     */ 
/*     */       
/* 506 */       stringBuffer.append("\n}\n");
/*     */     } 
/* 508 */     return stringBuffer.append(super.printMessage()).toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpV3Message.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */