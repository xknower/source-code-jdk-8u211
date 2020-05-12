package com.sun.tracing;

import java.lang.reflect.Method;

public interface Provider {
  Probe getProbe(Method paramMethod);
  
  void dispose();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\tracing\Provider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */