package com.sun.xml.internal.org.jvnet.fastinfoset.sax;

import org.xml.sax.SAXException;

public interface RestrictedAlphabetContentHandler {
  void numericCharacters(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException;
  
  void dateTimeCharacters(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException;
  
  void alphabetCharacters(String paramString, char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\org\jvnet\fastinfoset\sax\RestrictedAlphabetContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */