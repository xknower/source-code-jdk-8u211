package javax.swing;

public interface MutableComboBoxModel<E> extends ComboBoxModel<E> {
  void addElement(E paramE);
  
  void removeElement(Object paramObject);
  
  void insertElementAt(E paramE, int paramInt);
  
  void removeElementAt(int paramInt);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\MutableComboBoxModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */