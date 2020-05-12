package javax.xml.bind;

import org.xml.sax.ContentHandler;

public interface UnmarshallerHandler extends ContentHandler {
  Object getResult() throws JAXBException, IllegalStateException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\bind\UnmarshallerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */