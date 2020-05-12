package java.nio.file.attribute;

public interface DosFileAttributes extends BasicFileAttributes {
  boolean isReadOnly();
  
  boolean isHidden();
  
  boolean isArchive();
  
  boolean isSystem();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\file\attribute\DosFileAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */