package com.sun.xml.internal.bind.v2.model.core;

import java.util.List;

public interface ClassInfo<T, C> extends MaybeElement<T, C> {
  ClassInfo<T, C> getBaseClass();
  
  C getClazz();
  
  String getName();
  
  List<? extends PropertyInfo<T, C>> getProperties();
  
  boolean hasValueProperty();
  
  PropertyInfo<T, C> getProperty(String paramString);
  
  boolean hasProperties();
  
  boolean isAbstract();
  
  boolean isOrdered();
  
  boolean isFinal();
  
  boolean hasSubClasses();
  
  boolean hasAttributeWildcard();
  
  boolean inheritsAttributeWildcard();
  
  boolean declaresAttributeWildcard();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\core\ClassInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */