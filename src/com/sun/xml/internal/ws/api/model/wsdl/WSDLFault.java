package com.sun.xml.internal.ws.api.model.wsdl;

import com.sun.istack.internal.NotNull;
import javax.xml.namespace.QName;

public interface WSDLFault extends WSDLObject, WSDLExtensible {
  String getName();
  
  WSDLMessage getMessage();
  
  @NotNull
  WSDLOperation getOperation();
  
  @NotNull
  QName getQName();
  
  String getAction();
  
  boolean isDefaultAction();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\model\wsdl\WSDLFault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */