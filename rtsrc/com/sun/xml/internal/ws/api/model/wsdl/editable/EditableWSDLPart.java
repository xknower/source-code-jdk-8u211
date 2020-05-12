package com.sun.xml.internal.ws.api.model.wsdl.editable;

import com.sun.xml.internal.ws.api.model.ParameterBinding;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLPart;

public interface EditableWSDLPart extends WSDLPart {
  void setBinding(ParameterBinding paramParameterBinding);
  
  void setIndex(int paramInt);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\model\wsdl\editable\EditableWSDLPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */