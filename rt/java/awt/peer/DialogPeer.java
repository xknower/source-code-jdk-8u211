package java.awt.peer;

import java.awt.Window;
import java.util.List;

public interface DialogPeer extends WindowPeer {
  void setTitle(String paramString);
  
  void setResizable(boolean paramBoolean);
  
  void blockWindows(List<Window> paramList);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\peer\DialogPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */