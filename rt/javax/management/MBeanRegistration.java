package javax.management;

public interface MBeanRegistration {
  ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception;
  
  void postRegister(Boolean paramBoolean);
  
  void preDeregister() throws Exception;
  
  void postDeregister();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\MBeanRegistration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */