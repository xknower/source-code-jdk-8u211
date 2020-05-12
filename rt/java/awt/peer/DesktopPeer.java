package java.awt.peer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public interface DesktopPeer {
  boolean isSupported(Desktop.Action paramAction);
  
  void open(File paramFile) throws IOException;
  
  void edit(File paramFile) throws IOException;
  
  void print(File paramFile) throws IOException;
  
  void mail(URI paramURI) throws IOException;
  
  void browse(URI paramURI) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\peer\DesktopPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */