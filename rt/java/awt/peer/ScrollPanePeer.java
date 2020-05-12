package java.awt.peer;

import java.awt.Adjustable;

public interface ScrollPanePeer extends ContainerPeer {
  int getHScrollbarHeight();
  
  int getVScrollbarWidth();
  
  void setScrollPosition(int paramInt1, int paramInt2);
  
  void childResized(int paramInt1, int paramInt2);
  
  void setUnitIncrement(Adjustable paramAdjustable, int paramInt);
  
  void setValue(Adjustable paramAdjustable, int paramInt);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\peer\ScrollPanePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */