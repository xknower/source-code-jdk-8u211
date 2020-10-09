package com.sun.xml.internal.bind;

import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
import java.lang.reflect.Field;
import javax.xml.bind.JAXBException;

public interface InternalAccessorFactory extends AccessorFactory {
  Accessor createFieldAccessor(Class paramClass, Field paramField, boolean paramBoolean1, boolean paramBoolean2) throws JAXBException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\InternalAccessorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */