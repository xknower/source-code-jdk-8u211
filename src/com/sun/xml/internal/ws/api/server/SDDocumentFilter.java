package com.sun.xml.internal.ws.api.server;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface SDDocumentFilter {
  XMLStreamWriter filter(SDDocument paramSDDocument, XMLStreamWriter paramXMLStreamWriter) throws XMLStreamException, IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\server\SDDocumentFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */