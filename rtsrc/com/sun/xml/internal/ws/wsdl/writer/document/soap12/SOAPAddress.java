package com.sun.xml.internal.ws.wsdl.writer.document.soap12;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlElement("address")
public interface SOAPAddress extends TypedXmlWriter {
  @XmlAttribute
  SOAPAddress location(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\wsdl\writer\document\soap12\SOAPAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */