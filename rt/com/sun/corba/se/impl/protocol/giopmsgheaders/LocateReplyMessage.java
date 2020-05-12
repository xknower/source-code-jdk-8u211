package com.sun.corba.se.impl.protocol.giopmsgheaders;

public interface LocateReplyMessage extends Message, LocateReplyOrReplyMessage {
  public static final int UNKNOWN_OBJECT = 0;
  
  public static final int OBJECT_HERE = 1;
  
  public static final int OBJECT_FORWARD = 2;
  
  public static final int OBJECT_FORWARD_PERM = 3;
  
  public static final int LOC_SYSTEM_EXCEPTION = 4;
  
  public static final int LOC_NEEDS_ADDRESSING_MODE = 5;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\protocol\giopmsgheaders\LocateReplyMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */