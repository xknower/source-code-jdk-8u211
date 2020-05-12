package sun.management.snmp.jvmmib;

import com.sun.jmx.snmp.SnmpStatusException;

public interface JvmClassLoadingMBean {
  EnumJvmClassesVerboseLevel getJvmClassesVerboseLevel() throws SnmpStatusException;
  
  void setJvmClassesVerboseLevel(EnumJvmClassesVerboseLevel paramEnumJvmClassesVerboseLevel) throws SnmpStatusException;
  
  void checkJvmClassesVerboseLevel(EnumJvmClassesVerboseLevel paramEnumJvmClassesVerboseLevel) throws SnmpStatusException;
  
  Long getJvmClassesUnloadedCount() throws SnmpStatusException;
  
  Long getJvmClassesTotalLoadedCount() throws SnmpStatusException;
  
  Long getJvmClassesLoadedCount() throws SnmpStatusException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvmmib\JvmClassLoadingMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */