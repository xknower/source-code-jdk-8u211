package com.sun.xml.internal.ws.api.model.wsdl.editable;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.api.EndpointAddress;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundPortType;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLService;

public interface EditableWSDLPort extends WSDLPort {
  @NotNull
  EditableWSDLBoundPortType getBinding();
  
  @NotNull
  EditableWSDLService getOwner();
  
  void setAddress(EndpointAddress paramEndpointAddress);
  
  void setEPR(@NotNull WSEndpointReference paramWSEndpointReference);
  
  void freeze(EditableWSDLModel paramEditableWSDLModel);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\model\wsdl\editable\EditableWSDLPort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */