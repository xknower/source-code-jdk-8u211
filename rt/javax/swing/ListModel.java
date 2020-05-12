package javax.swing;

import javax.swing.event.ListDataListener;

public interface ListModel<E> {
  int getSize();
  
  E getElementAt(int paramInt);
  
  void addListDataListener(ListDataListener paramListDataListener);
  
  void removeListDataListener(ListDataListener paramListDataListener);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\ListModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */