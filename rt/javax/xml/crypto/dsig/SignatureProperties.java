package javax.xml.crypto.dsig;

import java.util.List;
import javax.xml.crypto.XMLStructure;

public interface SignatureProperties extends XMLStructure {
  public static final String TYPE = "http://www.w3.org/2000/09/xmldsig#SignatureProperties";
  
  String getId();
  
  List getProperties();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\crypto\dsig\SignatureProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */