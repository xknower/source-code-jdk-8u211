package org.xml.sax;

public interface ErrorHandler {
  void warning(SAXParseException paramSAXParseException) throws SAXException;
  
  void error(SAXParseException paramSAXParseException) throws SAXException;
  
  void fatalError(SAXParseException paramSAXParseException) throws SAXException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\xml\sax\ErrorHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */