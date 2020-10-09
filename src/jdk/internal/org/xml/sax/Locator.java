package jdk.internal.org.xml.sax;

public interface Locator {
  String getPublicId();
  
  String getSystemId();
  
  int getLineNumber();
  
  int getColumnNumber();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\org\xml\sax\Locator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */