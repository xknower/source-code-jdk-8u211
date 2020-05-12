package sun.management.snmp.jvmmib;

import com.sun.jmx.snmp.SnmpStatusException;

public interface JvmMemMgrPoolRelEntryMBean {
  String getJvmMemMgrRelPoolName() throws SnmpStatusException;
  
  String getJvmMemMgrRelManagerName() throws SnmpStatusException;
  
  Integer getJvmMemManagerIndex() throws SnmpStatusException;
  
  Integer getJvmMemPoolIndex() throws SnmpStatusException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvmmib\JvmMemMgrPoolRelEntryMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */