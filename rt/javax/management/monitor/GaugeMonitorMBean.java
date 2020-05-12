package javax.management.monitor;

import javax.management.ObjectName;

public interface GaugeMonitorMBean extends MonitorMBean {
  @Deprecated
  Number getDerivedGauge();
  
  @Deprecated
  long getDerivedGaugeTimeStamp();
  
  Number getDerivedGauge(ObjectName paramObjectName);
  
  long getDerivedGaugeTimeStamp(ObjectName paramObjectName);
  
  Number getHighThreshold();
  
  Number getLowThreshold();
  
  void setThresholds(Number paramNumber1, Number paramNumber2) throws IllegalArgumentException;
  
  boolean getNotifyHigh();
  
  void setNotifyHigh(boolean paramBoolean);
  
  boolean getNotifyLow();
  
  void setNotifyLow(boolean paramBoolean);
  
  boolean getDifferenceMode();
  
  void setDifferenceMode(boolean paramBoolean);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\monitor\GaugeMonitorMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */