package java.security;

import java.io.Serializable;

public abstract class SecureRandomSpi implements Serializable {
  private static final long serialVersionUID = -2991854161009191830L;
  
  protected abstract void engineSetSeed(byte[] paramArrayOfbyte);
  
  protected abstract void engineNextBytes(byte[] paramArrayOfbyte);
  
  protected abstract byte[] engineGenerateSeed(int paramInt);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\SecureRandomSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */