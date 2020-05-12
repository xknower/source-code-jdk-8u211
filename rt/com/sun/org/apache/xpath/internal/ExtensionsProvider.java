package com.sun.org.apache.xpath.internal;

import com.sun.org.apache.xpath.internal.functions.FuncExtFunction;
import java.util.Vector;
import javax.xml.transform.TransformerException;

public interface ExtensionsProvider {
  boolean functionAvailable(String paramString1, String paramString2) throws TransformerException;
  
  boolean elementAvailable(String paramString1, String paramString2) throws TransformerException;
  
  Object extFunction(String paramString1, String paramString2, Vector paramVector, Object paramObject) throws TransformerException;
  
  Object extFunction(FuncExtFunction paramFuncExtFunction, Vector paramVector) throws TransformerException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\ExtensionsProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */