package com.sun.xml.internal.org.jvnet.staxex;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public interface XMLStreamReaderEx extends XMLStreamReader {
  CharSequence getPCDATA() throws XMLStreamException;
  
  NamespaceContextEx getNamespaceContext();
  
  String getElementTextTrim() throws XMLStreamException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\org\jvnet\staxex\XMLStreamReaderEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */