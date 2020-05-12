package com.sun.org.apache.xerces.internal.xs;

public interface ElementPSVI extends ItemPSVI {
  XSElementDeclaration getElementDeclaration();
  
  XSNotationDeclaration getNotation();
  
  boolean getNil();
  
  XSModel getSchemaInformation();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xs\ElementPSVI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */