package com.sun.corba.se.spi.transport;

import com.sun.corba.se.pept.transport.Acceptor;
import com.sun.corba.se.spi.orb.ORB;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public interface ORBSocketFactory {
  void setORB(ORB paramORB);
  
  ServerSocket createServerSocket(String paramString, InetSocketAddress paramInetSocketAddress) throws IOException;
  
  Socket createSocket(String paramString, InetSocketAddress paramInetSocketAddress) throws IOException;
  
  void setAcceptedSocketOptions(Acceptor paramAcceptor, ServerSocket paramServerSocket, Socket paramSocket) throws SocketException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\transport\ORBSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */