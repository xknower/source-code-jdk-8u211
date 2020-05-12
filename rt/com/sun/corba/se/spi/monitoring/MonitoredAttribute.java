package com.sun.corba.se.spi.monitoring;

public interface MonitoredAttribute {
  MonitoredAttributeInfo getAttributeInfo();
  
  void setValue(Object paramObject);
  
  Object getValue();
  
  String getName();
  
  void clearState();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\monitoring\MonitoredAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */