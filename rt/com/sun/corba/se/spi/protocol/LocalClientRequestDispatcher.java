package com.sun.corba.se.spi.protocol;

import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ServantObject;

public interface LocalClientRequestDispatcher {
  boolean useLocalInvocation(Object paramObject);
  
  boolean is_local(Object paramObject);
  
  ServantObject servant_preinvoke(Object paramObject, String paramString, Class paramClass);
  
  void servant_postinvoke(Object paramObject, ServantObject paramServantObject);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\protocol\LocalClientRequestDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */