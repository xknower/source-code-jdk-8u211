package javax.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;

public interface FileObject {
  URI toUri();
  
  String getName();
  
  InputStream openInputStream() throws IOException;
  
  OutputStream openOutputStream() throws IOException;
  
  Reader openReader(boolean paramBoolean) throws IOException;
  
  CharSequence getCharContent(boolean paramBoolean) throws IOException;
  
  Writer openWriter() throws IOException;
  
  long getLastModified();
  
  boolean delete();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\tools\FileObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */