package java.rmi.activation;

import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Activator extends Remote {
  MarshalledObject<? extends Remote> activate(ActivationID paramActivationID, boolean paramBoolean) throws ActivationException, UnknownObjectException, RemoteException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\activation\Activator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */