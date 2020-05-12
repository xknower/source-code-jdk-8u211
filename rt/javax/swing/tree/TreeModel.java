package javax.swing.tree;

import javax.swing.event.TreeModelListener;

public interface TreeModel {
  Object getRoot();
  
  Object getChild(Object paramObject, int paramInt);
  
  int getChildCount(Object paramObject);
  
  boolean isLeaf(Object paramObject);
  
  void valueForPathChanged(TreePath paramTreePath, Object paramObject);
  
  int getIndexOfChild(Object paramObject1, Object paramObject2);
  
  void addTreeModelListener(TreeModelListener paramTreeModelListener);
  
  void removeTreeModelListener(TreeModelListener paramTreeModelListener);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\tree\TreeModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */