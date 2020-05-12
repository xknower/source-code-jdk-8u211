package java.rmi.registry;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Registry extends Remote {
  public static final int REGISTRY_PORT = 1099;
  
  Remote lookup(String paramString) throws RemoteException, NotBoundException, AccessException;
  
  void bind(String paramString, Remote paramRemote) throws RemoteException, AlreadyBoundException, AccessException;
  
  void unbind(String paramString) throws RemoteException, NotBoundException, AccessException;
  
  void rebind(String paramString, Remote paramRemote) throws RemoteException, AccessException;
  
  String[] list() throws RemoteException, AccessException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\registry\Registry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */