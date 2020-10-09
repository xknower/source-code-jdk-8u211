package sun.rmi.transport.proxy;

interface CGICommandHandler {
  String getName();
  
  void execute(String paramString) throws CGIClientException, CGIServerException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\transport\proxy\CGICommandHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */