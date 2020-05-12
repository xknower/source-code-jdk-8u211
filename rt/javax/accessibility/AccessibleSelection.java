package javax.accessibility;

public interface AccessibleSelection {
  int getAccessibleSelectionCount();
  
  Accessible getAccessibleSelection(int paramInt);
  
  boolean isAccessibleChildSelected(int paramInt);
  
  void addAccessibleSelection(int paramInt);
  
  void removeAccessibleSelection(int paramInt);
  
  void clearAccessibleSelection();
  
  void selectAllAccessibleSelection();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\accessibility\AccessibleSelection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */