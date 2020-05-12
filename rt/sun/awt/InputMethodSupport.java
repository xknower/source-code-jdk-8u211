package sun.awt;

import java.awt.AWTException;
import java.awt.Window;
import java.awt.im.spi.InputMethodDescriptor;
import java.util.Locale;
import sun.awt.im.InputContext;

public interface InputMethodSupport {
  InputMethodDescriptor getInputMethodAdapterDescriptor() throws AWTException;
  
  Window createInputMethodWindow(String paramString, InputContext paramInputContext);
  
  boolean enableInputMethodsForTextComponent();
  
  Locale getDefaultKeyboardLocale();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\InputMethodSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */