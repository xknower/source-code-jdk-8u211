package javax.swing.event;

import java.util.EventListener;

public interface MenuKeyListener extends EventListener {
  void menuKeyTyped(MenuKeyEvent paramMenuKeyEvent);
  
  void menuKeyPressed(MenuKeyEvent paramMenuKeyEvent);
  
  void menuKeyReleased(MenuKeyEvent paramMenuKeyEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\event\MenuKeyListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */