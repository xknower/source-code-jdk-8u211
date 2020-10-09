package com.sun.xml.internal.ws.spi.db;

import com.oracle.webservices.internal.api.databinding.Databinding;
import com.oracle.webservices.internal.api.databinding.WSDLGenerator;
import com.sun.xml.internal.ws.api.databinding.DatabindingConfig;
import java.util.Map;

public interface DatabindingProvider {
  boolean isFor(String paramString);
  
  void init(Map<String, Object> paramMap);
  
  Databinding create(DatabindingConfig paramDatabindingConfig);
  
  WSDLGenerator wsdlGen(DatabindingConfig paramDatabindingConfig);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\spi\db\DatabindingProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */