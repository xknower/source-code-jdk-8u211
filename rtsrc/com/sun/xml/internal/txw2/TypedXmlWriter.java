package com.sun.xml.internal.txw2;

import javax.xml.namespace.QName;

public interface TypedXmlWriter {
  void commit();
  
  void commit(boolean paramBoolean);
  
  void block();
  
  Document getDocument();
  
  void _attribute(String paramString, Object paramObject);
  
  void _attribute(String paramString1, String paramString2, Object paramObject);
  
  void _attribute(QName paramQName, Object paramObject);
  
  void _namespace(String paramString);
  
  void _namespace(String paramString1, String paramString2);
  
  void _namespace(String paramString, boolean paramBoolean);
  
  void _pcdata(Object paramObject);
  
  void _cdata(Object paramObject);
  
  void _comment(Object paramObject) throws UnsupportedOperationException;
  
  <T extends TypedXmlWriter> T _element(String paramString, Class<T> paramClass);
  
  <T extends TypedXmlWriter> T _element(String paramString1, String paramString2, Class<T> paramClass);
  
  <T extends TypedXmlWriter> T _element(QName paramQName, Class<T> paramClass);
  
  <T extends TypedXmlWriter> T _element(Class<T> paramClass);
  
  <T extends TypedXmlWriter> T _cast(Class<T> paramClass);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\txw2\TypedXmlWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */