package javax.naming.ldap;

import javax.naming.event.NamingListener;

public interface UnsolicitedNotificationListener extends NamingListener {
  void notificationReceived(UnsolicitedNotificationEvent paramUnsolicitedNotificationEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\naming\ldap\UnsolicitedNotificationListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */