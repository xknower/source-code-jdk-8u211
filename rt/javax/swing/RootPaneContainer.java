package javax.swing;

import java.awt.Component;
import java.awt.Container;

public interface RootPaneContainer {
  JRootPane getRootPane();
  
  void setContentPane(Container paramContainer);
  
  Container getContentPane();
  
  void setLayeredPane(JLayeredPane paramJLayeredPane);
  
  JLayeredPane getLayeredPane();
  
  void setGlassPane(Component paramComponent);
  
  Component getGlassPane();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\RootPaneContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */