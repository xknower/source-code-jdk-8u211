package javax.accessibility;

import java.awt.datatransfer.DataFlavor;
import java.io.InputStream;

public interface AccessibleStreamable {
  DataFlavor[] getMimeTypes();
  
  InputStream getStream(DataFlavor paramDataFlavor);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\accessibility\AccessibleStreamable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */