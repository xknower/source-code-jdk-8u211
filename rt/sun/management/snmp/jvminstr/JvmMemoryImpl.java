/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.MemoryType;
/*     */ import java.lang.management.MemoryUsage;
/*     */ import java.util.Map;
/*     */ import javax.management.MBeanServer;
/*     */ import sun.management.snmp.jvmmib.EnumJvmMemoryGCCall;
/*     */ import sun.management.snmp.jvmmib.EnumJvmMemoryGCVerboseLevel;
/*     */ import sun.management.snmp.jvmmib.JvmMemoryMBean;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JvmMemoryImpl
/*     */   implements JvmMemoryMBean
/*     */ {
/*  99 */   static final EnumJvmMemoryGCCall JvmMemoryGCCallSupported = new EnumJvmMemoryGCCall("supported");
/*     */   
/* 101 */   static final EnumJvmMemoryGCCall JvmMemoryGCCallStart = new EnumJvmMemoryGCCall("start");
/*     */   
/* 103 */   static final EnumJvmMemoryGCCall JvmMemoryGCCallFailed = new EnumJvmMemoryGCCall("failed");
/*     */   
/* 105 */   static final EnumJvmMemoryGCCall JvmMemoryGCCallStarted = new EnumJvmMemoryGCCall("started");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   static final EnumJvmMemoryGCVerboseLevel JvmMemoryGCVerboseLevelVerbose = new EnumJvmMemoryGCVerboseLevel("verbose");
/*     */   
/* 123 */   static final EnumJvmMemoryGCVerboseLevel JvmMemoryGCVerboseLevelSilent = new EnumJvmMemoryGCVerboseLevel("silent");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String heapMemoryTag = "jvmMemory.getHeapMemoryUsage";
/*     */ 
/*     */ 
/*     */   
/*     */   static final String nonHeapMemoryTag = "jvmMemory.getNonHeapMemoryUsage";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JvmMemoryImpl(SnmpMib paramSnmpMib) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JvmMemoryImpl(SnmpMib paramSnmpMib, MBeanServer paramMBeanServer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MemoryUsage getMemoryUsage(MemoryType paramMemoryType) {
/* 148 */     if (paramMemoryType == MemoryType.HEAP) {
/* 149 */       return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
/*     */     }
/* 151 */     return ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
/*     */   }
/*     */ 
/*     */   
/*     */   MemoryUsage getNonHeapMemoryUsage() {
/*     */     try {
/* 157 */       Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */       
/* 159 */       if (map != null) {
/*     */         
/* 161 */         MemoryUsage memoryUsage1 = (MemoryUsage)map.get("jvmMemory.getNonHeapMemoryUsage");
/* 162 */         if (memoryUsage1 != null) {
/* 163 */           log.debug("getNonHeapMemoryUsage", "jvmMemory.getNonHeapMemoryUsage found in cache.");
/*     */           
/* 165 */           return memoryUsage1;
/*     */         } 
/*     */         
/* 168 */         MemoryUsage memoryUsage2 = getMemoryUsage(MemoryType.NON_HEAP);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 174 */         map.put("jvmMemory.getNonHeapMemoryUsage", memoryUsage2);
/* 175 */         return memoryUsage2;
/*     */       } 
/*     */ 
/*     */       
/* 179 */       log.trace("getNonHeapMemoryUsage", "ERROR: should never come here!");
/*     */       
/* 181 */       return getMemoryUsage(MemoryType.NON_HEAP);
/* 182 */     } catch (RuntimeException runtimeException) {
/* 183 */       log.trace("getNonHeapMemoryUsage", "Failed to get NonHeapMemoryUsage: " + runtimeException);
/*     */       
/* 185 */       log.debug("getNonHeapMemoryUsage", runtimeException);
/* 186 */       throw runtimeException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   MemoryUsage getHeapMemoryUsage() {
/*     */     try {
/* 193 */       Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */       
/* 195 */       if (map != null) {
/* 196 */         MemoryUsage memoryUsage1 = (MemoryUsage)map.get("jvmMemory.getHeapMemoryUsage");
/* 197 */         if (memoryUsage1 != null) {
/* 198 */           log.debug("getHeapMemoryUsage", "jvmMemory.getHeapMemoryUsage found in cache.");
/*     */           
/* 200 */           return memoryUsage1;
/*     */         } 
/*     */         
/* 203 */         MemoryUsage memoryUsage2 = getMemoryUsage(MemoryType.HEAP);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 209 */         map.put("jvmMemory.getHeapMemoryUsage", memoryUsage2);
/* 210 */         return memoryUsage2;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 215 */       log.trace("getHeapMemoryUsage", "ERROR: should never come here!");
/* 216 */       return getMemoryUsage(MemoryType.HEAP);
/* 217 */     } catch (RuntimeException runtimeException) {
/* 218 */       log.trace("getHeapMemoryUsage", "Failed to get HeapMemoryUsage: " + runtimeException);
/*     */       
/* 220 */       log.debug("getHeapMemoryUsage", runtimeException);
/* 221 */       throw runtimeException;
/*     */     } 
/*     */   }
/*     */   
/* 225 */   static final Long Long0 = new Long(0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmMemoryNonHeapMaxSize() throws SnmpStatusException {
/* 232 */     long l = getNonHeapMemoryUsage().getMax();
/* 233 */     if (l > -1L) return new Long(l); 
/* 234 */     return Long0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmMemoryNonHeapCommitted() throws SnmpStatusException {
/* 241 */     long l = getNonHeapMemoryUsage().getCommitted();
/* 242 */     if (l > -1L) return new Long(l); 
/* 243 */     return Long0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmMemoryNonHeapUsed() throws SnmpStatusException {
/* 250 */     long l = getNonHeapMemoryUsage().getUsed();
/* 251 */     if (l > -1L) return new Long(l); 
/* 252 */     return Long0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmMemoryNonHeapInitSize() throws SnmpStatusException {
/* 259 */     long l = getNonHeapMemoryUsage().getInit();
/* 260 */     if (l > -1L) return new Long(l); 
/* 261 */     return Long0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmMemoryHeapMaxSize() throws SnmpStatusException {
/* 268 */     long l = getHeapMemoryUsage().getMax();
/* 269 */     if (l > -1L) return new Long(l); 
/* 270 */     return Long0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumJvmMemoryGCCall getJvmMemoryGCCall() throws SnmpStatusException {
/* 278 */     Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */     
/* 280 */     if (map != null) {
/*     */       
/* 282 */       EnumJvmMemoryGCCall enumJvmMemoryGCCall = (EnumJvmMemoryGCCall)map.get("jvmMemory.getJvmMemoryGCCall");
/* 283 */       if (enumJvmMemoryGCCall != null) return enumJvmMemoryGCCall; 
/*     */     } 
/* 285 */     return JvmMemoryGCCallSupported;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJvmMemoryGCCall(EnumJvmMemoryGCCall paramEnumJvmMemoryGCCall) throws SnmpStatusException {
/* 293 */     if (paramEnumJvmMemoryGCCall.intValue() == JvmMemoryGCCallStart.intValue()) {
/* 294 */       Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */       
/*     */       try {
/* 297 */         ManagementFactory.getMemoryMXBean().gc();
/* 298 */         if (map != null) map.put("jvmMemory.getJvmMemoryGCCall", JvmMemoryGCCallStarted);
/*     */       
/* 300 */       } catch (Exception exception) {
/* 301 */         if (map != null) map.put("jvmMemory.getJvmMemoryGCCall", JvmMemoryGCCallFailed);
/*     */       
/*     */       } 
/*     */       return;
/*     */     } 
/* 306 */     throw new SnmpStatusException(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkJvmMemoryGCCall(EnumJvmMemoryGCCall paramEnumJvmMemoryGCCall) throws SnmpStatusException {
/* 314 */     if (paramEnumJvmMemoryGCCall.intValue() != JvmMemoryGCCallStart.intValue()) {
/* 315 */       throw new SnmpStatusException(10);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmMemoryHeapCommitted() throws SnmpStatusException {
/* 322 */     long l = getHeapMemoryUsage().getCommitted();
/* 323 */     if (l > -1L) return new Long(l); 
/* 324 */     return Long0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumJvmMemoryGCVerboseLevel getJvmMemoryGCVerboseLevel() throws SnmpStatusException {
/* 332 */     if (ManagementFactory.getMemoryMXBean().isVerbose()) {
/* 333 */       return JvmMemoryGCVerboseLevelVerbose;
/*     */     }
/* 335 */     return JvmMemoryGCVerboseLevelSilent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJvmMemoryGCVerboseLevel(EnumJvmMemoryGCVerboseLevel paramEnumJvmMemoryGCVerboseLevel) throws SnmpStatusException {
/* 343 */     if (JvmMemoryGCVerboseLevelVerbose.intValue() == paramEnumJvmMemoryGCVerboseLevel.intValue()) {
/* 344 */       ManagementFactory.getMemoryMXBean().setVerbose(true);
/*     */     } else {
/* 346 */       ManagementFactory.getMemoryMXBean().setVerbose(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkJvmMemoryGCVerboseLevel(EnumJvmMemoryGCVerboseLevel paramEnumJvmMemoryGCVerboseLevel) throws SnmpStatusException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmMemoryHeapUsed() throws SnmpStatusException {
/* 361 */     long l = getHeapMemoryUsage().getUsed();
/* 362 */     if (l > -1L) return new Long(l); 
/* 363 */     return Long0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmMemoryHeapInitSize() throws SnmpStatusException {
/* 370 */     long l = getHeapMemoryUsage().getInit();
/* 371 */     if (l > -1L) return new Long(l); 
/* 372 */     return Long0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmMemoryPendingFinalCount() throws SnmpStatusException {
/* 381 */     long l = ManagementFactory.getMemoryMXBean().getObjectPendingFinalizationCount();
/*     */     
/* 383 */     if (l > -1L) return new Long((int)l);
/*     */ 
/*     */ 
/*     */     
/* 387 */     return new Long(0L);
/*     */   }
/*     */   
/* 390 */   static final MibLogger log = new MibLogger(JvmMemoryImpl.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmMemoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */