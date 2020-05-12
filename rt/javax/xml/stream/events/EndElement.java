package javax.xml.stream.events;

import java.util.Iterator;
import javax.xml.namespace.QName;

public interface EndElement extends XMLEvent {
  QName getName();
  
  Iterator getNamespaces();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\stream\events\EndElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */