package org.xml.sax;

public interface XMLFilter extends XMLReader {
  void setParent(XMLReader paramXMLReader);
  
  XMLReader getParent();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\xml\sax\XMLFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */