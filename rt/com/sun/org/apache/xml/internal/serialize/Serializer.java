package com.sun.org.apache.xml.internal.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import org.xml.sax.ContentHandler;
import org.xml.sax.DocumentHandler;

public interface Serializer {
  void setOutputByteStream(OutputStream paramOutputStream);
  
  void setOutputCharStream(Writer paramWriter);
  
  void setOutputFormat(OutputFormat paramOutputFormat);
  
  DocumentHandler asDocumentHandler() throws IOException;
  
  ContentHandler asContentHandler() throws IOException;
  
  DOMSerializer asDOMSerializer() throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\serialize\Serializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */