package sun.nio.ch;

import sun.misc.Cleaner;

public interface DirectBuffer {
  long address();
  
  Object attachment();
  
  Cleaner cleaner();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\DirectBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */