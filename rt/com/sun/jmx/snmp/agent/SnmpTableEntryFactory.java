package com.sun.jmx.snmp.agent;

import com.sun.jmx.snmp.SnmpOid;
import com.sun.jmx.snmp.SnmpStatusException;

public interface SnmpTableEntryFactory extends SnmpTableCallbackHandler {
  void createNewEntry(SnmpMibSubRequest paramSnmpMibSubRequest, SnmpOid paramSnmpOid, int paramInt, SnmpMibTable paramSnmpMibTable) throws SnmpStatusException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpTableEntryFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */