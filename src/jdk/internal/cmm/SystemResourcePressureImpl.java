/*     */ package jdk.internal.cmm;
/*     */ 
/*     */ import java.lang.management.ManagementPermission;
/*     */ import javax.management.AttributeChangeNotification;
/*     */ import javax.management.MBeanNotificationInfo;
/*     */ import javax.management.MalformedObjectNameException;
/*     */ import javax.management.NotificationBroadcasterSupport;
/*     */ import javax.management.ObjectName;
/*     */ import jdk.management.cmm.SystemResourcePressureMXBean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SystemResourcePressureImpl
/*     */   extends NotificationBroadcasterSupport
/*     */   implements SystemResourcePressureMXBean
/*     */ {
/*     */   private static final int MIN_PRESSURE_LEVEL = 0;
/*     */   private static final int MAX_PRESSURE_LEVEL = 10;
/*     */   public static final String RESOURCE_PRESSURE_MXBEAN_NAME = "com.oracle.management:type=ResourcePressureMBean";
/*     */   private static final String MEM_PRESSURE_ATTRIBUTE_NAME = "MemoryPressure";
/*     */   private long notifSeqNum;
/*  37 */   private static ManagementPermission controlPermission = new ManagementPermission("control");
/*     */ 
/*     */   
/*     */   public SystemResourcePressureImpl() {
/*  41 */     super(new MBeanNotificationInfo[] { new MBeanNotificationInfo(new String[] { "jmx.attribute.change" }, AttributeChangeNotification.class
/*     */ 
/*     */             
/*  44 */             .getName(), "Notification that Memory pressure level has changed") });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getMemoryPressure() {
/*  52 */     return getVmMemoryPressure();
/*     */   }
/*     */   
/*     */   public void setMemoryPressure(int paramInt) {
/*     */     AttributeChangeNotification attributeChangeNotification;
/*  57 */     if (paramInt < 0 || paramInt > 10)
/*     */     {
/*  59 */       throw new IllegalArgumentException("Invalid pressure level: " + paramInt);
/*     */     }
/*     */     
/*  62 */     SecurityManager securityManager = System.getSecurityManager();
/*  63 */     if (securityManager != null) {
/*  64 */       securityManager.checkPermission(controlPermission);
/*     */     }
/*     */     
/*  67 */     synchronized (this) {
/*  68 */       int i = setVmMemoryPressure(paramInt);
/*  69 */       if (paramInt == i) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  76 */       attributeChangeNotification = new AttributeChangeNotification(this, ++this.notifSeqNum, System.currentTimeMillis(), "Memory pressure level change detected", "MemoryPressure", "int", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */     } 
/*     */ 
/*     */     
/*  80 */     sendNotification(attributeChangeNotification);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native int setVmMemoryPressure(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native int getVmMemoryPressure();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName getObjectName() {
/*     */     try {
/* 102 */       return ObjectName.getInstance("com.oracle.management:type=ResourcePressureMBean");
/* 103 */     } catch (MalformedObjectNameException malformedObjectNameException) {
/* 104 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\cmm\SystemResourcePressureImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */