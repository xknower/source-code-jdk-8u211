package com.sun.xml.internal.org.jvnet.fastinfoset.stax;

import javax.xml.stream.XMLStreamException;

public interface FastInfosetStreamReader {
  int peekNext() throws XMLStreamException;
  
  int accessNamespaceCount();
  
  String accessLocalName();
  
  String accessNamespaceURI();
  
  String accessPrefix();
  
  char[] accessTextCharacters();
  
  int accessTextStart();
  
  int accessTextLength();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\org\jvnet\fastinfoset\stax\FastInfosetStreamReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */