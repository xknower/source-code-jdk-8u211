package javax.swing.event;

import java.util.EventListener;

public interface TableColumnModelListener extends EventListener {
  void columnAdded(TableColumnModelEvent paramTableColumnModelEvent);
  
  void columnRemoved(TableColumnModelEvent paramTableColumnModelEvent);
  
  void columnMoved(TableColumnModelEvent paramTableColumnModelEvent);
  
  void columnMarginChanged(ChangeEvent paramChangeEvent);
  
  void columnSelectionChanged(ListSelectionEvent paramListSelectionEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\event\TableColumnModelListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */