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
/*     */ public class SnmpMessage
/*     */   extends SnmpMsg
/*     */   implements SnmpDefinitions
/*     */ {
/*     */   public byte[] community;
/*     */   
/*     */   public int encodeMessage(byte[] paramArrayOfbyte) throws SnmpTooBigException {
/*  80 */     int i = 0;
/*  81 */     if (this.data == null) {
/*  82 */       throw new IllegalArgumentException("Data field is null");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  88 */       BerEncoder berEncoder = new BerEncoder(paramArrayOfbyte);
/*  89 */       berEncoder.openSequence();
/*  90 */       berEncoder.putAny(this.data, this.dataLength);
/*  91 */       berEncoder.putOctetString((this.community != null) ? this.community : new byte[0]);
/*  92 */       berEncoder.putInteger(this.version);
/*  93 */       berEncoder.closeSequence();
/*  94 */       i = berEncoder.trim();
/*     */     }
/*  96 */     catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/*  97 */       throw new SnmpTooBigException();
/*     */     } 
/*     */     
/* 100 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequestId(byte[] paramArrayOfbyte) throws SnmpStatusException {
/* 110 */     int i = 0;
/* 111 */     BerDecoder berDecoder1 = null;
/* 112 */     BerDecoder berDecoder2 = null;
/* 113 */     byte[] arrayOfByte = null;
/*     */     try {
/* 115 */       berDecoder1 = new BerDecoder(paramArrayOfbyte);
/* 116 */       berDecoder1.openSequence();
/* 117 */       berDecoder1.fetchInteger();
/* 118 */       berDecoder1.fetchOctetString();
/* 119 */       arrayOfByte = berDecoder1.fetchAny();
/* 120 */       berDecoder2 = new BerDecoder(arrayOfByte);
/* 121 */       int j = berDecoder2.getTag();
/* 122 */       berDecoder2.openSequence(j);
/* 123 */       i = berDecoder2.fetchInteger();
/*     */     }
/* 125 */     catch (BerException berException) {
/* 126 */       throw new SnmpStatusException("Invalid encoding");
/*     */     } 
/*     */     try {
/* 129 */       berDecoder1.closeSequence();
/*     */     }
/* 131 */     catch (BerException berException) {}
/*     */     
/*     */     try {
/* 134 */       berDecoder2.closeSequence();
/*     */     }
/* 136 */     catch (BerException berException) {}
/*     */     
/* 138 */     return i;
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
/*     */   public void decodeMessage(byte[] paramArrayOfbyte, int paramInt) throws SnmpStatusException {
/*     */     try {
/* 151 */       BerDecoder berDecoder = new BerDecoder(paramArrayOfbyte);
/* 152 */       berDecoder.openSequence();
/* 153 */       this.version = berDecoder.fetchInteger();
/* 154 */       this.community = berDecoder.fetchOctetString();
/* 155 */       this.data = berDecoder.fetchAny();
/* 156 */       this.dataLength = this.data.length;
/* 157 */       berDecoder.closeSequence();
/*     */     }
/* 159 */     catch (BerException berException) {
/* 160 */       throw new SnmpStatusException("Invalid encoding");
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
/*     */   public void encodeSnmpPdu(SnmpPdu paramSnmpPdu, int paramInt) throws SnmpStatusException, SnmpTooBigException {
/* 190 */     SnmpPduPacket snmpPduPacket = (SnmpPduPacket)paramSnmpPdu;
/* 191 */     this.version = snmpPduPacket.version;
/* 192 */     this.community = snmpPduPacket.community;
/* 193 */     this.address = snmpPduPacket.address;
/* 194 */     this.port = snmpPduPacket.port;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     this.data = new byte[paramInt];
/*     */     
/*     */     try {
/*     */       SnmpPduRequest snmpPduRequest;
/*     */       
/*     */       SnmpPduBulk snmpPduBulk;
/*     */       
/*     */       SnmpPduTrap snmpPduTrap;
/* 207 */       BerEncoder berEncoder = new BerEncoder(this.data);
/* 208 */       berEncoder.openSequence();
/* 209 */       encodeVarBindList(berEncoder, snmpPduPacket.varBindList);
/*     */       
/* 211 */       switch (snmpPduPacket.type) {
/*     */         
/*     */         case 160:
/*     */         case 161:
/*     */         case 162:
/*     */         case 163:
/*     */         case 166:
/*     */         case 167:
/*     */         case 168:
/* 220 */           snmpPduRequest = (SnmpPduRequest)snmpPduPacket;
/* 221 */           berEncoder.putInteger(snmpPduRequest.errorIndex);
/* 222 */           berEncoder.putInteger(snmpPduRequest.errorStatus);
/* 223 */           berEncoder.putInteger(snmpPduRequest.requestId);
/*     */           break;
/*     */         
/*     */         case 165:
/* 227 */           snmpPduBulk = (SnmpPduBulk)snmpPduPacket;
/* 228 */           berEncoder.putInteger(snmpPduBulk.maxRepetitions);
/* 229 */           berEncoder.putInteger(snmpPduBulk.nonRepeaters);
/* 230 */           berEncoder.putInteger(snmpPduBulk.requestId);
/*     */           break;
/*     */         
/*     */         case 164:
/* 234 */           snmpPduTrap = (SnmpPduTrap)snmpPduPacket;
/* 235 */           berEncoder.putInteger(snmpPduTrap.timeStamp, 67);
/* 236 */           berEncoder.putInteger(snmpPduTrap.specificTrap);
/* 237 */           berEncoder.putInteger(snmpPduTrap.genericTrap);
/* 238 */           if (snmpPduTrap.agentAddr != null) {
/* 239 */             berEncoder.putOctetString(snmpPduTrap.agentAddr.byteValue(), 64);
/*     */           } else {
/* 241 */             berEncoder.putOctetString(new byte[0], 64);
/* 242 */           }  berEncoder.putOid(snmpPduTrap.enterprise.longValue());
/*     */           break;
/*     */         
/*     */         default:
/* 246 */           throw new SnmpStatusException("Invalid pdu type " + String.valueOf(snmpPduPacket.type));
/*     */       } 
/* 248 */       berEncoder.closeSequence(snmpPduPacket.type);
/* 249 */       this.dataLength = berEncoder.trim();
/*     */     }
/* 251 */     catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 252 */       throw new SnmpTooBigException();
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
/*     */   public SnmpPdu decodeSnmpPdu() throws SnmpStatusException {
/*     */     SnmpPduTrap snmpPduTrap;
/* 270 */     SnmpPduRequest snmpPduRequest = null;
/* 271 */     BerDecoder berDecoder = new BerDecoder(this.data); try {
/*     */       SnmpPduBulk snmpPduBulk1; SnmpPduRequest snmpPduRequest1; SnmpPduBulk snmpPduBulk2; SnmpPduTrap snmpPduTrap1; byte[] arrayOfByte;
/* 273 */       int i = berDecoder.getTag();
/* 274 */       berDecoder.openSequence(i);
/* 275 */       switch (i) {
/*     */         
/*     */         case 160:
/*     */         case 161:
/*     */         case 162:
/*     */         case 163:
/*     */         case 166:
/*     */         case 167:
/*     */         case 168:
/* 284 */           snmpPduRequest1 = new SnmpPduRequest();
/* 285 */           snmpPduRequest1.requestId = berDecoder.fetchInteger();
/* 286 */           snmpPduRequest1.errorStatus = berDecoder.fetchInteger();
/* 287 */           snmpPduRequest1.errorIndex = berDecoder.fetchInteger();
/* 288 */           snmpPduRequest = snmpPduRequest1;
/*     */           break;
/*     */         
/*     */         case 165:
/* 292 */           snmpPduBulk2 = new SnmpPduBulk();
/* 293 */           snmpPduBulk2.requestId = berDecoder.fetchInteger();
/* 294 */           snmpPduBulk2.nonRepeaters = berDecoder.fetchInteger();
/* 295 */           snmpPduBulk2.maxRepetitions = berDecoder.fetchInteger();
/* 296 */           snmpPduBulk1 = snmpPduBulk2;
/*     */           break;
/*     */         
/*     */         case 164:
/* 300 */           snmpPduTrap1 = new SnmpPduTrap();
/* 301 */           snmpPduTrap1.enterprise = new SnmpOid(berDecoder.fetchOid());
/* 302 */           arrayOfByte = berDecoder.fetchOctetString(64);
/* 303 */           if (arrayOfByte.length != 0) {
/* 304 */             snmpPduTrap1.agentAddr = new SnmpIpAddress(arrayOfByte);
/*     */           } else {
/* 306 */             snmpPduTrap1.agentAddr = null;
/* 307 */           }  snmpPduTrap1.genericTrap = berDecoder.fetchInteger();
/* 308 */           snmpPduTrap1.specificTrap = berDecoder.fetchInteger();
/* 309 */           snmpPduTrap1.timeStamp = berDecoder.fetchInteger(67);
/* 310 */           snmpPduTrap = snmpPduTrap1;
/*     */           break;
/*     */         
/*     */         default:
/* 314 */           throw new SnmpStatusException(9);
/*     */       } 
/* 316 */       snmpPduTrap.type = i;
/* 317 */       snmpPduTrap.varBindList = decodeVarBindList(berDecoder);
/* 318 */       berDecoder.closeSequence();
/* 319 */     } catch (BerException berException) {
/* 320 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 321 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, SnmpMessage.class.getName(), "decodeSnmpPdu", "BerException", berException);
/*     */       }
/*     */       
/* 324 */       throw new SnmpStatusException(9);
/* 325 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 327 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 328 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, SnmpMessage.class.getName(), "decodeSnmpPdu", "IllegalArgumentException", illegalArgumentException);
/*     */       }
/*     */       
/* 331 */       throw new SnmpStatusException(9);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     snmpPduTrap.version = this.version;
/* 338 */     snmpPduTrap.community = this.community;
/* 339 */     snmpPduTrap.address = this.address;
/* 340 */     snmpPduTrap.port = this.port;
/*     */     
/* 342 */     return snmpPduTrap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String printMessage() {
/* 350 */     StringBuffer stringBuffer = new StringBuffer();
/* 351 */     if (this.community == null) {
/* 352 */       stringBuffer.append("Community: null");
/*     */     } else {
/*     */       
/* 355 */       stringBuffer.append("Community: {\n");
/* 356 */       stringBuffer.append(dumpHexBuffer(this.community, 0, this.community.length));
/* 357 */       stringBuffer.append("\n}\n");
/*     */     } 
/* 359 */     return stringBuffer.append(super.printMessage()).toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */