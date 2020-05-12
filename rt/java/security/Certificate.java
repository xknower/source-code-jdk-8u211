package java.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Deprecated
public interface Certificate {
  Principal getGuarantor();
  
  Principal getPrincipal();
  
  PublicKey getPublicKey();
  
  void encode(OutputStream paramOutputStream) throws KeyException, IOException;
  
  void decode(InputStream paramInputStream) throws KeyException, IOException;
  
  String getFormat();
  
  String toString(boolean paramBoolean);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\Certificate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */