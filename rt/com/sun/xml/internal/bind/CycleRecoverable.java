package com.sun.xml.internal.bind;

import javax.xml.bind.Marshaller;

public interface CycleRecoverable {
  Object onCycleDetected(Context paramContext);
  
  public static interface Context {
    Marshaller getMarshaller();
  }
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\CycleRecoverable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */