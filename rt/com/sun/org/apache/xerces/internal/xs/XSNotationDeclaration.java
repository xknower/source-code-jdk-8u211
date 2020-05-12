package com.sun.org.apache.xerces.internal.xs;

public interface XSNotationDeclaration extends XSObject {
  String getSystemId();
  
  String getPublicId();
  
  XSAnnotation getAnnotation();
  
  XSObjectList getAnnotations();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xs\XSNotationDeclaration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */