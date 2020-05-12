package com.sun.jmx.remote.security;

import javax.management.Notification;
import javax.management.ObjectName;
import javax.security.auth.Subject;

public interface NotificationAccessController {
  void addNotificationListener(String paramString, ObjectName paramObjectName, Subject paramSubject) throws SecurityException;
  
  void removeNotificationListener(String paramString, ObjectName paramObjectName, Subject paramSubject) throws SecurityException;
  
  void fetchNotification(String paramString, ObjectName paramObjectName, Notification paramNotification, Subject paramSubject) throws SecurityException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\remote\security\NotificationAccessController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */