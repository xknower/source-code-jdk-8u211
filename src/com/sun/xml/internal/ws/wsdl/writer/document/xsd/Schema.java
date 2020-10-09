package com.sun.xml.internal.ws.wsdl.writer.document.xsd;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import com.sun.xml.internal.ws.wsdl.writer.document.Documented;

@XmlElement("schema")
public interface Schema extends TypedXmlWriter, Documented {
  @XmlElement("import")
  Import _import();
  
  @XmlAttribute
  Schema targetNamespace(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\wsdl\writer\document\xsd\Schema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */