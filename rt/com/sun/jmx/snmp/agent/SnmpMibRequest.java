package com.sun.jmx.snmp.agent;

import com.sun.jmx.snmp.SnmpEngine;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpVarBind;
import java.util.Enumeration;
import java.util.Vector;

public interface SnmpMibRequest {
  Enumeration<SnmpVarBind> getElements();
  
  Vector<SnmpVarBind> getSubList();
  
  int getVersion();
  
  int getRequestPduVersion();
  
  SnmpEngine getEngine();
  
  String getPrincipal();
  
  int getSecurityLevel();
  
  int getSecurityModel();
  
  byte[] getContextName();
  
  byte[] getAccessContextName();
  
  Object getUserData();
  
  int getVarIndex(SnmpVarBind paramSnmpVarBind);
  
  void addVarBind(SnmpVarBind paramSnmpVarBind);
  
  int getSize();
  
  SnmpPdu getPdu();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpMibRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */