package com.sun.org.apache.xalan.internal.xsltc.runtime.output;

interface OutputBuffer {
  String close();
  
  OutputBuffer append(char paramChar);
  
  OutputBuffer append(String paramString);
  
  OutputBuffer append(char[] paramArrayOfchar, int paramInt1, int paramInt2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\runtime\output\OutputBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */