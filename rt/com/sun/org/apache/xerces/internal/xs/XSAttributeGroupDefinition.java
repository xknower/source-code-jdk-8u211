package com.sun.org.apache.xerces.internal.xs;

public interface XSAttributeGroupDefinition extends XSObject {
  XSObjectList getAttributeUses();
  
  XSWildcard getAttributeWildcard();
  
  XSAnnotation getAnnotation();
  
  XSObjectList getAnnotations();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xs\XSAttributeGroupDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */