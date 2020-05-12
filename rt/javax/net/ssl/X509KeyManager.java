package javax.net.ssl;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public interface X509KeyManager extends KeyManager {
  String[] getClientAliases(String paramString, Principal[] paramArrayOfPrincipal);
  
  String chooseClientAlias(String[] paramArrayOfString, Principal[] paramArrayOfPrincipal, Socket paramSocket);
  
  String[] getServerAliases(String paramString, Principal[] paramArrayOfPrincipal);
  
  String chooseServerAlias(String paramString, Principal[] paramArrayOfPrincipal, Socket paramSocket);
  
  X509Certificate[] getCertificateChain(String paramString);
  
  PrivateKey getPrivateKey(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\net\ssl\X509KeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */