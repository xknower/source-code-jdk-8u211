package com.sun.jmx.snmp.internal;

import com.sun.jmx.snmp.SnmpBadSecurityLevelException;
import com.sun.jmx.snmp.SnmpMsg;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpSecurityParameters;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpTooBigException;
import com.sun.jmx.snmp.SnmpUnknownSecModelException;
import java.net.InetAddress;

public interface SnmpIncomingRequest {
  SnmpSecurityParameters getSecurityParameters();
  
  boolean isReport();
  
  boolean isResponse();
  
  void noResponse();
  
  String getPrincipal();
  
  int getSecurityLevel();
  
  int getSecurityModel();
  
  byte[] getContextName();
  
  byte[] getContextEngineId();
  
  byte[] getAccessContext();
  
  int encodeMessage(byte[] paramArrayOfbyte) throws SnmpTooBigException;
  
  void decodeMessage(byte[] paramArrayOfbyte, int paramInt1, InetAddress paramInetAddress, int paramInt2) throws SnmpStatusException, SnmpUnknownSecModelException, SnmpBadSecurityLevelException;
  
  SnmpMsg encodeSnmpPdu(SnmpPdu paramSnmpPdu, int paramInt) throws SnmpStatusException, SnmpTooBigException;
  
  SnmpPdu decodeSnmpPdu() throws SnmpStatusException;
  
  String printRequestMessage();
  
  String printResponseMessage();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\internal\SnmpIncomingRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */