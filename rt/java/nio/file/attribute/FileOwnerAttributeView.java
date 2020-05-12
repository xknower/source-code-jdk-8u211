package java.nio.file.attribute;

import java.io.IOException;

public interface FileOwnerAttributeView extends FileAttributeView {
  String name();
  
  UserPrincipal getOwner() throws IOException;
  
  void setOwner(UserPrincipal paramUserPrincipal) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\file\attribute\FileOwnerAttributeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */