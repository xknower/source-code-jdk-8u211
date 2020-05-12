package com.sun.jmx.snmp.internal;

import com.sun.jmx.snmp.SnmpOid;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpUnknownAccContrModelException;

public interface SnmpAccessControlSubSystem extends SnmpSubSystem {
  void checkPduAccess(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte, SnmpPdu paramSnmpPdu) throws SnmpStatusException, SnmpUnknownAccContrModelException;
  
  void checkAccess(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte, SnmpOid paramSnmpOid) throws SnmpStatusException, SnmpUnknownAccContrModelException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\internal\SnmpAccessControlSubSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */