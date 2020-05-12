package com.sun.corba.se.spi.ior;

import org.omg.CORBA_2_3.portable.InputStream;

public interface IdentifiableFactory {
  int getId();
  
  Identifiable create(InputStream paramInputStream);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\ior\IdentifiableFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */