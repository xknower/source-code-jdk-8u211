package com.sun.xml.internal.ws;

import java.io.Closeable;
import javax.xml.ws.WebServiceException;

public interface Closeable extends Closeable {
  void close() throws WebServiceException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\Closeable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */