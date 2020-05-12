package javax.swing.colorchooser;

import java.awt.Color;
import javax.swing.event.ChangeListener;

public interface ColorSelectionModel {
  Color getSelectedColor();
  
  void setSelectedColor(Color paramColor);
  
  void addChangeListener(ChangeListener paramChangeListener);
  
  void removeChangeListener(ChangeListener paramChangeListener);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\colorchooser\ColorSelectionModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */