package com.sun.xml.internal.ws.api.model.wsdl.editable;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLFault;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLMessage;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOperation;

public interface EditableWSDLFault extends WSDLFault {
  EditableWSDLMessage getMessage();
  
  @NotNull
  EditableWSDLOperation getOperation();
  
  void setAction(String paramString);
  
  void setDefaultAction(boolean paramBoolean);
  
  void freeze(EditableWSDLModel paramEditableWSDLModel);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\model\wsdl\editable\EditableWSDLFault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */