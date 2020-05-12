package javax.swing.plaf;

import javax.swing.JComboBox;

public abstract class ComboBoxUI extends ComponentUI {
  public abstract void setPopupVisible(JComboBox paramJComboBox, boolean paramBoolean);
  
  public abstract boolean isPopupVisible(JComboBox paramJComboBox);
  
  public abstract boolean isFocusTraversable(JComboBox paramJComboBox);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\ComboBoxUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */