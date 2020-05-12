package sun.misc;

import java.net.InetAddress;
import java.net.URLClassLoader;

public interface JavaNetAccess {
  URLClassPath getURLClassPath(URLClassLoader paramURLClassLoader);
  
  String getOriginalHostName(InetAddress paramInetAddress);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\JavaNetAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */