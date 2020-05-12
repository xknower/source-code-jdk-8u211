package com.sun.jmx.snmp;

public interface SnmpPduRequestType extends SnmpAckPdu {
  void setErrorIndex(int paramInt);
  
  void setErrorStatus(int paramInt);
  
  int getErrorIndex();
  
  int getErrorStatus();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpPduRequestType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */