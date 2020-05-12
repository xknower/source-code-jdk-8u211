package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.Enumeration;

public interface CertAttrSet<T> {
  String toString();
  
  void encode(OutputStream paramOutputStream) throws CertificateException, IOException;
  
  void set(String paramString, Object paramObject) throws CertificateException, IOException;
  
  Object get(String paramString) throws CertificateException, IOException;
  
  void delete(String paramString) throws CertificateException, IOException;
  
  Enumeration<T> getElements();
  
  String getName();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\x509\CertAttrSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */