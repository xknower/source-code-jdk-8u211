package com.sun.org.apache.xerces.internal.xinclude;

import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentFilter;

public interface XPointerSchema extends XMLComponent, XMLDocumentFilter {
  void setXPointerSchemaName(String paramString);
  
  String getXpointerSchemaName();
  
  void setParent(Object paramObject);
  
  Object getParent();
  
  void setXPointerSchemaPointer(String paramString);
  
  String getXPointerSchemaPointer();
  
  boolean isSubResourceIndentified();
  
  void reset();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xinclude\XPointerSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */