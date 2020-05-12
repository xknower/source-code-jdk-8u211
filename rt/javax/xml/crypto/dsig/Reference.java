package javax.xml.crypto.dsig;

import java.io.InputStream;
import java.util.List;
import javax.xml.crypto.Data;
import javax.xml.crypto.URIReference;
import javax.xml.crypto.XMLStructure;

public interface Reference extends URIReference, XMLStructure {
  List getTransforms();
  
  DigestMethod getDigestMethod();
  
  String getId();
  
  byte[] getDigestValue();
  
  byte[] getCalculatedDigestValue();
  
  boolean validate(XMLValidateContext paramXMLValidateContext) throws XMLSignatureException;
  
  Data getDereferencedData();
  
  InputStream getDigestInputStream();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\crypto\dsig\Reference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */