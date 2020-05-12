package com.sun.jmx.snmp.internal;

import com.sun.jmx.snmp.SnmpEngine;
import com.sun.jmx.snmp.SnmpUnknownModelException;

public interface SnmpSubSystem {
  SnmpEngine getEngine();
  
  void addModel(int paramInt, SnmpModel paramSnmpModel);
  
  SnmpModel removeModel(int paramInt) throws SnmpUnknownModelException;
  
  SnmpModel getModel(int paramInt) throws SnmpUnknownModelException;
  
  int[] getModelIds();
  
  String[] getModelNames();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\internal\SnmpSubSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */