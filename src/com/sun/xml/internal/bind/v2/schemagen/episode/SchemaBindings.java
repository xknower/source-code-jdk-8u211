package com.sun.xml.internal.bind.v2.schemagen.episode;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

public interface SchemaBindings extends TypedXmlWriter {
  @XmlAttribute
  void map(boolean paramBoolean);
  
  @XmlElement("package")
  Package _package();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\schemagen\episode\SchemaBindings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */