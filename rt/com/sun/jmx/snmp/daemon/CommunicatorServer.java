/*      */ package com.sun.jmx.snmp.daemon;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.net.InetAddress;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Vector;
/*      */ import java.util.logging.Level;
/*      */ import javax.management.AttributeChangeNotification;
/*      */ import javax.management.ListenerNotFoundException;
/*      */ import javax.management.MBeanNotificationInfo;
/*      */ import javax.management.MBeanRegistration;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.NotificationBroadcaster;
/*      */ import javax.management.NotificationBroadcasterSupport;
/*      */ import javax.management.NotificationFilter;
/*      */ import javax.management.NotificationListener;
/*      */ import javax.management.ObjectName;
/*      */ import javax.management.remote.MBeanServerForwarder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class CommunicatorServer
/*      */   implements Runnable, MBeanRegistration, NotificationBroadcaster, CommunicatorServerMBean
/*      */ {
/*      */   public static final int ONLINE = 0;
/*      */   public static final int OFFLINE = 1;
/*      */   public static final int STOPPING = 2;
/*      */   public static final int STARTING = 3;
/*      */   public static final int SNMP_TYPE = 4;
/*  176 */   volatile transient int state = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   ObjectName objectName;
/*      */ 
/*      */   
/*      */   MBeanServer topMBS;
/*      */ 
/*      */   
/*      */   MBeanServer bottomMBS;
/*      */ 
/*      */   
/*  189 */   transient String dbgTag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  196 */   int maxActiveClientCount = 1;
/*      */ 
/*      */ 
/*      */   
/*  200 */   transient int servedClientCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  206 */   String host = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  212 */   int port = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  222 */   private transient Object stateLock = new Object();
/*      */   
/*  224 */   private transient Vector<ClientHandler> clientHandlerVector = new Vector<>();
/*      */ 
/*      */   
/*  227 */   private transient Thread mainThread = null;
/*      */   
/*      */   private volatile boolean stopRequested = false;
/*      */   private boolean interrupted = false;
/*  231 */   private transient Exception startException = null;
/*      */ 
/*      */   
/*  234 */   private transient long notifCount = 0L;
/*  235 */   private transient NotificationBroadcasterSupport notifBroadcaster = new NotificationBroadcasterSupport();
/*      */   
/*  237 */   private transient MBeanNotificationInfo[] notifInfos = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CommunicatorServer(int paramInt) throws IllegalArgumentException {
/*  251 */     switch (paramInt) {
/*      */       case 4:
/*      */         break;
/*      */       
/*      */       default:
/*  256 */         throw new IllegalArgumentException("Invalid connector Type");
/*      */     } 
/*  258 */     this.dbgTag = makeDebugTag();
/*      */   }
/*      */   
/*      */   protected Thread createMainThread() {
/*  262 */     return new Thread(this, makeThreadName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void start(long paramLong) throws CommunicationException, InterruptedException {
/*      */     boolean bool;
/*  286 */     synchronized (this.stateLock) {
/*  287 */       if (this.state == 2)
/*      */       {
/*      */         
/*  290 */         waitState(1, 60000L);
/*      */       }
/*  292 */       bool = (this.state == 1) ? true : false;
/*  293 */       if (bool) {
/*  294 */         changeState(3);
/*  295 */         this.stopRequested = false;
/*  296 */         this.interrupted = false;
/*  297 */         this.startException = null;
/*      */       } 
/*      */     } 
/*      */     
/*  301 */     if (!bool) {
/*  302 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  303 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "start", "Connector is not OFFLINE");
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  309 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  310 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "start", "--> Start connector ");
/*      */     }
/*      */ 
/*      */     
/*  314 */     this.mainThread = createMainThread();
/*      */     
/*  316 */     this.mainThread.start();
/*      */     
/*  318 */     if (paramLong > 0L) waitForStart(paramLong);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void start() {
/*      */     try {
/*  330 */       start(0L);
/*  331 */     } catch (InterruptedException interruptedException) {
/*      */       
/*  333 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  334 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "start", "interrupted", interruptedException);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stop() {
/*  348 */     synchronized (this.stateLock) {
/*  349 */       if (this.state == 1 || this.state == 2) {
/*  350 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  351 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "stop", "Connector is not ONLINE");
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*  356 */       changeState(2);
/*      */ 
/*      */ 
/*      */       
/*  360 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  361 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "stop", "Interrupt main thread");
/*      */       }
/*      */       
/*  364 */       this.stopRequested = true;
/*  365 */       if (!this.interrupted) {
/*  366 */         this.interrupted = true;
/*  367 */         this.mainThread.interrupt();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  374 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  375 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "stop", "terminateAllClient");
/*      */     }
/*      */     
/*  378 */     terminateAllClient();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  383 */     synchronized (this.stateLock) {
/*  384 */       if (this.state == 3) {
/*  385 */         changeState(1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isActive() {
/*  396 */     synchronized (this.stateLock) {
/*  397 */       return (this.state == 0);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean waitState(int paramInt, long paramLong) {
/*  435 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  436 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitState", paramInt + "(0on,1off,2st) TO=" + paramLong + " ; current state = " + 
/*      */           
/*  438 */           getStateString());
/*      */     }
/*      */     
/*  441 */     long l = 0L;
/*  442 */     if (paramLong > 0L) {
/*  443 */       l = System.currentTimeMillis() + paramLong;
/*      */     }
/*  445 */     synchronized (this.stateLock) {
/*  446 */       while (this.state != paramInt) {
/*  447 */         if (paramLong < 0L) {
/*  448 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  449 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitState", "timeOut < 0, return without wait");
/*      */           }
/*      */           
/*  452 */           return false;
/*      */         } 
/*      */         try {
/*  455 */           if (paramLong > 0L) {
/*  456 */             long l1 = l - System.currentTimeMillis();
/*  457 */             if (l1 <= 0L) {
/*  458 */               if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  459 */                 JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitState", "timed out");
/*      */               }
/*      */               
/*  462 */               return false;
/*      */             } 
/*  464 */             this.stateLock.wait(l1); continue;
/*      */           } 
/*  466 */           this.stateLock.wait();
/*      */         }
/*  468 */         catch (InterruptedException interruptedException) {
/*  469 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  470 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitState", "wait interrupted");
/*      */           }
/*      */           
/*  473 */           return (this.state == paramInt);
/*      */         } 
/*      */       } 
/*      */       
/*  477 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  478 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitState", "returning in desired state");
/*      */       }
/*      */       
/*  481 */       return true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void waitForStart(long paramLong) throws CommunicationException, InterruptedException {
/*  504 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  505 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitForStart", "Timeout=" + paramLong + " ; current state = " + 
/*      */           
/*  507 */           getStateString());
/*      */     }
/*      */     
/*  510 */     long l = System.currentTimeMillis();
/*      */     
/*  512 */     synchronized (this.stateLock) {
/*  513 */       while (this.state == 3) {
/*      */ 
/*      */         
/*  516 */         long l1 = System.currentTimeMillis() - l;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  523 */         long l2 = paramLong - l1;
/*      */ 
/*      */ 
/*      */         
/*  527 */         if (l2 < 0L) {
/*  528 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  529 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitForStart", "timeout < 0, return without wait");
/*      */           }
/*      */           
/*  532 */           throw new InterruptedException("Timeout expired");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  540 */           this.stateLock.wait(l2);
/*  541 */         } catch (InterruptedException interruptedException) {
/*  542 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  543 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitForStart", "wait interrupted");
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  551 */           if (this.state != 0) throw interruptedException;
/*      */         
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  557 */       if (this.state == 0) {
/*      */ 
/*      */         
/*  560 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  561 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitForStart", "started");
/*      */         }
/*      */         return;
/*      */       } 
/*  565 */       if (this.startException instanceof CommunicationException)
/*      */       {
/*      */ 
/*      */         
/*  569 */         throw (CommunicationException)this.startException; } 
/*  570 */       if (this.startException instanceof InterruptedException)
/*      */       {
/*      */ 
/*      */         
/*  574 */         throw (InterruptedException)this.startException; } 
/*  575 */       if (this.startException != null)
/*      */       {
/*      */ 
/*      */         
/*  579 */         throw new CommunicationException(this.startException, "Failed to start: " + this.startException);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  586 */       throw new CommunicationException("Failed to start: state is " + 
/*  587 */           getStringForState(this.state));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getState() {
/*  600 */     synchronized (this.stateLock) {
/*  601 */       return this.state;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getStateString() {
/*  613 */     return getStringForState(this.state);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHost() {
/*      */     try {
/*  624 */       this.host = InetAddress.getLocalHost().getHostName();
/*  625 */     } catch (Exception exception) {
/*  626 */       this.host = "Unknown host";
/*      */     } 
/*  628 */     return this.host;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPort() {
/*  638 */     synchronized (this.stateLock) {
/*  639 */       return this.port;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPort(int paramInt) throws IllegalStateException {
/*  654 */     synchronized (this.stateLock) {
/*  655 */       if (this.state == 0 || this.state == 3) {
/*  656 */         throw new IllegalStateException("Stop server before carrying out this operation");
/*      */       }
/*  658 */       this.port = paramInt;
/*  659 */       this.dbgTag = makeDebugTag();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String getProtocol();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getServedClientCount() {
/*  680 */     return this.servedClientCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getActiveClientCount() {
/*  691 */     return this.clientHandlerVector.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getMaxActiveClientCount() {
/*  704 */     return this.maxActiveClientCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setMaxActiveClientCount(int paramInt) throws IllegalStateException {
/*  718 */     synchronized (this.stateLock) {
/*  719 */       if (this.state == 0 || this.state == 3) {
/*  720 */         throw new IllegalStateException("Stop server before carrying out this operation");
/*      */       }
/*      */       
/*  723 */       this.maxActiveClientCount = paramInt;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void notifyClientHandlerCreated(ClientHandler paramClientHandler) {
/*  731 */     this.clientHandlerVector.addElement(paramClientHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void notifyClientHandlerDeleted(ClientHandler paramClientHandler) {
/*  738 */     this.clientHandlerVector.removeElement(paramClientHandler);
/*  739 */     notifyAll();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getBindTries() {
/*  747 */     return 50;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected long getBindSleepTime() {
/*  755 */     return 100L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void run() {
/*  771 */     byte b = 0;
/*  772 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  780 */       int i = getBindTries();
/*  781 */       long l = getBindSleepTime();
/*  782 */       while (b < i && !bool) {
/*      */ 
/*      */         
/*      */         try {
/*  786 */           doBind();
/*  787 */           bool = true;
/*  788 */         } catch (CommunicationException communicationException) {
/*  789 */           b++;
/*      */           try {
/*  791 */             Thread.sleep(l);
/*  792 */           } catch (InterruptedException interruptedException) {
/*  793 */             throw interruptedException;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  799 */       if (!bool)
/*      */       {
/*      */         
/*  802 */         doBind();
/*      */       }
/*      */     }
/*  805 */     catch (Exception exception) {
/*  806 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  807 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "run", "Got unexpected exception", exception);
/*      */       }
/*      */       
/*  810 */       synchronized (this.stateLock) {
/*  811 */         this.startException = exception;
/*  812 */         changeState(1);
/*      */       } 
/*  814 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  815 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "run", "State is OFFLINE");
/*      */       }
/*      */       
/*  818 */       doError(exception);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  826 */       changeState(0);
/*  827 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  828 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "run", "State is ONLINE");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  835 */       while (!this.stopRequested) {
/*  836 */         this.servedClientCount++;
/*  837 */         doReceive();
/*  838 */         waitIfTooManyClients();
/*  839 */         doProcess();
/*      */       } 
/*  841 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  842 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "run", "Stop has been requested");
/*      */       
/*      */       }
/*      */     }
/*  846 */     catch (InterruptedException interruptedException) {
/*  847 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  848 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "run", "Interrupt caught");
/*      */       }
/*      */       
/*  851 */       changeState(2);
/*  852 */     } catch (Exception exception) {
/*  853 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  854 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "run", "Got unexpected exception", exception);
/*      */       }
/*      */       
/*  857 */       changeState(2);
/*      */     } finally {
/*  859 */       synchronized (this.stateLock) {
/*  860 */         this.interrupted = true;
/*  861 */         Thread.interrupted();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  868 */         doUnbind();
/*  869 */         waitClientTermination();
/*  870 */         changeState(1);
/*  871 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  872 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "run", "State is OFFLINE");
/*      */         }
/*      */       }
/*  875 */       catch (Exception exception) {
/*  876 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  877 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "run", "Got unexpected exception", exception);
/*      */         }
/*      */         
/*  880 */         changeState(1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void doError(Exception paramException) throws CommunicationException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void doBind() throws CommunicationException, InterruptedException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void doReceive() throws CommunicationException, InterruptedException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void doProcess() throws CommunicationException, InterruptedException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void doUnbind() throws CommunicationException, InterruptedException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized MBeanServer getMBeanServer() {
/*  948 */     return this.topMBS;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setMBeanServer(MBeanServer paramMBeanServer) throws IllegalArgumentException, IllegalStateException {
/*  971 */     synchronized (this.stateLock) {
/*  972 */       if (this.state == 0 || this.state == 3) {
/*  973 */         throw new IllegalStateException("Stop server before carrying out this operation");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  980 */     Vector<MBeanServer> vector = new Vector();
/*  981 */     MBeanServer mBeanServer = paramMBeanServer;
/*  982 */     for (; mBeanServer != this.bottomMBS; 
/*  983 */       mBeanServer = ((MBeanServerForwarder)mBeanServer).getMBeanServer()) {
/*  984 */       if (!(mBeanServer instanceof MBeanServerForwarder))
/*  985 */         throw new IllegalArgumentException("MBeanServer argument must be MBean server where this server is registered, or an MBeanServerForwarder leading to that server"); 
/*  986 */       if (vector.contains(mBeanServer)) {
/*  987 */         throw new IllegalArgumentException("MBeanServerForwarder loop");
/*      */       }
/*  989 */       vector.addElement(mBeanServer);
/*      */     } 
/*  991 */     this.topMBS = paramMBeanServer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ObjectName getObjectName() {
/* 1001 */     return this.objectName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void changeState(int paramInt) {
/*      */     int i;
/* 1009 */     synchronized (this.stateLock) {
/* 1010 */       if (this.state == paramInt)
/*      */         return; 
/* 1012 */       i = this.state;
/* 1013 */       this.state = paramInt;
/* 1014 */       this.stateLock.notifyAll();
/*      */     } 
/* 1016 */     sendStateChangeNotification(i, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String makeDebugTag() {
/* 1023 */     return "CommunicatorServer[" + getProtocol() + ":" + getPort() + "]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String makeThreadName() {
/*      */     String str;
/* 1032 */     if (this.objectName == null) {
/* 1033 */       str = "CommunicatorServer";
/*      */     } else {
/* 1035 */       str = this.objectName.toString();
/*      */     } 
/* 1037 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void waitIfTooManyClients() throws InterruptedException {
/* 1047 */     while (getActiveClientCount() >= this.maxActiveClientCount) {
/* 1048 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1049 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitIfTooManyClients", "Waiting for a client to terminate");
/*      */       }
/*      */       
/* 1052 */       wait();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void waitClientTermination() {
/* 1060 */     int i = this.clientHandlerVector.size();
/* 1061 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER) && 
/* 1062 */       i >= 1) {
/* 1063 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitClientTermination", "waiting for " + i + " clients to terminate");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1081 */     while (!this.clientHandlerVector.isEmpty()) {
/*      */       try {
/* 1083 */         ((ClientHandler)this.clientHandlerVector.firstElement()).join();
/* 1084 */       } catch (NoSuchElementException noSuchElementException) {
/* 1085 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1086 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitClientTermination", "No elements left", noSuchElementException);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1092 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER) && 
/* 1093 */       i >= 1) {
/* 1094 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "waitClientTermination", "Ok, let's go...");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void terminateAllClient() {
/* 1104 */     int i = this.clientHandlerVector.size();
/* 1105 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER) && 
/* 1106 */       i >= 1) {
/* 1107 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "terminateAllClient", "Interrupting " + i + " clients");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1130 */     ClientHandler[] arrayOfClientHandler = this.clientHandlerVector.<ClientHandler>toArray(new ClientHandler[0]);
/* 1131 */     for (ClientHandler clientHandler : arrayOfClientHandler) {
/*      */       try {
/* 1133 */         clientHandler.interrupt();
/* 1134 */       } catch (Exception exception) {
/* 1135 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1136 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "terminateAllClient", "Failed to interrupt pending request. Ignore the exception.", exception);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1153 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1159 */     this.stateLock = new Object();
/* 1160 */     this.state = 1;
/* 1161 */     this.stopRequested = false;
/* 1162 */     this.servedClientCount = 0;
/* 1163 */     this.clientHandlerVector = new Vector<>();
/* 1164 */     this.mainThread = null;
/* 1165 */     this.notifCount = 0L;
/* 1166 */     this.notifInfos = null;
/* 1167 */     this.notifBroadcaster = new NotificationBroadcasterSupport();
/* 1168 */     this.dbgTag = makeDebugTag();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws IllegalArgumentException {
/* 1199 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 1200 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "addNotificationListener", "Adding listener " + paramNotificationListener + " with filter " + paramNotificationFilter + " and handback " + paramObject);
/*      */     }
/*      */ 
/*      */     
/* 1204 */     this.notifBroadcaster.addNotificationListener(paramNotificationListener, paramNotificationFilter, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotificationListener(NotificationListener paramNotificationListener) throws ListenerNotFoundException {
/* 1221 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 1222 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "removeNotificationListener", "Removing listener " + paramNotificationListener);
/*      */     }
/*      */     
/* 1225 */     this.notifBroadcaster.removeNotificationListener(paramNotificationListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MBeanNotificationInfo[] getNotificationInfo() {
/* 1241 */     if (this.notifInfos == null) {
/* 1242 */       this.notifInfos = new MBeanNotificationInfo[1];
/* 1243 */       String[] arrayOfString = { "jmx.attribute.change" };
/*      */       
/* 1245 */       this.notifInfos[0] = new MBeanNotificationInfo(arrayOfString, AttributeChangeNotification.class
/* 1246 */           .getName(), "Sent to notify that the value of the State attribute of this CommunicatorServer instance has changed.");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1251 */     return (MBeanNotificationInfo[])this.notifInfos.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendStateChangeNotification(int paramInt1, int paramInt2) {
/* 1259 */     String str1 = getStringForState(paramInt1);
/* 1260 */     String str2 = getStringForState(paramInt2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1265 */     String str3 = this.dbgTag + " The value of attribute State has changed from " + paramInt1 + " (" + str1 + ") to " + paramInt2 + " (" + str2 + ").";
/*      */     
/* 1267 */     this.notifCount++;
/*      */ 
/*      */ 
/*      */     
/* 1271 */     AttributeChangeNotification attributeChangeNotification = new AttributeChangeNotification(this, this.notifCount, System.currentTimeMillis(), str3, "State", "int", new Integer(paramInt1), new Integer(paramInt2));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1277 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 1278 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "sendStateChangeNotification", "Sending AttributeChangeNotification #" + this.notifCount + " with message: " + str3);
/*      */     }
/*      */ 
/*      */     
/* 1282 */     this.notifBroadcaster.sendNotification(attributeChangeNotification);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getStringForState(int paramInt) {
/* 1289 */     switch (paramInt) { case 0:
/* 1290 */         return "ONLINE";
/* 1291 */       case 3: return "STARTING";
/* 1292 */       case 1: return "OFFLINE";
/* 1293 */       case 2: return "STOPPING"; }
/* 1294 */      return "UNDEFINED";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/* 1319 */     this.objectName = paramObjectName;
/* 1320 */     synchronized (this) {
/* 1321 */       if (this.bottomMBS != null) {
/* 1322 */         throw new IllegalArgumentException("connector already registered in an MBean server");
/*      */       }
/*      */ 
/*      */       
/* 1326 */       this.topMBS = this.bottomMBS = paramMBeanServer;
/*      */     } 
/* 1328 */     this.dbgTag = makeDebugTag();
/* 1329 */     return paramObjectName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postRegister(Boolean paramBoolean) {
/* 1340 */     if (!paramBoolean.booleanValue()) {
/* 1341 */       synchronized (this) {
/* 1342 */         this.topMBS = this.bottomMBS = null;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preDeregister() throws Exception {
/* 1356 */     synchronized (this) {
/* 1357 */       this.topMBS = this.bottomMBS = null;
/*      */     } 
/* 1359 */     this.objectName = null;
/* 1360 */     int i = getState();
/* 1361 */     if (i == 0 || i == 3)
/* 1362 */       stop(); 
/*      */   }
/*      */   
/*      */   public void postDeregister() {}
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\CommunicatorServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */