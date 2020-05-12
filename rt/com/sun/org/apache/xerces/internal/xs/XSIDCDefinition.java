package com.sun.org.apache.xerces.internal.xs;

public interface XSIDCDefinition extends XSObject {
  public static final short IC_KEY = 1;
  
  public static final short IC_KEYREF = 2;
  
  public static final short IC_UNIQUE = 3;
  
  short getCategory();
  
  String getSelectorStr();
  
  StringList getFieldStrs();
  
  XSIDCDefinition getRefKey();
  
  XSObjectList getAnnotations();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xs\XSIDCDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */