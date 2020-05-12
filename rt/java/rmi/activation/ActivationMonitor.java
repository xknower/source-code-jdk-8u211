package java.rmi.activation;

import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ActivationMonitor extends Remote {
  void inactiveObject(ActivationID paramActivationID) throws UnknownObjectException, RemoteException;
  
  void activeObject(ActivationID paramActivationID, MarshalledObject<? extends Remote> paramMarshalledObject) throws UnknownObjectException, RemoteException;
  
  void inactiveGroup(ActivationGroupID paramActivationGroupID, long paramLong) throws UnknownGroupException, RemoteException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\activation\ActivationMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */