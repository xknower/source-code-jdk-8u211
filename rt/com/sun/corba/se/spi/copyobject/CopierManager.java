package com.sun.corba.se.spi.copyobject;

public interface CopierManager {
  void setDefaultId(int paramInt);
  
  int getDefaultId();
  
  ObjectCopierFactory getObjectCopierFactory(int paramInt);
  
  ObjectCopierFactory getDefaultObjectCopierFactory();
  
  void registerObjectCopierFactory(ObjectCopierFactory paramObjectCopierFactory, int paramInt);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\copyobject\CopierManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */