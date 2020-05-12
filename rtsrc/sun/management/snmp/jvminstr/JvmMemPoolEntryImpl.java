/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import java.lang.management.MemoryPoolMXBean;
/*     */ import java.lang.management.MemoryType;
/*     */ import java.lang.management.MemoryUsage;
/*     */ import java.util.Map;
/*     */ import sun.management.snmp.jvmmib.EnumJvmMemPoolCollectThreshdSupport;
/*     */ import sun.management.snmp.jvmmib.EnumJvmMemPoolState;
/*     */ import sun.management.snmp.jvmmib.EnumJvmMemPoolThreshdSupport;
/*     */ import sun.management.snmp.jvmmib.EnumJvmMemPoolType;
/*     */ import sun.management.snmp.jvmmib.JvmMemPoolEntryMBean;
/*     */ import sun.management.snmp.util.JvmContextFactory;
/*     */ import sun.management.snmp.util.MibLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JvmMemPoolEntryImpl
/*     */   implements JvmMemPoolEntryMBean
/*     */ {
/*     */   protected final int jvmMemPoolIndex;
/*     */   static final String memoryTag = "jvmMemPoolEntry.getUsage";
/*     */   static final String peakMemoryTag = "jvmMemPoolEntry.getPeakUsage";
/*     */   static final String collectMemoryTag = "jvmMemPoolEntry.getCollectionUsage";
/*  71 */   static final MemoryUsage ZEROS = new MemoryUsage(0L, 0L, 0L, 0L); final String entryMemoryTag;
/*     */   final String entryPeakMemoryTag;
/*     */   final String entryCollectMemoryTag;
/*     */   final MemoryPoolMXBean pool;
/*     */   private long jvmMemPoolPeakReset;
/*     */   
/*     */   MemoryUsage getMemoryUsage() {
/*     */     try {
/*  79 */       Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */       
/*  81 */       if (map != null) {
/*     */         
/*  83 */         MemoryUsage memoryUsage1 = (MemoryUsage)map.get(this.entryMemoryTag);
/*  84 */         if (memoryUsage1 != null) {
/*  85 */           log.debug("getMemoryUsage", this.entryMemoryTag + " found in cache.");
/*     */           
/*  87 */           return memoryUsage1;
/*     */         } 
/*     */         
/*  90 */         MemoryUsage memoryUsage2 = this.pool.getUsage();
/*  91 */         if (memoryUsage2 == null) memoryUsage2 = ZEROS;
/*     */         
/*  93 */         map.put(this.entryMemoryTag, memoryUsage2);
/*  94 */         return memoryUsage2;
/*     */       } 
/*     */ 
/*     */       
/*  98 */       log.trace("getMemoryUsage", "ERROR: should never come here!");
/*  99 */       return this.pool.getUsage();
/* 100 */     } catch (RuntimeException runtimeException) {
/* 101 */       log.trace("getMemoryUsage", "Failed to get MemoryUsage: " + runtimeException);
/*     */       
/* 103 */       log.debug("getMemoryUsage", runtimeException);
/* 104 */       throw runtimeException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   MemoryUsage getPeakMemoryUsage() {
/*     */     try {
/* 111 */       Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */       
/* 113 */       if (map != null) {
/*     */         
/* 115 */         MemoryUsage memoryUsage1 = (MemoryUsage)map.get(this.entryPeakMemoryTag);
/* 116 */         if (memoryUsage1 != null) {
/* 117 */           if (log.isDebugOn()) {
/* 118 */             log.debug("getPeakMemoryUsage", this.entryPeakMemoryTag + " found in cache.");
/*     */           }
/* 120 */           return memoryUsage1;
/*     */         } 
/*     */         
/* 123 */         MemoryUsage memoryUsage2 = this.pool.getPeakUsage();
/* 124 */         if (memoryUsage2 == null) memoryUsage2 = ZEROS;
/*     */         
/* 126 */         map.put(this.entryPeakMemoryTag, memoryUsage2);
/* 127 */         return memoryUsage2;
/*     */       } 
/*     */ 
/*     */       
/* 131 */       log.trace("getPeakMemoryUsage", "ERROR: should never come here!");
/* 132 */       return ZEROS;
/* 133 */     } catch (RuntimeException runtimeException) {
/* 134 */       log.trace("getPeakMemoryUsage", "Failed to get MemoryUsage: " + runtimeException);
/*     */       
/* 136 */       log.debug("getPeakMemoryUsage", runtimeException);
/* 137 */       throw runtimeException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   MemoryUsage getCollectMemoryUsage() {
/*     */     try {
/* 144 */       Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */       
/* 146 */       if (map != null) {
/*     */         
/* 148 */         MemoryUsage memoryUsage1 = (MemoryUsage)map.get(this.entryCollectMemoryTag);
/* 149 */         if (memoryUsage1 != null) {
/* 150 */           if (log.isDebugOn()) {
/* 151 */             log.debug("getCollectMemoryUsage", this.entryCollectMemoryTag + " found in cache.");
/*     */           }
/* 153 */           return memoryUsage1;
/*     */         } 
/*     */         
/* 156 */         MemoryUsage memoryUsage2 = this.pool.getCollectionUsage();
/* 157 */         if (memoryUsage2 == null) memoryUsage2 = ZEROS;
/*     */         
/* 159 */         map.put(this.entryCollectMemoryTag, memoryUsage2);
/* 160 */         return memoryUsage2;
/*     */       } 
/*     */ 
/*     */       
/* 164 */       log.trace("getCollectMemoryUsage", "ERROR: should never come here!");
/*     */       
/* 166 */       return ZEROS;
/* 167 */     } catch (RuntimeException runtimeException) {
/* 168 */       log.trace("getPeakMemoryUsage", "Failed to get MemoryUsage: " + runtimeException);
/*     */       
/* 170 */       log.debug("getPeakMemoryUsage", runtimeException);
/* 171 */       throw runtimeException;
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
/*     */   public JvmMemPoolEntryImpl(MemoryPoolMXBean paramMemoryPoolMXBean, int paramInt) {
/* 478 */     this.jvmMemPoolPeakReset = 0L; this.pool = paramMemoryPoolMXBean; this.jvmMemPoolIndex = paramInt; this.entryMemoryTag = "jvmMemPoolEntry.getUsage." + paramInt; this.entryPeakMemoryTag = "jvmMemPoolEntry.getPeakUsage." + paramInt; this.entryCollectMemoryTag = "jvmMemPoolEntry.getCollectionUsage." + paramInt;
/*     */   }
/* 480 */   public Long getJvmMemPoolMaxSize() throws SnmpStatusException { long l = getMemoryUsage().getMax(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public Long getJvmMemPoolUsed() throws SnmpStatusException { long l = getMemoryUsage().getUsed(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public Long getJvmMemPoolInitSize() throws SnmpStatusException { long l = getMemoryUsage().getInit(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public Long getJvmMemPoolCommitted() throws SnmpStatusException { long l = getMemoryUsage().getCommitted(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public Long getJvmMemPoolPeakMaxSize() throws SnmpStatusException { long l = getPeakMemoryUsage().getMax(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public Long getJvmMemPoolPeakUsed() throws SnmpStatusException { long l = getPeakMemoryUsage().getUsed(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public Long getJvmMemPoolPeakCommitted() throws SnmpStatusException { long l = getPeakMemoryUsage().getCommitted(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public Long getJvmMemPoolCollectMaxSize() throws SnmpStatusException { long l = getCollectMemoryUsage().getMax(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public Long getJvmMemPoolCollectUsed() throws SnmpStatusException { long l = getCollectMemoryUsage().getUsed(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public Long getJvmMemPoolCollectCommitted() throws SnmpStatusException { long l = getCollectMemoryUsage().getCommitted(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public Long getJvmMemPoolThreshold() throws SnmpStatusException { if (!this.pool.isUsageThresholdSupported()) return JvmMemoryImpl.Long0;  long l = this.pool.getUsageThreshold(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public void setJvmMemPoolThreshold(Long paramLong) throws SnmpStatusException { long l = paramLong.longValue(); if (l < 0L) throw new SnmpStatusException(10);  this.pool.setUsageThreshold(l); } public void checkJvmMemPoolThreshold(Long paramLong) throws SnmpStatusException { if (!this.pool.isUsageThresholdSupported()) throw new SnmpStatusException(12);  long l = paramLong.longValue(); if (l < 0L) throw new SnmpStatusException(10);  } public EnumJvmMemPoolThreshdSupport getJvmMemPoolThreshdSupport() throws SnmpStatusException { if (this.pool.isUsageThresholdSupported()) return EnumJvmMemPoolThreshdSupported;  return EnumJvmMemPoolThreshdUnsupported; } private static final EnumJvmMemPoolState JvmMemPoolStateValid = new EnumJvmMemPoolState("valid");
/*     */   public Long getJvmMemPoolThreshdCount() throws SnmpStatusException { if (!this.pool.isUsageThresholdSupported()) return JvmMemoryImpl.Long0;  long l = this.pool.getUsageThresholdCount(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; }
/* 482 */   public Long getJvmMemPoolCollectThreshold() throws SnmpStatusException { if (!this.pool.isCollectionUsageThresholdSupported()) return JvmMemoryImpl.Long0;  long l = this.pool.getCollectionUsageThreshold(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public void setJvmMemPoolCollectThreshold(Long paramLong) throws SnmpStatusException { long l = paramLong.longValue(); if (l < 0L) throw new SnmpStatusException(10);  this.pool.setCollectionUsageThreshold(l); } public void checkJvmMemPoolCollectThreshold(Long paramLong) throws SnmpStatusException { if (!this.pool.isCollectionUsageThresholdSupported()) throw new SnmpStatusException(12);  long l = paramLong.longValue(); if (l < 0L) throw new SnmpStatusException(10);  } public EnumJvmMemPoolCollectThreshdSupport getJvmMemPoolCollectThreshdSupport() throws SnmpStatusException { if (this.pool.isCollectionUsageThresholdSupported()) return EnumJvmMemPoolCollectThreshdSupported;  return EnumJvmMemPoolCollectThreshdUnsupported; } public Long getJvmMemPoolCollectThreshdCount() throws SnmpStatusException { if (!this.pool.isCollectionUsageThresholdSupported()) return JvmMemoryImpl.Long0;  long l = this.pool.getCollectionUsageThresholdCount(); if (l > -1L) return new Long(l);  return JvmMemoryImpl.Long0; } public static EnumJvmMemPoolType jvmMemPoolType(MemoryType paramMemoryType) throws SnmpStatusException { if (paramMemoryType.equals(MemoryType.HEAP)) return EnumJvmMemPoolTypeHeap;  if (paramMemoryType.equals(MemoryType.NON_HEAP)) return EnumJvmMemPoolTypeNonHeap;  throw new SnmpStatusException(10); } public EnumJvmMemPoolType getJvmMemPoolType() throws SnmpStatusException { return jvmMemPoolType(this.pool.getType()); } public String getJvmMemPoolName() throws SnmpStatusException { return JVM_MANAGEMENT_MIB_IMPL.validJavaObjectNameTC(this.pool.getName()); } public Integer getJvmMemPoolIndex() throws SnmpStatusException { return new Integer(this.jvmMemPoolIndex); } public EnumJvmMemPoolState getJvmMemPoolState() throws SnmpStatusException { if (this.pool.isValid()) return JvmMemPoolStateValid;  return JvmMemPoolStateInvalid; } public synchronized Long getJvmMemPoolPeakReset() throws SnmpStatusException { return new Long(this.jvmMemPoolPeakReset); } public synchronized void setJvmMemPoolPeakReset(Long paramLong) throws SnmpStatusException { long l = paramLong.longValue(); if (l > this.jvmMemPoolPeakReset) { long l1 = System.currentTimeMillis(); this.pool.resetPeakUsage(); this.jvmMemPoolPeakReset = l1; log.debug("setJvmMemPoolPeakReset", "jvmMemPoolPeakReset=" + l1); }  } public void checkJvmMemPoolPeakReset(Long paramLong) throws SnmpStatusException {} private static final EnumJvmMemPoolState JvmMemPoolStateInvalid = new EnumJvmMemPoolState("invalid");
/*     */ 
/*     */   
/* 485 */   private static final EnumJvmMemPoolType EnumJvmMemPoolTypeHeap = new EnumJvmMemPoolType("heap");
/*     */   
/* 487 */   private static final EnumJvmMemPoolType EnumJvmMemPoolTypeNonHeap = new EnumJvmMemPoolType("nonheap");
/*     */ 
/*     */ 
/*     */   
/* 491 */   private static final EnumJvmMemPoolThreshdSupport EnumJvmMemPoolThreshdSupported = new EnumJvmMemPoolThreshdSupport("supported");
/*     */ 
/*     */   
/* 494 */   private static final EnumJvmMemPoolThreshdSupport EnumJvmMemPoolThreshdUnsupported = new EnumJvmMemPoolThreshdSupport("unsupported");
/*     */ 
/*     */ 
/*     */   
/* 498 */   private static final EnumJvmMemPoolCollectThreshdSupport EnumJvmMemPoolCollectThreshdSupported = new EnumJvmMemPoolCollectThreshdSupport("supported");
/*     */ 
/*     */   
/* 501 */   private static final EnumJvmMemPoolCollectThreshdSupport EnumJvmMemPoolCollectThreshdUnsupported = new EnumJvmMemPoolCollectThreshdSupport("unsupported");
/*     */ 
/*     */ 
/*     */   
/* 505 */   static final MibLogger log = new MibLogger(JvmMemPoolEntryImpl.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmMemPoolEntryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */