package com.sun.org.apache.xerces.internal.xs.datatypes;

import com.sun.org.apache.xerces.internal.xs.XSException;
import java.util.List;

public interface ByteList extends List {
  int getLength();
  
  boolean contains(byte paramByte);
  
  byte item(int paramInt) throws XSException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xs\datatypes\ByteList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */