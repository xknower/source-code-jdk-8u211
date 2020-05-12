package javax.xml.stream.events;

public interface EntityDeclaration extends XMLEvent {
  String getPublicId();
  
  String getSystemId();
  
  String getName();
  
  String getNotationName();
  
  String getReplacementText();
  
  String getBaseURI();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\stream\events\EntityDeclaration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */