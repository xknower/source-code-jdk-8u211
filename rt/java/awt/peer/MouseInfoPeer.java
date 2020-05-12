package java.awt.peer;

import java.awt.Point;
import java.awt.Window;

public interface MouseInfoPeer {
  int fillPointWithCoords(Point paramPoint);
  
  boolean isWindowUnderMouse(Window paramWindow);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\peer\MouseInfoPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */