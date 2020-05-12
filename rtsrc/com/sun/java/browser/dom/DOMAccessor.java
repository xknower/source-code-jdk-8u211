package com.sun.java.browser.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public interface DOMAccessor {
  Document getDocument(Object paramObject) throws DOMException;
  
  DOMImplementation getDOMImplementation();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\java\browser\dom\DOMAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */