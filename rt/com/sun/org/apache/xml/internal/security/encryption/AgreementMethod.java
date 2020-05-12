package com.sun.org.apache.xml.internal.security.encryption;

import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
import java.util.Iterator;
import org.w3c.dom.Element;

public interface AgreementMethod {
  byte[] getKANonce();
  
  void setKANonce(byte[] paramArrayOfbyte);
  
  Iterator<Element> getAgreementMethodInformation();
  
  void addAgreementMethodInformation(Element paramElement);
  
  void revoveAgreementMethodInformation(Element paramElement);
  
  KeyInfo getOriginatorKeyInfo();
  
  void setOriginatorKeyInfo(KeyInfo paramKeyInfo);
  
  KeyInfo getRecipientKeyInfo();
  
  void setRecipientKeyInfo(KeyInfo paramKeyInfo);
  
  String getAlgorithm();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\security\encryption\AgreementMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */