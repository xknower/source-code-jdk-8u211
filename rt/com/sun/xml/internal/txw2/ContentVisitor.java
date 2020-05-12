package com.sun.xml.internal.txw2;

interface ContentVisitor {
  void onStartDocument();
  
  void onEndDocument();
  
  void onEndTag();
  
  void onPcdata(StringBuilder paramStringBuilder);
  
  void onCdata(StringBuilder paramStringBuilder);
  
  void onStartTag(String paramString1, String paramString2, Attribute paramAttribute, NamespaceDecl paramNamespaceDecl);
  
  void onComment(StringBuilder paramStringBuilder);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\txw2\ContentVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */