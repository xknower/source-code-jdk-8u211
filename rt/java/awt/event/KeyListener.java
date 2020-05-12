package java.awt.event;

import java.util.EventListener;

public interface KeyListener extends EventListener {
  void keyTyped(KeyEvent paramKeyEvent);
  
  void keyPressed(KeyEvent paramKeyEvent);
  
  void keyReleased(KeyEvent paramKeyEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\event\KeyListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */