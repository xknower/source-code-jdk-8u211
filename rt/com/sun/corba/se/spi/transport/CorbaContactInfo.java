package com.sun.corba.se.spi.transport;

import com.sun.corba.se.pept.transport.ContactInfo;
import com.sun.corba.se.spi.ior.IOR;
import com.sun.corba.se.spi.ior.iiop.IIOPProfile;

public interface CorbaContactInfo extends ContactInfo {
  IOR getTargetIOR();
  
  IOR getEffectiveTargetIOR();
  
  IIOPProfile getEffectiveProfile();
  
  void setAddressingDisposition(short paramShort);
  
  short getAddressingDisposition();
  
  String getMonitoringName();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\transport\CorbaContactInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */