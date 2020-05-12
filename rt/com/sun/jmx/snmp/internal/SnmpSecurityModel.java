package com.sun.jmx.snmp.internal;

import com.sun.jmx.snmp.SnmpSecurityException;
import com.sun.jmx.snmp.SnmpSecurityParameters;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpTooBigException;

public interface SnmpSecurityModel extends SnmpModel {
  int generateRequestMsg(SnmpSecurityCache paramSnmpSecurityCache, int paramInt1, int paramInt2, int paramInt3, byte paramByte, int paramInt4, SnmpSecurityParameters paramSnmpSecurityParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt5, byte[] paramArrayOfbyte4) throws SnmpTooBigException, SnmpStatusException, SnmpSecurityException;
  
  int generateResponseMsg(SnmpSecurityCache paramSnmpSecurityCache, int paramInt1, int paramInt2, int paramInt3, byte paramByte, int paramInt4, SnmpSecurityParameters paramSnmpSecurityParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt5, byte[] paramArrayOfbyte4) throws SnmpTooBigException, SnmpStatusException, SnmpSecurityException;
  
  SnmpSecurityParameters processIncomingRequest(SnmpSecurityCache paramSnmpSecurityCache, int paramInt1, int paramInt2, int paramInt3, byte paramByte, int paramInt4, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, SnmpDecryptedPdu paramSnmpDecryptedPdu) throws SnmpStatusException, SnmpSecurityException;
  
  SnmpSecurityParameters processIncomingResponse(SnmpSecurityCache paramSnmpSecurityCache, int paramInt1, int paramInt2, int paramInt3, byte paramByte, int paramInt4, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, SnmpDecryptedPdu paramSnmpDecryptedPdu) throws SnmpStatusException, SnmpSecurityException;
  
  SnmpSecurityCache createSecurityCache();
  
  void releaseSecurityCache(SnmpSecurityCache paramSnmpSecurityCache);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\internal\SnmpSecurityModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */