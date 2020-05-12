package javax.accessibility;

public interface AccessibleTable {
  Accessible getAccessibleCaption();
  
  void setAccessibleCaption(Accessible paramAccessible);
  
  Accessible getAccessibleSummary();
  
  void setAccessibleSummary(Accessible paramAccessible);
  
  int getAccessibleRowCount();
  
  int getAccessibleColumnCount();
  
  Accessible getAccessibleAt(int paramInt1, int paramInt2);
  
  int getAccessibleRowExtentAt(int paramInt1, int paramInt2);
  
  int getAccessibleColumnExtentAt(int paramInt1, int paramInt2);
  
  AccessibleTable getAccessibleRowHeader();
  
  void setAccessibleRowHeader(AccessibleTable paramAccessibleTable);
  
  AccessibleTable getAccessibleColumnHeader();
  
  void setAccessibleColumnHeader(AccessibleTable paramAccessibleTable);
  
  Accessible getAccessibleRowDescription(int paramInt);
  
  void setAccessibleRowDescription(int paramInt, Accessible paramAccessible);
  
  Accessible getAccessibleColumnDescription(int paramInt);
  
  void setAccessibleColumnDescription(int paramInt, Accessible paramAccessible);
  
  boolean isAccessibleSelected(int paramInt1, int paramInt2);
  
  boolean isAccessibleRowSelected(int paramInt);
  
  boolean isAccessibleColumnSelected(int paramInt);
  
  int[] getSelectedAccessibleRows();
  
  int[] getSelectedAccessibleColumns();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\accessibility\AccessibleTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */