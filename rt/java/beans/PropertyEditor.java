package java.beans;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

public interface PropertyEditor {
  void setValue(Object paramObject);
  
  Object getValue();
  
  boolean isPaintable();
  
  void paintValue(Graphics paramGraphics, Rectangle paramRectangle);
  
  String getJavaInitializationString();
  
  String getAsText();
  
  void setAsText(String paramString) throws IllegalArgumentException;
  
  String[] getTags();
  
  Component getCustomEditor();
  
  boolean supportsCustomEditor();
  
  void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
  
  void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\beans\PropertyEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */