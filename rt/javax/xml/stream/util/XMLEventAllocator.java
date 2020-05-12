package javax.xml.stream.util;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public interface XMLEventAllocator {
  XMLEventAllocator newInstance();
  
  XMLEvent allocate(XMLStreamReader paramXMLStreamReader) throws XMLStreamException;
  
  void allocate(XMLStreamReader paramXMLStreamReader, XMLEventConsumer paramXMLEventConsumer) throws XMLStreamException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\strea\\util\XMLEventAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */