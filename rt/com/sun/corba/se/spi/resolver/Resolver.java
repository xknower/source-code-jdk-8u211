package com.sun.corba.se.spi.resolver;

import java.util.Set;
import org.omg.CORBA.Object;

public interface Resolver {
  Object resolve(String paramString);
  
  Set list();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\resolver\Resolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */