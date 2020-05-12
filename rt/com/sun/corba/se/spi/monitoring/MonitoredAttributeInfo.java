package com.sun.corba.se.spi.monitoring;

public interface MonitoredAttributeInfo {
  boolean isWritable();
  
  boolean isStatistic();
  
  Class type();
  
  String getDescription();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\monitoring\MonitoredAttributeInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */