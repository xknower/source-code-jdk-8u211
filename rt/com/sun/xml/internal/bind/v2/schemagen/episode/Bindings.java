package com.sun.xml.internal.bind.v2.schemagen.episode;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlElement("bindings")
public interface Bindings extends TypedXmlWriter {
  @XmlElement
  Bindings bindings();
  
  @XmlElement("class")
  Klass klass();
  
  Klass typesafeEnumClass();
  
  @XmlElement
  SchemaBindings schemaBindings();
  
  @XmlAttribute
  void scd(String paramString);
  
  @XmlAttribute
  void version(String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\schemagen\episode\Bindings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */