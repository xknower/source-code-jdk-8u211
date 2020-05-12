package com.sun.corba.se.spi.ior;

import org.omg.CORBA_2_3.portable.InputStream;

public interface ObjectKeyFactory {
  ObjectKey create(byte[] paramArrayOfbyte);
  
  ObjectKeyTemplate createTemplate(InputStream paramInputStream);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\ior\ObjectKeyFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */