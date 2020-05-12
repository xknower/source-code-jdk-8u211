package com.sun.org.apache.xerces.internal.xs;

public interface XSFacet extends XSObject {
  short getFacetKind();
  
  String getLexicalFacetValue();
  
  boolean getFixed();
  
  XSAnnotation getAnnotation();
  
  XSObjectList getAnnotations();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xs\XSFacet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */