package com.sun.xml.internal.bind.v2.schemagen.xmlschema;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlElement;

public interface NestedParticle extends TypedXmlWriter {
  @XmlElement
  LocalElement element();
  
  @XmlElement
  Any any();
  
  @XmlElement
  ExplicitGroup sequence();
  
  @XmlElement
  ExplicitGroup choice();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\schemagen\xmlschema\NestedParticle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */