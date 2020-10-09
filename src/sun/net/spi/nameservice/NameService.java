package sun.net.spi.nameservice;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface NameService {
  InetAddress[] lookupAllHostAddr(String paramString) throws UnknownHostException;
  
  String getHostByAddr(byte[] paramArrayOfbyte) throws UnknownHostException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\spi\nameservice\NameService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */