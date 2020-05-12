package com.sun.jmx.remote.internal;

import java.util.List;
import javax.management.Notification;
import javax.management.ObjectName;
import javax.management.remote.TargetedNotification;

public interface NotificationBufferFilter {
  void apply(List<TargetedNotification> paramList, ObjectName paramObjectName, Notification paramNotification);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\remote\internal\NotificationBufferFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */