package java.net;

public interface SocketOption<T> {
  String name();
  
  Class<T> type();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\SocketOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */