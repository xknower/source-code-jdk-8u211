package sun.management;

import java.util.List;
import sun.management.counter.Counter;

public interface HotspotRuntimeMBean {
  long getSafepointCount();
  
  long getTotalSafepointTime();
  
  long getSafepointSyncTime();
  
  List<Counter> getInternalRuntimeCounters();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\HotspotRuntimeMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */