package java.lang.management;

import java.lang.management.PlatformManagedObject;

public interface CompilationMXBean extends PlatformManagedObject {
  String getName();
  
  boolean isCompilationTimeMonitoringSupported();
  
  long getTotalCompilationTime();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\management\CompilationMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */