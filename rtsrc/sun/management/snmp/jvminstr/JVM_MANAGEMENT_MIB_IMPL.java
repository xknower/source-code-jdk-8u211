/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpCounter64;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpOidTable;
/*     */ import com.sun.jmx.snmp.SnmpParameters;
/*     */ import com.sun.jmx.snmp.SnmpPeer;
/*     */ import com.sun.jmx.snmp.SnmpString;
/*     */ import com.sun.jmx.snmp.SnmpTimeticks;
/*     */ import com.sun.jmx.snmp.SnmpVarBind;
/*     */ import com.sun.jmx.snmp.SnmpVarBindList;
/*     */ import com.sun.jmx.snmp.agent.SnmpMibTable;
/*     */ import com.sun.jmx.snmp.daemon.SnmpAdaptorServer;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.MemoryNotificationInfo;
/*     */ import java.lang.management.MemoryPoolMXBean;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.management.ListenerNotFoundException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationEmitter;
/*     */ import javax.management.NotificationListener;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import sun.management.snmp.jvmmib.JVM_MANAGEMENT_MIB;
/*     */ import sun.management.snmp.jvmmib.JVM_MANAGEMENT_MIBOidTable;
/*     */ import sun.management.snmp.jvmmib.JvmCompilationMeta;
/*     */ import sun.management.snmp.jvmmib.JvmMemoryMeta;
/*     */ import sun.management.snmp.jvmmib.JvmRuntimeMeta;
/*     */ import sun.management.snmp.jvmmib.JvmThreadingMeta;
/*     */ import sun.management.snmp.util.MibLogger;
/*     */ import sun.management.snmp.util.SnmpCachedData;
/*     */ import sun.management.snmp.util.SnmpTableHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JVM_MANAGEMENT_MIB_IMPL
/*     */   extends JVM_MANAGEMENT_MIB
/*     */ {
/*     */   private static final long serialVersionUID = -8104825586888859831L;
/*  84 */   private static final MibLogger log = new MibLogger(JVM_MANAGEMENT_MIB_IMPL.class);
/*     */   
/*     */   private static WeakReference<SnmpOidTable> tableRef;
/*     */ 
/*     */   
/*     */   public static SnmpOidTable getOidTable() {
/*  90 */     JVM_MANAGEMENT_MIBOidTable jVM_MANAGEMENT_MIBOidTable = null;
/*  91 */     if (tableRef == null) {
/*  92 */       jVM_MANAGEMENT_MIBOidTable = new JVM_MANAGEMENT_MIBOidTable();
/*  93 */       tableRef = new WeakReference<>(jVM_MANAGEMENT_MIBOidTable);
/*  94 */       return jVM_MANAGEMENT_MIBOidTable;
/*     */     } 
/*     */     
/*  97 */     SnmpOidTable snmpOidTable = tableRef.get();
/*  98 */     if (snmpOidTable == null) {
/*  99 */       snmpOidTable = new JVM_MANAGEMENT_MIBOidTable();
/* 100 */       tableRef = new WeakReference<>(snmpOidTable);
/*     */     } 
/*     */     
/* 103 */     return snmpOidTable;
/*     */   }
/*     */ 
/*     */   
/*     */   private class NotificationHandler
/*     */     implements NotificationListener
/*     */   {
/*     */     private NotificationHandler() {}
/*     */     
/*     */     public void handleNotification(Notification param1Notification, Object param1Object) {
/* 113 */       JVM_MANAGEMENT_MIB_IMPL.log.debug("handleNotification", "Received notification [ " + param1Notification
/* 114 */           .getType() + "]");
/*     */       
/* 116 */       String str = param1Notification.getType();
/* 117 */       if (str.equals("java.management.memory.threshold.exceeded") || str
/* 118 */         .equals("java.management.memory.collection.threshold.exceeded")) {
/*     */ 
/*     */         
/* 121 */         MemoryNotificationInfo memoryNotificationInfo = MemoryNotificationInfo.from((CompositeData)param1Notification.getUserData());
/* 122 */         SnmpCounter64 snmpCounter641 = new SnmpCounter64(memoryNotificationInfo.getCount());
/*     */         
/* 124 */         SnmpCounter64 snmpCounter642 = new SnmpCounter64(memoryNotificationInfo.getUsage().getUsed());
/* 125 */         SnmpString snmpString = new SnmpString(memoryNotificationInfo.getPoolName());
/*     */         
/* 127 */         SnmpOid snmpOid1 = JVM_MANAGEMENT_MIB_IMPL.this.getJvmMemPoolEntryIndex(memoryNotificationInfo.getPoolName());
/*     */         
/* 129 */         if (snmpOid1 == null) {
/* 130 */           JVM_MANAGEMENT_MIB_IMPL.log.error("handleNotification", "Error: Can't find entry index for Memory Pool: " + memoryNotificationInfo
/*     */               
/* 132 */               .getPoolName() + ": No trap emitted for " + str);
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 137 */         SnmpOid snmpOid2 = null;
/*     */         
/* 139 */         SnmpOidTable snmpOidTable = JVM_MANAGEMENT_MIB_IMPL.getOidTable();
/*     */         try {
/* 141 */           SnmpOid snmpOid3 = null;
/* 142 */           SnmpOid snmpOid4 = null;
/*     */           
/* 144 */           if (str.equals("java.management.memory.threshold.exceeded")) {
/*     */ 
/*     */             
/* 147 */             snmpOid2 = new SnmpOid(snmpOidTable.resolveVarName("jvmLowMemoryPoolUsageNotif").getOid());
/*     */ 
/*     */             
/* 150 */             snmpOid3 = new SnmpOid(snmpOidTable.resolveVarName("jvmMemPoolUsed").getOid() + "." + snmpOid1);
/*     */ 
/*     */ 
/*     */             
/* 154 */             snmpOid4 = new SnmpOid(snmpOidTable.resolveVarName("jvmMemPoolThreshdCount").getOid() + "." + snmpOid1);
/*     */           }
/* 156 */           else if (str.equals("java.management.memory.collection.threshold.exceeded")) {
/*     */ 
/*     */ 
/*     */             
/* 160 */             snmpOid2 = new SnmpOid(snmpOidTable.resolveVarName("jvmLowMemoryPoolCollectNotif").getOid());
/*     */ 
/*     */             
/* 163 */             snmpOid3 = new SnmpOid(snmpOidTable.resolveVarName("jvmMemPoolCollectUsed").getOid() + "." + snmpOid1);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 168 */             snmpOid4 = new SnmpOid(snmpOidTable.resolveVarName("jvmMemPoolCollectThreshdCount").getOid() + "." + snmpOid1);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 173 */           SnmpVarBindList snmpVarBindList = new SnmpVarBindList();
/*     */ 
/*     */           
/* 176 */           SnmpOid snmpOid5 = new SnmpOid(snmpOidTable.resolveVarName("jvmMemPoolName").getOid() + "." + snmpOid1);
/*     */ 
/*     */           
/* 179 */           SnmpVarBind snmpVarBind1 = new SnmpVarBind(snmpOid4, snmpCounter641);
/* 180 */           SnmpVarBind snmpVarBind2 = new SnmpVarBind(snmpOid3, snmpCounter642);
/* 181 */           SnmpVarBind snmpVarBind3 = new SnmpVarBind(snmpOid5, snmpString);
/*     */ 
/*     */           
/* 184 */           snmpVarBindList.add(snmpVarBind3);
/* 185 */           snmpVarBindList.add(snmpVarBind1);
/* 186 */           snmpVarBindList.add(snmpVarBind2);
/*     */           
/* 188 */           JVM_MANAGEMENT_MIB_IMPL.this.sendTrap(snmpOid2, snmpVarBindList);
/* 189 */         } catch (Exception exception) {
/* 190 */           JVM_MANAGEMENT_MIB_IMPL.log.error("handleNotification", "Exception occurred : " + exception);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   private ArrayList<NotificationTarget> notificationTargets = new ArrayList<>();
/*     */   
/*     */   private final NotificationEmitter emitter;
/*     */   
/*     */   private final NotificationHandler handler;
/*     */   private static final int DISPLAY_STRING_MAX_LENGTH = 255;
/*     */   private static final int JAVA_OBJECT_NAME_MAX_LENGTH = 1023;
/*     */   private static final int PATH_ELEMENT_MAX_LENGTH = 1023;
/*     */   private static final int ARG_VALUE_MAX_LENGTH = 1023;
/*     */   private static final int DEFAULT_CACHE_VALIDITY_PERIOD = 1000;
/*     */   
/*     */   public JVM_MANAGEMENT_MIB_IMPL() {
/* 212 */     this.handler = new NotificationHandler();
/* 213 */     this.emitter = (NotificationEmitter)ManagementFactory.getMemoryMXBean();
/* 214 */     this.emitter.addNotificationListener(this.handler, null, null);
/*     */   }
/*     */   
/*     */   private synchronized void sendTrap(SnmpOid paramSnmpOid, SnmpVarBindList paramSnmpVarBindList) {
/* 218 */     Iterator<NotificationTarget> iterator = this.notificationTargets.iterator();
/*     */     
/* 220 */     SnmpAdaptorServer snmpAdaptorServer = (SnmpAdaptorServer)getSnmpAdaptor();
/*     */     
/* 222 */     if (snmpAdaptorServer == null) {
/* 223 */       log.error("sendTrap", "Cannot send trap: adaptor is null.");
/*     */       
/*     */       return;
/*     */     } 
/* 227 */     if (!snmpAdaptorServer.isActive()) {
/* 228 */       log.config("sendTrap", "Adaptor is not active: trap not sent.");
/*     */       
/*     */       return;
/*     */     } 
/* 232 */     while (iterator.hasNext()) {
/* 233 */       NotificationTarget notificationTarget = null;
/*     */       try {
/* 235 */         notificationTarget = iterator.next();
/*     */         
/* 237 */         SnmpPeer snmpPeer = new SnmpPeer(notificationTarget.getAddress(), notificationTarget.getPort());
/* 238 */         SnmpParameters snmpParameters = new SnmpParameters();
/* 239 */         snmpParameters.setRdCommunity(notificationTarget.getCommunity());
/* 240 */         snmpPeer.setParams(snmpParameters);
/* 241 */         log.debug("handleNotification", "Sending trap to " + notificationTarget
/* 242 */             .getAddress() + ":" + notificationTarget.getPort());
/* 243 */         snmpAdaptorServer.snmpV2Trap(snmpPeer, paramSnmpOid, paramSnmpVarBindList, (SnmpTimeticks)null);
/* 244 */       } catch (Exception exception) {
/* 245 */         log.error("sendTrap", "Exception occurred while sending trap to [" + notificationTarget + "]. Exception : " + exception);
/*     */ 
/*     */         
/* 248 */         log.debug("sendTrap", exception);
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
/*     */   public synchronized void addTarget(NotificationTarget paramNotificationTarget) throws IllegalArgumentException {
/* 260 */     if (paramNotificationTarget == null) {
/* 261 */       throw new IllegalArgumentException("Target is null");
/*     */     }
/* 263 */     this.notificationTargets.add(paramNotificationTarget);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminate() {
/*     */     try {
/* 271 */       this.emitter.removeNotificationListener(this.handler);
/* 272 */     } catch (ListenerNotFoundException listenerNotFoundException) {
/* 273 */       log.error("terminate", "Listener Not found : " + listenerNotFoundException);
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
/*     */   public synchronized void addTargets(List<NotificationTarget> paramList) throws IllegalArgumentException {
/* 285 */     if (paramList == null) {
/* 286 */       throw new IllegalArgumentException("Target list is null");
/*     */     }
/* 288 */     this.notificationTargets.addAll(paramList);
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
/*     */   protected Object createJvmMemoryMBean(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 317 */     if (paramMBeanServer != null) {
/* 318 */       return new JvmMemoryImpl(this, paramMBeanServer);
/*     */     }
/* 320 */     return new JvmMemoryImpl(this);
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
/*     */   protected JvmMemoryMeta createJvmMemoryMetaNode(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 342 */     return new JvmMemoryMetaImpl(this, this.objectserver);
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
/*     */   protected JvmThreadingMeta createJvmThreadingMetaNode(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 364 */     return new JvmThreadingMetaImpl(this, this.objectserver);
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
/*     */   protected Object createJvmThreadingMBean(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 394 */     if (paramMBeanServer != null) {
/* 395 */       return new JvmThreadingImpl(this, paramMBeanServer);
/*     */     }
/* 397 */     return new JvmThreadingImpl(this);
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
/*     */   protected JvmRuntimeMeta createJvmRuntimeMetaNode(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 419 */     return new JvmRuntimeMetaImpl(this, this.objectserver);
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
/*     */   protected Object createJvmRuntimeMBean(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 449 */     if (paramMBeanServer != null) {
/* 450 */       return new JvmRuntimeImpl(this, paramMBeanServer);
/*     */     }
/* 452 */     return new JvmRuntimeImpl(this);
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
/*     */   protected JvmCompilationMeta createJvmCompilationMetaNode(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 478 */     if (ManagementFactory.getCompilationMXBean() == null) return null; 
/* 479 */     return super.createJvmCompilationMetaNode(paramString1, paramString2, paramObjectName, paramMBeanServer);
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
/*     */   protected Object createJvmCompilationMBean(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 508 */     if (paramMBeanServer != null) {
/* 509 */       return new JvmCompilationImpl(this, paramMBeanServer);
/*     */     }
/* 511 */     return new JvmCompilationImpl(this);
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
/*     */   protected Object createJvmOSMBean(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 539 */     if (paramMBeanServer != null) {
/* 540 */       return new JvmOSImpl(this, paramMBeanServer);
/*     */     }
/* 542 */     return new JvmOSImpl(this);
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
/*     */   protected Object createJvmClassLoadingMBean(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 573 */     if (paramMBeanServer != null) {
/* 574 */       return new JvmClassLoadingImpl(this, paramMBeanServer);
/*     */     }
/* 576 */     return new JvmClassLoadingImpl(this);
/*     */   }
/*     */ 
/*     */   
/*     */   static String validDisplayStringTC(String paramString) {
/* 581 */     if (paramString == null) return "";
/*     */     
/* 583 */     if (paramString.length() > 255) {
/* 584 */       return paramString.substring(0, 255);
/*     */     }
/*     */     
/* 587 */     return paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   static String validJavaObjectNameTC(String paramString) {
/* 592 */     if (paramString == null) return "";
/*     */     
/* 594 */     if (paramString.length() > 1023) {
/* 595 */       return paramString.substring(0, 1023);
/*     */     }
/*     */     
/* 598 */     return paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   static String validPathElementTC(String paramString) {
/* 603 */     if (paramString == null) return "";
/*     */     
/* 605 */     if (paramString.length() > 1023) {
/* 606 */       return paramString.substring(0, 1023);
/*     */     }
/*     */     
/* 609 */     return paramString;
/*     */   }
/*     */   
/*     */   static String validArgValueTC(String paramString) {
/* 613 */     if (paramString == null) return "";
/*     */     
/* 615 */     if (paramString.length() > 1023) {
/* 616 */       return paramString.substring(0, 1023);
/*     */     }
/*     */     
/* 619 */     return paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SnmpTableHandler getJvmMemPoolTableHandler(Object paramObject) {
/* 627 */     SnmpMibTable snmpMibTable = getRegisteredTableMeta("JvmMemPoolTable");
/* 628 */     if (!(snmpMibTable instanceof JvmMemPoolTableMetaImpl)) {
/*     */ 
/*     */       
/* 631 */       String str = (snmpMibTable == null) ? "No metadata for JvmMemPoolTable" : ("Bad metadata class for JvmMemPoolTable: " + snmpMibTable.getClass().getName());
/* 632 */       log.error("getJvmMemPoolTableHandler", str);
/* 633 */       return null;
/*     */     } 
/* 635 */     JvmMemPoolTableMetaImpl jvmMemPoolTableMetaImpl = (JvmMemPoolTableMetaImpl)snmpMibTable;
/*     */     
/* 637 */     return jvmMemPoolTableMetaImpl.getHandler(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int findInCache(SnmpTableHandler paramSnmpTableHandler, String paramString) {
/* 646 */     if (!(paramSnmpTableHandler instanceof SnmpCachedData)) {
/* 647 */       if (paramSnmpTableHandler != null) {
/*     */         
/* 649 */         String str = "Bad class for JvmMemPoolTable datas: " + paramSnmpTableHandler.getClass().getName();
/* 650 */         log.error("getJvmMemPoolEntry", str);
/*     */       } 
/* 652 */       return -1;
/*     */     } 
/*     */     
/* 655 */     SnmpCachedData snmpCachedData = (SnmpCachedData)paramSnmpTableHandler;
/* 656 */     int i = snmpCachedData.datas.length;
/* 657 */     for (byte b = 0; b < snmpCachedData.datas.length; b++) {
/* 658 */       MemoryPoolMXBean memoryPoolMXBean = (MemoryPoolMXBean)snmpCachedData.datas[b];
/* 659 */       if (paramString.equals(memoryPoolMXBean.getName())) return b; 
/*     */     } 
/* 661 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SnmpOid getJvmMemPoolEntryIndex(SnmpTableHandler paramSnmpTableHandler, String paramString) {
/* 669 */     int i = findInCache(paramSnmpTableHandler, paramString);
/* 670 */     if (i < 0) return null; 
/* 671 */     return ((SnmpCachedData)paramSnmpTableHandler).indexes[i];
/*     */   }
/*     */   
/*     */   private SnmpOid getJvmMemPoolEntryIndex(String paramString) {
/* 675 */     return getJvmMemPoolEntryIndex(getJvmMemPoolTableHandler((Object)null), paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long validity() {
/* 685 */     return 1000L;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JVM_MANAGEMENT_MIB_IMPL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */