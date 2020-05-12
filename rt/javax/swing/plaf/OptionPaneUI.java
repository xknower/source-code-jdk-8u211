package javax.swing.plaf;

import javax.swing.JOptionPane;

public abstract class OptionPaneUI extends ComponentUI {
  public abstract void selectInitialValue(JOptionPane paramJOptionPane);
  
  public abstract boolean containsCustomComponents(JOptionPane paramJOptionPane);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\OptionPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */