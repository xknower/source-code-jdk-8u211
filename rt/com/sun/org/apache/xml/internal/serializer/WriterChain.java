package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

interface WriterChain {
  void write(int paramInt) throws IOException;
  
  void write(char[] paramArrayOfchar) throws IOException;
  
  void write(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException;
  
  void write(String paramString) throws IOException;
  
  void write(String paramString, int paramInt1, int paramInt2) throws IOException;
  
  void flush() throws IOException;
  
  void close() throws IOException;
  
  Writer getWriter();
  
  OutputStream getOutputStream();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\internal\serializer\WriterChain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */