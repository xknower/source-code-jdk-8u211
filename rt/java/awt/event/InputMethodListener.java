package java.awt.event;

import java.util.EventListener;

public interface InputMethodListener extends EventListener {
  void inputMethodTextChanged(InputMethodEvent paramInputMethodEvent);
  
  void caretPositionChanged(InputMethodEvent paramInputMethodEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\event\InputMethodListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */