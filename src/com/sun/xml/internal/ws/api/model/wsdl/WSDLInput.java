package com.sun.xml.internal.ws.api.model.wsdl;

import com.sun.istack.internal.NotNull;
import javax.xml.namespace.QName;

public interface WSDLInput extends WSDLObject, WSDLExtensible {
  String getName();
  
  WSDLMessage getMessage();
  
  String getAction();
  
  @NotNull
  WSDLOperation getOperation();
  
  @NotNull
  QName getQName();
  
  boolean isDefaultAction();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\model\wsdl\WSDLInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */