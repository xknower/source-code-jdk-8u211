package com.sun.xml.internal.bind.api;

public abstract class RawAccessor<B, V> {
  public abstract V get(B paramB) throws AccessorException;
  
  public abstract void set(B paramB, V paramV) throws AccessorException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\api\RawAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */