/*    */ package com.sun.jmx.remote.internal;
/*    */ 
/*    */ import javax.management.NotificationFilter;
/*    */ import javax.management.NotificationListener;
/*    */ import javax.management.ObjectName;
/*    */ import javax.security.auth.Subject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientListenerInfo
/*    */ {
/*    */   private final ObjectName name;
/*    */   private final Integer listenerID;
/*    */   private final NotificationFilter filter;
/*    */   private final NotificationListener listener;
/*    */   private final Object handback;
/*    */   private final Subject delegationSubject;
/*    */   
/*    */   public ClientListenerInfo(Integer paramInteger, ObjectName paramObjectName, NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject, Subject paramSubject) {
/* 48 */     this.listenerID = paramInteger;
/* 49 */     this.name = paramObjectName;
/* 50 */     this.listener = paramNotificationListener;
/* 51 */     this.filter = paramNotificationFilter;
/* 52 */     this.handback = paramObject;
/* 53 */     this.delegationSubject = paramSubject;
/*    */   }
/*    */   
/*    */   public ObjectName getObjectName() {
/* 57 */     return this.name;
/*    */   }
/*    */   
/*    */   public Integer getListenerID() {
/* 61 */     return this.listenerID;
/*    */   }
/*    */   
/*    */   public NotificationFilter getNotificationFilter() {
/* 65 */     return this.filter;
/*    */   }
/*    */   
/*    */   public NotificationListener getListener() {
/* 69 */     return this.listener;
/*    */   }
/*    */   
/*    */   public Object getHandback() {
/* 73 */     return this.handback;
/*    */   }
/*    */   
/*    */   public Subject getDelegationSubject() {
/* 77 */     return this.delegationSubject;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean sameAs(ObjectName paramObjectName) {
/* 82 */     return getObjectName().equals(paramObjectName);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean sameAs(ObjectName paramObjectName, NotificationListener paramNotificationListener) {
/* 87 */     return (getObjectName().equals(paramObjectName) && 
/* 88 */       getListener() == paramNotificationListener);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean sameAs(ObjectName paramObjectName, NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) {
/* 93 */     return (getObjectName().equals(paramObjectName) && 
/* 94 */       getListener() == paramNotificationListener && 
/* 95 */       getNotificationFilter() == paramNotificationFilter && 
/* 96 */       getHandback() == paramObject);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\remote\internal\ClientListenerInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */