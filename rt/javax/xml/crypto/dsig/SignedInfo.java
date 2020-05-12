package javax.xml.crypto.dsig;

import java.io.InputStream;
import java.util.List;
import javax.xml.crypto.XMLStructure;

public interface SignedInfo extends XMLStructure {
  CanonicalizationMethod getCanonicalizationMethod();
  
  SignatureMethod getSignatureMethod();
  
  List getReferences();
  
  String getId();
  
  InputStream getCanonicalizedData();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\crypto\dsig\SignedInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */