package com.sun.corba.se.spi.monitoring;

import java.util.Collection;

public interface MonitoredObject {
  String getName();
  
  String getDescription();
  
  void addChild(MonitoredObject paramMonitoredObject);
  
  void removeChild(String paramString);
  
  MonitoredObject getChild(String paramString);
  
  Collection getChildren();
  
  void setParent(MonitoredObject paramMonitoredObject);
  
  MonitoredObject getParent();
  
  void addAttribute(MonitoredAttribute paramMonitoredAttribute);
  
  void removeAttribute(String paramString);
  
  MonitoredAttribute getAttribute(String paramString);
  
  Collection getAttributes();
  
  void clearState();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\monitoring\MonitoredObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */