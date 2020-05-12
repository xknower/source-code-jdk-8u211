package com.sun.org.apache.xerces.internal.xs;

public interface XSMultiValueFacet extends XSObject {
  short getFacetKind();
  
  StringList getLexicalFacetValues();
  
  XSObjectList getAnnotations();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xs\XSMultiValueFacet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */