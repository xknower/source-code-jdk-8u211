package java.security.interfaces;

import java.math.BigInteger;
import java.security.spec.RSAOtherPrimeInfo;

public interface RSAMultiPrimePrivateCrtKey extends RSAPrivateKey {
  public static final long serialVersionUID = 618058533534628008L;
  
  BigInteger getPublicExponent();
  
  BigInteger getPrimeP();
  
  BigInteger getPrimeQ();
  
  BigInteger getPrimeExponentP();
  
  BigInteger getPrimeExponentQ();
  
  BigInteger getCrtCoefficient();
  
  RSAOtherPrimeInfo[] getOtherPrimeInfo();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\interfaces\RSAMultiPrimePrivateCrtKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */