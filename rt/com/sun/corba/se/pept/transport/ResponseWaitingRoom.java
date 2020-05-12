package com.sun.corba.se.pept.transport;

import com.sun.corba.se.pept.encoding.InputObject;
import com.sun.corba.se.pept.protocol.MessageMediator;

public interface ResponseWaitingRoom {
  void registerWaiter(MessageMediator paramMessageMediator);
  
  InputObject waitForResponse(MessageMediator paramMessageMediator);
  
  void responseReceived(InputObject paramInputObject);
  
  void unregisterWaiter(MessageMediator paramMessageMediator);
  
  int numberRegistered();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\pept\transport\ResponseWaitingRoom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */