package com.sun.jmx.snmp.mpm;

import com.sun.jmx.snmp.SnmpMsg;
import com.sun.jmx.snmp.SnmpSecurityParameters;

public interface SnmpMsgTranslator {
  int getMsgId(SnmpMsg paramSnmpMsg);
  
  int getMsgMaxSize(SnmpMsg paramSnmpMsg);
  
  byte getMsgFlags(SnmpMsg paramSnmpMsg);
  
  int getMsgSecurityModel(SnmpMsg paramSnmpMsg);
  
  int getSecurityLevel(SnmpMsg paramSnmpMsg);
  
  byte[] getFlatSecurityParameters(SnmpMsg paramSnmpMsg);
  
  SnmpSecurityParameters getSecurityParameters(SnmpMsg paramSnmpMsg);
  
  byte[] getContextEngineId(SnmpMsg paramSnmpMsg);
  
  byte[] getContextName(SnmpMsg paramSnmpMsg);
  
  byte[] getRawContextName(SnmpMsg paramSnmpMsg);
  
  byte[] getAccessContext(SnmpMsg paramSnmpMsg);
  
  byte[] getEncryptedPdu(SnmpMsg paramSnmpMsg);
  
  void setContextName(SnmpMsg paramSnmpMsg, byte[] paramArrayOfbyte);
  
  void setContextEngineId(SnmpMsg paramSnmpMsg, byte[] paramArrayOfbyte);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\mpm\SnmpMsgTranslator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */