package sun.net.spi.nameservice;

public interface NameServiceDescriptor {
  NameService createNameService() throws Exception;
  
  String getProviderName();
  
  String getType();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\spi\nameservice\NameServiceDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */