package com.sun.corba.se.spi.monitoring;

import java.io.Closeable;

public interface MonitoringManager extends Closeable {
  MonitoredObject getRootMonitoredObject();
  
  void clearState();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\monitoring\MonitoringManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */