package java.rmi.server;

import java.net.MalformedURLException;

public abstract class RMIClassLoaderSpi {
  public abstract Class<?> loadClass(String paramString1, String paramString2, ClassLoader paramClassLoader) throws MalformedURLException, ClassNotFoundException;
  
  public abstract Class<?> loadProxyClass(String paramString, String[] paramArrayOfString, ClassLoader paramClassLoader) throws MalformedURLException, ClassNotFoundException;
  
  public abstract ClassLoader getClassLoader(String paramString) throws MalformedURLException;
  
  public abstract String getClassAnnotation(Class<?> paramClass);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\server\RMIClassLoaderSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */