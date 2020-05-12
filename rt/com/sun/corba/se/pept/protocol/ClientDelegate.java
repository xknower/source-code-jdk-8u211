package com.sun.corba.se.pept.protocol;

import com.sun.corba.se.pept.broker.Broker;
import com.sun.corba.se.pept.transport.ContactInfoList;

public interface ClientDelegate {
  Broker getBroker();
  
  ContactInfoList getContactInfoList();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\pept\protocol\ClientDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */