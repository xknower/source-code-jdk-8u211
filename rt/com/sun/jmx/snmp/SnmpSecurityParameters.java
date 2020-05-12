package com.sun.jmx.snmp;

public interface SnmpSecurityParameters {
  int encode(byte[] paramArrayOfbyte) throws SnmpTooBigException;
  
  void decode(byte[] paramArrayOfbyte) throws SnmpStatusException;
  
  String getPrincipal();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpSecurityParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */