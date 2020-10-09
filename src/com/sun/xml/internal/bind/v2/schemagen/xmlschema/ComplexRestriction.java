package com.sun.xml.internal.bind.v2.schemagen.xmlschema;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import javax.xml.namespace.QName;

@XmlElement("restriction")
public interface ComplexRestriction extends Annotated, AttrDecls, TypeDefParticle, TypedXmlWriter {
  @XmlAttribute
  ComplexRestriction base(QName paramQName);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\schemagen\xmlschema\ComplexRestriction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */