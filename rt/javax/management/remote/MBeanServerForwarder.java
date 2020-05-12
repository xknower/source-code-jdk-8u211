package javax.management.remote;

import javax.management.MBeanServer;

public interface MBeanServerForwarder extends MBeanServer {
  MBeanServer getMBeanServer();
  
  void setMBeanServer(MBeanServer paramMBeanServer);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\remote\MBeanServerForwarder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */