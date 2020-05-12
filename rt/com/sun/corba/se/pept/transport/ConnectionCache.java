package com.sun.corba.se.pept.transport;

public interface ConnectionCache {
  String getCacheType();
  
  void stampTime(Connection paramConnection);
  
  long numberOfConnections();
  
  long numberOfIdleConnections();
  
  long numberOfBusyConnections();
  
  boolean reclaim();
  
  void close();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\pept\transport\ConnectionCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */