package org.w3c.dom;

public interface NameList {
  String getName(int paramInt);
  
  String getNamespaceURI(int paramInt);
  
  int getLength();
  
  boolean contains(String paramString);
  
  boolean containsNS(String paramString1, String paramString2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\NameList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */