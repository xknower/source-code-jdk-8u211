package com.sun.corba.se.impl.encoding;

interface MarkAndResetHandler {
  void mark(RestorableInputStream paramRestorableInputStream);
  
  void fragmentationOccured(ByteBufferWithInfo paramByteBufferWithInfo);
  
  void reset();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\encoding\MarkAndResetHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */