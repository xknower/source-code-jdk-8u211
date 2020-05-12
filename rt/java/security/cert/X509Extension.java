package java.security.cert;

import java.util.Set;

public interface X509Extension {
  boolean hasUnsupportedCriticalExtension();
  
  Set<String> getCriticalExtensionOIDs();
  
  Set<String> getNonCriticalExtensionOIDs();
  
  byte[] getExtensionValue(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\cert\X509Extension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */