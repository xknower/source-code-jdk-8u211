package com.sun.corba.se.pept.transport;

import java.util.Iterator;

public interface ContactInfoListIterator extends Iterator {
  ContactInfoList getContactInfoList();
  
  void reportSuccess(ContactInfo paramContactInfo);
  
  boolean reportException(ContactInfo paramContactInfo, RuntimeException paramRuntimeException);
  
  RuntimeException getFailureException();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\pept\transport\ContactInfoListIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */