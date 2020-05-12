package com.sun.corba.se.spi.orbutil.proxy;

import java.lang.reflect.InvocationHandler;

public interface InvocationHandlerFactory {
  InvocationHandler getInvocationHandler();
  
  Class[] getProxyInterfaces();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\orbutil\proxy\InvocationHandlerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */