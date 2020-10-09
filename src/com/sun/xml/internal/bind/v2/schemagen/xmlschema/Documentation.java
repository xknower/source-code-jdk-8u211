package com.sun.xml.internal.bind.v2.schemagen.xmlschema;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlElement("documentation")
public interface Documentation extends TypedXmlWriter {
  @XmlAttribute
  Documentation source(String paramString);
  
  @XmlAttribute(ns = "http://www.w3.org/XML/1998/namespace")
  Documentation lang(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\schemagen\xmlschema\Documentation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */