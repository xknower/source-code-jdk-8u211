package java.lang;

import java.io.IOException;

public interface Appendable {
  Appendable append(CharSequence paramCharSequence) throws IOException;
  
  Appendable append(CharSequence paramCharSequence, int paramInt1, int paramInt2) throws IOException;
  
  Appendable append(char paramChar) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\Appendable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */