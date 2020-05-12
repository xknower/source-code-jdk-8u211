package javax.xml.bind;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public interface Unmarshaller {
  Object unmarshal(File paramFile) throws JAXBException;
  
  Object unmarshal(InputStream paramInputStream) throws JAXBException;
  
  Object unmarshal(Reader paramReader) throws JAXBException;
  
  Object unmarshal(URL paramURL) throws JAXBException;
  
  Object unmarshal(InputSource paramInputSource) throws JAXBException;
  
  Object unmarshal(Node paramNode) throws JAXBException;
  
  <T> JAXBElement<T> unmarshal(Node paramNode, Class<T> paramClass) throws JAXBException;
  
  Object unmarshal(Source paramSource) throws JAXBException;
  
  <T> JAXBElement<T> unmarshal(Source paramSource, Class<T> paramClass) throws JAXBException;
  
  Object unmarshal(XMLStreamReader paramXMLStreamReader) throws JAXBException;
  
  <T> JAXBElement<T> unmarshal(XMLStreamReader paramXMLStreamReader, Class<T> paramClass) throws JAXBException;
  
  Object unmarshal(XMLEventReader paramXMLEventReader) throws JAXBException;
  
  <T> JAXBElement<T> unmarshal(XMLEventReader paramXMLEventReader, Class<T> paramClass) throws JAXBException;
  
  UnmarshallerHandler getUnmarshallerHandler();
  
  void setValidating(boolean paramBoolean) throws JAXBException;
  
  boolean isValidating() throws JAXBException;
  
  void setEventHandler(ValidationEventHandler paramValidationEventHandler) throws JAXBException;
  
  ValidationEventHandler getEventHandler() throws JAXBException;
  
  void setProperty(String paramString, Object paramObject) throws PropertyException;
  
  Object getProperty(String paramString) throws PropertyException;
  
  void setSchema(Schema paramSchema);
  
  Schema getSchema();
  
  void setAdapter(XmlAdapter paramXmlAdapter);
  
  <A extends XmlAdapter> void setAdapter(Class<A> paramClass, A paramA);
  
  <A extends XmlAdapter> A getAdapter(Class<A> paramClass);
  
  void setAttachmentUnmarshaller(AttachmentUnmarshaller paramAttachmentUnmarshaller);
  
  AttachmentUnmarshaller getAttachmentUnmarshaller();
  
  void setListener(Listener paramListener);
  
  Listener getListener();
  
  public static abstract class Listener {
    public void beforeUnmarshal(Object target, Object parent) {}
    
    public void afterUnmarshal(Object target, Object parent) {}
  }
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\bind\Unmarshaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */