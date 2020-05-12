package java.lang.management;

import java.lang.management.PlatformManagedObject;

public interface BufferPoolMXBean extends PlatformManagedObject {
  String getName();
  
  long getCount();
  
  long getTotalCapacity();
  
  long getMemoryUsed();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\management\BufferPoolMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */