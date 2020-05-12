package javax.swing.event;

import java.util.EventListener;

public interface MenuDragMouseListener extends EventListener {
  void menuDragMouseEntered(MenuDragMouseEvent paramMenuDragMouseEvent);
  
  void menuDragMouseExited(MenuDragMouseEvent paramMenuDragMouseEvent);
  
  void menuDragMouseDragged(MenuDragMouseEvent paramMenuDragMouseEvent);
  
  void menuDragMouseReleased(MenuDragMouseEvent paramMenuDragMouseEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\event\MenuDragMouseListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */