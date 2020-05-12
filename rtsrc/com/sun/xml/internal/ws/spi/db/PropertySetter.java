package com.sun.xml.internal.ws.spi.db;

public interface PropertySetter {
  Class getType();
  
  <A> A getAnnotation(Class<A> paramClass);
  
  void set(Object paramObject1, Object paramObject2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\spi\db\PropertySetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */