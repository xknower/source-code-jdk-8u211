package javax.management;

import java.util.EventListener;

public interface NotificationListener extends EventListener {
  void handleNotification(Notification paramNotification, Object paramObject);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\NotificationListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */