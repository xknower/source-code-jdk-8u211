package com.sun.org.apache.xerces.internal.xni.grammars;

import com.sun.org.apache.xerces.internal.xs.XSModel;

public interface XSGrammar extends Grammar {
  XSModel toXSModel();
  
  XSModel toXSModel(XSGrammar[] paramArrayOfXSGrammar);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xni\grammars\XSGrammar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */