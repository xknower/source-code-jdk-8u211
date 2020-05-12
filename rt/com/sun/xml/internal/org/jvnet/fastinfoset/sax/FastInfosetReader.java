package com.sun.xml.internal.org.jvnet.fastinfoset.sax;

import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetException;
import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetParser;
import java.io.IOException;
import java.io.InputStream;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

public interface FastInfosetReader extends XMLReader, FastInfosetParser {
  public static final String ENCODING_ALGORITHM_CONTENT_HANDLER_PROPERTY = "http://jvnet.org/fastinfoset/sax/properties/encoding-algorithm-content-handler";
  
  public static final String PRIMITIVE_TYPE_CONTENT_HANDLER_PROPERTY = "http://jvnet.org/fastinfoset/sax/properties/primitive-type-content-handler";
  
  void parse(InputStream paramInputStream) throws IOException, FastInfosetException, SAXException;
  
  void setLexicalHandler(LexicalHandler paramLexicalHandler);
  
  LexicalHandler getLexicalHandler();
  
  void setDeclHandler(DeclHandler paramDeclHandler);
  
  DeclHandler getDeclHandler();
  
  void setEncodingAlgorithmContentHandler(EncodingAlgorithmContentHandler paramEncodingAlgorithmContentHandler);
  
  EncodingAlgorithmContentHandler getEncodingAlgorithmContentHandler();
  
  void setPrimitiveTypeContentHandler(PrimitiveTypeContentHandler paramPrimitiveTypeContentHandler);
  
  PrimitiveTypeContentHandler getPrimitiveTypeContentHandler();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\org\jvnet\fastinfoset\sax\FastInfosetReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */