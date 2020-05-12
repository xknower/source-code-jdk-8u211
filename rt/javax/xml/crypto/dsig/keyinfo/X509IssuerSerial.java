package javax.xml.crypto.dsig.keyinfo;

import java.math.BigInteger;
import javax.xml.crypto.XMLStructure;

public interface X509IssuerSerial extends XMLStructure {
  String getIssuerName();
  
  BigInteger getSerialNumber();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\crypto\dsig\keyinfo\X509IssuerSerial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */