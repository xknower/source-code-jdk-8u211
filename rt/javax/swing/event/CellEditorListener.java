package javax.swing.event;

import java.util.EventListener;

public interface CellEditorListener extends EventListener {
  void editingStopped(ChangeEvent paramChangeEvent);
  
  void editingCanceled(ChangeEvent paramChangeEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\event\CellEditorListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */