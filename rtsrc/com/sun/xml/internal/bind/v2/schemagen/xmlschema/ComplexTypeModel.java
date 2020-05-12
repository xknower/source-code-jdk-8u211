package com.sun.xml.internal.bind.v2.schemagen.xmlschema;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

public interface ComplexTypeModel extends AttrDecls, TypeDefParticle, TypedXmlWriter {
  @XmlElement
  SimpleContent simpleContent();
  
  @XmlElement
  ComplexContent complexContent();
  
  @XmlAttribute
  ComplexTypeModel mixed(boolean paramBoolean);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\schemagen\xmlschema\ComplexTypeModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */