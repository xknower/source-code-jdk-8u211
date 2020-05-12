package com.sun.corba.se.impl.orb;

import java.util.Properties;

public interface ParserAction {
  String getPropertyName();
  
  boolean isPrefix();
  
  String getFieldName();
  
  Object apply(Properties paramProperties);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\orb\ParserAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */