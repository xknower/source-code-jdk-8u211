package javax.naming.event;

import java.util.EventListener;

public interface NamingListener extends EventListener {
  void namingExceptionThrown(NamingExceptionEvent paramNamingExceptionEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\naming\event\NamingListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */