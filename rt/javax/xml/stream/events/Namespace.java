package javax.xml.stream.events;

public interface Namespace extends Attribute {
  String getPrefix();
  
  String getNamespaceURI();
  
  boolean isDefaultNamespaceDeclaration();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\stream\events\Namespace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */