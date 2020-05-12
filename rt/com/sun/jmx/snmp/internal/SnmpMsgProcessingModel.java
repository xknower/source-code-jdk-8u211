package com.sun.jmx.snmp.internal;

import com.sun.jmx.snmp.SnmpParams;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpPduFactory;
import com.sun.jmx.snmp.SnmpSecurityParameters;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpTooBigException;
import com.sun.jmx.snmp.mpm.SnmpMsgTranslator;

public interface SnmpMsgProcessingModel extends SnmpModel {
  SnmpOutgoingRequest getOutgoingRequest(SnmpPduFactory paramSnmpPduFactory);
  
  SnmpIncomingRequest getIncomingRequest(SnmpPduFactory paramSnmpPduFactory);
  
  SnmpIncomingResponse getIncomingResponse(SnmpPduFactory paramSnmpPduFactory);
  
  SnmpPdu getRequestPdu(SnmpParams paramSnmpParams, int paramInt) throws SnmpStatusException;
  
  int encode(int paramInt1, int paramInt2, int paramInt3, byte paramByte, int paramInt4, SnmpSecurityParameters paramSnmpSecurityParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt5, byte[] paramArrayOfbyte4) throws SnmpTooBigException;
  
  int encodePriv(int paramInt1, int paramInt2, int paramInt3, byte paramByte, int paramInt4, SnmpSecurityParameters paramSnmpSecurityParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws SnmpTooBigException;
  
  SnmpDecryptedPdu decode(byte[] paramArrayOfbyte) throws SnmpStatusException;
  
  int encode(SnmpDecryptedPdu paramSnmpDecryptedPdu, byte[] paramArrayOfbyte) throws SnmpTooBigException;
  
  void setMsgTranslator(SnmpMsgTranslator paramSnmpMsgTranslator);
  
  SnmpMsgTranslator getMsgTranslator();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\internal\SnmpMsgProcessingModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */