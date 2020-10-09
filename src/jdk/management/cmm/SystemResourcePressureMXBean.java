package jdk.management.cmm;

import java.lang.management.PlatformManagedObject;
import jdk.Exported;

@Exported
public interface SystemResourcePressureMXBean extends PlatformManagedObject {
  int getMemoryPressure();
  
  void setMemoryPressure(int paramInt);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\cmm\SystemResourcePressureMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */