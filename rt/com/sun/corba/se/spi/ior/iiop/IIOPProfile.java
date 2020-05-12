package com.sun.corba.se.spi.ior.iiop;

import com.sun.corba.se.spi.ior.TaggedProfile;
import com.sun.corba.se.spi.orb.ORBVersion;

public interface IIOPProfile extends TaggedProfile {
  ORBVersion getORBVersion();
  
  Object getServant();
  
  GIOPVersion getGIOPVersion();
  
  String getCodebase();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\ior\iiop\IIOPProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */