package javax.xml.namespace;

import java.util.Iterator;

public interface NamespaceContext {
  String getNamespaceURI(String paramString);
  
  String getPrefix(String paramString);
  
  Iterator getPrefixes(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\namespace\NamespaceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */