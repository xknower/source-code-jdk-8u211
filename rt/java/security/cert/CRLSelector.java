package java.security.cert;

public interface CRLSelector extends Cloneable {
  boolean match(CRL paramCRL);
  
  Object clone();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\cert\CRLSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */