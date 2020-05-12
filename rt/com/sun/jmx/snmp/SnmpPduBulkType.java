package com.sun.jmx.snmp;

public interface SnmpPduBulkType extends SnmpAckPdu {
  void setMaxRepetitions(int paramInt);
  
  void setNonRepeaters(int paramInt);
  
  int getMaxRepetitions();
  
  int getNonRepeaters();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpPduBulkType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */