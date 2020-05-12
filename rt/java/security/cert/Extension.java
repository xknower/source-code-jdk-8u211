package java.security.cert;

import java.io.IOException;
import java.io.OutputStream;

public interface Extension {
  String getId();
  
  boolean isCritical();
  
  byte[] getValue();
  
  void encode(OutputStream paramOutputStream) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\cert\Extension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */