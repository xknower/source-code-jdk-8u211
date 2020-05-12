package com.sun.corba.se.spi.transport;

import com.sun.corba.se.pept.protocol.MessageMediator;
import com.sun.corba.se.pept.transport.ResponseWaitingRoom;
import org.omg.CORBA.SystemException;

public interface CorbaResponseWaitingRoom extends ResponseWaitingRoom {
  void signalExceptionToAllWaiters(SystemException paramSystemException);
  
  MessageMediator getMessageMediator(int paramInt);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\transport\CorbaResponseWaitingRoom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */