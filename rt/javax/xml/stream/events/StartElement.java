package javax.xml.stream.events;

import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

public interface StartElement extends XMLEvent {
  QName getName();
  
  Iterator getAttributes();
  
  Iterator getNamespaces();
  
  Attribute getAttributeByName(QName paramQName);
  
  NamespaceContext getNamespaceContext();
  
  String getNamespaceURI(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\stream\events\StartElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */