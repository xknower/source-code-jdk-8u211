package java.lang.management;

import java.lang.management.PlatformManagedObject;
import java.util.List;

public interface PlatformLoggingMXBean extends PlatformManagedObject {
  List<String> getLoggerNames();
  
  String getLoggerLevel(String paramString);
  
  void setLoggerLevel(String paramString1, String paramString2);
  
  String getParentLoggerName(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\management\PlatformLoggingMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */