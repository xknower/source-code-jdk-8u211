package java.security;

import java.security.spec.AlgorithmParameterSpec;

public abstract class AlgorithmParameterGeneratorSpi {
  protected abstract void engineInit(int paramInt, SecureRandom paramSecureRandom);
  
  protected abstract void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException;
  
  protected abstract AlgorithmParameters engineGenerateParameters();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\AlgorithmParameterGeneratorSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */