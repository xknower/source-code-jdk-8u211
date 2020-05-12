package com.sun.net.ssl;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

@Deprecated
public abstract class KeyManagerFactorySpi {
  protected abstract void engineInit(KeyStore paramKeyStore, char[] paramArrayOfchar) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException;
  
  protected abstract KeyManager[] engineGetKeyManagers();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\net\ssl\KeyManagerFactorySpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */