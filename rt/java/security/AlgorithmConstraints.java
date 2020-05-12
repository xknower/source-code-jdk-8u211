package java.security;

import java.util.Set;

public interface AlgorithmConstraints {
  boolean permits(Set<CryptoPrimitive> paramSet, String paramString, AlgorithmParameters paramAlgorithmParameters);
  
  boolean permits(Set<CryptoPrimitive> paramSet, Key paramKey);
  
  boolean permits(Set<CryptoPrimitive> paramSet, String paramString, Key paramKey, AlgorithmParameters paramAlgorithmParameters);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\AlgorithmConstraints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */