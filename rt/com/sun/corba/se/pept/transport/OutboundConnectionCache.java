package com.sun.corba.se.pept.transport;

public interface OutboundConnectionCache extends ConnectionCache {
  Connection get(ContactInfo paramContactInfo);
  
  void put(ContactInfo paramContactInfo, Connection paramConnection);
  
  void remove(ContactInfo paramContactInfo);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\pept\transport\OutboundConnectionCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */