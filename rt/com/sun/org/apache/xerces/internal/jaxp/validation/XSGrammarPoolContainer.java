package com.sun.org.apache.xerces.internal.jaxp.validation;

import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;

public interface XSGrammarPoolContainer {
  XMLGrammarPool getGrammarPool();
  
  boolean isFullyComposed();
  
  Boolean getFeature(String paramString);
  
  void setFeature(String paramString, boolean paramBoolean);
  
  Object getProperty(String paramString);
  
  void setProperty(String paramString, Object paramObject);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\jaxp\validation\XSGrammarPoolContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */