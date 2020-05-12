package java.rmi.server;

import java.io.IOException;
import java.net.Socket;

public interface RMIClientSocketFactory {
  Socket createSocket(String paramString, int paramInt) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\server\RMIClientSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */