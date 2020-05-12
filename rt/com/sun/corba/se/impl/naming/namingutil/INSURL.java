package com.sun.corba.se.impl.naming.namingutil;

import java.util.List;

public interface INSURL {
  boolean getRIRFlag();
  
  List getEndpointInfo();
  
  String getKeyString();
  
  String getStringifiedName();
  
  boolean isCorbanameURL();
  
  void dPrint();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\naming\namingutil\INSURL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */