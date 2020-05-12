package javax.xml.stream.events;

import javax.xml.namespace.QName;

public interface Attribute extends XMLEvent {
  QName getName();
  
  String getValue();
  
  String getDTDType();
  
  boolean isSpecified();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\stream\events\Attribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */