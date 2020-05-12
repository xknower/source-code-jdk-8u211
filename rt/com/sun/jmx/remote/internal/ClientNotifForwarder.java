/*     */ package com.sun.jmx.remote.internal;
/*     */ 
/*     */ import com.sun.jmx.remote.util.ClassLogger;
/*     */ import com.sun.jmx.remote.util.EnvHelp;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.ListenerNotFoundException;
/*     */ import javax.management.MBeanServerNotification;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationFilter;
/*     */ import javax.management.NotificationListener;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.remote.NotificationResult;
/*     */ import javax.management.remote.TargetedNotification;
/*     */ import javax.security.auth.Subject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ClientNotifForwarder
/*     */ {
/*     */   private final AccessControlContext acc;
/*     */   private static int threadId;
/*     */   private final ClassLoader defaultClassLoader;
/*     */   private final Executor executor;
/*     */   private final Map<Integer, ClientListenerInfo> infoList;
/*     */   private long clientSequenceNumber;
/*     */   private final int maxNotifications;
/*     */   private final long timeout;
/*     */   private Integer mbeanRemovedNotifID;
/*     */   private Thread currentFetchThread;
/*     */   private static final int STARTING = 0;
/*     */   private static final int STARTED = 1;
/*     */   private static final int STOPPING = 2;
/*     */   private static final int STOPPED = 3;
/*     */   private static final int TERMINATED = 4;
/*     */   private int state;
/*     */   private boolean beingReconnected;
/*     */   
/*     */   public ClientNotifForwarder(Map<String, ?> paramMap) {
/*  62 */     this(null, paramMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LinearExecutor
/*     */     implements Executor
/*     */   {
/*     */     private Runnable command;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Thread thread;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private LinearExecutor() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void execute(Runnable param1Runnable) {
/*  89 */       if (this.command != null)
/*  90 */         throw new IllegalArgumentException("More than one command"); 
/*  91 */       this.command = param1Runnable;
/*  92 */       if (this.thread == null) {
/*  93 */         this.thread = new Thread()
/*     */           {
/*     */             public void run()
/*     */             {
/*     */               while (true) {
/*     */                 Runnable runnable;
/*  99 */                 synchronized (ClientNotifForwarder.LinearExecutor.this) {
/* 100 */                   if (ClientNotifForwarder.LinearExecutor.this.command == null) {
/* 101 */                     ClientNotifForwarder.LinearExecutor.this.thread = null;
/*     */                     return;
/*     */                   } 
/* 104 */                   runnable = ClientNotifForwarder.LinearExecutor.this.command;
/* 105 */                   ClientNotifForwarder.LinearExecutor.this.command = null;
/*     */                 } 
/*     */                 
/* 108 */                 runnable.run();
/*     */               } 
/*     */             }
/*     */           };
/* 112 */         this.thread.setDaemon(true);
/* 113 */         this.thread.setName("ClientNotifForwarder-" + ClientNotifForwarder.access$204());
/* 114 */         this.thread.start();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientNotifForwarder(ClassLoader paramClassLoader, Map<String, ?> paramMap) {
/* 870 */     this.infoList = new HashMap<>();
/*     */ 
/*     */ 
/*     */     
/* 874 */     this.clientSequenceNumber = -1L;
/*     */ 
/*     */     
/* 877 */     this.mbeanRemovedNotifID = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 907 */     this.state = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 916 */     this.beingReconnected = false; this.maxNotifications = EnvHelp.getMaxFetchNotifNumber(paramMap); this.timeout = EnvHelp.getFetchTimeout(paramMap); Executor executor = (Executor)paramMap.get("jmx.remote.x.fetch.notifications.executor"); if (executor == null) { executor = new LinearExecutor(); }
/*     */     else if (logger.traceOn()) { logger.trace("ClientNotifForwarder", "executor is " + executor); }
/* 918 */      this.defaultClassLoader = paramClassLoader; this.executor = executor; this.acc = AccessController.getContext(); } private static final ClassLogger logger = new ClassLogger("javax.management.remote.misc", "ClientNotifForwarder");
/*     */   
/*     */   public synchronized void addNotificationListener(Integer paramInteger, ObjectName paramObjectName, NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject, Subject paramSubject) throws IOException, InstanceNotFoundException {
/*     */     if (logger.traceOn())
/*     */       logger.trace("addNotificationListener", "Add the listener " + paramNotificationListener + " at " + paramObjectName); 
/*     */     this.infoList.put(paramInteger, new ClientListenerInfo(paramInteger, paramObjectName, paramNotificationListener, paramNotificationFilter, paramObject, paramSubject));
/*     */     init(false);
/*     */   }
/*     */   
/*     */   public synchronized Integer[] removeNotificationListener(ObjectName paramObjectName, NotificationListener paramNotificationListener) throws ListenerNotFoundException, IOException {
/*     */     beforeRemove();
/*     */     if (logger.traceOn())
/*     */       logger.trace("removeNotificationListener", "Remove the listener " + paramNotificationListener + " from " + paramObjectName); 
/*     */     ArrayList<Integer> arrayList = new ArrayList();
/*     */     ArrayList<ClientListenerInfo> arrayList1 = new ArrayList(this.infoList.values());
/*     */     for (int i = arrayList1.size() - 1; i >= 0; i--) {
/*     */       ClientListenerInfo clientListenerInfo = arrayList1.get(i);
/*     */       if (clientListenerInfo.sameAs(paramObjectName, paramNotificationListener)) {
/*     */         arrayList.add(clientListenerInfo.getListenerID());
/*     */         this.infoList.remove(clientListenerInfo.getListenerID());
/*     */       } 
/*     */     } 
/*     */     if (arrayList.isEmpty())
/*     */       throw new ListenerNotFoundException("Listener not found"); 
/*     */     return arrayList.<Integer>toArray(new Integer[0]);
/*     */   }
/*     */   
/*     */   public synchronized Integer removeNotificationListener(ObjectName paramObjectName, NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws ListenerNotFoundException, IOException {
/*     */     if (logger.traceOn())
/*     */       logger.trace("removeNotificationListener", "Remove the listener " + paramNotificationListener + " from " + paramObjectName); 
/*     */     beforeRemove();
/*     */     Integer integer = null;
/*     */     ArrayList<ClientListenerInfo> arrayList = new ArrayList(this.infoList.values());
/*     */     for (int i = arrayList.size() - 1; i >= 0; i--) {
/*     */       ClientListenerInfo clientListenerInfo = arrayList.get(i);
/*     */       if (clientListenerInfo.sameAs(paramObjectName, paramNotificationListener, paramNotificationFilter, paramObject)) {
/*     */         integer = clientListenerInfo.getListenerID();
/*     */         this.infoList.remove(integer);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     if (integer == null)
/*     */       throw new ListenerNotFoundException("Listener not found"); 
/*     */     return integer;
/*     */   }
/*     */   
/*     */   public synchronized Integer[] removeNotificationListener(ObjectName paramObjectName) {
/*     */     if (logger.traceOn())
/*     */       logger.trace("removeNotificationListener", "Remove all listeners registered at " + paramObjectName); 
/*     */     ArrayList<Integer> arrayList = new ArrayList();
/*     */     ArrayList<ClientListenerInfo> arrayList1 = new ArrayList(this.infoList.values());
/*     */     for (int i = arrayList1.size() - 1; i >= 0; i--) {
/*     */       ClientListenerInfo clientListenerInfo = arrayList1.get(i);
/*     */       if (clientListenerInfo.sameAs(paramObjectName)) {
/*     */         arrayList.add(clientListenerInfo.getListenerID());
/*     */         this.infoList.remove(clientListenerInfo.getListenerID());
/*     */       } 
/*     */     } 
/*     */     return arrayList.<Integer>toArray(new Integer[0]);
/*     */   }
/*     */   
/*     */   public synchronized ClientListenerInfo[] preReconnection() throws IOException {
/*     */     if (this.state == 4 || this.beingReconnected)
/*     */       throw new IOException("Illegal state."); 
/*     */     ClientListenerInfo[] arrayOfClientListenerInfo = (ClientListenerInfo[])this.infoList.values().toArray((Object[])new ClientListenerInfo[0]);
/*     */     this.beingReconnected = true;
/*     */     this.infoList.clear();
/*     */     return arrayOfClientListenerInfo;
/*     */   }
/*     */   
/*     */   public synchronized void postReconnection(ClientListenerInfo[] paramArrayOfClientListenerInfo) throws IOException {
/*     */     if (this.state == 4)
/*     */       return; 
/*     */     while (this.state == 2) {
/*     */       try {
/*     */         wait();
/*     */       } catch (InterruptedException interruptedException) {
/*     */         IOException iOException = new IOException(interruptedException.toString());
/*     */         EnvHelp.initCause(iOException, interruptedException);
/*     */         throw iOException;
/*     */       } 
/*     */     } 
/*     */     boolean bool = logger.traceOn();
/*     */     int i = paramArrayOfClientListenerInfo.length;
/*     */     for (byte b = 0; b < i; b++) {
/*     */       if (bool)
/*     */         logger.trace("addNotificationListeners", "Add a listener at " + paramArrayOfClientListenerInfo[b].getListenerID()); 
/*     */       this.infoList.put(paramArrayOfClientListenerInfo[b].getListenerID(), paramArrayOfClientListenerInfo[b]);
/*     */     } 
/*     */     this.beingReconnected = false;
/*     */     notifyAll();
/*     */     if (this.currentFetchThread == Thread.currentThread() || this.state == 0 || this.state == 1) {
/*     */       try {
/*     */         this.mbeanRemovedNotifID = addListenerForMBeanRemovedNotif();
/*     */       } catch (Exception exception) {
/*     */         if (logger.traceOn())
/*     */           logger.trace("init", "Failed to register a listener to the mbean server: the client will not do clean when an MBean is unregistered", exception); 
/*     */       } 
/*     */     } else {
/*     */       while (this.state == 2) {
/*     */         try {
/*     */           wait();
/*     */         } catch (InterruptedException interruptedException) {
/*     */           IOException iOException = new IOException(interruptedException.toString());
/*     */           EnvHelp.initCause(iOException, interruptedException);
/*     */           throw iOException;
/*     */         } 
/*     */       } 
/*     */       if (paramArrayOfClientListenerInfo.length > 0) {
/*     */         init(true);
/*     */       } else if (this.infoList.size() > 0) {
/*     */         init(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void terminate() {
/*     */     if (this.state == 4)
/*     */       return; 
/*     */     if (logger.traceOn())
/*     */       logger.trace("terminate", "Terminating..."); 
/*     */     if (this.state == 1)
/*     */       this.infoList.clear(); 
/*     */     setState(4);
/*     */   }
/*     */   
/*     */   private class NotifFetcher implements Runnable {
/*     */     private volatile boolean alreadyLogged = false;
/*     */     
/*     */     private void logOnce(String param1String, SecurityException param1SecurityException) {
/*     */       if (this.alreadyLogged)
/*     */         return; 
/*     */       ClientNotifForwarder.logger.config("setContextClassLoader", param1String);
/*     */       if (param1SecurityException != null)
/*     */         ClientNotifForwarder.logger.fine("setContextClassLoader", param1SecurityException); 
/*     */       this.alreadyLogged = true;
/*     */     }
/*     */     
/*     */     private final ClassLoader setContextClassLoader(final ClassLoader loader) {
/*     */       AccessControlContext accessControlContext = ClientNotifForwarder.this.acc;
/*     */       if (accessControlContext == null) {
/*     */         logOnce("AccessControlContext must not be null.", null);
/*     */         throw new SecurityException("AccessControlContext must not be null");
/*     */       } 
/*     */       return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */             public ClassLoader run() {
/*     */               try {
/*     */                 ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*     */                 if (loader == classLoader)
/*     */                   return classLoader; 
/*     */                 Thread.currentThread().setContextClassLoader(loader);
/*     */                 return classLoader;
/*     */               } catch (SecurityException securityException) {
/*     */                 ClientNotifForwarder.NotifFetcher.this.logOnce("Permission to set ContextClassLoader missing. Notifications will not be dispatched. Please check your Java policy configuration: " + securityException, securityException);
/*     */                 throw securityException;
/*     */               } 
/*     */             }
/*     */           }accessControlContext);
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       ClassLoader classLoader;
/*     */       if (ClientNotifForwarder.this.defaultClassLoader != null) {
/*     */         classLoader = setContextClassLoader(ClientNotifForwarder.this.defaultClassLoader);
/*     */       } else {
/*     */         classLoader = null;
/*     */       } 
/*     */       try {
/*     */         doRun();
/*     */       } finally {
/*     */         if (ClientNotifForwarder.this.defaultClassLoader != null)
/*     */           setContextClassLoader(classLoader); 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void doRun() {
/*     */       synchronized (ClientNotifForwarder.this) {
/*     */         ClientNotifForwarder.this.currentFetchThread = Thread.currentThread();
/*     */         if (ClientNotifForwarder.this.state == 0)
/*     */           ClientNotifForwarder.this.setState(1); 
/*     */       } 
/*     */       NotificationResult notificationResult = null;
/*     */       if (!shouldStop() && (notificationResult = fetchNotifs()) != null) {
/*     */         HashMap<Object, Object> hashMap;
/*     */         Integer integer;
/*     */         TargetedNotification[] arrayOfTargetedNotification = notificationResult.getTargetedNotifications();
/*     */         int i = arrayOfTargetedNotification.length;
/*     */         long l = 0L;
/*     */         synchronized (ClientNotifForwarder.this) {
/*     */           if (ClientNotifForwarder.this.clientSequenceNumber >= 0L)
/*     */             l = notificationResult.getEarliestSequenceNumber() - ClientNotifForwarder.this.clientSequenceNumber; 
/*     */           ClientNotifForwarder.this.clientSequenceNumber = notificationResult.getNextSequenceNumber();
/*     */           hashMap = new HashMap<>();
/*     */           for (byte b1 = 0; b1 < i; b1++) {
/*     */             TargetedNotification targetedNotification = arrayOfTargetedNotification[b1];
/*     */             Integer integer1 = targetedNotification.getListenerID();
/*     */             if (!integer1.equals(ClientNotifForwarder.this.mbeanRemovedNotifID)) {
/*     */               ClientListenerInfo clientListenerInfo = (ClientListenerInfo)ClientNotifForwarder.this.infoList.get(integer1);
/*     */               if (clientListenerInfo != null)
/*     */                 hashMap.put(integer1, clientListenerInfo); 
/*     */             } else {
/*     */               Notification notification = targetedNotification.getNotification();
/*     */               if (notification instanceof MBeanServerNotification && notification.getType().equals("JMX.mbean.unregistered")) {
/*     */                 MBeanServerNotification mBeanServerNotification = (MBeanServerNotification)notification;
/*     */                 ObjectName objectName = mBeanServerNotification.getMBeanName();
/*     */                 ClientNotifForwarder.this.removeNotificationListener(objectName);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           integer = ClientNotifForwarder.this.mbeanRemovedNotifID;
/*     */         } 
/*     */         if (l > 0L) {
/*     */           String str = "May have lost up to " + l + " notification" + ((l == 1L) ? "" : "s");
/*     */           ClientNotifForwarder.this.lostNotifs(str, l);
/*     */           ClientNotifForwarder.logger.trace("NotifFetcher.run", str);
/*     */         } 
/*     */         for (byte b = 0; b < i; b++) {
/*     */           TargetedNotification targetedNotification = arrayOfTargetedNotification[b];
/*     */           dispatchNotification(targetedNotification, integer, (Map)hashMap);
/*     */         } 
/*     */       } 
/*     */       synchronized (ClientNotifForwarder.this) {
/*     */         ClientNotifForwarder.this.currentFetchThread = null;
/*     */       } 
/*     */       if (notificationResult == null && ClientNotifForwarder.logger.traceOn())
/*     */         ClientNotifForwarder.logger.trace("NotifFetcher-run", "Recieved null object as notifs, stops fetching because the notification server is terminated."); 
/*     */       if (notificationResult == null || shouldStop()) {
/*     */         ClientNotifForwarder.this.setState(3);
/*     */         try {
/*     */           ClientNotifForwarder.this.removeListenerForMBeanRemovedNotif(ClientNotifForwarder.this.mbeanRemovedNotifID);
/*     */         } catch (Exception exception) {
/*     */           if (ClientNotifForwarder.logger.traceOn())
/*     */             ClientNotifForwarder.logger.trace("NotifFetcher-run", "removeListenerForMBeanRemovedNotif", exception); 
/*     */         } 
/*     */       } else {
/*     */         ClientNotifForwarder.this.executor.execute(this);
/*     */       } 
/*     */     }
/*     */     
/*     */     void dispatchNotification(TargetedNotification param1TargetedNotification, Integer param1Integer, Map<Integer, ClientListenerInfo> param1Map) {
/*     */       Notification notification = param1TargetedNotification.getNotification();
/*     */       Integer integer = param1TargetedNotification.getListenerID();
/*     */       if (integer.equals(param1Integer))
/*     */         return; 
/*     */       ClientListenerInfo clientListenerInfo = param1Map.get(integer);
/*     */       if (clientListenerInfo == null) {
/*     */         ClientNotifForwarder.logger.trace("NotifFetcher.dispatch", "Listener ID not in map");
/*     */         return;
/*     */       } 
/*     */       NotificationListener notificationListener = clientListenerInfo.getListener();
/*     */       Object object = clientListenerInfo.getHandback();
/*     */       try {
/*     */         notificationListener.handleNotification(notification, object);
/*     */       } catch (RuntimeException runtimeException) {
/*     */         ClientNotifForwarder.logger.trace("NotifFetcher-run", "Failed to forward a notification to a listener", runtimeException);
/*     */       } 
/*     */     }
/*     */     
/*     */     private NotificationResult fetchNotifs() {
/*     */       try {
/*     */         NotificationResult notificationResult = ClientNotifForwarder.this.fetchNotifs(ClientNotifForwarder.this.clientSequenceNumber, ClientNotifForwarder.this.maxNotifications, ClientNotifForwarder.this.timeout);
/*     */         if (ClientNotifForwarder.logger.traceOn())
/*     */           ClientNotifForwarder.logger.trace("NotifFetcher-run", "Got notifications from the server: " + notificationResult); 
/*     */         return notificationResult;
/*     */       } catch (ClassNotFoundException|java.io.NotSerializableException|java.rmi.UnmarshalException classNotFoundException) {
/*     */         ClientNotifForwarder.logger.trace("NotifFetcher.fetchNotifs", classNotFoundException);
/*     */         return fetchOneNotif();
/*     */       } catch (IOException iOException) {
/*     */         if (!shouldStop()) {
/*     */           ClientNotifForwarder.logger.error("NotifFetcher-run", "Failed to fetch notification, stopping thread. Error is: " + iOException, iOException);
/*     */           ClientNotifForwarder.logger.debug("NotifFetcher-run", iOException);
/*     */         } 
/*     */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     private NotificationResult fetchOneNotif() {
/*     */       ClientNotifForwarder clientNotifForwarder = ClientNotifForwarder.this;
/*     */       long l1 = ClientNotifForwarder.this.clientSequenceNumber;
/*     */       byte b = 0;
/*     */       NotificationResult notificationResult = null;
/*     */       long l2 = -1L;
/*     */       while (notificationResult == null && !shouldStop()) {
/*     */         NotificationResult notificationResult1;
/*     */         try {
/*     */           notificationResult1 = clientNotifForwarder.fetchNotifs(l1, 0, 0L);
/*     */         } catch (ClassNotFoundException classNotFoundException) {
/*     */           ClientNotifForwarder.logger.warning("NotifFetcher.fetchOneNotif", "Impossible exception: " + classNotFoundException);
/*     */           ClientNotifForwarder.logger.debug("NotifFetcher.fetchOneNotif", classNotFoundException);
/*     */           return null;
/*     */         } catch (IOException iOException) {
/*     */           if (!shouldStop())
/*     */             ClientNotifForwarder.logger.trace("NotifFetcher.fetchOneNotif", iOException); 
/*     */           return null;
/*     */         } 
/*     */         if (shouldStop() || notificationResult1 == null)
/*     */           return null; 
/*     */         l1 = notificationResult1.getNextSequenceNumber();
/*     */         if (l2 < 0L)
/*     */           l2 = notificationResult1.getEarliestSequenceNumber(); 
/*     */         try {
/*     */           notificationResult = clientNotifForwarder.fetchNotifs(l1, 1, 0L);
/*     */         } catch (ClassNotFoundException|java.io.NotSerializableException|java.rmi.UnmarshalException classNotFoundException) {
/*     */           ClientNotifForwarder.logger.warning("NotifFetcher.fetchOneNotif", "Failed to deserialize a notification: " + classNotFoundException.toString());
/*     */           if (ClientNotifForwarder.logger.traceOn())
/*     */             ClientNotifForwarder.logger.trace("NotifFetcher.fetchOneNotif", "Failed to deserialize a notification.", classNotFoundException); 
/*     */           b++;
/*     */           l1++;
/*     */         } catch (Exception exception) {
/*     */           if (!shouldStop())
/*     */             ClientNotifForwarder.logger.trace("NotifFetcher.fetchOneNotif", exception); 
/*     */           return null;
/*     */         } 
/*     */       } 
/*     */       if (b > 0) {
/*     */         String str = "Dropped " + b + " notification" + ((b == 1) ? "" : "s") + " because classes were missing locally or incompatible";
/*     */         ClientNotifForwarder.this.lostNotifs(str, b);
/*     */         if (notificationResult != null)
/*     */           notificationResult = new NotificationResult(l2, notificationResult.getNextSequenceNumber(), notificationResult.getTargetedNotifications()); 
/*     */       } 
/*     */       return notificationResult;
/*     */     }
/*     */     
/*     */     private boolean shouldStop() {
/*     */       synchronized (ClientNotifForwarder.this) {
/*     */         if (ClientNotifForwarder.this.state != 1)
/*     */           return true; 
/*     */         if (ClientNotifForwarder.this.infoList.size() == 0) {
/*     */           ClientNotifForwarder.this.setState(2);
/*     */           return true;
/*     */         } 
/*     */         return false;
/*     */       } 
/*     */     }
/*     */     
/*     */     private NotifFetcher() {}
/*     */   }
/*     */   
/*     */   private synchronized void setState(int paramInt) {
/*     */     if (this.state == 4)
/*     */       return; 
/*     */     this.state = paramInt;
/*     */     notifyAll();
/*     */   }
/*     */   
/*     */   private synchronized void init(boolean paramBoolean) throws IOException {
/*     */     switch (this.state) {
/*     */       case 1:
/*     */         return;
/*     */       case 0:
/*     */         return;
/*     */       case 4:
/*     */         throw new IOException("The ClientNotifForwarder has been terminated.");
/*     */       case 2:
/*     */         if (this.beingReconnected == true)
/*     */           return; 
/*     */         while (this.state == 2) {
/*     */           try {
/*     */             wait();
/*     */           } catch (InterruptedException interruptedException) {
/*     */             IOException iOException = new IOException(interruptedException.toString());
/*     */             EnvHelp.initCause(iOException, interruptedException);
/*     */             throw iOException;
/*     */           } 
/*     */         } 
/*     */         init(paramBoolean);
/*     */         return;
/*     */       case 3:
/*     */         if (this.beingReconnected == true)
/*     */           return; 
/*     */         if (logger.traceOn())
/*     */           logger.trace("init", "Initializing..."); 
/*     */         if (!paramBoolean)
/*     */           try {
/*     */             NotificationResult notificationResult = fetchNotifs(-1L, 0, 0L);
/*     */             if (this.state != 3)
/*     */               return; 
/*     */             this.clientSequenceNumber = notificationResult.getNextSequenceNumber();
/*     */           } catch (ClassNotFoundException classNotFoundException) {
/*     */             logger.warning("init", "Impossible exception: " + classNotFoundException);
/*     */             logger.debug("init", classNotFoundException);
/*     */           }  
/*     */         try {
/*     */           this.mbeanRemovedNotifID = addListenerForMBeanRemovedNotif();
/*     */         } catch (Exception exception) {
/*     */           if (logger.traceOn())
/*     */             logger.trace("init", "Failed to register a listener to the mbean server: the client will not do clean when an MBean is unregistered", exception); 
/*     */         } 
/*     */         setState(0);
/*     */         this.executor.execute(new NotifFetcher());
/*     */         return;
/*     */     } 
/*     */     throw new IOException("Unknown state.");
/*     */   }
/*     */   
/*     */   private synchronized void beforeRemove() throws IOException {
/*     */     while (this.beingReconnected) {
/*     */       if (this.state == 4)
/*     */         throw new IOException("Terminated."); 
/*     */       try {
/*     */         wait();
/*     */       } catch (InterruptedException interruptedException) {
/*     */         IOException iOException = new IOException(interruptedException.toString());
/*     */         EnvHelp.initCause(iOException, interruptedException);
/*     */         throw iOException;
/*     */       } 
/*     */     } 
/*     */     if (this.state == 4)
/*     */       throw new IOException("Terminated."); 
/*     */   }
/*     */   
/*     */   protected abstract NotificationResult fetchNotifs(long paramLong1, int paramInt, long paramLong2) throws IOException, ClassNotFoundException;
/*     */   
/*     */   protected abstract Integer addListenerForMBeanRemovedNotif() throws IOException, InstanceNotFoundException;
/*     */   
/*     */   protected abstract void removeListenerForMBeanRemovedNotif(Integer paramInteger) throws IOException, InstanceNotFoundException, ListenerNotFoundException;
/*     */   
/*     */   protected abstract void lostNotifs(String paramString, long paramLong);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\remote\internal\ClientNotifForwarder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */