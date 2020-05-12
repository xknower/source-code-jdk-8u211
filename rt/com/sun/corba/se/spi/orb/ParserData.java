package com.sun.corba.se.spi.orb;

import java.util.Properties;

public interface ParserData {
  String getPropertyName();
  
  Operation getOperation();
  
  String getFieldName();
  
  Object getDefaultValue();
  
  Object getTestValue();
  
  void addToParser(PropertyParser paramPropertyParser);
  
  void addToProperties(Properties paramProperties);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\orb\ParserData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */