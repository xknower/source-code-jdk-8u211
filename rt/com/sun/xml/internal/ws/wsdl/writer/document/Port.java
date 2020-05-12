package com.sun.xml.internal.ws.wsdl.writer.document;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import javax.xml.namespace.QName;

@XmlElement("port")
public interface Port extends TypedXmlWriter, Documented {
  @XmlAttribute
  Port name(String paramString);
  
  @XmlAttribute
  Port arrayType(String paramString);
  
  @XmlAttribute
  Port binding(QName paramQName);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\wsdl\writer\document\Port.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */