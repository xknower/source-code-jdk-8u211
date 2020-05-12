/*     */ package com.sun.jmx.snmp;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SnmpMsg
/*     */   implements SnmpDefinitions
/*     */ {
/*  52 */   public int version = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public byte[] data = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public int dataLength = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public InetAddress address = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public int port = 0;
/*     */ 
/*     */ 
/*     */   
/*  84 */   public SnmpSecurityParameters securityParameters = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getProtocolVersion(byte[] paramArrayOfbyte) throws SnmpStatusException {
/*  92 */     int i = 0;
/*  93 */     BerDecoder berDecoder = null;
/*     */     try {
/*  95 */       berDecoder = new BerDecoder(paramArrayOfbyte);
/*  96 */       berDecoder.openSequence();
/*  97 */       i = berDecoder.fetchInteger();
/*     */     }
/*  99 */     catch (BerException berException) {
/* 100 */       throw new SnmpStatusException("Invalid encoding");
/*     */     } 
/*     */     try {
/* 103 */       berDecoder.closeSequence();
/*     */     }
/* 105 */     catch (BerException berException) {}
/*     */     
/* 107 */     return i;
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
/*     */   public abstract int getRequestId(byte[] paramArrayOfbyte) throws SnmpStatusException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int encodeMessage(byte[] paramArrayOfbyte) throws SnmpTooBigException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void decodeMessage(byte[] paramArrayOfbyte, int paramInt) throws SnmpStatusException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void encodeSnmpPdu(SnmpPdu paramSnmpPdu, int paramInt) throws SnmpStatusException, SnmpTooBigException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract SnmpPdu decodeSnmpPdu() throws SnmpStatusException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String dumpHexBuffer(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 184 */     StringBuffer stringBuffer = new StringBuffer(paramInt2 << 1);
/* 185 */     byte b = 1;
/* 186 */     int i = paramInt1 + paramInt2;
/*     */     
/* 188 */     for (int j = paramInt1; j < i; j++) {
/* 189 */       int k = paramArrayOfbyte[j] & 0xFF;
/* 190 */       stringBuffer.append(Character.forDigit(k >>> 4, 16));
/* 191 */       stringBuffer.append(Character.forDigit(k & 0xF, 16));
/* 192 */       b++;
/* 193 */       if (b % 16 == 0) {
/* 194 */         stringBuffer.append('\n');
/* 195 */         b = 1;
/*     */       } else {
/* 197 */         stringBuffer.append(' ');
/*     */       } 
/* 199 */     }  return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String printMessage() {
/* 208 */     StringBuffer stringBuffer = new StringBuffer();
/* 209 */     stringBuffer.append("Version: ");
/* 210 */     stringBuffer.append(this.version);
/* 211 */     stringBuffer.append("\n");
/* 212 */     if (this.data == null) {
/* 213 */       stringBuffer.append("Data: null");
/*     */     } else {
/*     */       
/* 216 */       stringBuffer.append("Data: {\n");
/* 217 */       stringBuffer.append(dumpHexBuffer(this.data, 0, this.dataLength));
/* 218 */       stringBuffer.append("\n}\n");
/*     */     } 
/*     */     
/* 221 */     return stringBuffer.toString();
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
/*     */   public void encodeVarBindList(BerEncoder paramBerEncoder, SnmpVarBind[] paramArrayOfSnmpVarBind) throws SnmpStatusException, SnmpTooBigException {
/* 233 */     byte b = 0;
/*     */     try {
/* 235 */       paramBerEncoder.openSequence();
/* 236 */       if (paramArrayOfSnmpVarBind != null) {
/* 237 */         for (int i = paramArrayOfSnmpVarBind.length - 1; i >= 0; i--) {
/* 238 */           SnmpVarBind snmpVarBind = paramArrayOfSnmpVarBind[i];
/* 239 */           if (snmpVarBind != null) {
/* 240 */             paramBerEncoder.openSequence();
/* 241 */             encodeVarBindValue(paramBerEncoder, snmpVarBind.value);
/* 242 */             paramBerEncoder.putOid(snmpVarBind.oid.longValue());
/* 243 */             paramBerEncoder.closeSequence();
/* 244 */             b++;
/*     */           } 
/*     */         } 
/*     */       }
/* 248 */       paramBerEncoder.closeSequence();
/*     */     }
/* 250 */     catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 251 */       throw new SnmpTooBigException(b);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void encodeVarBindValue(BerEncoder paramBerEncoder, SnmpValue paramSnmpValue) throws SnmpStatusException {
/* 260 */     if (paramSnmpValue == null) {
/* 261 */       paramBerEncoder.putNull();
/*     */     }
/* 263 */     else if (paramSnmpValue instanceof SnmpIpAddress) {
/* 264 */       paramBerEncoder.putOctetString(((SnmpIpAddress)paramSnmpValue).byteValue(), 64);
/*     */     }
/* 266 */     else if (paramSnmpValue instanceof SnmpCounter) {
/* 267 */       paramBerEncoder.putInteger(((SnmpCounter)paramSnmpValue).longValue(), 65);
/*     */     }
/* 269 */     else if (paramSnmpValue instanceof SnmpGauge) {
/* 270 */       paramBerEncoder.putInteger(((SnmpGauge)paramSnmpValue).longValue(), 66);
/*     */     }
/* 272 */     else if (paramSnmpValue instanceof SnmpTimeticks) {
/* 273 */       paramBerEncoder.putInteger(((SnmpTimeticks)paramSnmpValue).longValue(), 67);
/*     */     }
/* 275 */     else if (paramSnmpValue instanceof SnmpOpaque) {
/* 276 */       paramBerEncoder.putOctetString(((SnmpOpaque)paramSnmpValue).byteValue(), 68);
/*     */     }
/* 278 */     else if (paramSnmpValue instanceof SnmpInt) {
/* 279 */       paramBerEncoder.putInteger(((SnmpInt)paramSnmpValue).intValue());
/*     */     }
/* 281 */     else if (paramSnmpValue instanceof SnmpString) {
/* 282 */       paramBerEncoder.putOctetString(((SnmpString)paramSnmpValue).byteValue());
/*     */     }
/* 284 */     else if (paramSnmpValue instanceof SnmpOid) {
/* 285 */       paramBerEncoder.putOid(((SnmpOid)paramSnmpValue).longValue());
/*     */     }
/* 287 */     else if (paramSnmpValue instanceof SnmpCounter64) {
/* 288 */       if (this.version == 0) {
/* 289 */         throw new SnmpStatusException("Invalid value for SNMP v1 : " + paramSnmpValue);
/*     */       }
/* 291 */       paramBerEncoder.putInteger(((SnmpCounter64)paramSnmpValue).longValue(), 70);
/*     */     }
/* 293 */     else if (paramSnmpValue instanceof SnmpNull) {
/* 294 */       int i = ((SnmpNull)paramSnmpValue).getTag();
/* 295 */       if (this.version == 0 && i != 5) {
/* 296 */         throw new SnmpStatusException("Invalid value for SNMP v1 : " + paramSnmpValue);
/*     */       }
/* 298 */       if (this.version == 1 && i != 5 && i != 128 && i != 129 && i != 130)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 303 */         throw new SnmpStatusException("Invalid value " + paramSnmpValue);
/*     */       }
/* 305 */       paramBerEncoder.putNull(i);
/*     */     } else {
/*     */       
/* 308 */       throw new SnmpStatusException("Invalid value " + paramSnmpValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpVarBind[] decodeVarBindList(BerDecoder paramBerDecoder) throws BerException {
/* 319 */     paramBerDecoder.openSequence();
/* 320 */     Vector<SnmpVarBind> vector = new Vector();
/* 321 */     while (paramBerDecoder.cannotCloseSequence()) {
/* 322 */       SnmpVarBind snmpVarBind = new SnmpVarBind();
/* 323 */       paramBerDecoder.openSequence();
/* 324 */       snmpVarBind.oid = new SnmpOid(paramBerDecoder.fetchOid());
/* 325 */       snmpVarBind.setSnmpValue(decodeVarBindValue(paramBerDecoder));
/* 326 */       paramBerDecoder.closeSequence();
/* 327 */       vector.addElement(snmpVarBind);
/*     */     } 
/* 329 */     paramBerDecoder.closeSequence();
/* 330 */     SnmpVarBind[] arrayOfSnmpVarBind = new SnmpVarBind[vector.size()];
/* 331 */     vector.copyInto((Object[])arrayOfSnmpVarBind);
/* 332 */     return arrayOfSnmpVarBind; } SnmpValue decodeVarBindValue(BerDecoder paramBerDecoder) throws BerException { SnmpString snmpString; SnmpOid snmpOid;
/*     */     SnmpNull snmpNull2;
/*     */     SnmpIpAddress snmpIpAddress;
/*     */     SnmpCounter snmpCounter;
/*     */     SnmpGauge snmpGauge;
/*     */     SnmpTimeticks snmpTimeticks;
/*     */     SnmpOpaque snmpOpaque;
/*     */     SnmpCounter64 snmpCounter64;
/*     */     SnmpNull snmpNull1;
/* 341 */     SnmpInt snmpInt = null;
/* 342 */     int i = paramBerDecoder.getTag();
/*     */ 
/*     */ 
/*     */     
/* 346 */     switch (i) {
/*     */       
/*     */       case 2:
/*     */         
/*     */         try {
/*     */ 
/*     */           
/* 353 */           snmpInt = new SnmpInt(paramBerDecoder.fetchInteger());
/* 354 */         } catch (RuntimeException runtimeException) {
/* 355 */           throw new BerException();
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 473 */         return snmpInt;case 4: try { snmpString = new SnmpString(paramBerDecoder.fetchOctetString()); } catch (RuntimeException runtimeException) { throw new BerException(); }  return snmpString;case 6: try { snmpOid = new SnmpOid(paramBerDecoder.fetchOid()); } catch (RuntimeException runtimeException) { throw new BerException(); }  return snmpOid;case 5: paramBerDecoder.fetchNull(); try { snmpNull2 = new SnmpNull(); } catch (RuntimeException runtimeException) { throw new BerException(); }  return snmpNull2;case 64: try { snmpIpAddress = new SnmpIpAddress(paramBerDecoder.fetchOctetString(i)); } catch (RuntimeException runtimeException) { throw new BerException(); }  return snmpIpAddress;case 65: try { snmpCounter = new SnmpCounter(paramBerDecoder.fetchIntegerAsLong(i)); } catch (RuntimeException runtimeException) { throw new BerException(); }  return snmpCounter;case 66: try { snmpGauge = new SnmpGauge(paramBerDecoder.fetchIntegerAsLong(i)); } catch (RuntimeException runtimeException) { throw new BerException(); }  return snmpGauge;case 67: try { snmpTimeticks = new SnmpTimeticks(paramBerDecoder.fetchIntegerAsLong(i)); } catch (RuntimeException runtimeException) { throw new BerException(); }  return snmpTimeticks;case 68: try { snmpOpaque = new SnmpOpaque(paramBerDecoder.fetchOctetString(i)); } catch (RuntimeException runtimeException) { throw new BerException(); }  return snmpOpaque;case 70: if (this.version == 0) throw new BerException(1);  try { snmpCounter64 = new SnmpCounter64(paramBerDecoder.fetchIntegerAsLong(i)); } catch (RuntimeException runtimeException) { throw new BerException(); }  return snmpCounter64;case 128: if (this.version == 0) throw new BerException(1);  paramBerDecoder.fetchNull(i); snmpNull1 = SnmpVarBind.noSuchObject; return snmpNull1;case 129: if (this.version == 0) throw new BerException(1);  paramBerDecoder.fetchNull(i); snmpNull1 = SnmpVarBind.noSuchInstance; return snmpNull1;case 130: if (this.version == 0) throw new BerException(1);  paramBerDecoder.fetchNull(i); snmpNull1 = SnmpVarBind.endOfMibView; return snmpNull1;
/*     */     } 
/*     */     throw new BerException(); }
/*     */ 
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpMsg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */