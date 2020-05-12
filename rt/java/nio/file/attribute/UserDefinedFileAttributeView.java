package java.nio.file.attribute;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public interface UserDefinedFileAttributeView extends FileAttributeView {
  String name();
  
  List<String> list() throws IOException;
  
  int size(String paramString) throws IOException;
  
  int read(String paramString, ByteBuffer paramByteBuffer) throws IOException;
  
  int write(String paramString, ByteBuffer paramByteBuffer) throws IOException;
  
  void delete(String paramString) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\file\attribute\UserDefinedFileAttributeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */