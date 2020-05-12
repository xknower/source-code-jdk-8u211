package com.sun.jmx.snmp;

public interface SnmpEngine {
  int getEngineTime();
  
  SnmpEngineId getEngineId();
  
  int getEngineBoots();
  
  SnmpUsmKeyHandler getUsmKeyHandler();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */