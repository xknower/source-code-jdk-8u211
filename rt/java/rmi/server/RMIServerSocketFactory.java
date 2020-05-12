package java.rmi.server;

import java.io.IOException;
import java.net.ServerSocket;

public interface RMIServerSocketFactory {
  ServerSocket createServerSocket(int paramInt) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\server\RMIServerSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */