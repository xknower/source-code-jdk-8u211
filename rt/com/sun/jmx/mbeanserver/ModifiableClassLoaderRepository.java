package com.sun.jmx.mbeanserver;

import javax.management.ObjectName;
import javax.management.loading.ClassLoaderRepository;

public interface ModifiableClassLoaderRepository extends ClassLoaderRepository {
  void addClassLoader(ClassLoader paramClassLoader);
  
  void removeClassLoader(ClassLoader paramClassLoader);
  
  void addClassLoader(ObjectName paramObjectName, ClassLoader paramClassLoader);
  
  void removeClassLoader(ObjectName paramObjectName);
  
  ClassLoader getClassLoader(ObjectName paramObjectName);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\mbeanserver\ModifiableClassLoaderRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */