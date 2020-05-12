package sun.awt.datatransfer;

public interface ToolkitThreadBlockedHandler {
  void lock();
  
  void unlock();
  
  void enter();
  
  void exit();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\datatransfer\ToolkitThreadBlockedHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */