package javax.naming.ldap;

import java.io.Serializable;

public interface ExtendedResponse extends Serializable {
  String getID();
  
  byte[] getEncodedValue();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\naming\ldap\ExtendedResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */