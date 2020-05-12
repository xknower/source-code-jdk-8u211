package com.sun.jmx.snmp.internal;

import com.sun.jmx.snmp.SnmpMsg;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpSecurityException;
import com.sun.jmx.snmp.SnmpSecurityParameters;
import com.sun.jmx.snmp.SnmpStatusException;
import java.net.InetAddress;

public interface SnmpIncomingResponse {
  InetAddress getAddress();
  
  int getPort();
  
  SnmpSecurityParameters getSecurityParameters();
  
  void setSecurityCache(SnmpSecurityCache paramSnmpSecurityCache);
  
  int getSecurityLevel();
  
  int getSecurityModel();
  
  byte[] getContextName();
  
  SnmpMsg decodeMessage(byte[] paramArrayOfbyte, int paramInt1, InetAddress paramInetAddress, int paramInt2) throws SnmpStatusException, SnmpSecurityException;
  
  SnmpPdu decodeSnmpPdu() throws SnmpStatusException;
  
  int getRequestId(byte[] paramArrayOfbyte) throws SnmpStatusException;
  
  String printMessage();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\internal\SnmpIncomingResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */