package java.rmi.server;

import java.net.MalformedURLException;
import java.net.URL;

@Deprecated
public interface LoaderHandler {
  public static final String packagePrefix = "sun.rmi.server";
  
  @Deprecated
  Class<?> loadClass(String paramString) throws MalformedURLException, ClassNotFoundException;
  
  @Deprecated
  Class<?> loadClass(URL paramURL, String paramString) throws MalformedURLException, ClassNotFoundException;
  
  @Deprecated
  Object getSecurityContext(ClassLoader paramClassLoader);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\server\LoaderHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */