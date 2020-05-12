package javax.management.openmbean;

import javax.management.MBeanParameterInfo;

public interface OpenMBeanConstructorInfo {
  String getDescription();
  
  String getName();
  
  MBeanParameterInfo[] getSignature();
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  String toString();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\openmbean\OpenMBeanConstructorInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */