package java.nio.file.attribute;

import java.util.Set;

public interface PosixFileAttributes extends BasicFileAttributes {
  UserPrincipal owner();
  
  GroupPrincipal group();
  
  Set<PosixFilePermission> permissions();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\file\attribute\PosixFileAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */