package java.awt.im.spi;

import java.awt.Window;
import java.awt.font.TextHitInfo;
import java.awt.im.InputMethodRequests;
import java.text.AttributedCharacterIterator;
import javax.swing.JFrame;

public interface InputMethodContext extends InputMethodRequests {
  void dispatchInputMethodEvent(int paramInt1, AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt2, TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2);
  
  Window createInputMethodWindow(String paramString, boolean paramBoolean);
  
  JFrame createInputMethodJFrame(String paramString, boolean paramBoolean);
  
  void enableClientWindowNotification(InputMethod paramInputMethod, boolean paramBoolean);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\im\spi\InputMethodContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */