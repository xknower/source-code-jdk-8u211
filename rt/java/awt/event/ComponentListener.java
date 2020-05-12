package java.awt.event;

import java.util.EventListener;

public interface ComponentListener extends EventListener {
  void componentResized(ComponentEvent paramComponentEvent);
  
  void componentMoved(ComponentEvent paramComponentEvent);
  
  void componentShown(ComponentEvent paramComponentEvent);
  
  void componentHidden(ComponentEvent paramComponentEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\event\ComponentListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */