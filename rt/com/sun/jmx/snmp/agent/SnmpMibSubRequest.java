package com.sun.jmx.snmp.agent;

import com.sun.jmx.snmp.SnmpOid;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpVarBind;
import java.util.Enumeration;
import java.util.Vector;

public interface SnmpMibSubRequest extends SnmpMibRequest {
  Enumeration<SnmpVarBind> getElements();
  
  Vector<SnmpVarBind> getSubList();
  
  SnmpOid getEntryOid();
  
  boolean isNewEntry();
  
  SnmpVarBind getRowStatusVarBind();
  
  void registerGetException(SnmpVarBind paramSnmpVarBind, SnmpStatusException paramSnmpStatusException) throws SnmpStatusException;
  
  void registerSetException(SnmpVarBind paramSnmpVarBind, SnmpStatusException paramSnmpStatusException) throws SnmpStatusException;
  
  void registerCheckException(SnmpVarBind paramSnmpVarBind, SnmpStatusException paramSnmpStatusException) throws SnmpStatusException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpMibSubRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */