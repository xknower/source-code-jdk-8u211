package java.nio.file.attribute;

public interface BasicFileAttributes {
  FileTime lastModifiedTime();
  
  FileTime lastAccessTime();
  
  FileTime creationTime();
  
  boolean isRegularFile();
  
  boolean isDirectory();
  
  boolean isSymbolicLink();
  
  boolean isOther();
  
  long size();
  
  Object fileKey();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\file\attribute\BasicFileAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */