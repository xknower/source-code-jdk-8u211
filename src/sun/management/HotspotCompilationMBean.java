package sun.management;

import java.util.List;
import sun.management.counter.Counter;

public interface HotspotCompilationMBean {
  int getCompilerThreadCount();
  
  List<CompilerThreadStat> getCompilerThreadStats();
  
  long getTotalCompileCount();
  
  long getBailoutCompileCount();
  
  long getInvalidatedCompileCount();
  
  MethodInfo getLastCompile();
  
  MethodInfo getFailedCompile();
  
  MethodInfo getInvalidatedCompile();
  
  long getCompiledMethodCodeSize();
  
  long getCompiledMethodSize();
  
  List<Counter> getInternalCompilerCounters();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\HotspotCompilationMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */