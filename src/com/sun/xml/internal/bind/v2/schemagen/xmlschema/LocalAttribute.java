package com.sun.xml.internal.bind.v2.schemagen.xmlschema;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import javax.xml.namespace.QName;

@XmlElement("attribute")
public interface LocalAttribute extends Annotated, AttributeType, FixedOrDefault, TypedXmlWriter {
  @XmlAttribute
  LocalAttribute form(String paramString);
  
  @XmlAttribute
  LocalAttribute name(String paramString);
  
  @XmlAttribute
  LocalAttribute ref(QName paramQName);
  
  @XmlAttribute
  LocalAttribute use(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\schemagen\xmlschema\LocalAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */