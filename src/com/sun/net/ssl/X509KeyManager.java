package com.sun.net.ssl;

import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

@Deprecated
public interface X509KeyManager extends KeyManager {
  String[] getClientAliases(String paramString, Principal[] paramArrayOfPrincipal);
  
  String chooseClientAlias(String paramString, Principal[] paramArrayOfPrincipal);
  
  String[] getServerAliases(String paramString, Principal[] paramArrayOfPrincipal);
  
  String chooseServerAlias(String paramString, Principal[] paramArrayOfPrincipal);
  
  X509Certificate[] getCertificateChain(String paramString);
  
  PrivateKey getPrivateKey(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\net\ssl\X509KeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */