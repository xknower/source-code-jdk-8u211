package com.sun.corba.se.impl.orbutil;

import java.net.MalformedURLException;

public interface RepositoryIdInterface {
  Class getClassFromType() throws ClassNotFoundException;
  
  Class getClassFromType(String paramString) throws ClassNotFoundException, MalformedURLException;
  
  Class getClassFromType(Class paramClass, String paramString) throws ClassNotFoundException, MalformedURLException;
  
  String getClassName();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\orbutil\RepositoryIdInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */