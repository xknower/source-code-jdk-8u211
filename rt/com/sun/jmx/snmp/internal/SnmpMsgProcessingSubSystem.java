package com.sun.jmx.snmp.internal;

import com.sun.jmx.snmp.SnmpParams;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpPduFactory;
import com.sun.jmx.snmp.SnmpSecurityParameters;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpTooBigException;
import com.sun.jmx.snmp.SnmpUnknownMsgProcModelException;

public interface SnmpMsgProcessingSubSystem extends SnmpSubSystem {
  void setSecuritySubSystem(SnmpSecuritySubSystem paramSnmpSecuritySubSystem);
  
  SnmpSecuritySubSystem getSecuritySubSystem();
  
  SnmpIncomingRequest getIncomingRequest(int paramInt, SnmpPduFactory paramSnmpPduFactory) throws SnmpUnknownMsgProcModelException;
  
  SnmpOutgoingRequest getOutgoingRequest(int paramInt, SnmpPduFactory paramSnmpPduFactory) throws SnmpUnknownMsgProcModelException;
  
  SnmpPdu getRequestPdu(int paramInt1, SnmpParams paramSnmpParams, int paramInt2) throws SnmpUnknownMsgProcModelException, SnmpStatusException;
  
  SnmpIncomingResponse getIncomingResponse(int paramInt, SnmpPduFactory paramSnmpPduFactory) throws SnmpUnknownMsgProcModelException;
  
  int encode(int paramInt1, int paramInt2, int paramInt3, byte paramByte, int paramInt4, SnmpSecurityParameters paramSnmpSecurityParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt5, byte[] paramArrayOfbyte4) throws SnmpTooBigException, SnmpUnknownMsgProcModelException;
  
  int encodePriv(int paramInt1, int paramInt2, int paramInt3, byte paramByte, int paramInt4, SnmpSecurityParameters paramSnmpSecurityParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws SnmpTooBigException, SnmpUnknownMsgProcModelException;
  
  SnmpDecryptedPdu decode(int paramInt, byte[] paramArrayOfbyte) throws SnmpStatusException, SnmpUnknownMsgProcModelException;
  
  int encode(int paramInt, SnmpDecryptedPdu paramSnmpDecryptedPdu, byte[] paramArrayOfbyte) throws SnmpTooBigException, SnmpUnknownMsgProcModelException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\internal\SnmpMsgProcessingSubSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */