package java.net;

import java.io.IOException;

interface InetAddressImpl {
  String getLocalHostName() throws UnknownHostException;
  
  InetAddress[] lookupAllHostAddr(String paramString) throws UnknownHostException;
  
  String getHostByAddr(byte[] paramArrayOfbyte) throws UnknownHostException;
  
  InetAddress anyLocalAddress();
  
  InetAddress loopbackAddress();
  
  boolean isReachable(InetAddress paramInetAddress, int paramInt1, NetworkInterface paramNetworkInterface, int paramInt2) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\InetAddressImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */