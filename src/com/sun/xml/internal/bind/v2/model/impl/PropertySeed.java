package com.sun.xml.internal.bind.v2.model.impl;

import com.sun.xml.internal.bind.v2.model.annotation.AnnotationSource;
import com.sun.xml.internal.bind.v2.model.annotation.Locatable;

interface PropertySeed<T, C, F, M> extends Locatable, AnnotationSource {
  String getName();
  
  T getRawType();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\impl\PropertySeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */