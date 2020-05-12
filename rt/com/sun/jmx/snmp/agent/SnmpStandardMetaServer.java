package com.sun.jmx.snmp.agent;

import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpValue;

public interface SnmpStandardMetaServer {
  SnmpValue get(long paramLong, Object paramObject) throws SnmpStatusException;
  
  SnmpValue set(SnmpValue paramSnmpValue, long paramLong, Object paramObject) throws SnmpStatusException;
  
  void check(SnmpValue paramSnmpValue, long paramLong, Object paramObject) throws SnmpStatusException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpStandardMetaServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */