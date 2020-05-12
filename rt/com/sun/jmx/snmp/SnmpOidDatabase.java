package com.sun.jmx.snmp;

import java.util.Vector;

public interface SnmpOidDatabase extends SnmpOidTable {
  void add(SnmpOidTable paramSnmpOidTable);
  
  void remove(SnmpOidTable paramSnmpOidTable) throws SnmpStatusException;
  
  void removeAll();
  
  SnmpOidRecord resolveVarName(String paramString) throws SnmpStatusException;
  
  SnmpOidRecord resolveVarOid(String paramString) throws SnmpStatusException;
  
  Vector<?> getAllEntries();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpOidDatabase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */