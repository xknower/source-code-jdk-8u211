package com.sun.xml.internal.bind.v2.schemagen.xmlschema;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlElement("complexType")
public interface ComplexType extends Annotated, ComplexTypeModel, TypedXmlWriter {
  @XmlAttribute("final")
  ComplexType _final(String[] paramArrayOfString);
  
  @XmlAttribute("final")
  ComplexType _final(String paramString);
  
  @XmlAttribute
  ComplexType block(String[] paramArrayOfString);
  
  @XmlAttribute
  ComplexType block(String paramString);
  
  @XmlAttribute("abstract")
  ComplexType _abstract(boolean paramBoolean);
  
  @XmlAttribute
  ComplexType name(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\schemagen\xmlschema\ComplexType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */