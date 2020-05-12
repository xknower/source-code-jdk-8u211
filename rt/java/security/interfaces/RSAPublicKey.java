package java.security.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;

public interface RSAPublicKey extends PublicKey, RSAKey {
  public static final long serialVersionUID = -8727434096241101194L;
  
  BigInteger getPublicExponent();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\interfaces\RSAPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */