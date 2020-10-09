package sun.management.snmp.jvmmib;

import com.sun.jmx.snmp.SnmpStatusException;

public interface JvmCompilationMBean {
  EnumJvmJITCompilerTimeMonitoring getJvmJITCompilerTimeMonitoring() throws SnmpStatusException;
  
  Long getJvmJITCompilerTimeMs() throws SnmpStatusException;
  
  String getJvmJITCompilerName() throws SnmpStatusException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvmmib\JvmCompilationMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */