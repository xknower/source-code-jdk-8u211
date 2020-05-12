package java.awt;

import java.awt.event.ItemListener;

public interface ItemSelectable {
  Object[] getSelectedObjects();
  
  void addItemListener(ItemListener paramItemListener);
  
  void removeItemListener(ItemListener paramItemListener);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\ItemSelectable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */