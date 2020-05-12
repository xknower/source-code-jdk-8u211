package javax.swing.text;

import javax.swing.event.ChangeListener;

public interface Style extends MutableAttributeSet {
  String getName();
  
  void addChangeListener(ChangeListener paramChangeListener);
  
  void removeChangeListener(ChangeListener paramChangeListener);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\text\Style.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */