package com.sun.xml.internal.ws.wsdl.writer.document;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlElement("operation")
public interface Operation extends TypedXmlWriter, Documented {
  @XmlElement
  ParamType input();
  
  @XmlElement
  ParamType output();
  
  @XmlElement
  FaultType fault();
  
  @XmlAttribute
  Operation name(String paramString);
  
  @XmlAttribute
  Operation parameterOrder(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\wsdl\writer\document\Operation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */