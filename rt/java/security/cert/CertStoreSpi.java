package java.security.cert;

import java.security.InvalidAlgorithmParameterException;
import java.util.Collection;

public abstract class CertStoreSpi {
  public CertStoreSpi(CertStoreParameters paramCertStoreParameters) throws InvalidAlgorithmParameterException {}
  
  public abstract Collection<? extends Certificate> engineGetCertificates(CertSelector paramCertSelector) throws CertStoreException;
  
  public abstract Collection<? extends CRL> engineGetCRLs(CRLSelector paramCRLSelector) throws CertStoreException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\cert\CertStoreSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */