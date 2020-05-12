package com.sun.jmx.snmp.agent;

import com.sun.jmx.snmp.SnmpOid;
import com.sun.jmx.snmp.SnmpStatusException;
import javax.management.ObjectName;

public interface SnmpTableCallbackHandler {
  void addEntryCb(int paramInt, SnmpOid paramSnmpOid, ObjectName paramObjectName, Object paramObject, SnmpMibTable paramSnmpMibTable) throws SnmpStatusException;
  
  void removeEntryCb(int paramInt, SnmpOid paramSnmpOid, ObjectName paramObjectName, Object paramObject, SnmpMibTable paramSnmpMibTable) throws SnmpStatusException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpTableCallbackHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */