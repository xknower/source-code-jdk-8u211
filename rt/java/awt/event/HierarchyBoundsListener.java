package java.awt.event;

import java.util.EventListener;

public interface HierarchyBoundsListener extends EventListener {
  void ancestorMoved(HierarchyEvent paramHierarchyEvent);
  
  void ancestorResized(HierarchyEvent paramHierarchyEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\event\HierarchyBoundsListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */