package com.sun.xml.internal.ws.api.model;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
import javax.xml.namespace.QName;

public interface WSDLOperationMapping {
  WSDLBoundOperation getWSDLBoundOperation();
  
  JavaMethod getJavaMethod();
  
  QName getOperationName();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\model\WSDLOperationMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */