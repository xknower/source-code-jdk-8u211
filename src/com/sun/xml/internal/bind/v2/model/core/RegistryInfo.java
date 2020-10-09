package com.sun.xml.internal.bind.v2.model.core;

import java.util.Set;

public interface RegistryInfo<T, C> {
  Set<TypeInfo<T, C>> getReferences();
  
  C getClazz();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\core\RegistryInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */