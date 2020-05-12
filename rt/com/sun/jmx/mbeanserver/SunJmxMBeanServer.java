package com.sun.jmx.mbeanserver;

import javax.management.MBeanServer;
import javax.management.MBeanServerDelegate;

public interface SunJmxMBeanServer extends MBeanServer {
  MBeanInstantiator getMBeanInstantiator();
  
  boolean interceptorsEnabled();
  
  MBeanServer getMBeanServerInterceptor();
  
  void setMBeanServerInterceptor(MBeanServer paramMBeanServer);
  
  MBeanServerDelegate getMBeanServerDelegate();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\mbeanserver\SunJmxMBeanServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */