package com.sun.xml.internal.bind.v2.model.runtime;

import com.sun.xml.internal.bind.v2.model.core.MapPropertyInfo;
import com.sun.xml.internal.bind.v2.model.core.NonElement;
import java.lang.reflect.Type;

public interface RuntimeMapPropertyInfo extends RuntimePropertyInfo, MapPropertyInfo<Type, Class> {
  RuntimeNonElement getKeyType();
  
  RuntimeNonElement getValueType();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\runtime\RuntimeMapPropertyInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */