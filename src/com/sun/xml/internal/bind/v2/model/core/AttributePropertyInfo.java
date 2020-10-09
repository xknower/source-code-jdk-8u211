package com.sun.xml.internal.bind.v2.model.core;

import javax.xml.namespace.QName;

public interface AttributePropertyInfo<T, C> extends PropertyInfo<T, C>, NonElementRef<T, C> {
  NonElement<T, C> getTarget();
  
  boolean isRequired();
  
  QName getXmlName();
  
  Adapter<T, C> getAdapter();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\core\AttributePropertyInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */