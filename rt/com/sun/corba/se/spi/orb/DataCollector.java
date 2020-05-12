package com.sun.corba.se.spi.orb;

import java.util.Properties;

public interface DataCollector {
  boolean isApplet();
  
  boolean initialHostIsLocal();
  
  void setParser(PropertyParser paramPropertyParser);
  
  Properties getProperties();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\orb\DataCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */