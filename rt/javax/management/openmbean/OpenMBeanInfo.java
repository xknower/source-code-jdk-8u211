package javax.management.openmbean;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;

public interface OpenMBeanInfo {
  String getClassName();
  
  String getDescription();
  
  MBeanAttributeInfo[] getAttributes();
  
  MBeanOperationInfo[] getOperations();
  
  MBeanConstructorInfo[] getConstructors();
  
  MBeanNotificationInfo[] getNotifications();
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  String toString();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\openmbean\OpenMBeanInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */