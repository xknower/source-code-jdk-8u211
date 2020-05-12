package com.sun.org.apache.xml.internal.security.keys.storage;

import java.security.cert.Certificate;
import java.util.Iterator;

public abstract class StorageResolverSpi {
  public abstract Iterator<Certificate> getIterator();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\security\keys\storage\StorageResolverSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */