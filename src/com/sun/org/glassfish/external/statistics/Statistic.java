package com.sun.org.glassfish.external.statistics;

public interface Statistic {
  String getName();
  
  String getUnit();
  
  String getDescription();
  
  long getStartTime();
  
  long getLastSampleTime();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\glassfish\external\statistics\Statistic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */