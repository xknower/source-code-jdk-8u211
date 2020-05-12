package java.rmi.activation;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ActivationSystem extends Remote {
  public static final int SYSTEM_PORT = 1098;
  
  ActivationID registerObject(ActivationDesc paramActivationDesc) throws ActivationException, UnknownGroupException, RemoteException;
  
  void unregisterObject(ActivationID paramActivationID) throws ActivationException, UnknownObjectException, RemoteException;
  
  ActivationGroupID registerGroup(ActivationGroupDesc paramActivationGroupDesc) throws ActivationException, RemoteException;
  
  ActivationMonitor activeGroup(ActivationGroupID paramActivationGroupID, ActivationInstantiator paramActivationInstantiator, long paramLong) throws UnknownGroupException, ActivationException, RemoteException;
  
  void unregisterGroup(ActivationGroupID paramActivationGroupID) throws ActivationException, UnknownGroupException, RemoteException;
  
  void shutdown() throws RemoteException;
  
  ActivationDesc setActivationDesc(ActivationID paramActivationID, ActivationDesc paramActivationDesc) throws ActivationException, UnknownObjectException, UnknownGroupException, RemoteException;
  
  ActivationGroupDesc setActivationGroupDesc(ActivationGroupID paramActivationGroupID, ActivationGroupDesc paramActivationGroupDesc) throws ActivationException, UnknownGroupException, RemoteException;
  
  ActivationDesc getActivationDesc(ActivationID paramActivationID) throws ActivationException, UnknownObjectException, RemoteException;
  
  ActivationGroupDesc getActivationGroupDesc(ActivationGroupID paramActivationGroupID) throws ActivationException, UnknownGroupException, RemoteException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\activation\ActivationSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */