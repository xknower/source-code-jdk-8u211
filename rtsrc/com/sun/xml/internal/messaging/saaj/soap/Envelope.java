package com.sun.xml.internal.messaging.saaj.soap;

import java.io.IOException;
import java.io.OutputStream;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.transform.Source;

public interface Envelope extends SOAPEnvelope {
  Source getContent();
  
  void output(OutputStream paramOutputStream) throws IOException;
  
  void output(OutputStream paramOutputStream, boolean paramBoolean) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saaj\soap\Envelope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */