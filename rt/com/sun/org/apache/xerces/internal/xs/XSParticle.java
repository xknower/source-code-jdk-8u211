package com.sun.org.apache.xerces.internal.xs;

public interface XSParticle extends XSObject {
  int getMinOccurs();
  
  int getMaxOccurs();
  
  boolean getMaxOccursUnbounded();
  
  XSTerm getTerm();
  
  XSObjectList getAnnotations();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xs\XSParticle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */