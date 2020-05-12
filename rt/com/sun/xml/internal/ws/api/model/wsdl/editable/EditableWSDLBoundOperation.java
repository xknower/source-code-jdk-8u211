package com.sun.xml.internal.ws.api.model.wsdl.editable;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundPortType;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOperation;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLPart;
import java.util.Map;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;

public interface EditableWSDLBoundOperation extends WSDLBoundOperation {
  @NotNull
  EditableWSDLOperation getOperation();
  
  @NotNull
  EditableWSDLBoundPortType getBoundPortType();
  
  @Nullable
  EditableWSDLPart getPart(@NotNull String paramString, @NotNull WebParam.Mode paramMode);
  
  @NotNull
  Map<String, ? extends EditableWSDLPart> getInParts();
  
  @NotNull
  Map<String, ? extends EditableWSDLPart> getOutParts();
  
  @NotNull
  Iterable<? extends EditableWSDLBoundFault> getFaults();
  
  void addPart(EditableWSDLPart paramEditableWSDLPart, WebParam.Mode paramMode);
  
  void addFault(@NotNull EditableWSDLBoundFault paramEditableWSDLBoundFault);
  
  void setAnonymous(WSDLBoundOperation.ANONYMOUS paramANONYMOUS);
  
  void setInputExplicitBodyParts(boolean paramBoolean);
  
  void setOutputExplicitBodyParts(boolean paramBoolean);
  
  void setFaultExplicitBodyParts(boolean paramBoolean);
  
  void setRequestNamespace(String paramString);
  
  void setResponseNamespace(String paramString);
  
  void setSoapAction(String paramString);
  
  void setStyle(SOAPBinding.Style paramStyle);
  
  void freeze(EditableWSDLModel paramEditableWSDLModel);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\model\wsdl\editable\EditableWSDLBoundOperation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */