package com.sun.xml.internal.ws.wsdl.writer;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import com.sun.xml.internal.ws.wsdl.writer.document.StartWithExtensionsType;

@XmlElement(value = "http://www.w3.org/2006/05/addressing/wsdl", ns = "UsingAddressing")
public interface UsingAddressing extends TypedXmlWriter, StartWithExtensionsType {
  @XmlAttribute(value = "required", ns = "http://schemas.xmlsoap.org/wsdl/")
  void required(boolean paramBoolean);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\wsdl\writer\UsingAddressing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */