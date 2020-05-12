package org.w3c.dom;

public interface DOMError {
  public static final short SEVERITY_WARNING = 1;
  
  public static final short SEVERITY_ERROR = 2;
  
  public static final short SEVERITY_FATAL_ERROR = 3;
  
  short getSeverity();
  
  String getMessage();
  
  String getType();
  
  Object getRelatedException();
  
  Object getRelatedData();
  
  DOMLocator getLocation();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\DOMError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */