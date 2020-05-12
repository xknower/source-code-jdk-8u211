package com.sun.org.apache.xml.internal.security.encryption;

import org.w3c.dom.Attr;

public interface CipherReference {
  String getURI();
  
  Attr getURIAsAttr();
  
  Transforms getTransforms();
  
  void setTransforms(Transforms paramTransforms);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\security\encryption\CipherReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */