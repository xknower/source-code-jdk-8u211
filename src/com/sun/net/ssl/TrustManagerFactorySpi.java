package com.sun.net.ssl;

import java.security.KeyStore;
import java.security.KeyStoreException;

@Deprecated
public abstract class TrustManagerFactorySpi {
  protected abstract void engineInit(KeyStore paramKeyStore) throws KeyStoreException;
  
  protected abstract TrustManager[] engineGetTrustManagers();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\net\ssl\TrustManagerFactorySpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */