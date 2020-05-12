package com.sun.jmx.snmp.agent;

import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpStatusException;

public interface SnmpUserDataFactory {
  Object allocateUserData(SnmpPdu paramSnmpPdu) throws SnmpStatusException;
  
  void releaseUserData(Object paramObject, SnmpPdu paramSnmpPdu) throws SnmpStatusException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpUserDataFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */