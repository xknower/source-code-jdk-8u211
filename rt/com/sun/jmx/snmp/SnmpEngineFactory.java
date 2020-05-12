package com.sun.jmx.snmp;

public interface SnmpEngineFactory {
  SnmpEngine createEngine(SnmpEngineParameters paramSnmpEngineParameters);
  
  SnmpEngine createEngine(SnmpEngineParameters paramSnmpEngineParameters, InetAddressAcl paramInetAddressAcl);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpEngineFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */