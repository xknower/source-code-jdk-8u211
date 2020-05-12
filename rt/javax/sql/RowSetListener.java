package javax.sql;

import java.util.EventListener;

public interface RowSetListener extends EventListener {
  void rowSetChanged(RowSetEvent paramRowSetEvent);
  
  void rowChanged(RowSetEvent paramRowSetEvent);
  
  void cursorMoved(RowSetEvent paramRowSetEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sql\RowSetListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */