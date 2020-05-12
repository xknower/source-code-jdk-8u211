package com.sun.xml.internal.ws.wsdl.writer.document.xsd;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import com.sun.xml.internal.ws.wsdl.writer.document.Documented;

@XmlElement("import")
public interface Import extends TypedXmlWriter, Documented {
  @XmlAttribute
  Import schemaLocation(String paramString);
  
  @XmlAttribute
  Import namespace(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\wsdl\writer\document\xsd\Import.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */