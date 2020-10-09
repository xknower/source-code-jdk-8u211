package com.sun.xml.internal.ws.api.model.wsdl;

import java.util.List;
import javax.xml.namespace.QName;
import org.xml.sax.Locator;

public interface WSDLExtensible extends WSDLObject {
  Iterable<WSDLExtension> getExtensions();
  
  <T extends WSDLExtension> Iterable<T> getExtensions(Class<T> paramClass);
  
  <T extends WSDLExtension> T getExtension(Class<T> paramClass);
  
  void addExtension(WSDLExtension paramWSDLExtension);
  
  boolean areRequiredExtensionsUnderstood();
  
  void addNotUnderstoodExtension(QName paramQName, Locator paramLocator);
  
  List<? extends WSDLExtension> getNotUnderstoodExtensions();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\model\wsdl\WSDLExtensible.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */