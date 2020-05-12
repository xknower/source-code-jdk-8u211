package javax.naming.ldap;

import java.io.Serializable;
import javax.naming.NamingException;

public interface ExtendedRequest extends Serializable {
  String getID();
  
  byte[] getEncodedValue();
  
  ExtendedResponse createExtendedResponse(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws NamingException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\naming\ldap\ExtendedRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */