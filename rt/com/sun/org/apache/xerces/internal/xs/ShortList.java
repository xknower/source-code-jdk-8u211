package com.sun.org.apache.xerces.internal.xs;

import java.util.List;

public interface ShortList extends List {
  int getLength();
  
  boolean contains(short paramShort);
  
  short item(int paramInt) throws XSException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xs\ShortList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */