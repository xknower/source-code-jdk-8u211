package java.security.cert;

public interface CertPathBuilderResult extends Cloneable {
  CertPath getCertPath();
  
  Object clone();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\cert\CertPathBuilderResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */