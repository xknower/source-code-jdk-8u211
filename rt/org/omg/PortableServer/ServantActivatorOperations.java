package org.omg.PortableServer;

public interface ServantActivatorOperations extends ServantManagerOperations {
  Servant incarnate(byte[] paramArrayOfbyte, POA paramPOA) throws ForwardRequest;
  
  void etherealize(byte[] paramArrayOfbyte, POA paramPOA, Servant paramServant, boolean paramBoolean1, boolean paramBoolean2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableServer\ServantActivatorOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */