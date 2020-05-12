package java.awt.im.spi;

import java.awt.AWTEvent;
import java.awt.Rectangle;
import java.util.Locale;

public interface InputMethod {
  void setInputMethodContext(InputMethodContext paramInputMethodContext);
  
  boolean setLocale(Locale paramLocale);
  
  Locale getLocale();
  
  void setCharacterSubsets(Character.Subset[] paramArrayOfSubset);
  
  void setCompositionEnabled(boolean paramBoolean);
  
  boolean isCompositionEnabled();
  
  void reconvert();
  
  void dispatchEvent(AWTEvent paramAWTEvent);
  
  void notifyClientWindowChange(Rectangle paramRectangle);
  
  void activate();
  
  void deactivate(boolean paramBoolean);
  
  void hideWindows();
  
  void removeNotify();
  
  void endComposition();
  
  void dispose();
  
  Object getControlObject();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\im\spi\InputMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */