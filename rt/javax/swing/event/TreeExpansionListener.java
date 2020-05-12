package javax.swing.event;

import java.util.EventListener;

public interface TreeExpansionListener extends EventListener {
  void treeExpanded(TreeExpansionEvent paramTreeExpansionEvent);
  
  void treeCollapsed(TreeExpansionEvent paramTreeExpansionEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\event\TreeExpansionListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */