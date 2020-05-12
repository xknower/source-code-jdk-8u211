package javax.xml.stream;

public interface Location {
  int getLineNumber();
  
  int getColumnNumber();
  
  int getCharacterOffset();
  
  String getPublicId();
  
  String getSystemId();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\stream\Location.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */