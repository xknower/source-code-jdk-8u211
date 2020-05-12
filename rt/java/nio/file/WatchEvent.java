package java.nio.file;

public interface WatchEvent<T> {
  Kind<T> kind();
  
  int count();
  
  T context();
  
  public static interface Kind<T> {
    String name();
    
    Class<T> type();
  }
  
  public static interface Modifier {
    String name();
  }
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\file\WatchEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */