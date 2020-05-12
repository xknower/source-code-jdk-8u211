package org.omg.PortableInterceptor;

public interface ClientRequestInterceptorOperations extends InterceptorOperations {
  void send_request(ClientRequestInfo paramClientRequestInfo) throws ForwardRequest;
  
  void send_poll(ClientRequestInfo paramClientRequestInfo);
  
  void receive_reply(ClientRequestInfo paramClientRequestInfo);
  
  void receive_exception(ClientRequestInfo paramClientRequestInfo) throws ForwardRequest;
  
  void receive_other(ClientRequestInfo paramClientRequestInfo) throws ForwardRequest;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableInterceptor\ClientRequestInterceptorOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */