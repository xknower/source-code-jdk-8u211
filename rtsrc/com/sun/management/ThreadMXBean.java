package com.sun.management;

import java.lang.management.ThreadMXBean;
import jdk.Exported;

@Exported
public interface ThreadMXBean extends ThreadMXBean {
  long[] getThreadCpuTime(long[] paramArrayOflong);
  
  long[] getThreadUserTime(long[] paramArrayOflong);
  
  long getThreadAllocatedBytes(long paramLong);
  
  long[] getThreadAllocatedBytes(long[] paramArrayOflong);
  
  boolean isThreadAllocatedMemorySupported();
  
  boolean isThreadAllocatedMemoryEnabled();
  
  void setThreadAllocatedMemoryEnabled(boolean paramBoolean);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\management\ThreadMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */