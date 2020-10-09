package sun.management.snmp.jvmmib;

import com.sun.jmx.snmp.SnmpStatusException;

public interface JvmOSMBean {
  Integer getJvmOSProcessorCount() throws SnmpStatusException;
  
  String getJvmOSVersion() throws SnmpStatusException;
  
  String getJvmOSArch() throws SnmpStatusException;
  
  String getJvmOSName() throws SnmpStatusException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvmmib\JvmOSMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */