package sun.management;

import java.util.List;
import sun.management.counter.Counter;

public interface HotspotClassLoadingMBean {
  long getLoadedClassSize();
  
  long getUnloadedClassSize();
  
  long getClassLoadingTime();
  
  long getMethodDataSize();
  
  long getInitializedClassCount();
  
  long getClassInitializationTime();
  
  long getClassVerificationTime();
  
  List<Counter> getInternalClassLoadingCounters();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\HotspotClassLoadingMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */