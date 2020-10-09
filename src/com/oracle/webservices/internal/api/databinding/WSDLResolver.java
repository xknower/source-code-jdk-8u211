package com.oracle.webservices.internal.api.databinding;

import javax.xml.transform.Result;
import javax.xml.ws.Holder;

public interface WSDLResolver {
  Result getWSDL(String paramString);
  
  Result getAbstractWSDL(Holder<String> paramHolder);
  
  Result getSchemaOutput(String paramString, Holder<String> paramHolder);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\oracle\webservices\internal\api\databinding\WSDLResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */