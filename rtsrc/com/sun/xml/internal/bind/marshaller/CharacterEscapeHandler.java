package com.sun.xml.internal.bind.marshaller;

import java.io.IOException;
import java.io.Writer;

public interface CharacterEscapeHandler {
  void escape(char[] paramArrayOfchar, int paramInt1, int paramInt2, boolean paramBoolean, Writer paramWriter) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\marshaller\CharacterEscapeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */