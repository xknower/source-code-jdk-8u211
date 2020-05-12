package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;
import javax.xml.transform.Transformer;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;

public interface SerializationHandler extends ExtendedContentHandler, ExtendedLexicalHandler, XSLOutputAttributes, DeclHandler, DTDHandler, ErrorHandler, DOMSerializer, Serializer {
  void setContentHandler(ContentHandler paramContentHandler);
  
  void close();
  
  void serialize(Node paramNode) throws IOException;
  
  boolean setEscaping(boolean paramBoolean) throws SAXException;
  
  void setIndentAmount(int paramInt);
  
  void setTransformer(Transformer paramTransformer);
  
  Transformer getTransformer();
  
  void setNamespaceMappings(NamespaceMappings paramNamespaceMappings);
  
  void flushPending() throws SAXException;
  
  void setDTDEntityExpansion(boolean paramBoolean);
  
  void setIsStandalone(boolean paramBoolean);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\serializer\SerializationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */