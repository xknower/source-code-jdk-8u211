package com.sun.xml.internal.bind.v2.runtime.unmarshaller;

import org.xml.sax.SAXException;

public interface Receiver {
  void receive(UnmarshallingContext.State paramState, Object paramObject) throws SAXException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtim\\unmarshaller\Receiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */