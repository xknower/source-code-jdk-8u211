package com.sun.xml.internal.bind;

import java.util.concurrent.Callable;
import javax.xml.bind.ValidationEventHandler;
import org.xml.sax.SAXException;

public abstract class IDResolver {
  public void startDocument(ValidationEventHandler eventHandler) throws SAXException {}
  
  public void endDocument() throws SAXException {}
  
  public abstract void bind(String paramString, Object paramObject) throws SAXException;
  
  public abstract Callable<?> resolve(String paramString, Class paramClass) throws SAXException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\IDResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */