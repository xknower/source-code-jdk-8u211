package org.w3c.dom;

public interface DocumentType extends Node {
  String getName();
  
  NamedNodeMap getEntities();
  
  NamedNodeMap getNotations();
  
  String getPublicId();
  
  String getSystemId();
  
  String getInternalSubset();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\DocumentType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */