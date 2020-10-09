package com.sun.xml.internal.org.jvnet.staxex;

import java.io.OutputStream;
import javax.activation.DataHandler;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface XMLStreamWriterEx extends XMLStreamWriter {
  void writeBinary(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, String paramString) throws XMLStreamException;
  
  void writeBinary(DataHandler paramDataHandler) throws XMLStreamException;
  
  OutputStream writeBinary(String paramString) throws XMLStreamException;
  
  void writePCDATA(CharSequence paramCharSequence) throws XMLStreamException;
  
  NamespaceContextEx getNamespaceContext();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\org\jvnet\staxex\XMLStreamWriterEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */