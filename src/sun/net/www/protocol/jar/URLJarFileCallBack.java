package sun.net.www.protocol.jar;

import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;

public interface URLJarFileCallBack {
  JarFile retrieve(URL paramURL) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\www\protocol\jar\URLJarFileCallBack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */