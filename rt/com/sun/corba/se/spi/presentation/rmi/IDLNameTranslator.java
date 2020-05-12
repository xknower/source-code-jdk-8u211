package com.sun.corba.se.spi.presentation.rmi;

import java.lang.reflect.Method;

public interface IDLNameTranslator {
  Class[] getInterfaces();
  
  Method[] getMethods();
  
  Method getMethod(String paramString);
  
  String getIDLName(Method paramMethod);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\presentation\rmi\IDLNameTranslator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */