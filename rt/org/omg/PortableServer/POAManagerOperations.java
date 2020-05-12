package org.omg.PortableServer;

import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAManagerPackage.State;

public interface POAManagerOperations {
  void activate() throws AdapterInactive;
  
  void hold_requests(boolean paramBoolean) throws AdapterInactive;
  
  void discard_requests(boolean paramBoolean) throws AdapterInactive;
  
  void deactivate(boolean paramBoolean1, boolean paramBoolean2) throws AdapterInactive;
  
  State get_state();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableServer\POAManagerOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */