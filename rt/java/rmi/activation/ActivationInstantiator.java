package java.rmi.activation;

import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ActivationInstantiator extends Remote {
  MarshalledObject<? extends Remote> newInstance(ActivationID paramActivationID, ActivationDesc paramActivationDesc) throws ActivationException, RemoteException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\activation\ActivationInstantiator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */