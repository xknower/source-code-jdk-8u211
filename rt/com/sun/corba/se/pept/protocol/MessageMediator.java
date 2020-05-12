package com.sun.corba.se.pept.protocol;

import com.sun.corba.se.pept.broker.Broker;
import com.sun.corba.se.pept.encoding.InputObject;
import com.sun.corba.se.pept.encoding.OutputObject;
import com.sun.corba.se.pept.transport.Connection;
import com.sun.corba.se.pept.transport.ContactInfo;

public interface MessageMediator {
  Broker getBroker();
  
  ContactInfo getContactInfo();
  
  Connection getConnection();
  
  void initializeMessage();
  
  void finishSendingRequest();
  
  @Deprecated
  InputObject waitForResponse();
  
  void setOutputObject(OutputObject paramOutputObject);
  
  OutputObject getOutputObject();
  
  void setInputObject(InputObject paramInputObject);
  
  InputObject getInputObject();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\pept\protocol\MessageMediator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */