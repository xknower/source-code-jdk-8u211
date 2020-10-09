package sun.corba;

import com.sun.corba.se.impl.io.ValueHandlerImpl;

public interface JavaCorbaAccess {
  ValueHandlerImpl newValueHandlerImpl();
  
  Class<?> loadClass(String paramString) throws ClassNotFoundException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\corba\JavaCorbaAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */