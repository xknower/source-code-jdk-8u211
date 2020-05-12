package com.sun.java.util.jar.pack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

interface CodingMethod {
  void readArrayFrom(InputStream paramInputStream, int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException;
  
  void writeArrayTo(OutputStream paramOutputStream, int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException;
  
  byte[] getMetaCoding(Coding paramCoding);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jav\\util\jar\pack\CodingMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */