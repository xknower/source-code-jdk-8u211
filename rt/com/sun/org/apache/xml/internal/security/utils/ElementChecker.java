package com.sun.org.apache.xml.internal.security.utils;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Deprecated
public interface ElementChecker {
  void guaranteeThatElementInCorrectSpace(ElementProxy paramElementProxy, Element paramElement) throws XMLSecurityException;
  
  boolean isNamespaceElement(Node paramNode, String paramString1, String paramString2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\securit\\utils\ElementChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */