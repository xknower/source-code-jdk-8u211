package com.sun.jmx.snmp.agent;

import com.sun.jmx.snmp.SnmpOid;

public interface SnmpMibHandler {
  SnmpMibHandler addMib(SnmpMibAgent paramSnmpMibAgent) throws IllegalArgumentException;
  
  SnmpMibHandler addMib(SnmpMibAgent paramSnmpMibAgent, SnmpOid[] paramArrayOfSnmpOid) throws IllegalArgumentException;
  
  SnmpMibHandler addMib(SnmpMibAgent paramSnmpMibAgent, String paramString) throws IllegalArgumentException;
  
  SnmpMibHandler addMib(SnmpMibAgent paramSnmpMibAgent, String paramString, SnmpOid[] paramArrayOfSnmpOid) throws IllegalArgumentException;
  
  boolean removeMib(SnmpMibAgent paramSnmpMibAgent);
  
  boolean removeMib(SnmpMibAgent paramSnmpMibAgent, SnmpOid[] paramArrayOfSnmpOid);
  
  boolean removeMib(SnmpMibAgent paramSnmpMibAgent, String paramString);
  
  boolean removeMib(SnmpMibAgent paramSnmpMibAgent, String paramString, SnmpOid[] paramArrayOfSnmpOid);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpMibHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */