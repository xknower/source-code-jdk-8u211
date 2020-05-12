package com.sun.jmx.snmp;

public interface SnmpPduFactory {
  SnmpPdu decodeSnmpPdu(SnmpMsg paramSnmpMsg) throws SnmpStatusException;
  
  SnmpMsg encodeSnmpPdu(SnmpPdu paramSnmpPdu, int paramInt) throws SnmpStatusException, SnmpTooBigException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpPduFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */