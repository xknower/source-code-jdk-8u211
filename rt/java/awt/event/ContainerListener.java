package java.awt.event;

import java.util.EventListener;

public interface ContainerListener extends EventListener {
  void componentAdded(ContainerEvent paramContainerEvent);
  
  void componentRemoved(ContainerEvent paramContainerEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\event\ContainerListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */