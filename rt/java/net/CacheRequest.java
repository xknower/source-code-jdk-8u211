package java.net;

import java.io.IOException;
import java.io.OutputStream;

public abstract class CacheRequest {
  public abstract OutputStream getBody() throws IOException;
  
  public abstract void abort();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\CacheRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */