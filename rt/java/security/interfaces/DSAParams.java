package java.security.interfaces;

import java.math.BigInteger;

public interface DSAParams {
  BigInteger getP();
  
  BigInteger getQ();
  
  BigInteger getG();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\interfaces\DSAParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */