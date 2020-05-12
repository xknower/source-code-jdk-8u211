package sun.rmi.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Connection {
  InputStream getInputStream() throws IOException;
  
  void releaseInputStream() throws IOException;
  
  OutputStream getOutputStream() throws IOException;
  
  void releaseOutputStream() throws IOException;
  
  boolean isReusable();
  
  void close() throws IOException;
  
  Channel getChannel();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\transport\Connection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */