package com.sun.xml.internal.ws.api.model.wsdl;

import javax.xml.namespace.QName;

public interface WSDLMessage extends WSDLObject, WSDLExtensible {
  QName getName();
  
  Iterable<? extends WSDLPart> parts();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\model\wsdl\WSDLMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */