package org.omg.PortableInterceptor;

import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ValueBase;

public interface ObjectReferenceFactory extends ValueBase {
  Object make_object(String paramString, byte[] paramArrayOfbyte);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableInterceptor\ObjectReferenceFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */