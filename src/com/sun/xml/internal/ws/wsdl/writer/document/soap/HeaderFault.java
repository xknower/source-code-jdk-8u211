package com.sun.xml.internal.ws.wsdl.writer.document.soap;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import javax.xml.namespace.QName;

@XmlElement("headerFault")
public interface HeaderFault extends TypedXmlWriter, BodyType {
  @XmlAttribute
  HeaderFault message(QName paramQName);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\wsdl\writer\document\soap\HeaderFault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */