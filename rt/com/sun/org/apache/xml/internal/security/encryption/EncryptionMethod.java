package com.sun.org.apache.xml.internal.security.encryption;

import java.util.Iterator;
import org.w3c.dom.Element;

public interface EncryptionMethod {
  String getAlgorithm();
  
  int getKeySize();
  
  void setKeySize(int paramInt);
  
  byte[] getOAEPparams();
  
  void setOAEPparams(byte[] paramArrayOfbyte);
  
  void setDigestAlgorithm(String paramString);
  
  String getDigestAlgorithm();
  
  void setMGFAlgorithm(String paramString);
  
  String getMGFAlgorithm();
  
  Iterator<Element> getEncryptionMethodInformation();
  
  void addEncryptionMethodInformation(Element paramElement);
  
  void removeEncryptionMethodInformation(Element paramElement);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\security\encryption\EncryptionMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */