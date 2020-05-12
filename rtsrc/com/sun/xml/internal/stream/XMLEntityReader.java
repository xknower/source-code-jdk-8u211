package com.sun.xml.internal.stream;

import com.sun.org.apache.xerces.internal.util.XMLStringBuffer;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XMLLocator;
import com.sun.org.apache.xerces.internal.xni.XMLString;
import java.io.IOException;

public abstract class XMLEntityReader implements XMLLocator {
  public abstract void setEncoding(String paramString) throws IOException;
  
  public abstract String getEncoding();
  
  public abstract int getCharacterOffset();
  
  public abstract void setVersion(String paramString);
  
  public abstract String getVersion();
  
  public abstract boolean isExternal();
  
  public abstract int peekChar() throws IOException;
  
  public abstract int scanChar() throws IOException;
  
  public abstract String scanNmtoken() throws IOException;
  
  public abstract String scanName() throws IOException;
  
  public abstract boolean scanQName(QName paramQName) throws IOException;
  
  public abstract int scanContent(XMLString paramXMLString) throws IOException;
  
  public abstract int scanLiteral(int paramInt, XMLString paramXMLString) throws IOException;
  
  public abstract boolean scanData(String paramString, XMLStringBuffer paramXMLStringBuffer) throws IOException;
  
  public abstract boolean skipChar(int paramInt) throws IOException;
  
  public abstract boolean skipSpaces() throws IOException;
  
  public abstract boolean skipString(String paramString) throws IOException;
  
  public abstract void registerListener(XMLBufferListener paramXMLBufferListener);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\stream\XMLEntityReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */