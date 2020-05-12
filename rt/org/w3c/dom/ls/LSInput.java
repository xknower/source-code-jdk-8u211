package org.w3c.dom.ls;

import java.io.InputStream;
import java.io.Reader;

public interface LSInput {
  Reader getCharacterStream();
  
  void setCharacterStream(Reader paramReader);
  
  InputStream getByteStream();
  
  void setByteStream(InputStream paramInputStream);
  
  String getStringData();
  
  void setStringData(String paramString);
  
  String getSystemId();
  
  void setSystemId(String paramString);
  
  String getPublicId();
  
  void setPublicId(String paramString);
  
  String getBaseURI();
  
  void setBaseURI(String paramString);
  
  String getEncoding();
  
  void setEncoding(String paramString);
  
  boolean getCertifiedText();
  
  void setCertifiedText(boolean paramBoolean);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\ls\LSInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */