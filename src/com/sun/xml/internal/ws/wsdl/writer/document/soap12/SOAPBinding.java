package com.sun.xml.internal.ws.wsdl.writer.document.soap12;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlElement("binding")
public interface SOAPBinding extends TypedXmlWriter {
  @XmlAttribute
  SOAPBinding transport(String paramString);
  
  @XmlAttribute
  SOAPBinding style(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\wsdl\writer\document\soap12\SOAPBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */