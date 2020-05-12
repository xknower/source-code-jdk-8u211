package com.sun.org.apache.xml.internal.dtm.ref;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public interface IncrementalSAXSource {
  void setContentHandler(ContentHandler paramContentHandler);
  
  void setLexicalHandler(LexicalHandler paramLexicalHandler);
  
  void setDTDHandler(DTDHandler paramDTDHandler);
  
  Object deliverMoreNodes(boolean paramBoolean);
  
  void startParse(InputSource paramInputSource) throws SAXException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\dtm\ref\IncrementalSAXSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */