package com.sun.org.glassfish.external.statistics;

public interface TimeStatistic extends Statistic {
  long getCount();
  
  long getMaxTime();
  
  long getMinTime();
  
  long getTotalTime();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\glassfish\external\statistics\TimeStatistic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */