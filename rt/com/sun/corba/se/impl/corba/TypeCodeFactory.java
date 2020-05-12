package com.sun.corba.se.impl.corba;

public interface TypeCodeFactory {
  void setTypeCode(String paramString, TypeCodeImpl paramTypeCodeImpl);
  
  TypeCodeImpl getTypeCode(String paramString);
  
  void setTypeCodeForClass(Class paramClass, TypeCodeImpl paramTypeCodeImpl);
  
  TypeCodeImpl getTypeCodeForClass(Class paramClass);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\corba\TypeCodeFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */