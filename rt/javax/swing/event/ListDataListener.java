package javax.swing.event;

import java.util.EventListener;

public interface ListDataListener extends EventListener {
  void intervalAdded(ListDataEvent paramListDataEvent);
  
  void intervalRemoved(ListDataEvent paramListDataEvent);
  
  void contentsChanged(ListDataEvent paramListDataEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\event\ListDataListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */