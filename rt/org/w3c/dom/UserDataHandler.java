package org.w3c.dom;

public interface UserDataHandler {
  public static final short NODE_CLONED = 1;
  
  public static final short NODE_IMPORTED = 2;
  
  public static final short NODE_DELETED = 3;
  
  public static final short NODE_RENAMED = 4;
  
  public static final short NODE_ADOPTED = 5;
  
  void handle(short paramShort, String paramString, Object paramObject, Node paramNode1, Node paramNode2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\UserDataHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */