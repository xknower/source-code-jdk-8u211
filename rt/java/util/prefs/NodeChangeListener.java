package java.util.prefs;

import java.util.EventListener;

public interface NodeChangeListener extends EventListener {
  void childAdded(NodeChangeEvent paramNodeChangeEvent);
  
  void childRemoved(NodeChangeEvent paramNodeChangeEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\prefs\NodeChangeListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */