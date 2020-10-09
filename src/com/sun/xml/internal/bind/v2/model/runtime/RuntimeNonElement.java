package com.sun.xml.internal.bind.v2.model.runtime;

import com.sun.xml.internal.bind.v2.model.core.NonElement;
import com.sun.xml.internal.bind.v2.runtime.Transducer;
import java.lang.reflect.Type;

public interface RuntimeNonElement extends NonElement<Type, Class>, RuntimeTypeInfo {
  <V> Transducer<V> getTransducer();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\runtime\RuntimeNonElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */