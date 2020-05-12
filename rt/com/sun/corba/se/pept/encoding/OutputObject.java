package com.sun.corba.se.pept.encoding;

import com.sun.corba.se.pept.protocol.MessageMediator;
import java.io.IOException;

public interface OutputObject {
  void setMessageMediator(MessageMediator paramMessageMediator);
  
  MessageMediator getMessageMediator();
  
  void close() throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\pept\encoding\OutputObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */