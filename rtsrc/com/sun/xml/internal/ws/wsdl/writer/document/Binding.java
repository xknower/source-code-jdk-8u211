package com.sun.xml.internal.ws.wsdl.writer.document;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import com.sun.xml.internal.ws.wsdl.writer.document.soap.SOAPBinding;
import com.sun.xml.internal.ws.wsdl.writer.document.soap12.SOAPBinding;
import javax.xml.namespace.QName;

@XmlElement("binding")
public interface Binding extends TypedXmlWriter, StartWithExtensionsType {
  @XmlAttribute
  Binding type(QName paramQName);
  
  @XmlAttribute
  Binding name(String paramString);
  
  @XmlElement
  BindingOperationType operation();
  
  @XmlElement(value = "binding", ns = "http://schemas.xmlsoap.org/wsdl/soap/")
  SOAPBinding soapBinding();
  
  @XmlElement(value = "binding", ns = "http://schemas.xmlsoap.org/wsdl/soap12/")
  SOAPBinding soap12Binding();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\wsdl\writer\document\Binding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */