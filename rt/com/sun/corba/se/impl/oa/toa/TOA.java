package com.sun.corba.se.impl.oa.toa;

import com.sun.corba.se.spi.oa.ObjectAdapter;
import org.omg.CORBA.Object;

public interface TOA extends ObjectAdapter {
  void connect(Object paramObject);
  
  void disconnect(Object paramObject);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\oa\toa\TOA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */