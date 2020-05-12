package com.oracle.webservices.internal.api.databinding;

import java.lang.reflect.Method;

public interface JavaCallInfo {
  Method getMethod();
  
  Object[] getParameters();
  
  Object getReturnValue();
  
  void setReturnValue(Object paramObject);
  
  Throwable getException();
  
  void setException(Throwable paramThrowable);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\oracle\webservices\internal\api\databinding\JavaCallInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */