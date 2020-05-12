package javax.management;

import java.io.Serializable;

public interface NotificationFilter extends Serializable {
  boolean isNotificationEnabled(Notification paramNotification);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\NotificationFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */