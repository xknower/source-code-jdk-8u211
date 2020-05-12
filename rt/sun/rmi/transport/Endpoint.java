package sun.rmi.transport;

import java.rmi.RemoteException;

public interface Endpoint {
  Channel getChannel();
  
  void exportObject(Target paramTarget) throws RemoteException;
  
  Transport getInboundTransport();
  
  Transport getOutboundTransport();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\transport\Endpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */