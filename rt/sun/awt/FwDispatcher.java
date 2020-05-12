package sun.awt;

import java.awt.SecondaryLoop;

public interface FwDispatcher {
  boolean isDispatchThread();
  
  void scheduleDispatch(Runnable paramRunnable);
  
  SecondaryLoop createSecondaryLoop();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\FwDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */