package javax.swing;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface MenuElement {
  void processMouseEvent(MouseEvent paramMouseEvent, MenuElement[] paramArrayOfMenuElement, MenuSelectionManager paramMenuSelectionManager);
  
  void processKeyEvent(KeyEvent paramKeyEvent, MenuElement[] paramArrayOfMenuElement, MenuSelectionManager paramMenuSelectionManager);
  
  void menuSelectionChanged(boolean paramBoolean);
  
  MenuElement[] getSubElements();
  
  Component getComponent();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\MenuElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */