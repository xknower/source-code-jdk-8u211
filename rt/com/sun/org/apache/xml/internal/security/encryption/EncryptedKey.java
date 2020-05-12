package com.sun.org.apache.xml.internal.security.encryption;

public interface EncryptedKey extends EncryptedType {
  String getRecipient();
  
  void setRecipient(String paramString);
  
  ReferenceList getReferenceList();
  
  void setReferenceList(ReferenceList paramReferenceList);
  
  String getCarriedName();
  
  void setCarriedName(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\security\encryption\EncryptedKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */