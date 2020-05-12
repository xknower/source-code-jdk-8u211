package com.sun.corba.se.pept.transport;

import java.nio.ByteBuffer;

public interface ByteBufferPool {
  ByteBuffer getByteBuffer(int paramInt);
  
  void releaseByteBuffer(ByteBuffer paramByteBuffer);
  
  int activeCount();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\pept\transport\ByteBufferPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */