package com.sun.xml.internal.ws.spi.db;

public interface PropertyGetter {
  Class getType();
  
  <A> A getAnnotation(Class<A> paramClass);
  
  Object get(Object paramObject);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\spi\db\PropertyGetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */