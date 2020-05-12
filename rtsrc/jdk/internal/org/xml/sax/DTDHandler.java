package jdk.internal.org.xml.sax;

public interface DTDHandler {
  void notationDecl(String paramString1, String paramString2, String paramString3) throws SAXException;
  
  void unparsedEntityDecl(String paramString1, String paramString2, String paramString3, String paramString4) throws SAXException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\org\xml\sax\DTDHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */