package sun.awt;

import java.awt.event.WindowEvent;

public interface WindowClosingListener {
  RuntimeException windowClosingNotify(WindowEvent paramWindowEvent);
  
  RuntimeException windowClosingDelivered(WindowEvent paramWindowEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\WindowClosingListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */