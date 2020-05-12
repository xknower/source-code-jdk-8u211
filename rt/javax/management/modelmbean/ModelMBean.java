package javax.management.modelmbean;

import javax.management.DynamicMBean;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.PersistentMBean;
import javax.management.RuntimeOperationsException;

public interface ModelMBean extends DynamicMBean, PersistentMBean, ModelMBeanNotificationBroadcaster {
  void setModelMBeanInfo(ModelMBeanInfo paramModelMBeanInfo) throws MBeanException, RuntimeOperationsException;
  
  void setManagedResource(Object paramObject, String paramString) throws MBeanException, RuntimeOperationsException, InstanceNotFoundException, InvalidTargetObjectTypeException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\modelmbean\ModelMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */