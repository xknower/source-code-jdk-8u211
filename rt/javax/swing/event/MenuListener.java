package javax.swing.event;

import java.util.EventListener;

public interface MenuListener extends EventListener {
  void menuSelected(MenuEvent paramMenuEvent);
  
  void menuDeselected(MenuEvent paramMenuEvent);
  
  void menuCanceled(MenuEvent paramMenuEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\event\MenuListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */