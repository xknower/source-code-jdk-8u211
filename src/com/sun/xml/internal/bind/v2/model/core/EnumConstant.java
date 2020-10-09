package com.sun.xml.internal.bind.v2.model.core;

public interface EnumConstant<T, C> {
  EnumLeafInfo<T, C> getEnclosingClass();
  
  String getLexicalValue();
  
  String getName();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\core\EnumConstant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */