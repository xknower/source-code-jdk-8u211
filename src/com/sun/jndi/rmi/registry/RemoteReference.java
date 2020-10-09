package com.sun.jndi.rmi.registry;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import javax.naming.Reference;

public interface RemoteReference extends Remote {
  Reference getReference() throws NamingException, RemoteException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jndi\rmi\registry\RemoteReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */