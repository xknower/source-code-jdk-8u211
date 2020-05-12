package com.sun.tracing;

public interface Probe {
  boolean isEnabled();
  
  void trigger(Object... paramVarArgs);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\tracing\Probe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */