package com.sun.xml.internal.bind.v2.model.core;

import javax.xml.namespace.QName;

public interface Element<T, C> extends TypeInfo<T, C> {
  QName getElementName();
  
  Element<T, C> getSubstitutionHead();
  
  ClassInfo<T, C> getScope();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\core\Element.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */