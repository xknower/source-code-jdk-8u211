package javax.management;

public interface PersistentMBean {
  void load() throws MBeanException, RuntimeOperationsException, InstanceNotFoundException;
  
  void store() throws MBeanException, RuntimeOperationsException, InstanceNotFoundException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\PersistentMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */