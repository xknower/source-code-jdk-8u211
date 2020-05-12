package javax.swing.text;

import javax.swing.Action;
import javax.swing.KeyStroke;

public interface Keymap {
  String getName();
  
  Action getDefaultAction();
  
  void setDefaultAction(Action paramAction);
  
  Action getAction(KeyStroke paramKeyStroke);
  
  KeyStroke[] getBoundKeyStrokes();
  
  Action[] getBoundActions();
  
  KeyStroke[] getKeyStrokesForAction(Action paramAction);
  
  boolean isLocallyDefined(KeyStroke paramKeyStroke);
  
  void addActionForKeyStroke(KeyStroke paramKeyStroke, Action paramAction);
  
  void removeKeyStrokeBinding(KeyStroke paramKeyStroke);
  
  void removeBindings();
  
  Keymap getResolveParent();
  
  void setResolveParent(Keymap paramKeymap);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\text\Keymap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */