package javax.management.monitor;

import javax.management.ObjectName;

public interface StringMonitorMBean extends MonitorMBean {
  @Deprecated
  String getDerivedGauge();
  
  @Deprecated
  long getDerivedGaugeTimeStamp();
  
  String getDerivedGauge(ObjectName paramObjectName);
  
  long getDerivedGaugeTimeStamp(ObjectName paramObjectName);
  
  String getStringToCompare();
  
  void setStringToCompare(String paramString) throws IllegalArgumentException;
  
  boolean getNotifyMatch();
  
  void setNotifyMatch(boolean paramBoolean);
  
  boolean getNotifyDiffer();
  
  void setNotifyDiffer(boolean paramBoolean);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\monitor\StringMonitorMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */