package javax.management;

public interface NotificationEmitter extends NotificationBroadcaster {
  void removeNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws ListenerNotFoundException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\NotificationEmitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */