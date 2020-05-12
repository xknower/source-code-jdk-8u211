package java.rmi.server;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;

@Deprecated
public interface RemoteCall {
  @Deprecated
  ObjectOutput getOutputStream() throws IOException;
  
  @Deprecated
  void releaseOutputStream() throws IOException;
  
  @Deprecated
  ObjectInput getInputStream() throws IOException;
  
  @Deprecated
  void releaseInputStream() throws IOException;
  
  @Deprecated
  ObjectOutput getResultStream(boolean paramBoolean) throws IOException, StreamCorruptedException;
  
  @Deprecated
  void executeCall() throws Exception;
  
  @Deprecated
  void done() throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\server\RemoteCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */